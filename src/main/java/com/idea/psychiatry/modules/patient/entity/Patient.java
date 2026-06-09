package com.idea.psychiatry.modules.patient.entity;

import com.idea.psychiatry.modules.patient.enums.Gender;
import com.idea.psychiatry.shared.base.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "patients",
        indexes = {
                @Index(name = "idx_patient_org", columnList = "organization_id"),
                @Index(name = "idx_patient_national", columnList = "national_code")
        })
@Getter
@Setter
public class Patient extends BaseTenantEntity {

    @Column(nullable = false)
    private UUID userId; // link to auth user

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String nationalCode;

    private String mobile;

    private String email;

    private String address;

    private boolean active = true;
}
