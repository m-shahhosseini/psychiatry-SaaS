package com.idea.psychiatry.modules.diagnosis.mapper;

import com.idea.psychiatry.modules.diagnosis.dto.DiagnosisResponse;
import com.idea.psychiatry.modules.diagnosis.entity.Diagnosis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiagnosisMapper {

    @Mapping(source = "id", target = "diagnosisId")
    DiagnosisResponse toResponse(Diagnosis diagnosis);

    List<DiagnosisResponse> toResponseList(List<Diagnosis> diagnoses);
}
