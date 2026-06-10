package com.idea.psychiatry.modules.appointment.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record CreateAppointmentRequest(
        @NotNull(message = "doctorId is required")
        UUID doctorId,

        @NotNull(message = "patientId is required")
        UUID patientId,

        @NotNull(message = "startTime is required")
        Instant startTime,

        @NotNull(message = "endTime is required")
        Instant endTime,

        String notes
) {
}
