package com.ezcraftcart.catalog.domain;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_product_category", columnList = "category_id"),
    @Index(name = "idx_product_featured", columnList = "featured"),
    @Index(name = "idx_product_trending", columnList = "trending")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @Column(length = 36)
    private String id;

    @PrePersist
    void generateId() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
    }

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(precision = 10, scale = 2)
    private BigDecimal originalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String subcategory;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Column(precision = 2, scale = 1)
    private BigDecimal rating;

    private Integer reviewCount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "artisan_name")),
        @AttributeOverride(name = "location", column = @Column(name = "artisan_location")),
        @AttributeOverride(name = "avatar", column = @Column(name = "artisan_avatar"))
    })
    private Artisan artisan;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private Boolean inStock = true;

    @Builder.Default
    private Boolean featured = false;

    @Builder.Default
    private Boolean trending = false;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Artisan {
        private String name;
        private String location;
        private String avatar;
    }
}
