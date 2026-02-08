package com.ezcraftcart.cms.web;

import com.ezcraftcart.cms.domain.ContentStatus;
import com.ezcraftcart.cms.service.CmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cms/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "CMS Admin", description = "Admin APIs for content management")
public class CmsAdminController {

    private final CmsService cmsService;

    // ---- Pages ----
    @PostMapping("/pages")
    @Operation(summary = "Create page")
    public ResponseEntity<CmsPageResponse> createPage(@Valid @RequestBody CmsPageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cmsService.createPage(request));
    }

    @PutMapping("/pages/{id}")
    @Operation(summary = "Update page")
    public ResponseEntity<CmsPageResponse> updatePage(@PathVariable String id,
                                                      @Valid @RequestBody CmsPageRequest request) {
        return ResponseEntity.ok(cmsService.updatePage(id, request));
    }

    @PatchMapping("/pages/{id}/status")
    @Operation(summary = "Transition page status (DRAFT -> IN_REVIEW -> PUBLISHED)")
    public ResponseEntity<CmsPageResponse> transitionPageStatus(@PathVariable String id,
                                                                @RequestParam ContentStatus status) {
        return ResponseEntity.ok(cmsService.transitionPageStatus(id, status));
    }

    @PostMapping("/pages/{id}/rollback")
    @Operation(summary = "Rollback page to previous version")
    public ResponseEntity<CmsPageResponse> rollbackPage(@PathVariable String id,
                                                        @RequestParam Integer version) {
        return ResponseEntity.ok(cmsService.rollbackPage(id, version));
    }

    @GetMapping("/pages")
    @Operation(summary = "List all pages (admin)")
    public ResponseEntity<List<CmsPageResponse>> listPages(@RequestParam(required = false) String locale) {
        return ResponseEntity.ok(cmsService.listAllPages(locale));
    }

    @GetMapping("/pages/{id}")
    @Operation(summary = "Get page by ID")
    public ResponseEntity<CmsPageResponse> getPage(@PathVariable String id) {
        return ResponseEntity.ok(cmsService.getPageById(id));
    }

    // ---- Banners ----
    @PostMapping("/banners")
    @Operation(summary = "Create banner")
    public ResponseEntity<CmsBannerResponse> createBanner(@Valid @RequestBody CmsBannerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cmsService.createBanner(request));
    }

    @PutMapping("/banners/{id}")
    @Operation(summary = "Update banner")
    public ResponseEntity<CmsBannerResponse> updateBanner(@PathVariable String id,
                                                          @Valid @RequestBody CmsBannerRequest request) {
        return ResponseEntity.ok(cmsService.updateBanner(id, request));
    }

    @PatchMapping("/banners/{id}/publish")
    @Operation(summary = "Publish banner")
    public ResponseEntity<CmsBannerResponse> publishBanner(@PathVariable String id) {
        return ResponseEntity.ok(cmsService.publishBanner(id));
    }

    // ---- Content Blocks ----
    @PostMapping("/blocks")
    @Operation(summary = "Create content block")
    public ResponseEntity<ContentBlockResponse> createBlock(@Valid @RequestBody ContentBlockRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cmsService.createBlock(request));
    }

    @PutMapping("/blocks/{id}")
    @Operation(summary = "Update content block")
    public ResponseEntity<ContentBlockResponse> updateBlock(@PathVariable String id,
                                                            @Valid @RequestBody ContentBlockRequest request) {
        return ResponseEntity.ok(cmsService.updateBlock(id, request));
    }

    @PatchMapping("/blocks/{id}/publish")
    @Operation(summary = "Publish content block")
    public ResponseEntity<ContentBlockResponse> publishBlock(@PathVariable String id) {
        return ResponseEntity.ok(cmsService.publishBlock(id));
    }
}
