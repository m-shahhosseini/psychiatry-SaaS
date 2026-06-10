package com.idea.psychiatry.modules.patient.controller;


import com.idea.psychiatry.modules.patient.dto.CreatePatientRequest;
import com.idea.psychiatry.modules.patient.dto.PatientResponse;
import com.idea.psychiatry.modules.patient.dto.UpdatePatientRequest;
import com.idea.psychiatry.modules.patient.service.PatientService;
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
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patient")
public class PatientController {

    private final PatientService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new patient")
    public ApiResponse<PatientResponse> create(@Valid @RequestBody CreatePatientRequest request) {
        return ApiResponse.<PatientResponse>builder()
                .success(true)
                .message("Patient created")
                .data(service.create(request))
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID")
    public ApiResponse<PatientResponse> getById(@PathVariable UUID id) {
        return ApiResponse.<PatientResponse>builder()
                .success(true)
                .data(service.getById(id))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all active patients in current organization")
    public ApiResponse<List<PatientResponse>> getAllActive() {
        return ApiResponse.<List<PatientResponse>>builder()
                .success(true)
                .data(service.getAllActive())
                .build();
    }

    @PutMapping("/{patientId}")
    @Operation(summary = "Update patient details")
    public ApiResponse<PatientResponse> update(
            @PathVariable UUID patientId,
            @Valid @RequestBody UpdatePatientRequest request) {
        return ApiResponse.<PatientResponse>builder()
                .success(true)
                .message("Patient updated")
                .data(service.update(patientId, request))
                .build();
    }

    @PatchMapping("/{patientId}/deactivate")
    @Operation(summary = "Deactivate a patient")
    public ApiResponse<Void> deactivate(@PathVariable UUID patientId) {
        service.deactivate(patientId);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Patient deactivated")
                .build();
    }
}

