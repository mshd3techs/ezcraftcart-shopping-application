package com.ezcraftcart.core.order.service;

import com.ezcraftcart.core.cart.service.CartService;
import com.ezcraftcart.core.cart.web.CartResponse;
import com.ezcraftcart.core.catalog.domain.Product;
import com.ezcraftcart.core.catalog.repository.ProductRepository;
import com.ezcraftcart.common.exception.ResourceNotFoundException;
import com.ezcraftcart.core.order.domain.Order;
import com.ezcraftcart.core.order.domain.OrderItem;
import com.ezcraftcart.core.order.repository.OrderRepository;
import com.ezcraftcart.core.order.web.OrderRequest;
import com.ezcraftcart.core.order.web.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        CartResponse cart = cartService.getCart(request.getCartId());
        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cannot create order from empty cart");
        }

        Order order = Order.builder()
                .userId(request.getUserId())
                .cartId(request.getCartId())
                .status(Order.OrderStatus.PENDING_PAYMENT)
                .totalAmount(cart.getTotalPrice())
                .items(new ArrayList<>())
                .build();

        for (CartResponse.CartItemResponse item : cart.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", item.getProductId()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productId(product.getId())
                    .productName(product.getName())
                    .unitPrice(product.getPrice())
                    .quantity(item.getQuantity())
                    .lineTotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .build();
            order.getItems().add(orderItem);
        }

        order = orderRepository.save(order);
        cartService.clearCart(request.getCartId());

        return toOrderResponse(order);
    }

    public OrderResponse getOrder(String orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        if (userId != null && !order.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Order", orderId);
        }
        return toOrderResponse(order);
    }

    public Page<OrderResponse> getOrdersByUser(String userId, Pageable pageable) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(this::toOrderResponse);
    }

    private OrderResponse toOrderResponse(Order order) {
        List<OrderResponse.OrderItemResponse> items = order.getItems().stream()
                .map(i -> new OrderResponse.OrderItemResponse(
                        i.getProductId(),
                        i.getProductName(),
                        i.getUnitPrice(),
                        i.getQuantity(),
                        i.getLineTotal()))
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .items(items)
                .createdAt(order.getCreatedAt())
                .build();
    }
}

