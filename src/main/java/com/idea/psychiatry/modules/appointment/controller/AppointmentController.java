package com.idea.psychiatry.modules.appointment.controller;


import com.idea.psychiatry.modules.appointment.dto.AppointmentResponse;
import com.idea.psychiatry.modules.appointment.dto.CreateAppointmentRequest;
import com.idea.psychiatry.modules.appointment.dto.UpdateAppointmentRequest;
import com.idea.psychiatry.modules.appointment.service.AppointmentService;
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
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment", description = "Appointment management")
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new appointment")
    public ApiResponse<AppointmentResponse> create(@Valid @RequestBody CreateAppointmentRequest request) {
        return ApiResponse.<AppointmentResponse>builder()
                .success(true)
                .message("Appointment created")
                .data(service.create(request))
                .build();
    }

    @GetMapping("/{appointmentId}")
    @Operation(summary = "Get appointment by ID")
    public ApiResponse<AppointmentResponse> getById(@PathVariable UUID appointmentId) {
        return ApiResponse.<AppointmentResponse>builder()
                .success(true)
                .data(service.getById(appointmentId))
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all appointments in current organization")
    public ApiResponse<List<AppointmentResponse>> getAll() {
        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .data(service.getAll())
                .build();
    }

    @GetMapping("/doctor/{doctorId}")
    @Operation(summary = "Get all appointments for a doctor")
    public ApiResponse<List<AppointmentResponse>> getByDoctor(@PathVariable UUID doctorId) {
        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .data(service.getByDoctor(doctorId))
                .build();
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get all appointments for a patient")
    public ApiResponse<List<AppointmentResponse>> getByPatient(@PathVariable UUID patientId) {
        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .data(service.getByPatient(patientId))
                .build();
    }

    @GetMapping("/today")
    @Operation(summary = "Get today's appointments")
    public ApiResponse<List<AppointmentResponse>> getToday() {
        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .data(service.getTodayAppointments())
                .build();
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Get upcoming appointments (next 7 days)")
    public ApiResponse<List<AppointmentResponse>> getUpcoming() {
        return ApiResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .data(service.getUpcomingAppointments())
                .build();
    }

    @PutMapping("/{appointmentId}")
    @Operation(summary = "Update appointment time, status, or notes")
    public ApiResponse<AppointmentResponse> update(
            @PathVariable UUID appointmentId,
            @Valid @RequestBody UpdateAppointmentRequest request) {
        return ApiResponse.<AppointmentResponse>builder()
                .success(true)
                .message("Appointment updated")
                .data(service.update(appointmentId, request))
                .build();
    }

    @PatchMapping("/{appointmentId}/cancel")
    @Operation(summary = "Cancel an appointment")
    public ApiResponse<Void> cancel(@PathVariable UUID appointmentId) {
        service.cancel(appointmentId);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Appointment cancelled")
                .build();
    }
}

