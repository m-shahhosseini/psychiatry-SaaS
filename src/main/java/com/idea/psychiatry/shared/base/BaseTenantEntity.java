package com.idea.psychiatry.shared.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseTenantEntity extends AuditableEntity {

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;
}
