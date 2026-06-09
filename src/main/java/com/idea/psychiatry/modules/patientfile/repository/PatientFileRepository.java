package com.idea.psychiatry.modules.patientfile.repository;

import com.idea.psychiatry.modules.patientfile.entity.PatientFile;
import com.idea.psychiatry.modules.patientfile.enums.PatientFileStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientFileRepository extends JpaRepository<PatientFile, UUID> {
    List<PatientFile> findByPatientId(UUID patientId);

    Optional<PatientFile> findByFileNumber(String fileNumber);

    List<PatientFile> findAllByOrganizationId(UUID organizationId);

    List<PatientFile> findByPatientIdAndStatus(UUID patientId, PatientFileStatus status);

    boolean existsByPatientIdAndStatus(UUID patientId, PatientFileStatus status);

    // برای تولید شماره پرونده — بزرگ‌ترین شماره سریال سازمان را برمی‌گرداند
    // TODO: با database sequence جایگزین شود
    long countByOrganizationId(UUID organizationId);
}
