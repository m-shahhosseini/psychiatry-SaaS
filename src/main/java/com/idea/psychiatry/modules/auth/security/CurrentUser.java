package com.idea.psychiatry.modules.auth.security;


import com.idea.psychiatry.modules.user.enums.UserRole;

import com.idea.psychiatry.shared.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

/**
 * Thread-safe utility برای خواندن اطلاعات کاربر جاری از SecurityContext.
 *
 * SecurityContextHolder در Spring از ThreadLocal استفاده می‌کند —
 * هر thread (هر request) context مخصوص خودش را دارد، بنابراین
 * هیچ state مشترک و mutable وجود ندارد.
 *
 * نحوه استفاده:
 *   UUID orgId  = CurrentUser.getOrganizationId();
 *   UUID userId = CurrentUser.getUserId();
 *   boolean isAdmin = CurrentUser.isAdmin();
 */
public final class CurrentUser {

    private CurrentUser() {}

    // ── Core Getters ──────────────────────────

    public static UUID getUserId() {
        return getPrincipal().getUserId();
    }

    public static UUID getOrganizationId() {
        return getPrincipal().getOrganizationId();
    }

    public static String getEmail() {
        return getPrincipal().getUsername();
    }

    public static UserRole getRole() {
        return UserRole.valueOf(getPrincipal().getRole());
    }

    // ── Role Helpers ──────────────────────────

    public static boolean isAdmin() {
        return getRole() == UserRole.ADMIN;
    }

    public static boolean isSuperAdmin() {
        return getRole() == UserRole.SUPER_ADMIN;
    }

    public static boolean isDoctor() {
        return getRole() == UserRole.DOCTOR;
    }

    public static boolean isReceptionist() {
        return getRole() == UserRole.RECEPTIONIST;
    }

    public static boolean isNurse() {
        return getRole() == UserRole.NURSE;
    }

    /**
     * چک می‌کند کاربر جاری به organizationId داده‌شده تعلق دارد.
     * برای جلوگیری از دسترسی cross-tenant استفاده می‌شود.
     */
    public static boolean belongsTo(UUID organizationId) {
        return getOrganizationId().equals(organizationId);
    }

    /**
     * اگر کاربر به سازمان دیگری تعلق داشته باشد exception می‌اندازد.
     */
    public static void requireSameOrganization(UUID organizationId) {
        if (!belongsTo(organizationId)) {
            throw new UnauthorizedException(
                    "Access denied: resource belongs to a different organization"
            );
        }
    }

    // ── private ───────────────────────────────

    private static CustomUserPrincipal getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || !(auth.getPrincipal() instanceof CustomUserPrincipal principal)) {
            throw new UnauthorizedException("No authenticated user in current context");
        }

        return principal;
    }
}
