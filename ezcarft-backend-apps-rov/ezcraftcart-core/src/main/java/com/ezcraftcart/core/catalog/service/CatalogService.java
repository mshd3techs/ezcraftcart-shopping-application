package com.ezcraftcart.core.catalog.service;

import com.ezcraftcart.core.catalog.domain.Category;
import com.ezcraftcart.core.catalog.domain.Product;
import com.ezcraftcart.core.catalog.repository.CategoryRepository;
import com.ezcraftcart.core.catalog.repository.ProductRepository;
import com.ezcraftcart.core.catalog.web.CategoryResponse;
import com.ezcraftcart.core.catalog.web.ProductResponse;
import com.ezcraftcart.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatalogService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Cacheable(value = "products", key = "#id", unless = "#result == null")
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        return toProductResponse(product);
    }

    @Cacheable(value = "productPages", key = "#category + '-' + #page + '-' + #size + '-' + #sortBy")
    public Page<ProductResponse> getProducts(String category, BigDecimal minPrice, BigDecimal maxPrice,
                                             Boolean inStockOnly, int page, int size, String sortBy) {
        Pageable pageable = createPageable(page, size, sortBy);
        Page<Product> products = productRepository.findWithFilters(
                category, minPrice, maxPrice, inStockOnly, pageable);
        return products.map(this::toProductResponse);
    }

    @Cacheable(value = "featuredProducts")
    public List<ProductResponse> getFeaturedProducts() {
        return productRepository.findByFeaturedTrue().stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "trendingProducts")
    public List<ProductResponse> getTrendingProducts() {
        return productRepository.findByTrendingTrue().stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "categories")
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category", slug));
        return toCategoryResponse(category);
    }

    private Pageable createPageable(int page, int size, String sortBy) {
        Sort sort = switch (sortBy != null ? sortBy : "featured") {
            case "price-low" -> Sort.by("price").ascending();
            case "price-high" -> Sort.by("price").descending();
            case "rating" -> Sort.by("rating").descending();
            case "newest" -> Sort.by("id").descending();
            default -> Sort.by("featured").descending().and(Sort.by("rating").descending());
        };
        return PageRequest.of(page, Math.min(size, 100), sort);
    }

    private ProductResponse toProductResponse(Product p) {
        ProductResponse.ArtisanResponse artisan = p.getArtisan() != null
                ? new ProductResponse.ArtisanResponse(
                p.getArtisan().getName(),
                p.getArtisan().getLocation(),
                p.getArtisan().getAvatar())
                : null;

        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .originalPrice(p.getOriginalPrice())
                .category(p.getCategory() != null ? p.getCategory().getSlug() : null)
                .subcategory(p.getSubcategory())
                .images(p.getImages() != null ? List.copyOf(p.getImages()) : List.of())
                .rating(p.getRating())
                .reviewCount(p.getReviewCount())
                .artisan(artisan)
                .tags(p.getTags() != null ? List.copyOf(p.getTags()) : List.of())
                .inStock(p.getInStock())
                .featured(p.getFeatured())
                .trending(p.getTrending())
                .build();
    }

    private CategoryResponse toCategoryResponse(Category c) {
        long count = categoryRepository.countProductsByCategoryId(c.getId());
        return CategoryResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .slug(c.getSlug())
                .description(c.getDescription())
                .image(c.getImage())
                .productCount(count)
                .build();
    }
}

