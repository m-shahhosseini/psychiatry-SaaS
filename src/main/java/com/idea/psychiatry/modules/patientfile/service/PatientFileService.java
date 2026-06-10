package com.idea.psychiatry.modules.patientfile.service;


import com.idea.psychiatry.modules.auth.security.CurrentUser;
import com.idea.psychiatry.modules.patientfile.entity.PatientFile;
import com.idea.psychiatry.modules.patientfile.enums.PatientFileStatus;
import com.idea.psychiatry.modules.patientfile.repository.PatientFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import com.idea.psychiatry.modules.patientfile.dto.CreatePatientFileRequest;
import com.idea.psychiatry.modules.patientfile.dto.PatientFileResponse;
import com.idea.psychiatry.modules.patientfile.dto.UpdatePatientFileRequest;
import com.idea.psychiatry.modules.patientfile.entity.PatientFile;
import com.idea.psychiatry.modules.patientfile.enums.PatientFileStatus;
import com.idea.psychiatry.modules.patientfile.mapper.PatientFileMapper;
import com.idea.psychiatry.modules.patientfile.repository.PatientFileRepository;
import com.idea.psychiatry.modules.patientfile.rules.PatientFileRules;
import com.idea.psychiatry.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientFileService {

    private final PatientFileRepository patientFileRepository;
    private final PatientFileMapper mapper;
    private final PatientFileRules rules;

    public PatientFileResponse create(CreatePatientFileRequest request) {
        UUID organizationId = CurrentUser.getOrganizationId();

        rules.validatePatientExists(request.patientId());
        rules.validateNoOpenFileExists(
                request.patientId(),
                patientFileRepository.existsByPatientIdAndStatus(
                        request.patientId(), PatientFileStatus.OPEN)
        );

        PatientFile file = new PatientFile();
        file.setPatientId(request.patientId());
        file.setOrganizationId(organizationId);
        file.setStatus(PatientFileStatus.OPEN);
        file.setOpenedDate(LocalDate.now());
        file.setFileNumber(generateFileNumber(organizationId));
        file.setNotes(request.notes());

        return mapper.toResponse(patientFileRepository.save(file));
    }

    public PatientFileResponse getById(UUID patientFileId) {
        PatientFile file = findOrThrow(patientFileId);
        CurrentUser.requireSameOrganization(file.getOrganizationId());
        return mapper.toResponse(file);
    }

    public PatientFileResponse getByFileNumber(String fileNumber) {
        PatientFile file = patientFileRepository.findByFileNumber(fileNumber)
                .orElseThrow(() -> new NotFoundException("PatientFile not found: " + fileNumber));
        CurrentUser.requireSameOrganization(file.getOrganizationId());
        return mapper.toResponse(file);
    }

    public List<PatientFileResponse> getByPatient(UUID patientId) {
        return mapper.toResponseList(
                patientFileRepository.findByPatientId(patientId)
        );
    }

    public List<PatientFileResponse> getByOrganization() {
        return mapper.toResponseList(
                patientFileRepository.findAllByOrganizationId(CurrentUser.getOrganizationId())
        );
    }

    public PatientFileResponse update(UUID patientFileId, UpdatePatientFileRequest request) {
        PatientFile file = findOrThrow(patientFileId);
        CurrentUser.requireSameOrganization(file.getOrganizationId());
        rules.validateFileIsOpen(file);

        if (request.notes() != null) {
            file.setNotes(request.notes());
        }

        return mapper.toResponse(patientFileRepository.save(file));
    }

    public PatientFileResponse close(UUID patientFileId) {
        PatientFile file = findOrThrow(patientFileId);
        CurrentUser.requireSameOrganization(file.getOrganizationId());
        rules.validateStatusTransition(file.getStatus(), PatientFileStatus.CLOSED);
        file.setStatus(PatientFileStatus.CLOSED);
        file.setClosedDate(LocalDate.now());
        return mapper.toResponse(patientFileRepository.save(file));
    }

    public PatientFileResponse reopen(UUID patientFileId) {
        PatientFile file = findOrThrow(patientFileId);
        CurrentUser.requireSameOrganization(file.getOrganizationId());
        rules.validateStatusTransition(file.getStatus(), PatientFileStatus.OPEN);
        rules.validateNoOpenFileExists(
                file.getPatientId(),
                patientFileRepository.existsByPatientIdAndStatus(
                        file.getPatientId(), PatientFileStatus.OPEN)
        );
        file.setStatus(PatientFileStatus.OPEN);
        file.setClosedDate(null);
        return mapper.toResponse(patientFileRepository.save(file));
    }

    public PatientFileResponse archive(UUID patientFileId) {
        PatientFile file = findOrThrow(patientFileId);
        CurrentUser.requireSameOrganization(file.getOrganizationId());
        rules.validateStatusTransition(file.getStatus(), PatientFileStatus.ARCHIVED);
        file.setStatus(PatientFileStatus.ARCHIVED);
        if (file.getClosedDate() == null) {
            file.setClosedDate(LocalDate.now());
        }
        return mapper.toResponse(patientFileRepository.save(file));
    }

    // ── private ──────────────────────────────

    private PatientFile findOrThrow(UUID id) {
        return patientFileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("PatientFile not found: " + id));
    }

    /**
     * تولید شماره پرونده با فرمت PF-YYYY-XXXXXX
     * TODO: با database-backed sequence جایگزین شود (ایمن برای concurrent requests)
     */
    private String generateFileNumber(UUID organizationId) {
        long count = patientFileRepository.countByOrganizationId(organizationId) + 1;
        return String.format("PF-%d-%06d", Year.now().getValue(), count);
    }
}