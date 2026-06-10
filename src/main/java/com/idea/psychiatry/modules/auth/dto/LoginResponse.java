package com.idea.psychiatry.modules.auth.dto;

import com.idea.psychiatry.modules.user.enums.UserRole;

import java.util.UUID;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Long expiresIn,
        UUID userId,
        UUID organizationId,
        String email,
        UserRole role
) {}

