package com.ezcraftcart.shipping.service;

import com.ezcraftcart.shipping.domain.*;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByOrderId(Long orderId);
    List<Shipment> findByStatus(ShipmentStatus status);
}

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {
    
    private final ShipmentRepository shipmentRepository;
    
    @Transactional
    public Shipment createShipment(ShipmentRequest request) {
        log.info("Creating shipment for order {}", request.getOrderId());
        
        Shipment shipment = Shipment.builder()
            .trackingNumber(generateTrackingNumber())
            .orderId(request.getOrderId())
            .recipientName(request.getRecipientName())
            .recipientEmail(request.getRecipientEmail())
            .recipientPhone(request.getRecipientPhone())
            .shippingAddress(request.getShippingAddress())
            .shippingMethod(request.getShippingMethod())
            .shippingCost(calculateShippingCost(request))
            .carrier(selectCarrier(request.getShippingMethod()))
            .estimatedDeliveryDate(calculateEstimatedDelivery(request.getShippingMethod()))
            .status(ShipmentStatus.PENDING)
            .build();
        
        return shipmentRepository.save(shipment);
    }
    
    @Transactional
    public Shipment updateShipmentStatus(Long shipmentId, ShipmentStatus status) {
        log.info("Updating shipment {} to status {}", shipmentId, status);
        
        Shipment shipment = shipmentRepository.findById(shipmentId)
            .orElseThrow(() -> new RuntimeException("Shipment not found"));
        
        shipment.setStatus(status);
        
        if (status == ShipmentStatus.SHIPPED) {
            shipment.setShippedDate(LocalDateTime.now());
        } else if (status == ShipmentStatus.DELIVERED) {
            shipment.setActualDeliveryDate(LocalDateTime.now());
        }
        
        return shipmentRepository.save(shipment);
    }
    
    public List<Shipment> getShipmentsByOrder(Long orderId) {
        return shipmentRepository.findByOrderId(orderId);
    }
    
    public Shipment getShipment(Long shipmentId) {
        return shipmentRepository.findById(shipmentId)
            .orElseThrow(() -> new RuntimeException("Shipment not found"));
    }
    
    public BigDecimal calculateShippingCost(ShipmentRequest request) {
        // Simplified calculation based on shipping method
        return switch (request.getShippingMethod()) {
            case STANDARD -> new BigDecimal("5.99");
            case EXPEDITED -> new BigDecimal("12.99");
            case EXPRESS -> new BigDecimal("19.99");
            case OVERNIGHT -> new BigDecimal("29.99");
            case INTERNATIONAL -> new BigDecimal("39.99");
        };
    }
    
    private String generateTrackingNumber() {
        return "EZC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private String selectCarrier(ShippingMethod method) {
        return switch (method) {
            case STANDARD, EXPEDITED -> "USPS";
            case EXPRESS, OVERNIGHT -> "FedEx";
            case INTERNATIONAL -> "DHL";
        };
    }
    
    private LocalDateTime calculateEstimatedDelivery(ShippingMethod method) {
        LocalDateTime now = LocalDateTime.now();
        return switch (method) {
            case STANDARD -> now.plusDays(6);
            case EXPEDITED -> now.plusDays(3);
            case EXPRESS -> now.plusDays(2);
            case OVERNIGHT -> now.plusDays(1);
            case INTERNATIONAL -> now.plusDays(10);
        };
    }
}


