package com.ezcraftcart.shipping.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String trackingNumber;
    
    @Column(nullable = false)
    private Long orderId;
    
    @Column(nullable = false)
    private String recipientName;
    
    @Column(nullable = false)
    private String recipientEmail;
    
    @Column(nullable = false)
    private String recipientPhone;
    
    @Embedded
    private Address shippingAddress;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShippingMethod shippingMethod;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status = ShipmentStatus.PENDING;
    
    @Column(nullable = false)
    private BigDecimal shippingCost;
    
    private String carrier; // UPS, FedEx, USPS, DHL
    
    private LocalDateTime estimatedDeliveryDate;
    
    private LocalDateTime actualDeliveryDate;
    
    private LocalDateTime shippedDate;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}