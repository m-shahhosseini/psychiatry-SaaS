package com.idea.psychiatry.modules.organization.rules;

import com.idea.psychiatry.modules.organization.entity.Organization;
import com.idea.psychiatry.shared.exception.BusinessException;
import com.idea.psychiatry.shared.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationRules {
    /**
     * کد سازمان باید یکتا باشد.
     */
    public void validateCodeIsUnique(boolean exists) {
        if (exists) {
            throw new ConflictException("Organization with this code already exists");
        }
    }

    /**
     * نام سازمان باید یکتا باشد.
     */
    public void validateNameIsUnique(boolean exists) {
        if (exists) {
            throw new ConflictException("Organization with this name already exists");
        }
    }

    /**
     * عملیات روی سازمان غیرفعال مجاز نیست.
     */
    public void validateIsActive(Organization organization) {
        if (!organization.isActive()) {
            throw new BusinessException("Cannot perform operation on an inactive organization");
        }
    }

}
