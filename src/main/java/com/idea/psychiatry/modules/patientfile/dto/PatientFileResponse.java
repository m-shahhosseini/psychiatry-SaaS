package com.idea.psychiatry.modules.patientfile.dto;
import com.idea.psychiatry.modules.patientfile.enums.PatientFileStatus;

import java.time.LocalDate;
import java.util.UUID;


public record PatientFileResponse(
        UUID patientFileId,
        UUID patientId,
        UUID organizationId,
        String fileNumber,
        PatientFileStatus status,
        LocalDate openedDate,
        LocalDate closedDate,
        String notes
) {}