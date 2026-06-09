package com.idea.psychiatry.modules.organization.entity;


import com.idea.psychiatry.shared.base.AuditableEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "organizations")
public class Organization extends AuditableEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private boolean active = true;
}
