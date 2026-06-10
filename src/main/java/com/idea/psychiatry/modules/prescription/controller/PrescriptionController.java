package com.idea.psychiatry.modules.prescription.controller;


import com.idea.psychiatry.modules.prescription.dto.*;
import com.idea.psychiatry.modules.prescription.service.PrescriptionService;
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
@RequestMapping("/api/v1/prescriptions")
@RequiredArgsConstructor
@Tag(name = "Prescription", description = "Prescription and medication management")
public class PrescriptionController {

    private final PrescriptionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new prescription with medication items")
    public ApiResponse<PrescriptionResponse> create(@Valid @RequestBody CreatePrescriptionRequest request) {
        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .message("Prescription created")
                .data(service.create(request))
                .build();
    }

    @GetMapping("/{prescriptionId}")
    @Operation(summary = "Get prescription with all items by ID")
    public ApiResponse<PrescriptionResponse> getById(@PathVariable UUID prescriptionId) {
        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .data(service.getById(prescriptionId))
                .build();
    }

    @GetMapping("/encounter/{encounterId}")
    @Operation(summary = "Get all prescriptions for an encounter")
    public ApiResponse<List<PrescriptionResponse>> getByEncounter(@PathVariable UUID encounterId) {
        return ApiResponse.<List<PrescriptionResponse>>builder()
                .success(true)
                .data(service.getByEncounter(encounterId))
                .build();
    }

    @GetMapping("/patient-file/{patientFileId}")
    @Operation(summary = "Get all prescriptions for a patient file")
    public ApiResponse<List<PrescriptionResponse>> getByPatientFile(@PathVariable UUID patientFileId) {
        return ApiResponse.<List<PrescriptionResponse>>builder()
                .success(true)
                .data(service.getByPatientFile(patientFileId))
                .build();
    }

    @PostMapping("/{prescriptionId}/items")
    @Operation(summary = "Add medication items to an existing prescription")
    public ApiResponse<PrescriptionResponse> addItems(
            @PathVariable UUID prescriptionId,
            @Valid @RequestBody AddItemRequest request) {
        return ApiResponse.<PrescriptionResponse>builder()
                .success(true)
                .message("Items added to prescription")
                .data(service.addItems(prescriptionId, request))
                .build();
    }

    @DeleteMapping("/{prescriptionId}/items/{itemId}")
    @Operation(summary = "Remove a medication item from a prescription")
    public ApiResponse<Void> removeItem(
            @PathVariable UUID prescriptionId,
            @PathVariable UUID itemId) {
        service.removeItem(prescriptionId, itemId);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Item removed from prescription")
                .build();
    }
}
