package com.ezcraftcart.core.inventory.repository;

import com.ezcraftcart.core.inventory.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inventory i WHERE i.productId = :productId")
    Optional<Inventory> findByProductIdForUpdate(Long productId);
    
    Optional<Inventory> findByProductId(Long productId);
    
    @Query("SELECT i FROM Inventory i WHERE i.quantityAvailable - i.quantityReserved <= i.reorderLevel")
    List<Inventory> findLowStockItems();
    
    List<Inventory> findByProductIdIn(List<Long> productIds);
}

