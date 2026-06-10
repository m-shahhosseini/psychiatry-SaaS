package com.idea.psychiatry.modules.patientfile.rules;


import com.idea.psychiatry.modules.patientfile.entity.PatientFile;
import com.idea.psychiatry.modules.patientfile.enums.PatientFileStatus;
import com.idea.psychiatry.modules.patient.repository.PatientRepository;
import com.idea.psychiatry.shared.exception.BusinessException;
import com.idea.psychiatry.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientFileRules {

    private final PatientRepository patientRepository;

    /**
     * بیمار باید وجود داشته باشد و active باشد تا پرونده باز شود.
     */
    public void validatePatientExists(UUID patientId) {
        var patient = patientRepository.findById(patientId).orElseThrow(() -> new NotFoundException("Patient not found: " + patientId));

        if (!patient.isActive()) {
            throw new BusinessException("Cannot open file: patient is not active");
        }
    }

    /**
     * یک بیمار نمی‌تواند بیش از یک پرونده OPEN داشته باشد.
     */
    public void validateNoOpenFileExists(UUID patientId, boolean hasOpenFile) {
        if (hasOpenFile) {
            throw new BusinessException("Patient already has an OPEN file. Close the existing file before opening a new one.");
        }
    }

    /**
     * عملیات روی پرونده بسته مجاز نیست.
     */
    public void validateFileIsOpen(PatientFile file) {
        if (file.getStatus() != PatientFileStatus.OPEN) {
            throw new BusinessException("Cannot perform operation on a " + file.getStatus() + " patient file");
        }
    }

    /**
     * قوانین انتقال وضعیت PatientFile:
     * OPEN   → CLOSED, ARCHIVED
     * CLOSED → OPEN (reopen), ARCHIVED
     * ARCHIVED → (نهایی — تغییر مجاز نیست)
     */
    public void validateStatusTransition(PatientFileStatus current, PatientFileStatus next) {
        if (next == null) return;

        boolean valid = switch (current) {
            case OPEN -> next == PatientFileStatus.CLOSED || next == PatientFileStatus.ARCHIVED;
            case CLOSED -> next == PatientFileStatus.OPEN || next == PatientFileStatus.ARCHIVED;
            case ARCHIVED -> false;
        };

        if (!valid) {
            throw new BusinessException("Invalid status transition: " + current + " → " + next);
        }
    }


}
