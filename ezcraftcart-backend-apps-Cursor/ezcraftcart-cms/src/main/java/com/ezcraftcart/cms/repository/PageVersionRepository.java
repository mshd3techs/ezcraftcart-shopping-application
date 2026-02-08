package com.ezcraftcart.cms.repository;

import com.ezcraftcart.cms.domain.PageVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageVersionRepository extends JpaRepository<PageVersion, String> {

    Optional<PageVersion> findBySlugAndLocaleAndVersion(String slug, String locale, Integer version);
}
