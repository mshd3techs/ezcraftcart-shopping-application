package com.ezcraftcart.cart.web;

import com.ezcraftcart.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Shopping cart APIs")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get cart")
    public ResponseEntity<CartResponse> getCart(
            @RequestHeader(value = "X-Cart-Id", required = false) String cartId) {
        String id = cartId != null ? cartId : java.util.UUID.randomUUID().toString();
        return ResponseEntity.ok(cartService.getCart(id));
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart")
    public ResponseEntity<CartResponse> addItem(
            @RequestHeader(value = "X-Cart-Id", required = false) String cartId,
            @Valid @RequestBody CartItemRequest request) {
        String id = cartId != null ? cartId : java.util.UUID.randomUUID().toString();
        return ResponseEntity.ok(cartService.addItem(id, request.getProductId(), request.getQuantity()));
    }

    @PutMapping("/items/{productId}")
    @Operation(summary = "Update item quantity")
    public ResponseEntity<CartResponse> updateQuantity(
            @RequestHeader(value = "X-Cart-Id", required = false) String cartId,
            @PathVariable String productId,
            @RequestParam int quantity) {
        String id = cartId != null ? cartId : java.util.UUID.randomUUID().toString();
        return ResponseEntity.ok(cartService.updateQuantity(id, productId, quantity));
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<CartResponse> removeItem(
            @RequestHeader(value = "X-Cart-Id", required = false) String cartId,
            @PathVariable String productId) {
        String id = cartId != null ? cartId : java.util.UUID.randomUUID().toString();
        return ResponseEntity.ok(cartService.removeItem(id, productId));
    }

    @DeleteMapping
    @Operation(summary = "Clear cart")
    public ResponseEntity<Void> clearCart(
            @RequestHeader(value = "X-Cart-Id", required = false) String cartId) {
        if (cartId != null) {
            cartService.clearCart(cartId);
        }
        return ResponseEntity.noContent().build();
    }
}
