package com.idea.psychiatry.modules.prescription.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreatePrescriptionRequest(

        @NotNull(message = "patientFileId is required")
        UUID patientFileId,

        @NotNull(message = "encounterId is required")
        UUID encounterId,

        @NotNull(message = "prescriberId is required")
        UUID prescriberId,

        String notes,

        @Valid
        @NotEmpty(message = "at least one prescription item is required")
        List<PrescriptionItemRequest> items
) {}
