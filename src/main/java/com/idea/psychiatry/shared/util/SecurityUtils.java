package com.idea.psychiatry.shared.util;


import com.idea.psychiatry.modules.auth.security.CustomUserPrincipal;
import com.idea.psychiatry.shared.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

/**
 * Helper برای خواندن اطلاعات کاربر جاری از SecurityContext.
 * جایگزین X-Organization-Id header می‌شود در تمام service ها.
 *
 * استفاده:
 *   UUID orgId = SecurityUtils.getCurrentOrganizationId();
 *   UUID userId = SecurityUtils.getCurrentUserId();
 */
public class SecurityUtils {

    private SecurityUtils() {}

    public static CustomUserPrincipal getCurrentPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()
                || !(auth.getPrincipal() instanceof CustomUserPrincipal principal)) {
            throw new UnauthorizedException("No authenticated user found");
        }
        return principal;
    }

    public static UUID getCurrentUserId() {
        return getCurrentPrincipal().getUserId();
    }

    public static UUID getCurrentOrganizationId() {
        return getCurrentPrincipal().getOrganizationId();
    }

    public static String getCurrentRole() {
        return getCurrentPrincipal().getRole();
    }
}
