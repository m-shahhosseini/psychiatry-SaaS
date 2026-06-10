package com.idea.psychiatry.modules.appointment.rules;

import com.idea.psychiatry.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AppointmentRules {

    /**
     * endTime باید بعد از startTime باشد.
     */
    public void validateTimeRange(Instant startTime, Instant endTime) {
        if (!endTime.isAfter(startTime)) {
            throw new BusinessException("endTime must be after startTime");
        }
    }

    /**
     * نوبت نباید در گذشته باشد.
     */
    public void validateNotInPast(Instant startTime) {
        if (startTime.isBefore(Instant.now())) {
            throw new BusinessException("Appointment startTime cannot be in the past");
        }
    }

    /**
     * پزشک نباید در همان بازه زمانی نوبت دیگری داشته باشد.
     */
    public void validateNoOverlap(boolean hasOverlap, String doctorId) {
        if (hasOverlap) {
            throw new BusinessException(
                    "Doctor already has an appointment in the requested time range: " + doctorId
            );
        }
    }

    /**
     * قوانین انتقال وضعیت Appointment:
     * SCHEDULED  → CONFIRMED, CANCELLED
     * CONFIRMED  → COMPLETED, CANCELLED, NO_SHOW
     * COMPLETED  → (نهایی)
     * CANCELLED  → (نهایی)
     * NO_SHOW    → (نهایی)
     */
    public void validateStatusTransition(
            com.idea.psychiatry.modules.appointment.enums.AppointmentStatus current,
            com.idea.psychiatry.modules.appointment.enums.AppointmentStatus next) {
        if (next == null) return;

        boolean valid = switch (current) {
            case SCHEDULED -> next == com.idea.psychiatry.modules.appointment.enums.AppointmentStatus.CONFIRMED
                    || next == com.idea.psychiatry.modules.appointment.enums.AppointmentStatus.CANCELLED;
            case CONFIRMED -> next == com.idea.psychiatry.modules.appointment.enums.AppointmentStatus.COMPLETED
                    || next == com.idea.psychiatry.modules.appointment.enums.AppointmentStatus.CANCELLED
                    || next == com.idea.psychiatry.modules.appointment.enums.AppointmentStatus.NO_SHOW;
            case COMPLETED, CANCELLED, NO_SHOW -> false;
        };

        if (!valid) {
            throw new BusinessException(
                    "Invalid status transition: " + current + " → " + next
            );
        }
    }
}
