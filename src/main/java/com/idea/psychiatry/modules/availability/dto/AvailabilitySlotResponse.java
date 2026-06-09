package com.idea.psychiatry.modules.availability.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


public record AvailabilitySlotResponse(
        UUID slotId,
        LocalDate slotDate,
        LocalTime startTime,
        LocalTime endTime,
        boolean available
) {}
