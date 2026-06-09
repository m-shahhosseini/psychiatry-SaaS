package com.idea.psychiatry.modules.availability.repository;

import com.idea.psychiatry.modules.availability.entity.DoctorAvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DoctorAvailabilitySlotRepository
        extends JpaRepository<DoctorAvailabilitySlot, UUID> {

    List<DoctorAvailabilitySlot> findByDoctorIdAndSlotDateAndActiveTrue(
            UUID doctorId,
            LocalDate slotDate
    );
}
