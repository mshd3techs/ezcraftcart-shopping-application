package com.ezcraftcart.cms.repository;

import com.ezcraftcart.cms.domain.ContentBlock;
import com.ezcraftcart.cms.domain.ContentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentBlockRepository extends JpaRepository<ContentBlock, String> {

    Optional<ContentBlock> findByBlockKeyAndLocaleAndStatus(String blockKey, String locale, ContentStatus status);

    List<ContentBlock> findByLocaleAndStatus(String locale, ContentStatus status);

    boolean existsByBlockKeyAndLocale(String blockKey, String locale);
}
