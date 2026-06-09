package com.idea.psychiatry.modules.patientfile.entity;

import com.idea.psychiatry.modules.patientfile.enums.PatientFileStatus;
import com.idea.psychiatry.shared.base.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "patient_files",
        indexes = {
                @Index(name = "idx_pf_org", columnList = "organization_id"),
                @Index(name = "idx_pf_patient", columnList = "patient_id"),
                @Index(name = "idx_pf_number", columnList = "file_number")
        }
)
@Getter
@Setter
public class PatientFile extends BaseTenantEntity {

    @Column(nullable = false)
    private UUID patientId;

    @Column(nullable = false, unique = true, updatable = false)
    private String fileNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PatientFileStatus status;

    private LocalDate openedDate;

    private LocalDate closedDate;

    @Column(length = 1000)
    private String notes;
}


// TODO:
// Current implementation uses a simple file number strategy.
// Replace with a database-backed sequence generator.
// Format should be:
// PF-YYYY-XXXXXX
//
// Requirements:
// - Unique across the system
// - Generated automatically by backend
// - Immutable after creation
// - Safe for concurrent requests
// - Consider tenant-specific numbering in future SaaS versions
//   Example: ORGCODE-PF-000001