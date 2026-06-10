package com.idea.psychiatry.modules.patient.service;

import com.idea.psychiatry.modules.auth.security.CurrentUser;
import com.idea.psychiatry.modules.patient.dto.CreatePatientRequest;
import com.idea.psychiatry.modules.patient.dto.PatientResponse;
import com.idea.psychiatry.modules.patient.dto.UpdatePatientRequest;
import com.idea.psychiatry.modules.patient.entity.Patient;
import com.idea.psychiatry.modules.patient.mapper.PatientMapper;
import com.idea.psychiatry.modules.patient.repository.PatientRepository;
import com.idea.psychiatry.shared.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;

    public PatientResponse create(CreatePatientRequest request) {
        Patient patient = mapper.toEntity(request);
        patient.setOrganizationId(CurrentUser.getOrganizationId());
        patient.setUserId(CurrentUser.getUserId());
        return mapper.toResponse(repository.save(patient));
    }

    public PatientResponse getById(UUID id) {
        Patient patient = findOrThrow(id);
        CurrentUser.requireSameOrganization(patient.getOrganizationId());
        return mapper.toResponse(patient);
    }

    public PatientResponse getByUserId(UUID userId) {
        Patient patient = repository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Patient not found for userId: " + userId));
        CurrentUser.requireSameOrganization(patient.getOrganizationId());
        return mapper.toResponse(patient);
    }

    public List<PatientResponse> getAllActive() {
        return mapper.toResponseList(
                repository.findByOrganizationIdAndActiveTrue(CurrentUser.getOrganizationId())
        );
    }

    @Transactional
    public PatientResponse update(UUID patientId, UpdatePatientRequest request) {
        Patient patient = findOrThrow(patientId);
        CurrentUser.requireSameOrganization(patient.getOrganizationId());

        if (request.firstName() != null)  patient.setFirstName(request.firstName());
        if (request.lastName() != null)   patient.setLastName(request.lastName());
        if (request.birthDate() != null)  patient.setBirthDate(request.birthDate());
        if (request.mobile() != null)     patient.setMobile(request.mobile());
        if (request.address() != null)    patient.setAddress(request.address());

        return mapper.toResponse(repository.save(patient));
    }

    @Transactional
    public void deactivate(UUID patientId) {
        Patient patient = findOrThrow(patientId);
        CurrentUser.requireSameOrganization(patient.getOrganizationId());
        patient.setActive(false);
        repository.save(patient);
    }

    // ── private ──────────────────────────────

    private Patient findOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found: " + id));
    }
}
