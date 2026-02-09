package com.ezcraftcart.payment.service;

import com.ezcraftcart.common.exception.ResourceNotFoundException;
import com.ezcraftcart.core.order.domain.Order;
import com.ezcraftcart.core.order.repository.OrderRepository;
import com.ezcraftcart.payment.domain.Payment;
import com.ezcraftcart.payment.repository.PaymentRepository;
import com.ezcraftcart.payment.web.PaymentIntentRequest;
import com.ezcraftcart.payment.web.PaymentIntentResponse;
import com.ezcraftcart.payment.web.PaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final StripeService stripeService;

    @Transactional
    public PaymentIntentResponse createPaymentIntent(String orderId, PaymentIntentRequest request, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        if (userId != null && !userId.equals(order.getUserId())) {
            throw new ResourceNotFoundException("Order", orderId);
        }

        if (order.getStatus() != Order.OrderStatus.PENDING_PAYMENT) {
            throw new IllegalArgumentException("Order is not pending payment");
        }

        Optional<Payment> existing = paymentRepository.findByOrderId(orderId);
        if (existing.isPresent() && existing.get().getStatus() == Payment.PaymentStatus.PENDING) {
            return PaymentIntentResponse.builder()
                    .paymentId(existing.get().getId())
                    .clientSecret(existing.get().getClientSecret())
                    .amount(existing.get().getAmount())
                    .currency(existing.get().getCurrency())
                    .build();
        }

        long amountCents = order.getTotalAmount()
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .longValue();

        if (amountCents < 50) {
            throw new IllegalArgumentException("Minimum payment amount is 0.50");
        }

        try {
            String clientSecret = stripeService.createPaymentIntent(
                    amountCents,
                    request.getCurrency() != null ? request.getCurrency() : "usd",
                    orderId,
                    request.getCustomerId(),
                    request.getReceiptEmail());

            Payment payment = Payment.builder()
                    .orderId(orderId)
                    .amount(order.getTotalAmount())
                    .currency(request.getCurrency() != null ? request.getCurrency() : "usd")
                    .status(Payment.PaymentStatus.PENDING)
                    .gateway("stripe")
                    .paymentIntentId(extractPaymentIntentId(clientSecret))
                    .clientSecret(clientSecret)
                    .build();
            payment = paymentRepository.save(payment);

            return PaymentIntentResponse.builder()
                    .paymentId(payment.getId())
                    .clientSecret(payment.getClientSecret())
                    .amount(payment.getAmount())
                    .currency(payment.getCurrency())
                    .build();
        } catch (StripeException e) {
            log.error("Stripe error creating payment intent: {}", e.getMessage());
            Payment failed = Payment.builder()
                    .orderId(orderId)
                    .amount(order.getTotalAmount())
                    .currency(request.getCurrency() != null ? request.getCurrency() : "usd")
                    .status(Payment.PaymentStatus.FAILED)
                    .gateway("stripe")
                    .gatewayError(e.getMessage())
                    .build();
            paymentRepository.save(failed);
            throw new RuntimeException("Payment intent creation failed: " + e.getMessage());
        }
    }

    @Transactional
    public void handlePaymentSucceeded(String paymentIntentId) {
        Payment payment = paymentRepository.findByPaymentIntentId(paymentIntentId)
                .orElse(null);
        if (payment == null) {
            log.warn("Payment not found for intent: {}", paymentIntentId);
            return;
        }
        if (payment.getStatus() == Payment.PaymentStatus.SUCCEEDED) {
            return;
        }

        payment.setStatus(Payment.PaymentStatus.SUCCEEDED);
        payment.setCompletedAt(java.time.Instant.now());
        paymentRepository.save(payment);

        orderRepository.findById(payment.getOrderId()).ifPresent(order -> {
            order.setStatus(Order.OrderStatus.CONFIRMED);
            orderRepository.save(order);
        });
    }

    @Transactional
    public void handlePaymentFailed(String paymentIntentId, String errorMessage) {
        paymentRepository.findByPaymentIntentId(paymentIntentId).ifPresent(payment -> {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            payment.setGatewayError(errorMessage);
            paymentRepository.save(payment);
        });
    }

    public PaymentResponse getPaymentByOrderId(String orderId, String userId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", orderId));
        if (userId != null) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
            if (!userId.equals(order.getUserId())) {
                throw new ResourceNotFoundException("Payment", orderId);
            }
        }
        return toResponse(payment);
    }

    public PaymentResponse getPaymentById(String id, String userId) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
        if (userId != null) {
            Order order = orderRepository.findById(payment.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order", payment.getOrderId()));
            if (!userId.equals(order.getUserId())) {
                throw new ResourceNotFoundException("Payment", id);
            }
        }
        return toResponse(payment);
    }

    private String extractPaymentIntentId(String clientSecret) {
        if (clientSecret == null) return null;
        int secretIdx = clientSecret.indexOf("_secret_");
        return secretIdx > 0 ? clientSecret.substring(0, secretIdx) : clientSecret;
    }

    private PaymentResponse toResponse(Payment p) {
        return PaymentResponse.builder()
                .id(p.getId())
                .orderId(p.getOrderId())
                .amount(p.getAmount())
                .currency(p.getCurrency())
                .status(p.getStatus().name())
                .gateway(p.getGateway())
                .createdAt(p.getCreatedAt())
                .completedAt(p.getCompletedAt())
                .build();
    }
}
