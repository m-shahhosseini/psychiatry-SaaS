package com.idea.psychiatry.modules.doctor.service;


import com.idea.psychiatry.modules.doctor.dto.CreateDoctorRequest;
import com.idea.psychiatry.modules.doctor.dto.DoctorResponse;
import com.idea.psychiatry.modules.doctor.dto.UpdateDoctorRequest;
import com.idea.psychiatry.modules.doctor.entity.Doctor;
import com.idea.psychiatry.modules.doctor.mapper.DoctorMapper;
import com.idea.psychiatry.modules.doctor.repository.DoctorRepository;
import com.idea.psychiatry.modules.user.entity.User;
import com.idea.psychiatry.shared.exception.BusinessException;
import com.idea.psychiatry.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorService {


    private final DoctorRepository repository;
    private final DoctorMapper mapper;

    public DoctorResponse create(CreateDoctorRequest request) {

        Doctor doctor = mapper.toEntity(request);

        // TODO: replace with current user's organizationId
        doctor.setOrganizationId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        return mapper.toResponse(repository.save(doctor));
    }

    public DoctorResponse getById(UUID id) {

        Doctor doctor = repository.findById(id).orElseThrow(() -> new BusinessException("Doctor not found"));

        return mapper.toResponse(doctor);
    }

    public List<DoctorResponse> getAllActive() {
        return mapper.toResponseList(repository.findByActiveTrue());
    }

    @Transactional
    public DoctorResponse update(UUID doctorId, UpdateDoctorRequest request) {

        Doctor doctor = repository.findById(doctorId).orElseThrow(() -> new NotFoundException("Doctor not found"));

        doctor.setFullName(request.fullName());
        doctor.setSpecialization(request.specialization());
        doctor.setMedicalLicenseNumber(request.medicalLicenseNumber());
        doctor.setPhoneNumber(request.phoneNumber());
        doctor.setEmail(request.email());

        return mapper.toResponse(repository.save(doctor));
    }

    @Transactional
    public void deactivate(UUID doctorId) {

        Doctor doctor = repository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor not found"));

        doctor.setActive(false);

        repository.save(doctor);
    }
}
