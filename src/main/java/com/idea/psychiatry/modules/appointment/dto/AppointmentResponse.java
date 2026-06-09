package com.idea.psychiatry.modules.appointment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponse(
        UUID appointmentId,
        UUID doctorId,
        UUID patientId,
        UUID slotId,
        LocalDateTime appointmentTime,
        String status
) {}
