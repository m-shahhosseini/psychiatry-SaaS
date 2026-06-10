package com.idea.psychiatry.modules.organization.mapper;

import com.idea.psychiatry.modules.organization.dto.OrganizationResponse;
import com.idea.psychiatry.modules.organization.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    @Mapping(source = "id", target = "organizationId")
    OrganizationResponse toResponse(Organization organization);

    List<OrganizationResponse> toResponseList(List<Organization> organizations);
}
