package com.idea.psychiatry.modules.doctor.dto;


public record UpdateDoctorRequest(
        String fullName,
        String specialization,
        String medicalLicenseNumber,
        String phoneNumber,
        String email
) {
}
