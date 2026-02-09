package com.ezcraftcart.core.inventory.service;

import com.ezcraftcart.core.inventory.domain.Inventory;
import com.ezcraftcart.core.inventory.repository.InventoryRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    
    private final InventoryRepository inventoryRepository;
    
    @Cacheable(value = "inventory", key = "#productId")
    public Inventory getInventory(Long productId) {
        return inventoryRepository.findByProductId(productId)
            .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));
    }
    
    @Transactional
    @CacheEvict(value = "inventory", key = "#productId")
    @Retry(name = "inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "reserveFallback")
    public boolean reserveStock(Long productId, int quantity) {
        log.info("Reserving {} units for product {}", quantity, productId);
        
        Inventory inventory = inventoryRepository.findByProductIdForUpdate(productId)
            .orElseThrow(() -> new RuntimeException("Inventory not found"));
        
        if (!inventory.canReserve(quantity)) {
            log.warn("Insufficient stock for product {}. Available: {}, Requested: {}", 
                productId, inventory.getAvailableQuantity(), quantity);
            return false;
        }
        
        inventory.reserve(quantity);
        inventoryRepository.save(inventory);
        log.info("Successfully reserved {} units for product {}", quantity, productId);
        return true;
    }
    
    @Transactional
    @CacheEvict(value = "inventory", key = "#productId")
    public void releaseStock(Long productId, int quantity) {
        log.info("Releasing {} units for product {}", quantity, productId);
        
        Inventory inventory = inventoryRepository.findByProductIdForUpdate(productId)
            .orElseThrow(() -> new RuntimeException("Inventory not found"));
        
        inventory.release(quantity);
        inventoryRepository.save(inventory);
    }
    
    @Transactional
    @CacheEvict(value = "inventory", key = "#productId")
    public void confirmReservation(Long productId, int quantity) {
        log.info("Confirming reservation of {} units for product {}", quantity, productId);
        
        Inventory inventory = inventoryRepository.findByProductIdForUpdate(productId)
            .orElseThrow(() -> new RuntimeException("Inventory not found"));
        
        inventory.confirmReservation(quantity);
        inventoryRepository.save(inventory);
    }
    
    @Transactional
    @CacheEvict(value = "inventory", key = "#productId")
    public void addStock(Long productId, int quantity) {
        log.info("Adding {} units to product {}", quantity, productId);
        
        Inventory inventory = inventoryRepository.findByProductId(productId)
            .orElseThrow(() -> new RuntimeException("Inventory not found"));
        
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        inventoryRepository.save(inventory);
    }
    
    public Map<Long, Integer> checkAvailability(List<Long> productIds) {
        List<Inventory> inventories = inventoryRepository.findByProductIdIn(productIds);
        return inventories.stream()
            .collect(Collectors.toMap(
                Inventory::getProductId,
                Inventory::getAvailableQuantity
            ));
    }
    
    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findLowStockItems();
    }
    
    // Fallback method for circuit breaker
    private boolean reserveFallback(Long productId, int quantity, Exception ex) {
        log.error("Inventory service is unavailable. Fallback triggered for product {}", productId, ex);
        return false;
    }
}

