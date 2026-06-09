package com.idea.psychiatry.modules.availability.service;

import com.idea.psychiatry.modules.availability.dto.AvailabilitySlotResponse;
import com.idea.psychiatry.modules.availability.dto.CreateSlotRequest;
import com.idea.psychiatry.modules.availability.entity.DoctorAvailabilitySlot;
import com.idea.psychiatry.modules.availability.mapper.AvailabilitySlotMapper;
import com.idea.psychiatry.modules.availability.repository.DoctorAvailabilitySlotRepository;
import com.idea.psychiatry.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilitySlotService {
    private final DoctorAvailabilitySlotRepository repository;
    private final AvailabilitySlotMapper mapper;


    public AvailabilitySlotResponse createSlot(CreateSlotRequest request) {

        DoctorAvailabilitySlot slot = mapper.toEntity(request);

        DoctorAvailabilitySlot saved = repository.save(slot);

        return mapper.toResponse(saved);
    }

    public List<AvailabilitySlotResponse> getSlots(UUID doctorId, LocalDate date) {

        List<DoctorAvailabilitySlot> slots =
                repository.findByDoctorIdAndSlotDateAndActiveTrue(doctorId, date);

        return mapper.toResponseList(slots);
    }
}
