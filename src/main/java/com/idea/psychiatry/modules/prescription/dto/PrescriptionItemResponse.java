package com.idea.psychiatry.modules.prescription.dto;

import java.util.UUID;

public record PrescriptionItemResponse(
        UUID itemId,
        UUID prescriptionId,
        String medicationName,
        String dosage,
        String frequency,
        String duration,
        String instructions,
        Integer sortOrder
) {}
