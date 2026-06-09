package com.idea.psychiatry.modules.diagnosis.entity;


import com.idea.psychiatry.modules.diagnosis.enums.DiagnosisStatus;
import com.idea.psychiatry.shared.base.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "diagnoses",
        indexes = {
                @Index(name = "idx_diag_org", columnList = "organization_id"),
                @Index(name = "idx_diag_patient_file", columnList = "patient_file_id"),
                @Index(name = "idx_diag_encounter", columnList = "encounter_id")
        }
)
@Getter
@Setter
public class Diagnosis extends BaseTenantEntity {

    @Column(nullable = false)
    private UUID patientFileId;

    @Column(nullable = false)
    private UUID encounterId;

    @Column(nullable = false)
    private String code; // ICD-10 or DSM code

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private DiagnosisStatus status;

    private Instant diagnosedAt;
}
