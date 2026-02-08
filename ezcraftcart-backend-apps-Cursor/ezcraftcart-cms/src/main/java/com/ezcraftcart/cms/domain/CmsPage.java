package com.ezcraftcart.cms.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "cms_pages", indexes = {
    @Index(name = "idx_cms_page_slug_locale", columnList = "slug, locale"),
    @Index(name = "idx_cms_page_status", columnList = "status"),
    @Index(name = "idx_cms_page_version", columnList = "slug, locale, version")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CmsPage {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String locale = "en";

    @Column(nullable = false)
    @Builder.Default
    private Integer version = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;

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
