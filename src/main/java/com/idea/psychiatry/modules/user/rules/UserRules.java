package com.idea.psychiatry.modules.user.rules;

import com.idea.psychiatry.modules.user.entity.User;
import com.idea.psychiatry.modules.user.enums.UserRole;
import com.idea.psychiatry.shared.exception.BusinessException;
import com.idea.psychiatry.shared.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRules {
    private final PasswordEncoder passwordEncoder;

    /**
     * ایمیل باید در کل سیستم یکتا باشد.
     */
    public void validateEmailIsUnique(boolean exists) {
        if (exists) {
            throw new ConflictException("A user with this email already exists");
        }
    }

    /**
     * هر سازمان فقط یک ADMIN می‌تواند داشته باشد.
     */
    public void validateSingleAdmin(UserRole role, boolean adminExists) {
        if (role == UserRole.ADMIN && adminExists) {
            throw new ConflictException("Organization already has an ADMIN user");
        }
    }

    /**
     * رمز عبور باید حداقل ۸ کاراکتر، یک حرف بزرگ، یک عدد داشته باشد.
     */
    public void validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new BusinessException("Password must be at least 8 characters");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new BusinessException("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new BusinessException("Password must contain at least one number");
        }
    }

    /**
     * رمز عبور فعلی صحیح باشد.
     */
    public void validateCurrentPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BusinessException("Current password is incorrect");
        }
    }

    /**
     * رمز عبور جدید با رمز قبلی یکسان نباشد.
     */
    public void validateNewPasswordIsDifferent(String newPassword, String encodedOldPassword) {
        if (passwordEncoder.matches(newPassword, encodedOldPassword)) {
            throw new BusinessException("New password must be different from current password");
        }
    }

    /**
     * عملیات روی کاربر غیرفعال مجاز نیست.
     */
    public void validateIsActive(User user) {
        if (!user.isActive()) {
            throw new BusinessException("Cannot perform operation on an inactive user");
        }
    }
}
