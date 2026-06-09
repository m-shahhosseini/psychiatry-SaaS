package com.idea.psychiatry.modules.patient.mapper;

import com.idea.psychiatry.modules.patient.dto.CreatePatientRequest;
import com.idea.psychiatry.modules.patient.dto.PatientResponse;
import com.idea.psychiatry.modules.patient.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Patient toEntity(CreatePatientRequest request);

    @Mapping(source = "id", target = "patientId")
    PatientResponse toResponse(Patient patient);

    List<PatientResponse> toResponseList(List<Patient> patients);
}
