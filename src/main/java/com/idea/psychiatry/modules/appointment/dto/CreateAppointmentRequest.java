package com.idea.psychiatry.modules.appointment.dto;

import java.util.UUID;

public record CreateAppointmentRequest(
        UUID slotId,
        UUID patientId
) {
}
