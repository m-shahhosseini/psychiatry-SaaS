package com.idea.psychiatry.modules.patientfile.service;


import com.idea.psychiatry.modules.patientfile.entity.PatientFile;
import com.idea.psychiatry.modules.patientfile.enums.PatientFileStatus;
import com.idea.psychiatry.modules.patientfile.repository.PatientFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PatientFileService {

    private final PatientFileRepository repository;

    public PatientFile create(PatientFile patientFile) {

        patientFile.setStatus(
                PatientFileStatus.OPEN
        );

        patientFile.setOpenedDate(
                LocalDate.now()
        );

        return repository.save(patientFile);
    }
}
