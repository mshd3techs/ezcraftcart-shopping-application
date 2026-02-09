package com.ezcraftcart.core.inventory.web;

import com.ezcraftcart.core.inventory.domain.Inventory;
import com.ezcraftcart.core.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    
    private final InventoryService inventoryService;
    
    @GetMapping("/products/{productId}")
    public ResponseEntity<Inventory> getInventory(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventory(productId));
    }
    
    @PostMapping("/products/{productId}/reserve")
    public ResponseEntity<Boolean> reserveStock(
            @PathVariable Long productId,
            @RequestParam int quantity) {
        boolean reserved = inventoryService.reserveStock(productId, quantity);
        return ResponseEntity.ok(reserved);
    }
    
    @PostMapping("/products/{productId}/release")
    public ResponseEntity<Void> releaseStock(
            @PathVariable Long productId,
            @RequestParam int quantity) {
        inventoryService.releaseStock(productId, quantity);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/products/{productId}/confirm")
    public ResponseEntity<Void> confirmReservation(
            @PathVariable Long productId,
            @RequestParam int quantity) {
        inventoryService.confirmReservation(productId, quantity);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/products/{productId}/add")
    public ResponseEntity<Void> addStock(
            @PathVariable Long productId,
            @RequestParam int quantity) {
        inventoryService.addStock(productId, quantity);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/check-availability")
    public ResponseEntity<Map<Long, Integer>> checkAvailability(
            @RequestBody List<Long> productIds) {
        return ResponseEntity.ok(inventoryService.checkAvailability(productIds));
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<List<Inventory>> getLowStockItems() {
        return ResponseEntity.ok(inventoryService.getLowStockItems());
    }
}

