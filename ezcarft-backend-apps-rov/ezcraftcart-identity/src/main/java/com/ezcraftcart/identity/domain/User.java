package com.ezcraftcart.identity.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", indexes = @Index(name = "idx_user_email", columnList = "email", unique = true))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Builder.Default
    private Set<String> roles = new HashSet<>();

    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = true;

    @PrePersist
    void generateId() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
    }
}
