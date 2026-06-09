package com.idea.psychiatry.modules.encounter.entity;


import com.idea.psychiatry.modules.encounter.enums.EncounterStatus;
import com.idea.psychiatry.modules.encounter.enums.EncounterType;
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
        name = "encounters",
        indexes = {
                @Index(name = "idx_enc_org", columnList = "organization_id"),
                @Index(name = "idx_enc_patient_file", columnList = "patient_file_id")
        }
)
@Getter
@Setter
public class Encounter extends BaseTenantEntity {


    @Column(nullable = false)
    private UUID patientFileId;

    @Column(nullable = false)
    private UUID clinicianId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EncounterType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EncounterStatus status;

    @Column(nullable = false)
    private Instant encounterDate;

    @Column(length = 2000)
    private String chiefComplaint;

    @Column(length = 5000)
    private String notes;
}
