package com.ezcraftcart.cms.repository;

import com.ezcraftcart.cms.domain.CmsBanner;
import com.ezcraftcart.cms.domain.ContentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CmsBannerRepository extends JpaRepository<CmsBanner, String> {

    List<CmsBanner> findBySlotAndLocaleAndStatusOrderBySortOrderAsc(String slot, String locale, ContentStatus status);

    List<CmsBanner> findByLocaleAndStatusOrderBySortOrderAsc(String locale, ContentStatus status);
}
