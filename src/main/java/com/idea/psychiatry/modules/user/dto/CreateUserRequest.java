package com.idea.psychiatry.modules.user.dto;

import com.idea.psychiatry.modules.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateUserRequest(

        @NotNull(message = "organizationId is required")
        UUID organizationId,

        @NotBlank(message = "firstName is required")
        String firstName,

        @NotBlank(message = "lastName is required")
        String lastName,

        @NotBlank(message = "email is required")
        @Email(message = "email format is invalid")
        String email,

        @NotBlank(message = "password is required")
        String password,

        @NotNull(message = "role is required")
        UserRole role
) {}
