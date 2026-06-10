package com.idea.psychiatry.modules.diagnosis.rules;

import com.idea.psychiatry.modules.diagnosis.enums.DiagnosisStatus;
import com.idea.psychiatry.modules.encounter.entity.Encounter;
import com.idea.psychiatry.modules.encounter.enums.EncounterStatus;
import com.idea.psychiatry.modules.encounter.repository.EncounterRepository;
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
public class DiagnosisRules {

    private final PatientFileRepository patientFileRepository;
    private final EncounterRepository encounterRepository;

    /**
     * پرونده بیمار باید OPEN باشد تا تشخیص ثبت شود.
     */
    public void validatePatientFileIsOpen(UUID patientFileId) {
        PatientFile file = patientFileRepository.findById(patientFileId)
                .orElseThrow(() -> new NotFoundException("PatientFile not found: " + patientFileId));

        if (file.getStatus() != PatientFileStatus.OPEN) {
            throw new BusinessException(
                    "Cannot add diagnosis: patient file is not OPEN"
            );
        }
    }

    /**
     * Encounter باید IN_PROGRESS یا COMPLETED باشد.
     * (نمی‌توان روی یک ویزیت SCHEDULED یا CANCELLED تشخیص ثبت کرد)
     */
    public void validateEncounterIsActive(UUID encounterId) {
        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new NotFoundException("Encounter not found: " + encounterId));

        if (encounter.getStatus() == EncounterStatus.SCHEDULED
                || encounter.getStatus() == EncounterStatus.CANCELLED) {
            throw new BusinessException(
                    "Cannot add diagnosis: encounter must be IN_PROGRESS or COMPLETED"
            );
        }
    }

    /**
     * یک کد تشخیصی نباید در همان پرونده تکرار ACTIVE داشته باشد.
     */
    public void validateNoDuplicateActiveCode(UUID patientFileId, String code, boolean exists) {
        if (exists) {
            throw new BusinessException(
                    "Diagnosis with code '" + code + "' is already ACTIVE for this patient file"
            );
        }
    }

    /**
     * قوانین انتقال وضعیت Diagnosis:
     * ACTIVE    → RESOLVED, RULED_OUT
     * RESOLVED  → ACTIVE (عود بیماری)
     * RULED_OUT → (نهایی — تغییر مجاز نیست)
     */
    public void validateStatusTransition(DiagnosisStatus current, DiagnosisStatus next) {
        if (next == null) return;

        boolean valid = switch (current) {
            case ACTIVE    -> next == DiagnosisStatus.RESOLVED || next == DiagnosisStatus.RULED_OUT;
            case RESOLVED  -> next == DiagnosisStatus.ACTIVE;
            case RULED_OUT -> false;
        };

        if (!valid) {
            throw new BusinessException(
                    "Invalid status transition: " + current + " → " + next
            );
        }
    }
}
