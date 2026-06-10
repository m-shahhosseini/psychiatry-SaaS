package com.idea.psychiatry.modules.user.controller;

import com.idea.psychiatry.modules.user.dto.*;
import com.idea.psychiatry.modules.user.enums.UserRole;
import com.idea.psychiatry.modules.user.service.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management")
public class UserController {
    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user")
    public ApiResponse<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User created")
                .data(service.create(request))
                .build();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID")
    public ApiResponse<UserResponse> getById(@PathVariable UUID userId) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .data(service.getById(userId))
                .build();
    }

    @GetMapping("/organization/{organizationId}")
    @Operation(summary = "Get all users in an organization")
    public ApiResponse<List<UserResponse>> getByOrganization(@PathVariable UUID organizationId) {
        return ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .data(service.getByOrganization(organizationId))
                .build();
    }

    @GetMapping("/organization/{organizationId}/role/{role}")
    @Operation(summary = "Get users by organization and role")
    public ApiResponse<List<UserResponse>> getByOrganizationAndRole(
            @PathVariable UUID organizationId,
            @PathVariable UserRole role) {
        return ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .data(service.getByOrganizationAndRole(organizationId, role))
                .build();
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user name")
    public ApiResponse<UserResponse> update(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User updated")
                .data(service.update(userId, request))
                .build();
    }

    @PatchMapping("/{userId}/change-password")
    @Operation(summary = "Change user password")
    public ApiResponse<Void> changePassword(
            @PathVariable UUID userId,
            @Valid @RequestBody ChangePasswordRequest request) {
        service.changePassword(userId, request);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Password changed successfully")
                .build();
    }

    @PatchMapping("/{userId}/deactivate")
    @Operation(summary = "Deactivate a user")
    public ApiResponse<UserResponse> deactivate(@PathVariable UUID userId) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User deactivated")
                .data(service.deactivate(userId))
                .build();
    }

    @PatchMapping("/{userId}/activate")
    @Operation(summary = "Activate a user")
    public ApiResponse<UserResponse> activate(@PathVariable UUID userId) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User activated")
                .data(service.activate(userId))
                .build();
    }
}
