package com.ezcraftcart.payment.web;

import com.ezcraftcart.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment intent and status APIs")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/orders/{orderId}/intent")
    @Operation(summary = "Create payment intent for order")
    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(
            @PathVariable String orderId,
            @RequestBody(required = false) PaymentIntentRequest request,
            Authentication auth) {
        String userId = auth != null && auth.getPrincipal() != null ? auth.getPrincipal().toString() : null;
        PaymentIntentRequest req = request != null ? request : new PaymentIntentRequest();
        return ResponseEntity.ok(paymentService.createPaymentIntent(orderId, req, userId));
    }

    @GetMapping("/orders/{orderId}")
    @Operation(summary = "Get payment by order ID")
    public ResponseEntity<PaymentResponse> getPaymentByOrder(
            @PathVariable String orderId,
            Authentication auth) {
        String userId = auth != null && auth.getPrincipal() != null ? auth.getPrincipal().toString() : null;
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId, userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable String id,
            Authentication auth) {
        String userId = auth != null && auth.getPrincipal() != null ? auth.getPrincipal().toString() : null;
        return ResponseEntity.ok(paymentService.getPaymentById(id, userId));
    }
}
