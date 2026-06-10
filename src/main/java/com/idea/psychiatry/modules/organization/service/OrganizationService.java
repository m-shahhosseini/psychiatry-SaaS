package com.idea.psychiatry.modules.organization.service;

import com.idea.psychiatry.modules.organization.dto.CreateOrganizationRequest;
import com.idea.psychiatry.modules.organization.dto.OrganizationResponse;
import com.idea.psychiatry.modules.organization.dto.UpdateOrganizationRequest;
import com.idea.psychiatry.modules.organization.entity.Organization;
import com.idea.psychiatry.modules.organization.mapper.OrganizationMapper;
import com.idea.psychiatry.modules.organization.repository.OrganizationRepository;
import com.idea.psychiatry.modules.organization.rules.OrganizationRules;
import com.idea.psychiatry.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper mapper;
    private final OrganizationRules rules;

    public OrganizationResponse create(CreateOrganizationRequest request) {
        rules.validateCodeIsUnique(organizationRepository.existsByCode(request.code()));
        rules.validateNameIsUnique(organizationRepository.existsByName(request.name()));

        Organization organization = new Organization();
        organization.setName(request.name());
        organization.setCode(request.code().toUpperCase().trim());
        organization.setAddress(request.address());
        organization.setPhone(request.phone());
        organization.setActive(true);

        return mapper.toResponse(organizationRepository.save(organization));
    }

    public OrganizationResponse getById(UUID organizationId) {
        return mapper.toResponse(findOrThrow(organizationId));
    }

    public OrganizationResponse getByCode(String code) {
        Organization organization = organizationRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Organization not found: " + code));
        return mapper.toResponse(organization);
    }

    public List<OrganizationResponse> getAll() {
        return mapper.toResponseList(organizationRepository.findAll());
    }

    public List<OrganizationResponse> getAllActive() {
        return mapper.toResponseList(organizationRepository.findAllByActiveTrue());
    }

    public OrganizationResponse update(UUID organizationId, UpdateOrganizationRequest request) {
        Organization organization = findOrThrow(organizationId);
        rules.validateIsActive(organization);

        // چک یکتایی نام فقط اگر تغییر کرده باشد
        if (request.name() != null && !request.name().equals(organization.getName())) {
            rules.validateNameIsUnique(organizationRepository.existsByName(request.name()));
            organization.setName(request.name());
        }
        if (request.address() != null) {
            organization.setAddress(request.address());
        }
        if (request.phone() != null) {
            organization.setPhone(request.phone());
        }

        return mapper.toResponse(organizationRepository.save(organization));
    }

    public OrganizationResponse deactivate(UUID organizationId) {
        Organization organization = findOrThrow(organizationId);
        rules.validateIsActive(organization);
        organization.setActive(false);
        return mapper.toResponse(organizationRepository.save(organization));
    }

    public OrganizationResponse activate(UUID organizationId) {
        Organization organization = findOrThrow(organizationId);
        if (organization.isActive()) {
            throw new com.idea.psychiatry.shared.exception.BusinessException("Organization is already active");
        }
        organization.setActive(true);
        return mapper.toResponse(organizationRepository.save(organization));
    }

    // ── private ──────────────────────────────

    private Organization findOrThrow(UUID id) {
        return organizationRepository.findById(id).orElseThrow(() -> new NotFoundException("Organization not found: " + id));
    }

}
