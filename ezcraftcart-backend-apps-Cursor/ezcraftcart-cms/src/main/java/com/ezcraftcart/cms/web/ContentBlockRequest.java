package com.ezcraftcart.cms.web;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class ContentBlockRequest {

    @NotBlank
    private String blockKey;

    private String locale = "en";

    private String markdownContent;
    private Map<String, Object> jsonContent;
}
