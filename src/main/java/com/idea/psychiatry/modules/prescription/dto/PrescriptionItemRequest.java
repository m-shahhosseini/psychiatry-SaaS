package com.idea.psychiatry.modules.prescription.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PrescriptionItemRequest(

        @NotBlank(message = "medicationName is required")
        String medicationName,

        @NotBlank(message = "dosage is required")
        String dosage,          // مثال: 10mg

        @NotBlank(message = "frequency is required")
        String frequency,       // مثال: 2 times a day

        @NotBlank(message = "duration is required")
        String duration,        // مثال: 7 days

        String instructions,

        @NotNull(message = "sortOrder is required")
        Integer sortOrder
) {}
