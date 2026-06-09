package com.idea.psychiatry.modules.availability.mapper;


import com.idea.psychiatry.modules.availability.dto.AvailabilitySlotResponse;
import com.idea.psychiatry.modules.availability.dto.CreateSlotRequest;
import com.idea.psychiatry.modules.availability.entity.DoctorAvailabilitySlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvailabilitySlotMapper {

    // DTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "booked", constant = "false")
    @Mapping(target = "active", constant = "true")
    DoctorAvailabilitySlot toEntity(CreateSlotRequest request);

    // Entity → DTO
    @Mapping(target = "available", expression = "java(!slot.isBooked())")
    AvailabilitySlotResponse toResponse(DoctorAvailabilitySlot slot);

    List<AvailabilitySlotResponse> toResponseList(List<DoctorAvailabilitySlot> slots);
}
