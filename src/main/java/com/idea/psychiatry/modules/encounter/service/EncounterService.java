package com.idea.psychiatry.modules.encounter.service;


import com.idea.psychiatry.modules.encounter.dto.CreateEncounterRequest;
import com.idea.psychiatry.modules.encounter.dto.EncounterResponse;
import com.idea.psychiatry.modules.encounter.dto.UpdateEncounterRequest;
import com.idea.psychiatry.modules.encounter.entity.Encounter;
import com.idea.psychiatry.modules.encounter.enums.EncounterStatus;
import com.idea.psychiatry.modules.encounter.mapper.EncounterMapper;
import com.idea.psychiatry.modules.encounter.repository.EncounterRepository;
import com.idea.psychiatry.modules.encounter.rules.EncounterRules;
import com.idea.psychiatry.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EncounterService {

    private final EncounterRepository encounterRepository;
    private final EncounterMapper mapper;
    private final EncounterRules rules;

    public EncounterResponse create(CreateEncounterRequest request) {
        rules.validatePatientFileIsOpen(request.patientFileId());

        Encounter encounter = new Encounter();
        encounter.setPatientFileId(request.patientFileId());
        encounter.setClinicianId(request.clinicianId());
        encounter.setType(request.type());
        encounter.setStatus(EncounterStatus.SCHEDULED);
        encounter.setEncounterDate(request.encounterDate());
        encounter.setChiefComplaint(request.chiefComplaint());
        encounter.setNotes(request.notes());

        return mapper.toResponse(encounterRepository.save(encounter));
    }

    public EncounterResponse getById(UUID encounterId) {
        return mapper.toResponse(findOrThrow(encounterId));
    }

    public List<EncounterResponse> getByPatientFile(UUID patientFileId) {
        return mapper.toResponseList(encounterRepository.findByPatientFileId(patientFileId));
    }


    public List<EncounterResponse> getByOrganization(UUID organizationId) {
        return mapper.toResponseList(encounterRepository.findAllByOrganizationId(organizationId));
    }

    public EncounterResponse update(UUID encounterId, UpdateEncounterRequest request) {
        Encounter encounter = findOrThrow(encounterId);

        rules.validateStatusTransition(encounter.getStatus(), request.status());

        if (request.status() != null) {
            encounter.setStatus(request.status());
        }
        if (request.chiefComplaint() != null) {
            encounter.setChiefComplaint(request.chiefComplaint());
        }
        if (request.notes() != null) {
            encounter.setNotes(request.notes());
        }

        return mapper.toResponse(encounterRepository.save(encounter));
    }

    public EncounterResponse complete(UUID encounterId) {
        Encounter encounter = findOrThrow(encounterId);
        rules.validateStatusTransition(encounter.getStatus(), EncounterStatus.COMPLETED);
        encounter.setStatus(EncounterStatus.COMPLETED);
        return mapper.toResponse(encounterRepository.save(encounter));
    }

    public EncounterResponse cancel(UUID encounterId) {
        Encounter encounter = findOrThrow(encounterId);
        rules.validateStatusTransition(encounter.getStatus(), EncounterStatus.CANCELLED);
        encounter.setStatus(EncounterStatus.CANCELLED);
        return mapper.toResponse(encounterRepository.save(encounter));
    }

    private Encounter findOrThrow(UUID id) {
        return encounterRepository.findById(id).orElseThrow(() -> new NotFoundException("Encounter not found: " + id));
    }
}
