package com.idea.psychiatry.modules.prescription.mapper;

import com.idea.psychiatry.modules.prescription.dto.PrescriptionItemResponse;
import com.idea.psychiatry.modules.prescription.dto.PrescriptionResponse;
import com.idea.psychiatry.modules.prescription.entity.Prescription;
import com.idea.psychiatry.modules.prescription.entity.PrescriptionItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    @Mapping(source = "id", target = "prescriptionId")
    @Mapping(target = "items", ignore = true) // items جداگانه در service ست می‌شود
    PrescriptionResponse toResponse(Prescription prescription);

    @Mapping(source = "id", target = "itemId")
    PrescriptionItemResponse toItemResponse(PrescriptionItem item);

    List<PrescriptionItemResponse> toItemResponseList(List<PrescriptionItem> items);
}
