package com.ezcraftcart.cms.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "cms_page_versions", indexes = {
    @Index(name = "idx_page_version_slug_locale_ver", columnList = "slug, locale, version", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageVersion {

    @Id
    @Column(length = 36)
    private String id;

    private String slug;
    private String locale;
    private Integer version;

    private String title;

    @Column(columnDefinition = "text")
    private String excerpt;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> content;

    @Embedded
    private SeoMetadata seo;

    @PrePersist
    void generateId() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
    }
}
