package com.idea.psychiatry.modules.encounter.mapper;


import com.idea.psychiatry.modules.encounter.dto.EncounterResponse;
import com.idea.psychiatry.modules.encounter.entity.Encounter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EncounterMapper {

    @Mapping(source = "id", target = "encounterId")
    EncounterResponse toResponse(Encounter encounter);

    List<EncounterResponse> toResponseList(List<Encounter> encounters);
}
