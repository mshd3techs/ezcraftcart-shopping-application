package com.ezcraftcart.cms.web;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class CmsPageRequest {

    @NotBlank
    private String slug;

    private String locale = "en";

    private String title;
    private String excerpt;
    private Map<String, Object> content;
    private CmsPageResponse.SeoMetadataResponse seo;
}
