package com.idea.psychiatry.modules.patientfile.repository;

import com.idea.psychiatry.modules.patientfile.entity.PatientFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientFileRepository extends JpaRepository<PatientFile, UUID> {
    List<PatientFile> findByPatientId(UUID patientId);

    Optional<PatientFile> findByFileNumber(String fileNumber);

    List<PatientFile> findAllByOrganizationId(UUID organizationId);
}
