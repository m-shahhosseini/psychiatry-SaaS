package com.idea.psychiatry.modules.prescription.rules;

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
public class PrescriptionRules {
    private final PatientFileRepository patientFileRepository;
    private final EncounterRepository encounterRepository;

    /**
     * پرونده بیمار باید OPEN باشد.
     */
    public void validatePatientFileIsOpen(UUID patientFileId) {
        PatientFile file = patientFileRepository.findById(patientFileId)
                .orElseThrow(() -> new NotFoundException("PatientFile not found: " + patientFileId));

        if (file.getStatus() != PatientFileStatus.OPEN) {
            throw new BusinessException(
                    "Cannot create prescription: patient file is not OPEN"
            );
        }
    }

    /**
     * Encounter باید IN_PROGRESS یا COMPLETED باشد.
     */
    public void validateEncounterIsActive(UUID encounterId) {
        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new NotFoundException("Encounter not found: " + encounterId));

        if (encounter.getStatus() == EncounterStatus.SCHEDULED
                || encounter.getStatus() == EncounterStatus.CANCELLED) {
            throw new BusinessException(
                    "Cannot create prescription: encounter must be IN_PROGRESS or COMPLETED"
            );
        }
    }

    /**
     * نسخه باید حداقل یک قلم دارو داشته باشد.
     */
    public void validateHasItems(int itemCount) {
        if (itemCount == 0) {
            throw new BusinessException("Prescription must have at least one medication item");
        }
    }

    /**
     * آیتم متعلق به همین نسخه باشد.
     */
    public void validateItemBelongsToPrescription(UUID itemPrescriptionId, UUID prescriptionId) {
        if (!itemPrescriptionId.equals(prescriptionId)) {
            throw new BusinessException("Item does not belong to this prescription");
        }
    }
}
