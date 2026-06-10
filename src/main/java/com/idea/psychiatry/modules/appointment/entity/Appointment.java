package com.idea.psychiatry.modules.appointment.entity;

import com.idea.psychiatry.modules.appointment.enums.AppointmentStatus;
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
        name = "appointments",
        indexes = {
                @Index(name = "idx_app_org",    columnList = "organization_id"),
                @Index(name = "idx_app_doctor",  columnList = "doctor_id"),
                @Index(name = "idx_app_patient", columnList = "patient_id"),
                @Index(name = "idx_app_start",   columnList = "start_time")
        }
)
@Getter
@Setter
public class Appointment extends BaseTenantEntity {

    @Column(nullable = false)
    private UUID doctorId;

    @Column(nullable = false)
    private UUID patientId;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Column(length = 1000)
    private String notes;
}
