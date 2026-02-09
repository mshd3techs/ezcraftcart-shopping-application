package com.ezcraftcart.cms.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "cms_banners", indexes = {
    @Index(name = "idx_cms_banner_slot_locale", columnList = "slot, locale"),
    @Index(name = "idx_cms_banner_status", columnList = "status"),
    @Index(name = "idx_cms_banner_sort", columnList = "sortOrder")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CmsBanner {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String slot;

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String locale = "en";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;

    private String title;
    private String subtitle;

    @Column(length = 1000)
    private String imageUrl;

    @Column(length = 1000)
    private String linkUrl;

    @Column(length = 50)
    private String ctaText;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata;

    private Integer sortOrder;

    @PrePersist
    void generateId() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
    }
}
