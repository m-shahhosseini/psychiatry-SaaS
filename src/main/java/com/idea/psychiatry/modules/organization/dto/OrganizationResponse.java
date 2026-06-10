package com.idea.psychiatry.modules.organization.dto;

import java.time.Instant;
import java.util.UUID;

public record OrganizationResponse(
        UUID organizationId,
        String name,
        String code,
        String address,
        String phone,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {}
