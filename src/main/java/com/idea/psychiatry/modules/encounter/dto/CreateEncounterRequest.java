package com.idea.psychiatry.modules.encounter.dto;
import com.idea.psychiatry.modules.encounter.enums.EncounterType;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record CreateEncounterRequest(

        @NotNull(message = "patientFileId is required")
        UUID patientFileId,

        @NotNull(message = "clinicianId is required")
        UUID clinicianId,

        @NotNull(message = "type is required")
        EncounterType type,

        @NotNull(message = "encounterDate is required")
        Instant encounterDate,

        String chiefComplaint,
        String notes
) {}

