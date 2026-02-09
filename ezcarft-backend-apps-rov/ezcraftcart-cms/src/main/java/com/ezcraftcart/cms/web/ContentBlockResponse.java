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
public class ContentBlockResponse {

    private String id;
    private String blockKey;
    private String locale;
    private String status;
    private String markdownContent;
    private Map<String, Object> jsonContent;
}
