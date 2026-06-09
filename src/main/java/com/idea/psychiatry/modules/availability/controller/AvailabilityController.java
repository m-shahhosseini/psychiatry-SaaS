package com.idea.psychiatry.modules.availability.controller;


import com.idea.psychiatry.modules.availability.dto.AvailabilitySlotResponse;
import com.idea.psychiatry.modules.availability.dto.CreateSlotRequest;
import com.idea.psychiatry.modules.availability.service.DoctorAvailabilitySlotService;
import com.idea.psychiatry.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/availability")
@RequiredArgsConstructor
@Tag(name = "Availability", description = "Doctor availability management")
public class AvailabilityController {

    private final DoctorAvailabilitySlotService service;

    @PostMapping("/slots")
    @Operation(summary = "Create availability slot for doctor")
    public ApiResponse<AvailabilitySlotResponse> create(@RequestBody CreateSlotRequest request) {

        return ApiResponse.<AvailabilitySlotResponse>builder()
                .success(true)
                .message("Slot created")
                .data(service.createSlot(request))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/doctors/{doctorId}/slots")
    @Operation(summary = "Get slots by doctor and date")
    public ApiResponse<List<AvailabilitySlotResponse>> getSlots(
            @PathVariable UUID doctorId,
            @RequestParam LocalDate date
    ) {
        return ApiResponse.<List<AvailabilitySlotResponse>>builder()
                .success(true)
                .data(service.getSlots(doctorId, date))
                .timestamp(LocalDateTime.now())
                .build();
    }
}
