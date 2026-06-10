package com.idea.psychiatry.modules.diagnosis.dto;

import com.idea.psychiatry.modules.diagnosis.enums.DiagnosisStatus;

import java.time.Instant;
import java.util.UUID;

public record DiagnosisResponse(
        UUID diagnosisId,
        UUID patientFileId,
        UUID encounterId,
        UUID organizationId,
        String code,
        String title,
        String description,
        DiagnosisStatus status,
        Instant diagnosedAt
) {}
