package com.idea.psychiatry.modules.patientfile.controller;


import com.idea.psychiatry.modules.patientfile.dto.CreatePatientFileRequest;
import com.idea.psychiatry.modules.patientfile.dto.PatientFileResponse;
import com.idea.psychiatry.modules.patientfile.dto.UpdatePatientFileRequest;
import com.idea.psychiatry.modules.patientfile.service.PatientFileService;
import com.idea.psychiatry.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patient-files")
@RequiredArgsConstructor
@Tag(name = "PatientFile", description = "Patient clinical file management")
public class PatientFileController {

    private final PatientFileService service;

    // TODO: organizationId باید از SecurityContext (JWT token) گرفته شود
    //       فعلاً به‌عنوان header پاس می‌شود تا Security پیاده‌سازی شود
    @PostMapping
    @Operation(summary = "Open a new patient file")
    public ApiResponse<PatientFileResponse> create(@Valid @RequestBody CreatePatientFileRequest request, @RequestHeader("X-Organization-Id") UUID organizationId) {
        return ApiResponse.<PatientFileResponse>builder().success(true).message("Patient file opened").data(service.create(request, organizationId)).build();
    }

    @GetMapping("/{patientFileId}")
    @Operation(summary = "Get patient file by ID")
    public ApiResponse<PatientFileResponse> getById(@PathVariable UUID patientFileId) {
        return ApiResponse.<PatientFileResponse>builder().success(true).data(service.getById(patientFileId)).build();
    }

    @GetMapping("/number/{fileNumber}")
    @Operation(summary = "Get patient file by file number (e.g. PF-2025-000001)")
    public ApiResponse<PatientFileResponse> getByFileNumber(@PathVariable String fileNumber) {
        return ApiResponse.<PatientFileResponse>builder().success(true).data(service.getByFileNumber(fileNumber)).build();
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get all files for a patient")
    public ApiResponse<List<PatientFileResponse>> getByPatient(@PathVariable UUID patientId) {
        return ApiResponse.<List<PatientFileResponse>>builder().success(true).data(service.getByPatient(patientId)).build();
    }

    @PutMapping("/{patientFileId}")
    @Operation(summary = "Update patient file notes")
    public ApiResponse<PatientFileResponse> update(@PathVariable UUID patientFileId, @Valid @RequestBody UpdatePatientFileRequest request) {
        return ApiResponse.<PatientFileResponse>builder().success(true).message("Patient file updated").data(service.update(patientFileId, request)).build();
    }

    @PatchMapping("/{patientFileId}/close")
    @Operation(summary = "Close a patient file")
    public ApiResponse<PatientFileResponse> close(@PathVariable UUID patientFileId) {
        return ApiResponse.<PatientFileResponse>builder().success(true).message("Patient file closed").data(service.close(patientFileId)).build();
    }

    @PatchMapping("/{patientFileId}/reopen")
    @Operation(summary = "Reopen a closed patient file")
    public ApiResponse<PatientFileResponse> reopen(@PathVariable UUID patientFileId) {
        return ApiResponse.<PatientFileResponse>builder().success(true).message("Patient file reopened").data(service.reopen(patientFileId)).build();
    }

    @PatchMapping("/{patientFileId}/archive")
    @Operation(summary = "Archive a patient file (irreversible)")
    public ApiResponse<PatientFileResponse> archive(@PathVariable UUID patientFileId) {
        return ApiResponse.<PatientFileResponse>builder().success(true).message("Patient file archived").data(service.archive(patientFileId)).build();
    }
}
