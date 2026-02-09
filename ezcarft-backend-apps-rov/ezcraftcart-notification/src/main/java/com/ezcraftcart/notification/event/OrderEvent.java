package com.ezcraftcart.notification.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {
    private Long orderId;
    private String customerEmail;
    private String customerName;
    private BigDecimal totalAmount;
    private String status;
    private Map<String, Object> additionalData;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class PaymentEvent {
    private Long orderId;
    private String customerEmail;
    private BigDecimal amount;
    private String status;
    private String transactionId;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ShipmentEvent {
    private Long orderId;
    private String customerEmail;
    private String trackingNumber;
    private String carrier;
    private String status;
}
