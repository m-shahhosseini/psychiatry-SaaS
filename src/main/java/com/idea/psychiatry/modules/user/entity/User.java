package com.idea.psychiatry.modules.user.entity;


import com.idea.psychiatry.modules.user.enums.UserRole;
import com.idea.psychiatry.shared.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_org", columnList = "organization_id")
        }
)
@Getter
@Setter
public class User extends AuditableEntity {

    @Column(nullable = false)
    private UUID organizationId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean active = true;
}
