package com.idea.psychiatry.modules.doctor.entity;

import com.idea.psychiatry.shared.base.BaseTenantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "doctors",
        indexes = {
                @Index(name = "idx_doctor_user", columnList = "user_id")
        }
)
@Getter
@Setter
public class Doctor extends BaseTenantEntity {

    @Column(nullable = false)
    private UUID userId; // link to auth user

    @Column(nullable = false)
    private String fullName;

    private String specialization;

    private String medicalLicenseNumber;

    private String phoneNumber;

    private String email;

    private Boolean active = true;
}
