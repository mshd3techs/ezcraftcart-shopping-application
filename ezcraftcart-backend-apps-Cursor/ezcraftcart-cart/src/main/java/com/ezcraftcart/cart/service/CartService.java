package com.ezcraftcart.cart.service;

import com.ezcraftcart.catalog.domain.Product;
import com.ezcraftcart.catalog.repository.ProductRepository;
import com.ezcraftcart.cart.web.CartResponse;
import com.ezcraftcart.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final String CART_KEY_PREFIX = "cart:";
    private static final long CART_TTL_DAYS = 7;

    private final RedisTemplate<String, String> redisTemplate;
    private final ProductRepository productRepository;

    public CartResponse getCart(String cartId) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        Map<String, String> items = hashOps.entries(CART_KEY_PREFIX + cartId);
        return buildCartResponse(cartId, items);
    }

    public CartResponse addItem(String cartId, String productId, int quantity) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String key = CART_KEY_PREFIX + cartId;
        String existing = hashOps.get(key, productId);
        int newQty = existing != null ? Integer.parseInt(existing) + quantity : quantity;
        hashOps.put(key, productId, String.valueOf(newQty));
        redisTemplate.expire(key, CART_TTL_DAYS, TimeUnit.DAYS);

        return getCart(cartId);
    }

    public CartResponse updateQuantity(String cartId, String productId, int quantity) {
        if (quantity <= 0) {
            return removeItem(cartId, productId);
        }

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String key = CART_KEY_PREFIX + cartId;
        if (!Boolean.TRUE.equals(hashOps.hasKey(key, productId))) {
            throw new ResourceNotFoundException("Cart item", productId);
        }
        hashOps.put(key, productId, String.valueOf(quantity));
        redisTemplate.expire(key, CART_TTL_DAYS, TimeUnit.DAYS);
        return getCart(cartId);
    }

    public CartResponse removeItem(String cartId, String productId) {
        redisTemplate.opsForHash().delete(CART_KEY_PREFIX + cartId, productId);
        return getCart(cartId);
    }

    public void clearCart(String cartId) {
        redisTemplate.delete(CART_KEY_PREFIX + cartId);
    }

    private CartResponse buildCartResponse(String cartId, Map<String, String> items) {
        List<CartResponse.CartItemResponse> itemResponses = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalItems = 0;

        for (Map.Entry<String, String> entry : items.entrySet()) {
            String productId = entry.getKey();
            int qty = Integer.parseInt(entry.getValue());

            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(qty));
                totalPrice = totalPrice.add(lineTotal);
                totalItems += qty;

                String imageUrl = product.getImages() != null && !product.getImages().isEmpty()
                        ? product.getImages().get(0) : null;

                itemResponses.add(new CartResponse.CartItemResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        qty,
                        imageUrl
                ));
            }
        }

        return CartResponse.builder()
                .cartId(cartId)
                .items(itemResponses)
                .totalItems(totalItems)
                .totalPrice(totalPrice)
                .build();
    }
}
