package com.idea.psychiatry.modules.patient.service;

import com.idea.psychiatry.modules.doctor.dto.DoctorResponse;
import com.idea.psychiatry.modules.doctor.dto.UpdateDoctorRequest;
import com.idea.psychiatry.modules.doctor.entity.Doctor;
import com.idea.psychiatry.modules.patient.dto.CreatePatientRequest;
import com.idea.psychiatry.modules.patient.dto.PatientResponse;
import com.idea.psychiatry.modules.patient.dto.UpdatePatientRequest;
import com.idea.psychiatry.modules.patient.entity.Patient;
import com.idea.psychiatry.modules.patient.mapper.PatientMapper;
import com.idea.psychiatry.modules.patient.repository.PatientRepository;
import com.idea.psychiatry.shared.exception.BusinessException;
import com.idea.psychiatry.shared.exception.ConflictException;
import com.idea.psychiatry.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;

    public PatientResponse create(CreatePatientRequest request) {
        Patient patient = mapper.toEntity(request);
        // TODO: replace with current user's organizationId
        patient.setOrganizationId(UUID.fromString("11111111-1111-1111-1111-111111111112"));
        patient.setUserId(UUID.fromString("11111111-1111-1111-1111-111111111113"));

        return mapper.toResponse(repository.save(patient));
    }

    public PatientResponse getById(UUID id) {

        Patient patient = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Patient not found"));

        return mapper.toResponse(patient);
    }

    public PatientResponse getByUserId(UUID userId) {

        Patient patient = repository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Patient not found"));

        return mapper.toResponse(patient);
    }

    public List<PatientResponse> getAllActive() {
        return mapper.toResponseList(repository.findByActiveTrue());
    }

    @Transactional
    public PatientResponse update(UUID patientId, UpdatePatientRequest request) {

        Patient patientLoaded = repository.findById(patientId).orElseThrow(() -> new NotFoundException("Patient not found"));

        patientLoaded.setFirstName(request.firstName());
        patientLoaded.setLastName(request.lastName());
        patientLoaded.setBirthDate(request.birthDate());
        patientLoaded.setMobile(request.mobile());
        patientLoaded.setAddress(request.address());

        return mapper.toResponse(repository.save(patientLoaded));
    }

    @Transactional
    public void deactivate(UUID patientId) {

        Patient patientLoaded = repository.findById(patientId).orElseThrow(() -> new NotFoundException("Patient not found"));

        patientLoaded.setActive(false);

        repository.save(patientLoaded);
    }
}
