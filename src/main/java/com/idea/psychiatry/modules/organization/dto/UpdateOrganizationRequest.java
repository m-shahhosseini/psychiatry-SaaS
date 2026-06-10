package com.idea.psychiatry.modules.organization.dto;

public record UpdateOrganizationRequest(
        String name,
        String address,
        String phone
) {}
