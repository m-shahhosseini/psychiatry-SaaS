package com.idea.psychiatry.modules.encounter.rules;

import com.idea.psychiatry.modules.encounter.enums.EncounterStatus;
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
    private final PatientFileRepository patientFileRepository;

    /**
     * پرونده بیمار باید OPEN باشد تا بتوان Encounter ایجاد کرد.
     */
    public void validatePatientFileIsOpen(UUID patientFileId) {
        PatientFile file = patientFileRepository.findById(patientFileId).orElseThrow(() -> new NotFoundException("PatientFile not found: " + patientFileId));

        if (file.getStatus() != PatientFileStatus.OPEN) {
            throw new BusinessException("Cannot create encounter: patient file is not OPEN");
        }
    }

    /**
     * قوانین انتقال وضعیت Encounter:
     * SCHEDULED   → IN_PROGRESS, CANCELLED
     * IN_PROGRESS → COMPLETED, CANCELLED
     * COMPLETED   → (نهایی — تغییر مجاز نیست)
     * CANCELLED   → (نهایی — تغییر مجاز نیست)
     */
    public void validateStatusTransition(EncounterStatus current, EncounterStatus next) {
        if (next == null) return;

        boolean valid = switch (current) {
            case SCHEDULED -> next == EncounterStatus.IN_PROGRESS || next == EncounterStatus.CANCELLED;
            case IN_PROGRESS -> next == EncounterStatus.COMPLETED || next == EncounterStatus.CANCELLED;
            case COMPLETED, CANCELLED -> false;
        };

        if (!valid) {
            throw new BusinessException(
                    "Invalid status transition: " + current + " → " + next
            );
        }
    }

}
