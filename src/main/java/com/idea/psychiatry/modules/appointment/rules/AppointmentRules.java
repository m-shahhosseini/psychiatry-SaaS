package com.idea.psychiatry.modules.appointment.rules;

import com.idea.psychiatry.modules.appointment.entity.Appointment;
import com.idea.psychiatry.modules.appointment.enums.AppointmentStatus;
import com.idea.psychiatry.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class AppointmentRules {
    /**
     * قوانین مربوط به نوبت‌دهی (Appointment)
     * <p>
     * این کلاس وضعیت نوبت‌ها را قبل از تبدیل به Encounter بررسی می‌کند.
     * <p>
     * قوانین:
     * - فقط نوبت‌های تایید شده (CONFIRMED) می‌توانند تبدیل به Encounter شوند
     * - نوبت نامعتبر یا لغوشده قابل استفاده نیست
     */

    public void validateForEncounter(Appointment appointment) {

        if (appointment == null) {
            throw new BusinessException("Appointment required for encounter creation");
        }

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new BusinessException("Appointment must be CONFIRMED");
        }
    }
}
