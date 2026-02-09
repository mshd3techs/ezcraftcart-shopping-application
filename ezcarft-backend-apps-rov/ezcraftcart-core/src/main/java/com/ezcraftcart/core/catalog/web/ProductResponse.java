package com.ezcraftcart.core.catalog.web;

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
public class ProductResponse {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String category;
    private String subcategory;
    private List<String> images;
    private BigDecimal rating;
    private Integer reviewCount;
    private ArtisanResponse artisan;
    private List<String> tags;
    private Boolean inStock;
    private Boolean featured;
    private Boolean trending;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArtisanResponse {
        private String name;
        private String location;
        private String avatar;
    }
}

