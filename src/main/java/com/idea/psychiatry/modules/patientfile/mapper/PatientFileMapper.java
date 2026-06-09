package com.idea.psychiatry.modules.patientfile.mapper;
import com.idea.psychiatry.modules.patientfile.dto.PatientFileResponse;
import com.idea.psychiatry.modules.patientfile.entity.PatientFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PatientFileMapper {

    @Mapping(source = "id", target = "patientFileId")
    PatientFileResponse toResponse(PatientFile patientFile);

    List<PatientFileResponse> toResponseList(List<PatientFile> patientFiles);
}
