package com.idea.psychiatry.modules.patient.repository;

import com.idea.psychiatry.modules.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByUserId(UUID userId);

    List<Patient> findByActiveTrue();

    List<Patient> findByOrganizationIdAndActiveTrue(UUID organizationId);

    List<Patient> findAllByOrganizationId(UUID organizationId);
}
