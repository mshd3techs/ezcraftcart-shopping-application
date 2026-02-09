package com.ezcraftcart.cms.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsBannerResponse {

    private String id;
    private String slot;
    private String locale;
    private String status;
    private String title;
    private String subtitle;
    private String imageUrl;
    private String linkUrl;
    private String ctaText;
    private Map<String, Object> metadata;
    private Integer sortOrder;
}
