package com.ezcraftcart.cms.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "cms_content_blocks", indexes = {
    @Index(name = "idx_content_block_key_locale", columnList = "blockKey, locale", unique = true),
    @Index(name = "idx_content_block_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentBlock {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String blockKey;

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String locale = "en";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;

    @Column(columnDefinition = "text")
    private String markdownContent;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> jsonContent;

    @PrePersist
    void generateId() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
    }
}
