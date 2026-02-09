package com.ezcraftcart.core.cart.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private String cartId;
    private String userId;
    private List<CartItemResponse> items;
    private int totalItems;
    private BigDecimal totalPrice;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItemResponse {
        private String productId;
        private String productName;
        private BigDecimal price;
        private int quantity;
        private String imageUrl;
    }
}

