package com.idea.psychiatry.modules.encounter.dto;

import com.idea.psychiatry.modules.encounter.enums.EncounterStatus;

public record UpdateEncounterRequest(
        EncounterStatus status,
        String chiefComplaint,
        String notes
) {}
