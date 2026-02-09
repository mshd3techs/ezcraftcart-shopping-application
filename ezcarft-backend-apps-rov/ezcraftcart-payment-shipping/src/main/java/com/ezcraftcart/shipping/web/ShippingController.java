package com.ezcraftcart.shipping.web;

import com.ezcraftcart.shipping.domain.Shipment;
import com.ezcraftcart.shipping.domain.ShipmentStatus;
import com.ezcraftcart.shipping.service.ShipmentRequest;
import com.ezcraftcart.shipping.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shipping")
@RequiredArgsConstructor
public class ShippingController {
    
    private final ShippingService shippingService;
    
    @PostMapping("/shipments")
    public ResponseEntity<Shipment> createShipment(@RequestBody ShipmentRequest request) {
        Shipment shipment = shippingService.createShipment(request);
        return ResponseEntity.ok(shipment);
    }
    
    @GetMapping("/shipments/{id}")
    public ResponseEntity<Shipment> getShipment(@PathVariable Long id) {
        return ResponseEntity.ok(shippingService.getShipment(id));
    }
    
    @GetMapping("/orders/{orderId}/shipments")
    public ResponseEntity<List<Shipment>> getOrderShipments(@PathVariable Long orderId) {
        return ResponseEntity.ok(shippingService.getShipmentsByOrder(orderId));
    }
    
    @PutMapping("/shipments/{id}/status")
    public ResponseEntity<Shipment> updateStatus(
            @PathVariable Long id,
            @RequestParam ShipmentStatus status) {
        return ResponseEntity.ok(shippingService.updateShipmentStatus(id, status));
    }
    
    @PostMapping("/calculate-cost")
    public ResponseEntity<BigDecimal> calculateShippingCost(@RequestBody ShipmentRequest request) {
        return ResponseEntity.ok(shippingService.calculateShippingCost(request));
    }
}
