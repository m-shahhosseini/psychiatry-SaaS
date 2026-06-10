package com.idea.psychiatry.modules.user.dto;

import com.idea.psychiatry.modules.user.enums.UserRole;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID userId,
        UUID organizationId,
        String firstName,
        String lastName,
        String email,
        UserRole role,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {}
