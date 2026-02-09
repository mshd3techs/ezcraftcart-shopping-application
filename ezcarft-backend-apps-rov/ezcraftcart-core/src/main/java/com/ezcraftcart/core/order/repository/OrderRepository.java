package com.ezcraftcart.core.order.repository;

import com.ezcraftcart.core.order.domain.Order;
import com.ezcraftcart.core.order.domain.Order.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    Page<Order> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    List<Order> findByUserId(String userId);
}

