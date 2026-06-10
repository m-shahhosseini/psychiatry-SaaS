package com.idea.psychiatry.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "mobile is required")
        String mobile,

        @NotBlank(message = "password is required")
        String password
) {}
