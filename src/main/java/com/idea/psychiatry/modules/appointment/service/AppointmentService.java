package com.idea.psychiatry.modules.appointment.service;

import com.idea.psychiatry.modules.appointment.dto.AppointmentResponse;
import com.idea.psychiatry.modules.appointment.dto.CreateAppointmentRequest;
import com.idea.psychiatry.modules.appointment.entity.Appointment;
import com.idea.psychiatry.modules.appointment.enums.AppointmentStatus;
import com.idea.psychiatry.modules.appointment.mapper.AppointmentMapper;
import com.idea.psychiatry.modules.appointment.repository.AppointmentRepository;
import com.idea.psychiatry.modules.availability.entity.DoctorAvailabilitySlot;
import com.idea.psychiatry.modules.availability.repository.DoctorAvailabilitySlotRepository;
import com.idea.psychiatry.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorAvailabilitySlotRepository slotRepository;
    private final AppointmentMapper mapper;

    public AppointmentResponse bookAppointment(CreateAppointmentRequest request) {

        DoctorAvailabilitySlot slot = slotRepository.findById(request.slotId())
                .orElseThrow(() -> new BusinessException("Slot not found"));

        if (slot.isBooked()) {
            throw new BusinessException("Slot already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctorId(slot.getDoctorId());
        appointment.setPatientId(request.patientId());
        appointment.setSlotId(slot.getId());
        appointment.setAppointmentTime(
                slot.getSlotDate().atTime(slot.getStartTime())
        );
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        slot.setBooked(true);
        slotRepository.save(slot);

        return mapper.toResponse(appointmentRepository.save(appointment));
    }
}
