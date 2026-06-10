package com.idea.psychiatry.modules.appointment.dto;


import com.idea.psychiatry.modules.appointment.enums.AppointmentStatus;

import java.time.Instant;
public record UpdateAppointmentRequest(
        Instant startTime,
        Instant endTime,
        AppointmentStatus status,
        String notes
) {}
