package com.idea.psychiatry.modules.prescription.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PrescriptionResponse(
        UUID prescriptionId,
        UUID patientFileId,
        UUID encounterId,
        UUID prescriberId,
        UUID organizationId,
        Instant prescribedAt,
        String notes,
        List<PrescriptionItemResponse> items
) {}