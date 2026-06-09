package com.idea.psychiatry.modules.encounter.rules;

import com.idea.psychiatry.modules.patientfile.entity.PatientFile;
import com.idea.psychiatry.modules.patientfile.enums.PatientFileStatus;
import com.idea.psychiatry.modules.patientfile.repository.PatientFileRepository;
import com.idea.psychiatry.shared.exception.BusinessException;
import com.idea.psychiatry.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EncounterRules {

    /**
     * قوانین مربوط به Encounter (جلسه درمانی)
     *
     * این کلاس تضمین می‌کند که جلسات درمانی فقط در شرایط معتبر ساخته شوند.
     *
     * قوانین:
     * - Encounter فقط برای پرونده باز (OPEN) قابل ایجاد است
     * - باید PatientFile معتبر وجود داشته باشد
     */
    private final PatientFileRepository patientFileRepository;

    // Encounter فقط روی PatientFile باز ساخته می‌شود
    public void validateCreate(UUID patientFileId) {

        PatientFile file = patientFileRepository.findById(patientFileId)
                .orElseThrow(() -> new NotFoundException("Patient file not found"));

        if (file.getStatus() != PatientFileStatus.OPEN) {
            throw new BusinessException("Encounter can only be created on OPEN file");
        }
    }
}
