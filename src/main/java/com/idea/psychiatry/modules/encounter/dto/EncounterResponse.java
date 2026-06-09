package com.idea.psychiatry.modules.encounter.dto;

import com.idea.psychiatry.modules.encounter.enums.EncounterStatus;
import com.idea.psychiatry.modules.encounter.enums.EncounterType;

import java.time.Instant;
import java.util.UUID;

public record EncounterResponse(
        UUID encounterId,
        UUID patientFileId,
        UUID clinicianId,
        EncounterType type,
        EncounterStatus status,
        Instant encounterDate,
        String chiefComplaint,
        String notes
) {
}
