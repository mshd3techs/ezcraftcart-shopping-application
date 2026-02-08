package com.ezcraftcart.order.web;

import com.ezcraftcart.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management APIs")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create order from cart")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request,
                                                     Authentication auth) {
        if (auth != null && auth.getPrincipal() != null) {
            request.setUserId(auth.getPrincipal().toString());
        }
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String id, Authentication auth) {
        String userId = auth != null && auth.getPrincipal() != null ? auth.getPrincipal().toString() : null;
        return ResponseEntity.ok(orderService.getOrder(id, userId));
    }

    @GetMapping
    @Operation(summary = "Get user orders")
    public ResponseEntity<Page<OrderResponse>> getOrders(Authentication auth, Pageable pageable) {
        String userId = auth != null && auth.getPrincipal() != null ? auth.getPrincipal().toString() : null;
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(orderService.getOrdersByUser(userId, pageable));
    }
}
