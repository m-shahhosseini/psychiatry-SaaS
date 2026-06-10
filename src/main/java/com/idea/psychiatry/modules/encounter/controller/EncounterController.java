package com.idea.psychiatry.modules.encounter.controller;


import com.idea.psychiatry.modules.encounter.dto.CreateEncounterRequest;
import com.idea.psychiatry.modules.encounter.dto.EncounterResponse;
import com.idea.psychiatry.modules.encounter.dto.UpdateEncounterRequest;
import com.idea.psychiatry.modules.encounter.service.EncounterService;
import com.idea.psychiatry.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/encounters")
@RequiredArgsConstructor
@Tag(name = "Encounter", description = "Clinical encounter management")
public class EncounterController {

    private final EncounterService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new encounter for a patient file")
    public ApiResponse<EncounterResponse> create(@Valid @RequestBody CreateEncounterRequest request) {
        return ApiResponse.<EncounterResponse>builder()
                .success(true)
                .message("Encounter created")
                .data(service.create(request))
                .build();
    }

    @GetMapping("/{encounterId}")
    @Operation(summary = "Get encounter by ID")
    public ApiResponse<EncounterResponse> getById(@PathVariable UUID encounterId) {
        return ApiResponse.<EncounterResponse>builder()
                .success(true)
                .data(service.getById(encounterId))
                .build();
    }

    @GetMapping("/patient-file/{patientFileId}")
    @Operation(summary = "Get all encounters for a patient file")
    public ApiResponse<List<EncounterResponse>> getByPatientFile(@PathVariable UUID patientFileId) {
        return ApiResponse.<List<EncounterResponse>>builder()
                .success(true)
                .data(service.getByPatientFile(patientFileId))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all encounters in current organization")
    public ApiResponse<List<EncounterResponse>> getByOrganization() {
        return ApiResponse.<List<EncounterResponse>>builder()
                .success(true)
                .data(service.getByOrganization())
                .build();
    }

    @PutMapping("/{encounterId}")
    @Operation(summary = "Update encounter notes, complaint, or status")
    public ApiResponse<EncounterResponse> update(
            @PathVariable UUID encounterId,
            @Valid @RequestBody UpdateEncounterRequest request) {
        return ApiResponse.<EncounterResponse>builder()
                .success(true)
                .message("Encounter updated")
                .data(service.update(encounterId, request))
                .build();
    }

    @PatchMapping("/{encounterId}/complete")
    @Operation(summary = "Mark encounter as COMPLETED")
    public ApiResponse<EncounterResponse> complete(@PathVariable UUID encounterId) {
        return ApiResponse.<EncounterResponse>builder()
                .success(true)
                .message("Encounter completed")
                .data(service.complete(encounterId))
                .build();
    }

    @PatchMapping("/{encounterId}/cancel")
    @Operation(summary = "Cancel an encounter")
    public ApiResponse<EncounterResponse> cancel(@PathVariable UUID encounterId) {
        return ApiResponse.<EncounterResponse>builder()
                .success(true)
                .message("Encounter cancelled")
                .data(service.cancel(encounterId))
                .build();
    }
}
