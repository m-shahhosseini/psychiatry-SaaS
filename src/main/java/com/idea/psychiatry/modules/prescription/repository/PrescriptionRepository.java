package com.idea.psychiatry.modules.prescription.repository;

import com.idea.psychiatry.modules.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

    List<Prescription> findByEncounterId(UUID encounterId);

    List<Prescription> findByPatientFileId(UUID patientFileId);

    List<Prescription> findAllByOrganizationId(UUID organizationId);
}
