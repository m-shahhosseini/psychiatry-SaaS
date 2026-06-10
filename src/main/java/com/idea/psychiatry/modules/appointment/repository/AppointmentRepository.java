package com.idea.psychiatry.modules.appointment.repository;

import com.idea.psychiatry.modules.appointment.entity.Appointment;
import com.idea.psychiatry.modules.appointment.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findByDoctorId(UUID doctorId);

    List<Appointment> findByPatientId(UUID patientId);

    List<Appointment> findByOrganizationIdAndStatus(UUID organizationId, AppointmentStatus status);

    List<Appointment> findByStartTimeBetween(Instant start, Instant end);

    List<Appointment> findByOrganizationIdAndStartTimeBetween(
            UUID organizationId, Instant start, Instant end);

    // بررسی تداخل نوبت برای یک پزشک
    // نوبت جدید با نوبت موجود تداخل دارد اگر: start < existingEnd AND end > existingStart
    @Query("""
            SELECT COUNT(a) > 0 FROM Appointment a
            WHERE a.doctorId = :doctorId
              AND a.status NOT IN ('CANCELLED', 'NO_SHOW')
              AND a.startTime < :endTime
              AND a.endTime > :startTime
            """)
    boolean existsOverlappingAppointment(
            @Param("doctorId") UUID doctorId,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime
    );

    // همان query با exclude کردن یک appointment (برای update)
    @Query("""
            SELECT COUNT(a) > 0 FROM Appointment a
            WHERE a.doctorId = :doctorId
              AND a.id != :excludeId
              AND a.status NOT IN ('CANCELLED', 'NO_SHOW')
              AND a.startTime < :endTime
              AND a.endTime > :startTime
            """)
    boolean existsOverlappingAppointmentExcluding(
            @Param("doctorId") UUID doctorId,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime,
            @Param("excludeId") UUID excludeId
    );
}
