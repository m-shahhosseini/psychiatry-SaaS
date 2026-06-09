package com.idea.psychiatry.modules.diagnosis.rules;

import com.idea.psychiatry.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DiagnosisRules {
    /**
     * قوانین مربوط به تشخیص (Diagnosis)
     * <p>
     * این کلاس کنترل می‌کند که تشخیص‌ها فقط در بستر صحیح ثبت شوند.
     * <p>
     * قوانین:
     * - تشخیص باید به یک Encounter مرتبط باشد
     * - ثبت تشخیص بدون جلسه درمانی مجاز نیست
     */
    public void validateCreate(UUID encounterId) {

        if (encounterId == null) {
            throw new BusinessException("Diagnosis must belong to an encounter");
        }
    }
}
