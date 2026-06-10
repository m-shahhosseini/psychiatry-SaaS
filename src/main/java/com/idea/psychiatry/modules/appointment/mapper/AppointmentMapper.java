package com.idea.psychiatry.modules.appointment.mapper;


import com.idea.psychiatry.modules.appointment.dto.AppointmentResponse;
import com.idea.psychiatry.modules.appointment.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "id", target = "appointmentId")
    AppointmentResponse toResponse(Appointment appointment);

    List<AppointmentResponse> toResponseList(List<Appointment> appointments);
}
