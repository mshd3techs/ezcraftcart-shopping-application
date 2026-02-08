package com.ezcraftcart.config;

import com.ezcraftcart.cms.domain.*;
import com.ezcraftcart.cms.repository.CmsBannerRepository;
import com.ezcraftcart.cms.repository.ContentBlockRepository;
import com.ezcraftcart.cms.repository.CmsPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class CmsDataLoader implements CommandLineRunner {

    private final CmsPageRepository pageRepository;
    private final CmsBannerRepository bannerRepository;
    private final ContentBlockRepository blockRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (pageRepository.count() > 0) return;

        CmsPage homePage = CmsPage.builder()
                .slug("home")
                .locale("en")
                .version(1)
                .status(ContentStatus.PUBLISHED)
                .title("EzCraftCart - Handcrafted Artisan Marketplace")
                .excerpt("Discover unique handcrafted treasures from artisans around the world.")
                .content(Map.of(
                        "hero", Map.of(
                                "headline", "Handcrafted Artisan Marketplace",
                                "subheadline", "Discover unique treasures from around the world"
                        ),
                        "sections", java.util.List.of("featured", "categories", "testimonials")
                ))
                .seo(SeoMetadata.builder()
                        .title("EzCraftCart - Handcrafted Artisan Marketplace")
                        .description("Shop handcrafted ceramics, wooden crafts, essential oils and more from artisan makers worldwide.")
                        .keywords("handcrafted, artisan, ceramics, wooden crafts, handmade")
                        .ogType("website")
                        .build())
                .build();
        pageRepository.save(homePage);

        CmsBanner heroBanner = CmsBanner.builder()
                .slot("hero")
                .locale("en")
                .status(ContentStatus.PUBLISHED)
                .title("Handcrafted Artisan Marketplace")
                .subtitle("Discover unique treasures from around the world")
                .imageUrl("https://images.unsplash.com/photo-1565193566173-7a0ee3dbe261?w=1200&h=600&fit=crop")
                .linkUrl("/products")
                .ctaText("Shop Now")
                .sortOrder(1)
                .build();
        bannerRepository.save(heroBanner);

        ContentBlock footerBlock = ContentBlock.builder()
                .blockKey("footer")
                .locale("en")
                .status(ContentStatus.PUBLISHED)
                .markdownContent("© 2025 EzCraftCart. All rights reserved.")
                .jsonContent(Map.of(
                        "links", java.util.List.of(
                                Map.of("label", "About", "url", "/about"),
                                Map.of("label", "Contact", "url", "/contact")
                        )
                ))
                .build();
        blockRepository.save(footerBlock);
    }
}
