package com.idea.psychiatry.modules.diagnosis.repository;

import com.idea.psychiatry.modules.diagnosis.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, UUID> {

    List<Diagnosis> findByEncounterId(UUID encounterId);

    List<Diagnosis> findByPatientFileId(UUID patientFileId);

    List<Diagnosis> findAllByOrganizationId(UUID organizationId);
}
