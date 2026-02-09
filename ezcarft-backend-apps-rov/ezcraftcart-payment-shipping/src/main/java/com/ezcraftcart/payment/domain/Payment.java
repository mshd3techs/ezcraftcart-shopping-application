package com.ezcraftcart.payment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_payment_order", columnList = "orderId"),
    @Index(name = "idx_payment_intent", columnList = "paymentIntentId"),
    @Index(name = "idx_payment_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(nullable = false)
    @Builder.Default
    private String gateway = "stripe";

    private String paymentIntentId;

    private String clientSecret;

    private Instant createdAt;

    private Instant completedAt;

    private String gatewayError;

    @PrePersist
    void generateId() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
        if (createdAt == null) createdAt = Instant.now();
    }

    public enum PaymentStatus {
        PENDING,
        PROCESSING,
        SUCCEEDED,
        FAILED,
        CANCELLED,
        REFUNDED
    }
}
