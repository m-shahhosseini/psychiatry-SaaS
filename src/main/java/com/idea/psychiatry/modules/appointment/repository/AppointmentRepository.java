package com.idea.psychiatry.modules.appointment.repository;

import com.idea.psychiatry.modules.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findByDoctorId(UUID doctorId);

    List<Appointment> findByPatientId(UUID patientId);

    List<Appointment> findAllByOrganizationId(UUID organizationId);

    List<Appointment> findByAppointmentTimeBetween(
            Instant start,
            Instant end
    );

    Optional<Appointment> findBySlotId(UUID slotId);
}
