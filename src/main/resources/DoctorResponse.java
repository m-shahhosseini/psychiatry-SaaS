package com.idea.psychiatry.modules.doctor.dto;


import java.util.UUID;

public record DoctorResponse(
        UUID doctorId,
        UUID userId,
        String fullName,
        String specialization,
        String medicalLicenseNumber,
        String phoneNumber,
        String email,
        Boolean active
) {}
