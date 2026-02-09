package com.ezcraftcart.cms.service;

import com.ezcraftcart.cms.domain.ContentBlock;
import com.ezcraftcart.cms.domain.ContentStatus;
import com.ezcraftcart.cms.domain.CmsBanner;
import com.ezcraftcart.cms.domain.CmsPage;
import com.ezcraftcart.cms.domain.PageVersion;
import com.ezcraftcart.cms.domain.SeoMetadata;
import com.ezcraftcart.cms.repository.CmsBannerRepository;
import com.ezcraftcart.cms.repository.CmsPageRepository;
import com.ezcraftcart.cms.repository.ContentBlockRepository;
import com.ezcraftcart.cms.repository.PageVersionRepository;
import com.ezcraftcart.cms.web.*;
import com.ezcraftcart.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CmsService {

    private final CmsPageRepository pageRepository;
    private final CmsBannerRepository bannerRepository;
    private final ContentBlockRepository blockRepository;
    private final PageVersionRepository pageVersionRepository;

    // ---- Public API (cached, PUBLISHED only) ----

    @Cacheable(value = "cmsPages", key = "#slug + '-' + #locale")
    public CmsPageResponse getPublishedPage(String slug, String locale) {
        CmsPage page = pageRepository.findBySlugAndLocaleAndStatus(slug, locale, ContentStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException("Page", slug));
        return toPageResponse(page);
    }

    @Cacheable(value = "cmsBanners", key = "#slot + '-' + #locale")
    public List<CmsBannerResponse> getPublishedBanners(String slot, String locale) {
        List<CmsBanner> banners = slot != null
                ? bannerRepository.findBySlotAndLocaleAndStatusOrderBySortOrderAsc(slot, locale, ContentStatus.PUBLISHED)
                : bannerRepository.findByLocaleAndStatusOrderBySortOrderAsc(locale, ContentStatus.PUBLISHED);
        return banners.stream().map(this::toBannerResponse).collect(Collectors.toList());
    }

    @Cacheable(value = "cmsBlocks", key = "#blockKey + '-' + #locale")
    public ContentBlockResponse getPublishedBlock(String blockKey, String locale) {
        ContentBlock block = blockRepository.findByBlockKeyAndLocaleAndStatus(blockKey, locale, ContentStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException("Content block", blockKey));
        return toBlockResponse(block);
    }

    // ---- Admin API (CRUD + workflow) ----

    @Transactional
    @CacheEvict(value = {"cmsPages", "cmsBanners", "cmsBlocks"}, allEntries = true)
    public CmsPageResponse createPage(CmsPageRequest request) {
        if (pageRepository.existsBySlugAndLocale(request.getSlug(), request.getLocale())) {
            throw new IllegalArgumentException("Page already exists for slug: " + request.getSlug());
        }
        CmsPage page = CmsPage.builder()
                .slug(request.getSlug())
                .locale(request.getLocale())
                .version(1)
                .status(ContentStatus.DRAFT)
                .title(request.getTitle())
                .excerpt(request.getExcerpt())
                .content(request.getContent())
                .seo(toSeo(request.getSeo()))
                .build();
        page = pageRepository.save(page);
        return toPageResponse(page);
    }

    @Transactional
    @CacheEvict(value = {"cmsPages", "cmsBanners", "cmsBlocks"}, allEntries = true)
    public CmsPageResponse updatePage(String id, CmsPageRequest request) {
        CmsPage page = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page", id));
        page.setTitle(request.getTitle());
        page.setExcerpt(request.getExcerpt());
        page.setContent(request.getContent());
        page.setSeo(toSeo(request.getSeo()));
        page = pageRepository.save(page);
        return toPageResponse(page);
    }

    @Transactional
    @CacheEvict(value = {"cmsPages", "cmsBanners", "cmsBlocks"}, allEntries = true)
    public CmsPageResponse transitionPageStatus(String id, ContentStatus newStatus) {
        CmsPage page = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page", id));
        validateTransition(page.getStatus(), newStatus);
        page.setStatus(newStatus);
        if (newStatus == ContentStatus.PUBLISHED) {
            int newVersion = page.getVersion() + 1;
            pageVersionRepository.save(PageVersion.builder()
                    .slug(page.getSlug())
                    .locale(page.getLocale())
                    .version(page.getVersion())
                    .title(page.getTitle())
                    .excerpt(page.getExcerpt())
                    .content(page.getContent())
                    .seo(page.getSeo())
                    .build());
            page.setVersion(newVersion);
        }
        page = pageRepository.save(page);
        return toPageResponse(page);
    }

    @Transactional
    @CacheEvict(value = {"cmsPages", "cmsBanners", "cmsBlocks"}, allEntries = true)
    public CmsPageResponse rollbackPage(String id, Integer version) {
        CmsPage current = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page", id));
        PageVersion previous = pageVersionRepository.findBySlugAndLocaleAndVersion(
                        current.getSlug(), current.getLocale(), version)
                .orElseThrow(() -> new ResourceNotFoundException("Page version", version.toString()));
        current.setTitle(previous.getTitle());
        current.setExcerpt(previous.getExcerpt());
        current.setContent(previous.getContent());
        current.setSeo(previous.getSeo());
        current.setStatus(ContentStatus.DRAFT);
        current = pageRepository.save(current);
        return toPageResponse(current);
    }

    public CmsPageResponse getPageById(String id) {
        CmsPage page = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page", id));
        return toPageResponse(page);
    }

    public List<CmsPageResponse> listAllPages(String locale) {
        List<CmsPage> pages = locale != null
                ? pageRepository.findByLocale(locale)
                : pageRepository.findAll();
        return pages.stream().map(this::toPageResponse).collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = {"cmsPages", "cmsBanners", "cmsBlocks"}, allEntries = true)
    public CmsBannerResponse createBanner(CmsBannerRequest request) {
        CmsBanner banner = CmsBanner.builder()
                .slot(request.getSlot())
                .locale(request.getLocale())
                .status(ContentStatus.DRAFT)
                .title(request.getTitle())
                .subtitle(request.getSubtitle())
                .imageUrl(request.getImageUrl())
                .linkUrl(request.getLinkUrl())
                .ctaText(request.getCtaText())
                .metadata(request.getMetadata())
                .sortOrder(request.getSortOrder())
                .build();
        banner = bannerRepository.save(banner);
        return toBannerResponse(banner);
    }

    @Transactional
    @CacheEvict(value = {"cmsPages", "cmsBanners", "cmsBlocks"}, allEntries = true)
    public CmsBannerResponse updateBanner(String id, CmsBannerRequest request) {
        CmsBanner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banner", id));
        banner.setTitle(request.getTitle());
        banner.setSubtitle(request.getSubtitle());
        banner.setImageUrl(request.getImageUrl());
        banner.setLinkUrl(request.getLinkUrl());
        banner.setCtaText(request.getCtaText());
        banner.setMetadata(request.getMetadata());
        banner.setSortOrder(request.getSortOrder());
        banner = bannerRepository.save(banner);
        return toBannerResponse(banner);
    }

    @Transactional
    @CacheEvict(value = {"cmsPages", "cmsBanners", "cmsBlocks"}, allEntries = true)
    public CmsBannerResponse publishBanner(String id) {
        CmsBanner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Banner", id));
        banner.setStatus(ContentStatus.PUBLISHED);
        banner = bannerRepository.save(banner);
        return toBannerResponse(banner);
    }

    @Transactional
    @CacheEvict(value = {"cmsPages", "cmsBanners", "cmsBlocks"}, allEntries = true)
    public ContentBlockResponse createBlock(ContentBlockRequest request) {
        if (blockRepository.existsByBlockKeyAndLocale(request.getBlockKey(), request.getLocale())) {
            throw new IllegalArgumentException("Block already exists: " + request.getBlockKey());
        }
        ContentBlock block = ContentBlock.builder()
                .blockKey(request.getBlockKey())
                .locale(request.getLocale())
                .status(ContentStatus.DRAFT)
                .markdownContent(request.getMarkdownContent())
                .jsonContent(request.getJsonContent())
                .build();
        block = blockRepository.save(block);
        return toBlockResponse(block);
    }

    @Transactional
    @CacheEvict(value = {"cmsPages", "cmsBanners", "cmsBlocks"}, allEntries = true)
    public ContentBlockResponse updateBlock(String id, ContentBlockRequest request) {
        ContentBlock block = blockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content block", id));
        block.setMarkdownContent(request.getMarkdownContent());
        block.setJsonContent(request.getJsonContent());
        block = blockRepository.save(block);
        return toBlockResponse(block);
    }

    @Transactional
    @CacheEvict(value = {"cmsPages", "cmsBanners", "cmsBlocks"}, allEntries = true)
    public ContentBlockResponse publishBlock(String id) {
        ContentBlock block = blockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content block", id));
        block.setStatus(ContentStatus.PUBLISHED);
        block = blockRepository.save(block);
        return toBlockResponse(block);
    }

    private void validateTransition(ContentStatus current, ContentStatus target) {
        if (current == target) return;
        boolean valid = switch (current) {
            case DRAFT -> target == ContentStatus.IN_REVIEW || target == ContentStatus.PUBLISHED;
            case IN_REVIEW -> target == ContentStatus.DRAFT || target == ContentStatus.PUBLISHED;
            case PUBLISHED -> target == ContentStatus.DRAFT;
        };
        if (!valid) {
            throw new IllegalArgumentException("Invalid status transition: " + current + " -> " + target);
        }
    }

    private SeoMetadata toSeo(CmsPageResponse.SeoMetadataResponse r) {
        if (r == null) return null;
        return SeoMetadata.builder()
                .title(r.getTitle())
                .description(r.getDescription())
                .keywords(r.getKeywords())
                .canonicalUrl(r.getCanonicalUrl())
                .ogImage(r.getOgImage())
                .ogType(r.getOgType())
                .build();
    }

    private CmsPageResponse toPageResponse(CmsPage p) {
        CmsPageResponse.SeoMetadataResponse seo = p.getSeo() != null
                ? new CmsPageResponse.SeoMetadataResponse(
                p.getSeo().getTitle(), p.getSeo().getDescription(), p.getSeo().getKeywords(),
                p.getSeo().getCanonicalUrl(), p.getSeo().getOgImage(), p.getSeo().getOgType())
                : null;
        return CmsPageResponse.builder()
                .id(p.getId())
                .slug(p.getSlug())
                .locale(p.getLocale())
                .version(p.getVersion())
                .status(p.getStatus().name())
                .title(p.getTitle())
                .excerpt(p.getExcerpt())
                .content(p.getContent())
                .seo(seo)
                .build();
    }

    private CmsBannerResponse toBannerResponse(CmsBanner b) {
        return CmsBannerResponse.builder()
                .id(b.getId())
                .slot(b.getSlot())
                .locale(b.getLocale())
                .status(b.getStatus().name())
                .title(b.getTitle())
                .subtitle(b.getSubtitle())
                .imageUrl(b.getImageUrl())
                .linkUrl(b.getLinkUrl())
                .ctaText(b.getCtaText())
                .metadata(b.getMetadata())
                .sortOrder(b.getSortOrder())
                .build();
    }

    private ContentBlockResponse toBlockResponse(ContentBlock b) {
        return ContentBlockResponse.builder()
                .id(b.getId())
                .blockKey(b.getBlockKey())
                .locale(b.getLocale())
                .status(b.getStatus().name())
                .markdownContent(b.getMarkdownContent())
                .jsonContent(b.getJsonContent())
                .build();
    }
}
