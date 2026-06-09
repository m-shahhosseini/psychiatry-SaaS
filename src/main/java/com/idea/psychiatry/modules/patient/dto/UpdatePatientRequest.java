package com.idea.psychiatry.modules.patient.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UpdatePatientRequest(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        LocalDate birthDate,

        String nationalCode,

        String phoneNumber,

        String address,
        String mobile
) {
}
