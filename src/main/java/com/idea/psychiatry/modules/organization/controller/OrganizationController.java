package com.idea.psychiatry.modules.organization.controller;

import com.idea.psychiatry.modules.organization.dto.CreateOrganizationRequest;
import com.idea.psychiatry.modules.organization.dto.OrganizationResponse;
import com.idea.psychiatry.modules.organization.dto.UpdateOrganizationRequest;
import com.idea.psychiatry.modules.organization.service.OrganizationService;
import com.idea.psychiatry.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
@Tag(name = "Organization", description = "Organization (tenant) management")
public class OrganizationController {

    private final OrganizationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new organization (tenant)")
    public ApiResponse<OrganizationResponse> create(@Valid @RequestBody CreateOrganizationRequest request) {
        return ApiResponse.<OrganizationResponse>builder().success(true).message("Organization created").data(service.create(request)).build();
    }

    @GetMapping("/{organizationId}")
    @Operation(summary = "Get organization by ID")
    public ApiResponse<OrganizationResponse> getById(@PathVariable UUID organizationId) {
        return ApiResponse.<OrganizationResponse>builder().success(true).data(service.getById(organizationId)).build();
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get organization by code (e.g. CLINIC-TEHRAN)")
    public ApiResponse<OrganizationResponse> getByCode(@PathVariable String code) {
        return ApiResponse.<OrganizationResponse>builder().success(true).data(service.getByCode(code)).build();
    }

    @GetMapping
    @Operation(summary = "Get all organizations")
    public ApiResponse<List<OrganizationResponse>> getAll() {
        return ApiResponse.<List<OrganizationResponse>>builder().success(true).data(service.getAll()).build();
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active organizations")
    public ApiResponse<List<OrganizationResponse>> getAllActive() {
        return ApiResponse.<List<OrganizationResponse>>builder().success(true).data(service.getAllActive()).build();
    }

    @PutMapping("/{organizationId}")
    @Operation(summary = "Update organization details")
    public ApiResponse<OrganizationResponse> update(@PathVariable UUID organizationId, @Valid @RequestBody UpdateOrganizationRequest request) {
        return ApiResponse.<OrganizationResponse>builder().success(true).message("Organization updated").data(service.update(organizationId, request)).build();
    }

    @PatchMapping("/{organizationId}/deactivate")
    @Operation(summary = "Deactivate an organization")
    public ApiResponse<OrganizationResponse> deactivate(@PathVariable UUID organizationId) {
        return ApiResponse.<OrganizationResponse>builder().success(true).message("Organization deactivated").data(service.deactivate(organizationId)).build();
    }

    @PatchMapping("/{organizationId}/activate")
    @Operation(summary = "Activate an organization")
    public ApiResponse<OrganizationResponse> activate(@PathVariable UUID organizationId) {
        return ApiResponse.<OrganizationResponse>builder().success(true).message("Organization activated").data(service.activate(organizationId)).build();
    }
}
