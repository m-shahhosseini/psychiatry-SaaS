package com.idea.psychiatry.modules.diagnosis.service;


import com.idea.psychiatry.modules.diagnosis.dto.CreateDiagnosisRequest;
import com.idea.psychiatry.modules.diagnosis.dto.DiagnosisResponse;
import com.idea.psychiatry.modules.diagnosis.dto.UpdateDiagnosisRequest;
import com.idea.psychiatry.modules.diagnosis.entity.Diagnosis;
import com.idea.psychiatry.modules.diagnosis.enums.DiagnosisStatus;
import com.idea.psychiatry.modules.diagnosis.mapper.DiagnosisMapper;
import com.idea.psychiatry.modules.diagnosis.repository.DiagnosisRepository;
import com.idea.psychiatry.modules.diagnosis.rules.DiagnosisRules;
import com.idea.psychiatry.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final DiagnosisMapper mapper;
    private final DiagnosisRules rules;

    public DiagnosisResponse create(CreateDiagnosisRequest request, UUID organizationId) {
        rules.validatePatientFileIsOpen(request.patientFileId());
        rules.validateEncounterIsActive(request.encounterId());
        rules.validateNoDuplicateActiveCode(
                request.patientFileId(),
                request.code(),
                diagnosisRepository.existsByPatientFileIdAndCode(request.patientFileId(), request.code())
        );

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setOrganizationId(organizationId);
        diagnosis.setPatientFileId(request.patientFileId());
        diagnosis.setEncounterId(request.encounterId());
        diagnosis.setCode(request.code().toUpperCase().trim());
        diagnosis.setTitle(request.title());
        diagnosis.setDescription(request.description());
        diagnosis.setStatus(DiagnosisStatus.ACTIVE);
        diagnosis.setDiagnosedAt(Instant.now());

        return mapper.toResponse(diagnosisRepository.save(diagnosis));
    }

    public DiagnosisResponse getById(UUID diagnosisId) {
        return mapper.toResponse(findOrThrow(diagnosisId));
    }

    public List<DiagnosisResponse> getByEncounter(UUID encounterId) {
        return mapper.toResponseList(
                diagnosisRepository.findByEncounterId(encounterId)
        );
    }

    public List<DiagnosisResponse> getByPatientFile(UUID patientFileId) {
        return mapper.toResponseList(
                diagnosisRepository.findByPatientFileId(patientFileId)
        );
    }

    public List<DiagnosisResponse> getActiveByPatientFile(UUID patientFileId) {
        return mapper.toResponseList(
                diagnosisRepository.findByPatientFileIdAndStatus(patientFileId, DiagnosisStatus.ACTIVE)
        );
    }

    public DiagnosisResponse update(UUID diagnosisId, UpdateDiagnosisRequest request) {
        Diagnosis diagnosis = findOrThrow(diagnosisId);

        rules.validateStatusTransition(diagnosis.getStatus(), request.status());

        if (request.status() != null) {
            diagnosis.setStatus(request.status());
        }
        if (request.description() != null) {
            diagnosis.setDescription(request.description());
        }

        return mapper.toResponse(diagnosisRepository.save(diagnosis));
    }

    public DiagnosisResponse resolve(UUID diagnosisId) {
        Diagnosis diagnosis = findOrThrow(diagnosisId);
        rules.validateStatusTransition(diagnosis.getStatus(), DiagnosisStatus.RESOLVED);
        diagnosis.setStatus(DiagnosisStatus.RESOLVED);
        return mapper.toResponse(diagnosisRepository.save(diagnosis));
    }

    public DiagnosisResponse ruleOut(UUID diagnosisId) {
        Diagnosis diagnosis = findOrThrow(diagnosisId);
        rules.validateStatusTransition(diagnosis.getStatus(), DiagnosisStatus.RULED_OUT);
        diagnosis.setStatus(DiagnosisStatus.RULED_OUT);
        return mapper.toResponse(diagnosisRepository.save(diagnosis));
    }

    // ── private ──────────────────────────────

    private Diagnosis findOrThrow(UUID id) {
        return diagnosisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diagnosis not found: " + id));
    }
}
