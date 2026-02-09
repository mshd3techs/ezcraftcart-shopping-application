package com.ezcraftcart.config;

import com.ezcraftcart.catalog.domain.Category;
import com.ezcraftcart.catalog.domain.Product;
import com.ezcraftcart.catalog.repository.CategoryRepository;
import com.ezcraftcart.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (categoryRepository.count() > 0) return;

        Category ceramic = Category.builder()
                .name("Ceramic Crafts")
                .slug("ceramic")
                .description("Handcrafted pottery and ceramic artworks")
                .image("https://images.unsplash.com/photo-1565193566173-7a0ee3dbe261?w=600&h=400&fit=crop")
                .build();
        ceramic = categoryRepository.save(ceramic);

        Category wooden = Category.builder()
                .name("Wooden Crafts")
                .slug("wooden")
                .description("Artisan woodwork and carved treasures")
                .image("https://images.unsplash.com/photo-1605627079912-97c3810a11a4?w=600&h=400&fit=crop")
                .build();
        wooden = categoryRepository.save(wooden);

        Category earth = Category.builder()
                .name("Earth Crafts")
                .slug("earth")
                .description("Natural clay and terracotta creations")
                .image("https://images.unsplash.com/photo-1493106641515-6b5631de4bb9?w=600&h=400&fit=crop")
                .build();
        earth = categoryRepository.save(earth);

        productRepository.save(Product.builder()
                .name("Hand-Painted Ceramic Vase Set")
                .description("Set of 3 beautifully hand-painted ceramic vases with traditional Asian floral motifs.")
                .price(new BigDecimal("145.00"))
                .category(ceramic)
                .subcategory("Vases")
                .images(List.of(
                        "https://images.unsplash.com/photo-1578749556568-bc2c40e68b61?w=600&h=600&fit=crop",
                        "https://images.unsplash.com/photo-1565193566173-7a0ee3dbe261?w=600&h=600&fit=crop"))
                .rating(new BigDecimal("4.9"))
                .reviewCount(87)
                .artisan(new Product.Artisan("Chen Ceramics", "Jingdezhen, China",
                        "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100&h=100&fit=crop"))
                .tags(List.of("ceramic", "vase", "hand-painted", "floral"))
                .inStock(true)
                .featured(true)
                .trending(true)
                .build());

        productRepository.save(Product.builder()
                .name("Hand-Carved Wooden Bowl")
                .description("Exquisitely hand-carved wooden bowl made from sustainable teak.")
                .price(new BigDecimal("95.00"))
                .originalPrice(new BigDecimal("120.00"))
                .category(wooden)
                .subcategory("Bowls")
                .images(List.of("https://images.unsplash.com/photo-1605627079912-97c3810a11a4?w=600&h=600&fit=crop"))
                .rating(new BigDecimal("4.7"))
                .reviewCount(156)
                .artisan(new Product.Artisan("Bali Woodworks", "Ubud, Indonesia",
                        "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=100&h=100&fit=crop"))
                .tags(List.of("wooden", "bowl", "teak", "handcarved"))
                .inStock(true)
                .featured(true)
                .trending(true)
                .build());

        productRepository.save(Product.builder()
                .name("Terracotta Garden Planter")
                .description("Large handmade terracotta planter with traditional geometric patterns.")
                .price(new BigDecimal("65.00"))
                .category(earth)
                .subcategory("Planters")
                .images(List.of("https://images.unsplash.com/photo-1485955900006-10f4d324d411?w=600&h=600&fit=crop"))
                .rating(new BigDecimal("4.6"))
                .reviewCount(203)
                .artisan(new Product.Artisan("Rajasthani Pottery", "Jaipur, India",
                        "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=100&h=100&fit=crop"))
                .tags(List.of("terracotta", "planter", "garden", "handmade"))
                .inStock(true)
                .trending(true)
                .build());
    }
}
