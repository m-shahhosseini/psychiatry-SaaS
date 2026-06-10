package com.idea.psychiatry.modules.prescription.service;


import com.idea.psychiatry.modules.auth.security.CurrentUser;
import com.idea.psychiatry.modules.prescription.dto.*;
import com.idea.psychiatry.modules.prescription.entity.Prescription;
import com.idea.psychiatry.modules.prescription.entity.PrescriptionItem;
import com.idea.psychiatry.modules.prescription.mapper.PrescriptionMapper;
import com.idea.psychiatry.modules.prescription.repository.PrescriptionItemRepository;
import com.idea.psychiatry.modules.prescription.repository.PrescriptionRepository;
import com.idea.psychiatry.modules.prescription.rules.PrescriptionRules;
import com.idea.psychiatry.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionItemRepository prescriptionItemRepository;
    private final PrescriptionMapper mapper;
    private final PrescriptionRules rules;

    @Transactional
    public PrescriptionResponse create(CreatePrescriptionRequest request) {
        rules.validatePatientFileIsOpen(request.patientFileId());
        rules.validateEncounterIsActive(request.encounterId());
        rules.validateHasItems(request.items().size());

        Prescription prescription = new Prescription();
        prescription.setOrganizationId(CurrentUser.getOrganizationId());
        prescription.setPatientFileId(request.patientFileId());
        prescription.setEncounterId(request.encounterId());
        prescription.setPrescriberId(request.prescriberId());
        prescription.setPrescribedAt(Instant.now());
        prescription.setNotes(request.notes());

        Prescription saved = prescriptionRepository.save(prescription);
        prescriptionItemRepository.saveAll(buildItems(request.items(), saved.getId()));

        return toFullResponse(saved);
    }

    public PrescriptionResponse getById(UUID prescriptionId) {
        Prescription prescription = findOrThrow(prescriptionId);
        CurrentUser.requireSameOrganization(prescription.getOrganizationId());
        return toFullResponse(prescription);
    }

    public List<PrescriptionResponse> getByEncounter(UUID encounterId) {
        return prescriptionRepository.findByEncounterId(encounterId)
                .stream().map(this::toFullResponse).toList();
    }

    public List<PrescriptionResponse> getByPatientFile(UUID patientFileId) {
        return prescriptionRepository.findByPatientFileId(patientFileId)
                .stream().map(this::toFullResponse).toList();
    }

    @Transactional
    public PrescriptionResponse addItems(UUID prescriptionId, AddItemRequest request) {
        Prescription prescription = findOrThrow(prescriptionId);
        CurrentUser.requireSameOrganization(prescription.getOrganizationId());
        rules.validateHasItems(request.items().size());
        prescriptionItemRepository.saveAll(buildItems(request.items(), prescriptionId));
        return toFullResponse(prescription);
    }

    @Transactional
    public void removeItem(UUID prescriptionId, UUID itemId) {
        Prescription prescription = findOrThrow(prescriptionId);
        CurrentUser.requireSameOrganization(prescription.getOrganizationId());

        PrescriptionItem item = prescriptionItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("PrescriptionItem not found: " + itemId));
        rules.validateItemBelongsToPrescription(item.getPrescriptionId(), prescriptionId);

        List<PrescriptionItem> remaining = prescriptionItemRepository
                .findByPrescriptionIdOrderBySortOrder(prescriptionId);
        rules.validateHasItems(remaining.size() - 1);

        prescriptionItemRepository.delete(item);
    }

    // ── private ──────────────────────────────

    private Prescription findOrThrow(UUID id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prescription not found: " + id));
    }

    private List<PrescriptionItem> buildItems(List<PrescriptionItemRequest> requests, UUID prescriptionId) {
        return requests.stream().map(req -> {
            PrescriptionItem item = new PrescriptionItem();
            item.setPrescriptionId(prescriptionId);
            item.setMedicationName(req.medicationName());
            item.setDosage(req.dosage());
            item.setFrequency(req.frequency());
            item.setDuration(req.duration());
            item.setInstructions(req.instructions());
            item.setSortOrder(req.sortOrder());
            return item;
        }).toList();
    }

    private PrescriptionResponse toFullResponse(Prescription prescription) {
        List<PrescriptionItemResponse> items = mapper.toItemResponseList(
                prescriptionItemRepository.findByPrescriptionIdOrderBySortOrder(prescription.getId())
        );
        PrescriptionResponse base = mapper.toResponse(prescription);
        return new PrescriptionResponse(
                base.prescriptionId(), base.patientFileId(), base.encounterId(),
                base.prescriberId(), base.organizationId(), base.prescribedAt(),
                base.notes(), items
        );
    }
}
