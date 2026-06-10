package com.idea.psychiatry.modules.organization.entity;


import com.idea.psychiatry.shared.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "organizations")
@Getter
@Setter
public class Organization extends AuditableEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private boolean active = true;
}
