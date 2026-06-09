package com.idea.psychiatry.modules.appointment.controller;


import com.idea.psychiatry.modules.appointment.dto.AppointmentResponse;
import com.idea.psychiatry.modules.appointment.dto.CreateAppointmentRequest;
import com.idea.psychiatry.modules.appointment.service.AppointmentService;
import com.idea.psychiatry.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment", description = "Appointment management")
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    @Operation(summary = "Book appointment using slot")
    public ApiResponse<AppointmentResponse> book(@RequestBody CreateAppointmentRequest request) {

        return ApiResponse.<AppointmentResponse>builder()
                .success(true)
                .message("Appointment booked")
                .data(service.bookAppointment(request))
                .build();
    }


}
