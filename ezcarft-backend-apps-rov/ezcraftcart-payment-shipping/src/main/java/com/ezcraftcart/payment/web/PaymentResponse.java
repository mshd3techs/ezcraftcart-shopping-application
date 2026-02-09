package com.ezcraftcart.payment.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String id;
    private String orderId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String gateway;
    private Instant createdAt;
    private Instant completedAt;
}
