package com.ezcraftcart.cms.repository;

import com.ezcraftcart.cms.domain.CmsPage;
import com.ezcraftcart.cms.domain.ContentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CmsPageRepository extends JpaRepository<CmsPage, String> {

    Optional<CmsPage> findBySlugAndLocaleAndStatus(String slug, String locale, ContentStatus status);

    Optional<CmsPage> findBySlugAndLocaleAndVersion(String slug, String locale, Integer version);

    List<CmsPage> findByStatus(ContentStatus status);

    List<CmsPage> findByLocaleAndStatus(String locale, ContentStatus status);

    List<CmsPage> findByLocale(String locale);

    boolean existsBySlugAndLocale(String slug, String locale);
}
