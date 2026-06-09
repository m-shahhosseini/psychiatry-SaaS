package com.idea.psychiatry.modules.encounter.repository;

import com.idea.psychiatry.modules.encounter.entity.Encounter;
import com.idea.psychiatry.modules.encounter.enums.EncounterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EncounterRepository extends JpaRepository<Encounter, UUID> {

    List<Encounter> findByPatientFileId(UUID patientFileId);

    List<Encounter> findByClinicianId(UUID clinicianId);

    List<Encounter> findAllByOrganizationId(UUID organizationId);

    List<Encounter> findByPatientFileIdAndStatus(UUID patientFileId, EncounterStatus status);

    boolean existsByPatientFileIdAndStatus(UUID patientFileId, EncounterStatus status);
}
