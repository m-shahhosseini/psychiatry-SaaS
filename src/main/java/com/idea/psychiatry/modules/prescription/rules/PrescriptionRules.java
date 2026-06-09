package com.idea.psychiatry.modules.prescription.rules;

import com.idea.psychiatry.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PrescriptionRules {
    /**
     * قوانین مربوط به نسخه دارویی (Prescription)
     * <p>
     * این کلاس مسئول کنترل صحت ایجاد نسخه دارویی است.
     * <p>
     * قوانین:
     * - نسخه باید حتماً به یک Encounter مرتبط باشد
     * - ایجاد نسخه بدون جلسه درمانی مجاز نیست
     */
    public void validateCreate(UUID encounterId) {

        if (encounterId == null) {
            throw new BusinessException("Encounter is required for prescription");
        }
    }
}
