package com.ezcraftcart.core.catalog.repository;

import com.ezcraftcart.core.catalog.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findByCategorySlug(String categorySlug, Pageable pageable);

    List<Product> findByFeaturedTrue();

    List<Product> findByTrendingTrue();

    @Query("SELECT p FROM Product p WHERE (:categorySlug IS NULL OR p.category.slug = :categorySlug) " +
           "AND (:minPrice IS NULL OR p.price >= :minPrice) AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
           "AND (:inStock IS NULL OR p.inStock = :inStock)")
    Page<Product> findWithFilters(@Param("categorySlug") String categorySlug,
                                  @Param("minPrice") BigDecimal minPrice,
                                  @Param("maxPrice") BigDecimal maxPrice,
                                  @Param("inStock") Boolean inStock,
                                  Pageable pageable);
}

