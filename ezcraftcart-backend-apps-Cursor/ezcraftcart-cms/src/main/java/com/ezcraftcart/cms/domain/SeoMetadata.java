package com.ezcraftcart.cms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeoMetadata {

    private String title;
    private String description;

    @Column(length = 1000)
    private String keywords;

    private String canonicalUrl;
    private String ogImage;
    private String ogType;
}
