package com.idea.psychiatry.modules.organization.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record CreateOrganizationRequest(

        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "code is required")
        @Size(max = 50, message = "code must be at most 50 characters")
        @Pattern(regexp = "^[A-Z0-9_-]+$", message = "code must be uppercase letters, numbers, hyphens or underscores")
        String code,

        String address,

        String phone
) {}
