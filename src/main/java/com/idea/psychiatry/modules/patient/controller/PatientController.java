package com.idea.psychiatry.modules.patient.controller;


import com.idea.psychiatry.modules.doctor.dto.DoctorResponse;
import com.idea.psychiatry.modules.doctor.dto.UpdateDoctorRequest;
import com.idea.psychiatry.modules.patient.dto.CreatePatientRequest;
import com.idea.psychiatry.modules.patient.dto.PatientResponse;
import com.idea.psychiatry.modules.patient.dto.UpdatePatientRequest;
import com.idea.psychiatry.modules.patient.service.PatientService;
import com.idea.psychiatry.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<PatientResponse> create(@RequestBody CreatePatientRequest request) {

        return ApiResponse.<PatientResponse>builder().success(true).message("Patient created").data(service.create(request)).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PatientResponse> get(@PathVariable UUID id) {

        return ApiResponse.<PatientResponse>builder().success(true).data(service.getByUserId(id)).build();
    }

    @GetMapping
    public ApiResponse<List<PatientResponse>> getAll() {
        return ApiResponse.<List<PatientResponse>>builder().success(true).data(service.getAllActive()).build();
    }

    @PutMapping("/{patientId}")
    public ApiResponse<PatientResponse> update(@PathVariable UUID patientId, @Valid @RequestBody UpdatePatientRequest request) {

        return ApiResponse.<PatientResponse>builder().success(true).message("Doctor updated").data(service.update(patientId, request)).build();
    }

    @DeleteMapping("/{patientId}")
    public ApiResponse<Void> deactivate(@PathVariable UUID patientId) {

        service.deactivate(patientId);

        return ApiResponse.<Void>builder().success(true).message("Doctor deactivated").build();
    }
}
