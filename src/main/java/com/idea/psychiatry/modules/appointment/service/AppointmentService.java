package com.idea.psychiatry.modules.appointment.service;

import com.idea.psychiatry.modules.appointment.dto.AppointmentResponse;
import com.idea.psychiatry.modules.appointment.dto.CreateAppointmentRequest;
import com.idea.psychiatry.modules.appointment.dto.UpdateAppointmentRequest;
import com.idea.psychiatry.modules.appointment.entity.Appointment;
import com.idea.psychiatry.modules.appointment.enums.AppointmentStatus;
import com.idea.psychiatry.modules.appointment.mapper.AppointmentMapper;
import com.idea.psychiatry.modules.appointment.repository.AppointmentRepository;
import com.idea.psychiatry.modules.appointment.rules.AppointmentRules;
import com.idea.psychiatry.modules.auth.security.CurrentUser;
import com.idea.psychiatry.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper mapper;
    private final AppointmentRules rules;

    public AppointmentResponse create(CreateAppointmentRequest request) {
        rules.validateTimeRange(request.startTime(), request.endTime());
        rules.validateNotInPast(request.startTime());
        rules.validateNoOverlap(
                appointmentRepository.existsOverlappingAppointment(
                        request.doctorId(), request.startTime(), request.endTime()),
                request.doctorId().toString()
        );

        Appointment appointment = new Appointment();
        appointment.setOrganizationId(CurrentUser.getOrganizationId());
        appointment.setDoctorId(request.doctorId());
        appointment.setPatientId(request.patientId());
        appointment.setStartTime(request.startTime());
        appointment.setEndTime(request.endTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setNotes(request.notes());

        return mapper.toResponse(appointmentRepository.save(appointment));
    }

    public AppointmentResponse getById(UUID appointmentId) {
        Appointment appointment = findOrThrow(appointmentId);
        CurrentUser.requireSameOrganization(appointment.getOrganizationId());
        return mapper.toResponse(appointment);
    }

    public List<AppointmentResponse> getAll() {
        return mapper.toResponseList(
                appointmentRepository.findByOrganizationIdAndStartTimeBetween(
                        CurrentUser.getOrganizationId(),
                        Instant.EPOCH,
                        Instant.now().plus(365, ChronoUnit.DAYS)
                )
        );
    }

    public List<AppointmentResponse> getByDoctor(UUID doctorId) {
        return mapper.toResponseList(
                appointmentRepository.findByDoctorId(doctorId)
        );
    }

    public List<AppointmentResponse> getByPatient(UUID patientId) {
        return mapper.toResponseList(
                appointmentRepository.findByPatientId(patientId)
        );
    }

    public List<AppointmentResponse> getTodayAppointments() {
        Instant startOfDay = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant endOfDay   = startOfDay.plus(1, ChronoUnit.DAYS);

        return mapper.toResponseList(
                appointmentRepository.findByOrganizationIdAndStartTimeBetween(
                        CurrentUser.getOrganizationId(), startOfDay, endOfDay)
        );
    }

    public List<AppointmentResponse> getUpcomingAppointments() {
        Instant now     = Instant.now();
        Instant oneWeek = now.plus(7, ChronoUnit.DAYS);

        return mapper.toResponseList(
                appointmentRepository.findByOrganizationIdAndStartTimeBetween(
                        CurrentUser.getOrganizationId(), now, oneWeek)
        );
    }

    @Transactional
    public AppointmentResponse update(UUID appointmentId, UpdateAppointmentRequest request) {
        Appointment appointment = findOrThrow(appointmentId);
        CurrentUser.requireSameOrganization(appointment.getOrganizationId());

        // تعیین بازه زمانی نهایی برای چک overlap
        Instant newStart = request.startTime() != null ? request.startTime() : appointment.getStartTime();
        Instant newEnd   = request.endTime()   != null ? request.endTime()   : appointment.getEndTime();

        if (request.startTime() != null || request.endTime() != null) {
            rules.validateTimeRange(newStart, newEnd);
            rules.validateNotInPast(newStart);
            rules.validateNoOverlap(
                    appointmentRepository.existsOverlappingAppointmentExcluding(
                            appointment.getDoctorId(), newStart, newEnd, appointmentId),
                    appointment.getDoctorId().toString()
            );
            appointment.setStartTime(newStart);
            appointment.setEndTime(newEnd);
        }

        if (request.status() != null) {
            rules.validateStatusTransition(appointment.getStatus(), request.status());
            appointment.setStatus(request.status());
        }
        if (request.notes() != null) {
            appointment.setNotes(request.notes());
        }

        return mapper.toResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public void cancel(UUID appointmentId) {
        Appointment appointment = findOrThrow(appointmentId);
        CurrentUser.requireSameOrganization(appointment.getOrganizationId());
        rules.validateStatusTransition(appointment.getStatus(), AppointmentStatus.CANCELLED);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    // ── private ──────────────────────────────

    private Appointment findOrThrow(UUID id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found: " + id));
    }
}
