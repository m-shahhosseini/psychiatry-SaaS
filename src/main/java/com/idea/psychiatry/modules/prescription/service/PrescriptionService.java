package com.idea.psychiatry.modules.prescription.service;


import com.idea.psychiatry.modules.prescription.entity.Prescription;
import com.idea.psychiatry.modules.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository repository;

    public Prescription create(Prescription prescription) {

        prescription.setPrescribedAt(Instant.now());

        return repository.save(prescription);
    }
}
