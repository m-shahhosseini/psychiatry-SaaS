package com.idea.psychiatry.modules.doctor.service;


import com.idea.psychiatry.modules.auth.security.CurrentUser;
import com.idea.psychiatry.modules.doctor.dto.CreateDoctorRequest;
import com.idea.psychiatry.modules.doctor.dto.DoctorResponse;
import com.idea.psychiatry.modules.doctor.dto.UpdateDoctorRequest;
import com.idea.psychiatry.modules.doctor.entity.Doctor;
import com.idea.psychiatry.modules.doctor.mapper.DoctorMapper;
import com.idea.psychiatry.modules.doctor.repository.DoctorRepository;
import com.idea.psychiatry.shared.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repository;
    private final DoctorMapper mapper;

    public DoctorResponse create(CreateDoctorRequest request) {
        Doctor doctor = mapper.toEntity(request);
        doctor.setOrganizationId(CurrentUser.getOrganizationId());
        return mapper.toResponse(repository.save(doctor));
    }

    public DoctorResponse getById(UUID id) {
        Doctor doctor = findOrThrow(id);
        CurrentUser.requireSameOrganization(doctor.getOrganizationId());
        return mapper.toResponse(doctor);
    }

    public List<DoctorResponse> getAllActive() {
        return mapper.toResponseList(
                repository.findByOrganizationIdAndActiveTrue(CurrentUser.getOrganizationId())
        );
    }

    @Transactional
    public DoctorResponse update(UUID doctorId, UpdateDoctorRequest request) {
        Doctor doctor = findOrThrow(doctorId);
        CurrentUser.requireSameOrganization(doctor.getOrganizationId());

        if (request.fullName() != null) doctor.setFullName(request.fullName());
        if (request.specialization() != null) doctor.setSpecialization(request.specialization());
        if (request.medicalLicenseNumber() != null) doctor.setMedicalLicenseNumber(request.medicalLicenseNumber());
        if (request.phoneNumber() != null) doctor.setPhoneNumber(request.phoneNumber());
        if (request.email() != null) doctor.setEmail(request.email());

        return mapper.toResponse(repository.save(doctor));
    }

    @Transactional
    public void deactivate(UUID doctorId) {
        Doctor doctor = findOrThrow(doctorId);
        CurrentUser.requireSameOrganization(doctor.getOrganizationId());
        doctor.setActive(false);
        repository.save(doctor);
    }

    // ── private ──────────────────────────────

    private Doctor findOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found: " + id));
    }
}

