package com.ezcraftcart.cms.web;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class CmsBannerRequest {

    @NotBlank
    private String slot;

    private String locale = "en";

    private String title;
    private String subtitle;
    private String imageUrl;
    private String linkUrl;
    private String ctaText;
    private Map<String, Object> metadata;
    private Integer sortOrder;
}
