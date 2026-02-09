package com.ezcraftcart.cms.web;

import com.ezcraftcart.cms.service.CmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cms")
@RequiredArgsConstructor
@Tag(name = "CMS Public", description = "Public content APIs (cached, published only)")
public class CmsPublicController {

    private final CmsService cmsService;

    @GetMapping("/pages/{slug}")
    @Operation(summary = "Get published page by slug")
    public ResponseEntity<CmsPageResponse> getPage(
            @PathVariable String slug,
            @RequestParam(defaultValue = "en") String locale) {
        return ResponseEntity.ok(cmsService.getPublishedPage(slug, locale));
    }

    @GetMapping("/banners")
    @Operation(summary = "Get published banners")
    public ResponseEntity<List<CmsBannerResponse>> getBanners(
            @RequestParam(required = false) String slot,
            @RequestParam(defaultValue = "en") String locale) {
        return ResponseEntity.ok(cmsService.getPublishedBanners(slot, locale));
    }

    @GetMapping("/blocks/{blockKey}")
    @Operation(summary = "Get published content block")
    public ResponseEntity<ContentBlockResponse> getBlock(
            @PathVariable String blockKey,
            @RequestParam(defaultValue = "en") String locale) {
        return ResponseEntity.ok(cmsService.getPublishedBlock(blockKey, locale));
    }
}
