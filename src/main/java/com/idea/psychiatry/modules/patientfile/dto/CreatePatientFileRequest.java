package com.idea.psychiatry.modules.patientfile.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePatientFileRequest(

        @NotNull(message = "patientId is required")
        UUID patientId,

        String notes
) {}
