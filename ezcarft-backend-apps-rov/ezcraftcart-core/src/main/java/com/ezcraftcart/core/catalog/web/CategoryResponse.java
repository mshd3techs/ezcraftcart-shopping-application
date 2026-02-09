package com.ezcraftcart.core.catalog.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private String id;
    private String name;
    private String slug;
    private String description;
    private String image;
    private Long productCount;
}

