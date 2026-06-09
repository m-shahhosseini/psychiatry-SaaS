package com.idea.psychiatry.modules.patient.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PatientResponse(
        UUID patientId,
        UUID userId,
        String fullName,
        String nationalId,
        String phoneNumber,
        String email,
        LocalDate dateOfBirth,
        String gender,
        Boolean active
) {
}
