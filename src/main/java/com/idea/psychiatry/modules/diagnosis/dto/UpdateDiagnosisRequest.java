package com.idea.psychiatry.modules.diagnosis.dto;

import com.idea.psychiatry.modules.diagnosis.enums.DiagnosisStatus;

public record UpdateDiagnosisRequest(
        DiagnosisStatus status,
        String description
) {}
