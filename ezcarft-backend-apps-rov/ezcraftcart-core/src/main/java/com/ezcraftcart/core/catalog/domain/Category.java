package com.ezcraftcart.core.catalog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @Column(length = 36)
    private String id;

    @PrePersist
    void generateId() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String description;

    private String image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}

