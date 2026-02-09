package com.ezcraftcart.core.inventory.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private Long productId;
    
    @Column(nullable = false)
    private Integer quantityAvailable = 0;
    
    @Column(nullable = false)
    private Integer quantityReserved = 0;
    
    @Column(nullable = false)
    private Integer reorderLevel = 10;
    
    @Column(nullable = false)
    private Integer reorderQuantity = 50;
    
    @Column(nullable = false)
    private String warehouseLocation;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockStatus status = StockStatus.IN_STOCK;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        updateStockStatus();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        updateStockStatus();
    }
    
    private void updateStockStatus() {
        int available = quantityAvailable - quantityReserved;
        if (available <= 0) {
            status = StockStatus.OUT_OF_STOCK;
        } else if (available <= reorderLevel) {
            status = StockStatus.LOW_STOCK;
        } else {
            status = StockStatus.IN_STOCK;
        }
    }
    
    public boolean canReserve(int quantity) {
        return (quantityAvailable - quantityReserved) >= quantity;
    }
    
    public void reserve(int quantity) {
        if (!canReserve(quantity)) {
            throw new IllegalStateException("Insufficient stock to reserve");
        }
        quantityReserved += quantity;
    }
    
    public void release(int quantity) {
        quantityReserved = Math.max(0, quantityReserved - quantity);
    }
    
    public void confirmReservation(int quantity) {
        if (quantityReserved < quantity) {
            throw new IllegalStateException("Cannot confirm more than reserved");
        }
        quantityReserved -= quantity;
        quantityAvailable -= quantity;
    }
    
    public int getAvailableQuantity() {
        return quantityAvailable - quantityReserved;
    }
}

enum StockStatus {
    IN_STOCK,
    LOW_STOCK,
    OUT_OF_STOCK
}

