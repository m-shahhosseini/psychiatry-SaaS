package com.idea.psychiatry.modules.user.service;

import com.idea.psychiatry.modules.user.dto.*;
import com.idea.psychiatry.modules.user.entity.User;
import com.idea.psychiatry.modules.user.enums.UserRole;
import com.idea.psychiatry.modules.user.mapper.UserMapper;
import com.idea.psychiatry.modules.user.repository.UserRepository;
import com.idea.psychiatry.modules.user.rules.UserRules;
import com.idea.psychiatry.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final UserRules rules;
    private final PasswordEncoder passwordEncoder;

    public UserResponse create(CreateUserRequest request) {
        rules.validateEmailIsUnique(userRepository.existsByEmail(request.email()));
        rules.validatePasswordStrength(request.password());
        rules.validateSingleAdmin(request.role(), userRepository.existsByOrganizationIdAndRole(request.organizationId(), UserRole.ADMIN));

        User user = new User();
        user.setOrganizationId(request.organizationId());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email().toLowerCase().trim());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        user.setActive(true);

        return mapper.toResponse(userRepository.save(user));
    }

    public UserResponse getById(UUID userId) {
        return mapper.toResponse(findOrThrow(userId));
    }

    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email.toLowerCase().trim()).orElseThrow(() -> new NotFoundException("User not found: " + email));
        return mapper.toResponse(user);
    }

    public List<UserResponse> getByOrganization(UUID organizationId) {
        return mapper.toResponseList(userRepository.findAllByOrganizationId(organizationId));
    }

    public List<UserResponse> getByOrganizationAndRole(UUID organizationId, UserRole role) {
        return mapper.toResponseList(userRepository.findByOrganizationIdAndRole(organizationId, role));
    }

    public UserResponse update(UUID userId, UpdateUserRequest request) {
        User user = findOrThrow(userId);
        rules.validateIsActive(user);

        if (request.firstName() != null) {
            user.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            user.setLastName(request.lastName());
        }

        return mapper.toResponse(userRepository.save(user));
    }

    public void changePassword(UUID userId, ChangePasswordRequest request) {
        User user = findOrThrow(userId);
        rules.validateIsActive(user);
        rules.validateCurrentPassword(request.currentPassword(), user.getPasswordHash());
        rules.validatePasswordStrength(request.newPassword());
        rules.validateNewPasswordIsDifferent(request.newPassword(), user.getPasswordHash());

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    public UserResponse deactivate(UUID userId) {
        User user = findOrThrow(userId);
        rules.validateIsActive(user);
        user.setActive(false);
        return mapper.toResponse(userRepository.save(user));
    }

    public UserResponse activate(UUID userId) {
        User user = findOrThrow(userId);
        if (user.isActive()) {
            throw new com.idea.psychiatry.shared.exception.BusinessException("User is already active");
        }
        user.setActive(true);
        return mapper.toResponse(userRepository.save(user));
    }

    // ── private ──────────────────────────────

    private User findOrThrow(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

}
