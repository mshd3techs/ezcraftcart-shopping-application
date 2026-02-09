package com.ezcraftcart.payment.repository;

import com.ezcraftcart.payment.domain.Payment;
import com.ezcraftcart.payment.domain.Payment.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findByOrderId(String orderId);

    Optional<Payment> findByPaymentIntentId(String paymentIntentId);

    List<Payment> findByOrderIdOrderByCreatedAtDesc(String orderId);
}
