package com.idea.psychiatry.modules.user.repository;

import com.idea.psychiatry.modules.user.entity.User;
import com.idea.psychiatry.modules.user.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    List<User> findAllByOrganizationId(UUID organizationId);

    List<User> findByOrganizationIdAndRole(UUID organizationId, UserRole role);

    List<User> findByOrganizationIdAndActiveTrue(UUID organizationId);

    boolean existsByEmail(String email);

    boolean existsByOrganizationIdAndRole(UUID organizationId, UserRole role);
}
