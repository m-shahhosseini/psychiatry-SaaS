package com.idea.psychiatry.modules.doctor.controller;


import com.idea.psychiatry.modules.doctor.dto.CreateDoctorRequest;
import com.idea.psychiatry.modules.doctor.dto.DoctorResponse;
import com.idea.psychiatry.modules.doctor.dto.UpdateDoctorRequest;
import com.idea.psychiatry.modules.doctor.service.DoctorService;
import com.idea.psychiatry.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctor")
public class DoctorController {

    private final DoctorService service;

    @PostMapping
    public ApiResponse<DoctorResponse> create(@Valid @RequestBody CreateDoctorRequest request) {
        return ApiResponse.<DoctorResponse>builder().success(true).message("Doctor created").data(service.create(request)).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DoctorResponse> get(@PathVariable UUID id) {
        return ApiResponse.<DoctorResponse>builder().success(true).data(service.getById(id)).build();
    }

    @GetMapping
    public ApiResponse<List<DoctorResponse>> getAll() {
        return ApiResponse.<List<DoctorResponse>>builder().success(true).data(service.getAllActive()).build();
    }

    @PutMapping("/{doctorId}")
    public ApiResponse<DoctorResponse> update(@PathVariable UUID doctorId, @Valid @RequestBody UpdateDoctorRequest request) {

        return ApiResponse.<DoctorResponse>builder().success(true).message("Doctor updated").data(service.update(doctorId, request)).build();
    }

    @DeleteMapping("/{doctorId}")
    public ApiResponse<Void> deactivate(@PathVariable UUID doctorId) {

        service.deactivate(doctorId);

        return ApiResponse.<Void>builder().success(true).message("Doctor deactivated").build();
    }
}
