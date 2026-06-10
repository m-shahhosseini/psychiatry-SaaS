package com.idea.psychiatry.modules.organization.repository;

import com.idea.psychiatry.modules.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    Optional<Organization> findByCode(String code);

    Optional<Organization> findByName(String name);

    List<Organization> findAllByActiveTrue();

    boolean existsByCode(String code);

    boolean existsByName(String name);
}
