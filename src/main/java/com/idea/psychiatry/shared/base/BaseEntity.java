package com.idea.psychiatry.shared.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;
}