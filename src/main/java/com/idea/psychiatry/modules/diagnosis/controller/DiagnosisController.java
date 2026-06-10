package com.idea.psychiatry.modules.diagnosis.controller;

import com.idea.psychiatry.modules.diagnosis.dto.CreateDiagnosisRequest;
import com.idea.psychiatry.modules.diagnosis.dto.DiagnosisResponse;
import com.idea.psychiatry.modules.diagnosis.dto.UpdateDiagnosisRequest;
import com.idea.psychiatry.modules.diagnosis.service.DiagnosisService;
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
@RequestMapping("/api/v1/diagnoses")
@RequiredArgsConstructor
@Tag(name = "Diagnosis", description = "Clinical diagnosis management")
public class DiagnosisController {

    private final DiagnosisService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new diagnosis for an encounter")
    public ApiResponse<DiagnosisResponse> create(@Valid @RequestBody CreateDiagnosisRequest request) {
        return ApiResponse.<DiagnosisResponse>builder()
                .success(true)
                .message("Diagnosis created")
                .data(service.create(request))
                .build();
    }

    @GetMapping("/{diagnosisId}")
    @Operation(summary = "Get diagnosis by ID")
    public ApiResponse<DiagnosisResponse> getById(@PathVariable UUID diagnosisId) {
        return ApiResponse.<DiagnosisResponse>builder()
                .success(true)
                .data(service.getById(diagnosisId))
                .build();
    }

    @GetMapping("/encounter/{encounterId}")
    @Operation(summary = "Get all diagnoses for an encounter")
    public ApiResponse<List<DiagnosisResponse>> getByEncounter(@PathVariable UUID encounterId) {
        return ApiResponse.<List<DiagnosisResponse>>builder()
                .success(true)
                .data(service.getByEncounter(encounterId))
                .build();
    }

    @GetMapping("/patient-file/{patientFileId}")
    @Operation(summary = "Get all diagnoses for a patient file")
    public ApiResponse<List<DiagnosisResponse>> getByPatientFile(@PathVariable UUID patientFileId) {
        return ApiResponse.<List<DiagnosisResponse>>builder()
                .success(true)
                .data(service.getByPatientFile(patientFileId))
                .build();
    }

    @GetMapping("/patient-file/{patientFileId}/active")
    @Operation(summary = "Get only ACTIVE diagnoses for a patient file")
    public ApiResponse<List<DiagnosisResponse>> getActiveByPatientFile(@PathVariable UUID patientFileId) {
        return ApiResponse.<List<DiagnosisResponse>>builder()
                .success(true)
                .data(service.getActiveByPatientFile(patientFileId))
                .build();
    }

    @PutMapping("/{diagnosisId}")
    @Operation(summary = "Update diagnosis description or status")
    public ApiResponse<DiagnosisResponse> update(
            @PathVariable UUID diagnosisId,
            @Valid @RequestBody UpdateDiagnosisRequest request) {
        return ApiResponse.<DiagnosisResponse>builder()
                .success(true)
                .message("Diagnosis updated")
                .data(service.update(diagnosisId, request))
                .build();
    }

    @PatchMapping("/{diagnosisId}/resolve")
    @Operation(summary = "Mark diagnosis as RESOLVED")
    public ApiResponse<DiagnosisResponse> resolve(@PathVariable UUID diagnosisId) {
        return ApiResponse.<DiagnosisResponse>builder()
                .success(true)
                .message("Diagnosis resolved")
                .data(service.resolve(diagnosisId))
                .build();
    }

    @PatchMapping("/{diagnosisId}/rule-out")
    @Operation(summary = "Mark diagnosis as RULED_OUT")
    public ApiResponse<DiagnosisResponse> ruleOut(@PathVariable UUID diagnosisId) {
        return ApiResponse.<DiagnosisResponse>builder()
                .success(true)
                .message("Diagnosis ruled out")
                .data(service.ruleOut(diagnosisId))
                .build();
    }
}
