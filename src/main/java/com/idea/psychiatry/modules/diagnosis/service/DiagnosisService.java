package com.idea.psychiatry.modules.diagnosis.service;


import com.idea.psychiatry.modules.diagnosis.entity.Diagnosis;
import com.idea.psychiatry.modules.diagnosis.enums.DiagnosisStatus;
import com.idea.psychiatry.modules.diagnosis.repository.DiagnosisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class DiagnosisService {

    private final DiagnosisRepository repository;

    public Diagnosis create(Diagnosis diagnosis) {

        diagnosis.setDiagnosedAt(Instant.now());
        diagnosis.setStatus(DiagnosisStatus.ACTIVE);

        return repository.save(diagnosis);
    }
}
