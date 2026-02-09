package com.ezcraftcart.core.catalog.web;

import com.ezcraftcart.core.catalog.service.CatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
@Tag(name = "Catalog", description = "Product and category APIs")
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/products/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
        return ResponseEntity.ok(catalogService.getProductById(id));
    }

    @GetMapping("/products")
    @Operation(summary = "List products with filters")
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean inStockOnly,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "featured") String sortBy) {
        return ResponseEntity.ok(catalogService.getProducts(
                category, minPrice, maxPrice, inStockOnly, page, size, sortBy));
    }

    @GetMapping("/products/featured")
    @Operation(summary = "Get featured products")
    public ResponseEntity<List<ProductResponse>> getFeaturedProducts() {
        return ResponseEntity.ok(catalogService.getFeaturedProducts());
    }

    @GetMapping("/products/trending")
    @Operation(summary = "Get trending products")
    public ResponseEntity<List<ProductResponse>> getTrendingProducts() {
        return ResponseEntity.ok(catalogService.getTrendingProducts());
    }

    @GetMapping("/categories")
    @Operation(summary = "List all categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(catalogService.getAllCategories());
    }

    @GetMapping("/categories/{slug}")
    @Operation(summary = "Get category by slug")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String slug) {
        return ResponseEntity.ok(catalogService.getCategoryBySlug(slug));
    }
}

