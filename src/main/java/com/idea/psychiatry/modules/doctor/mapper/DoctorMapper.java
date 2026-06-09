package com.idea.psychiatry.modules.doctor.mapper;

import com.idea.psychiatry.modules.doctor.dto.CreateDoctorRequest;
import com.idea.psychiatry.modules.doctor.dto.DoctorResponse;
import com.idea.psychiatry.modules.doctor.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Doctor toEntity(CreateDoctorRequest request);

    @Mapping(source = "id", target = "doctorId")
    DoctorResponse toResponse(Doctor doctor);

    List<DoctorResponse> toResponseList(List<Doctor> doctors);
}
