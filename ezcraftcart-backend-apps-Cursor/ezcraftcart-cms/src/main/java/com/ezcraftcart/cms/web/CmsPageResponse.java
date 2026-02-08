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
public class CmsPageResponse {

    private String id;
    private String slug;
    private String locale;
    private Integer version;
    private String status;
    private String title;
    private String excerpt;
    private Map<String, Object> content;
    private SeoMetadataResponse seo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeoMetadataResponse {
        private String title;
        private String description;
        private String keywords;
        private String canonicalUrl;
        private String ogImage;
        private String ogType;
    }
}
