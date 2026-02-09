package com.ezcraftcart.core.order.domain;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @Column(length = 36)
    private String id;

    @PrePersist
    void generateId() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String productId;
    private String productName;

    @Column(precision = 10, scale = 2)
    private BigDecimal unitPrice;

    private int quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal lineTotal;
}

