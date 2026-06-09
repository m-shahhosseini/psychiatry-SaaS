package com.idea.psychiatry.modules.doctor.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record CreateDoctorRequest(
        @NotNull
        UUID userId,

        @NotBlank
        String fullName,
        String specialization,
        String medicalLicenseNumber,
        String phoneNumber,
        String email
) {}
