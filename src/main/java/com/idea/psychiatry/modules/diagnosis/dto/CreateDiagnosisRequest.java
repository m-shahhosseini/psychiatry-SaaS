package com.idea.psychiatry.modules.diagnosis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateDiagnosisRequest(

        @NotNull(message = "patientFileId is required")
        UUID patientFileId,

        @NotNull(message = "encounterId is required")
        UUID encounterId,

        @NotBlank(message = "code is required")
        String code,          // ICD-10 or DSM — e.g. F32.1

        @NotBlank(message = "title is required")
        String title,

        String description
) {}
