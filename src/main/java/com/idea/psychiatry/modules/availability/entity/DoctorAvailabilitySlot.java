package com.idea.psychiatry.modules.availability.entity;

import com.idea.psychiatry.shared.base.BaseTenantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "doctor_availability_slots",
        indexes = {
                @Index(name = "idx_slot_doctor_date", columnList = "doctor_id,slot_date")
        }
)
@Getter
@Setter
public class DoctorAvailabilitySlot extends BaseTenantEntity {

    @Column(nullable = false)
    private UUID doctorId;

    @Column(nullable = false)
    private LocalDate slotDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private boolean booked = false;

    @Column(nullable = false)
    private boolean active = true;
}
