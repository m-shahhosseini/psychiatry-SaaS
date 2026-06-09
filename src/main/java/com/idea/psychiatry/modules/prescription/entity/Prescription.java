package com.idea.psychiatry.modules.prescription.entity;


import com.idea.psychiatry.shared.base.BaseTenantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "prescriptions",
        indexes = {
                @Index(name = "idx_pres_org", columnList = "organization_id"),
                @Index(name = "idx_pres_patient_file", columnList = "patient_file_id"),
                @Index(name = "idx_pres_encounter", columnList = "encounter_id")
        }
)
@Getter
@Setter
public class Prescription extends BaseTenantEntity {
    @Column(nullable = false)
    private UUID patientFileId;

    @Column(nullable = false)
    private UUID encounterId;

    @Column(nullable = false)
    private UUID prescriberId;

    private Instant prescribedAt;

    @Column(length = 2000)
    private String notes;
}
