package com.idea.psychiatry.modules.patient.rules;

import com.idea.psychiatry.modules.patientfile.entity.PatientFile;
import com.idea.psychiatry.modules.patientfile.enums.PatientFileStatus;
import com.idea.psychiatry.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class PatientRules {


    /**
     * قوانین مربوط به پرونده بیمار (PatientFile)
     *
     * این کلاس مسئول بررسی صحت عملیات روی پرونده بیمار است.
     * قبل از هر عملیات مهم مثل ایجاد Encounter یا تغییر وضعیت پرونده،
     * این قوانین باید اجرا شوند.
     *
     * قوانین:
     * - عملیات روی پرونده بسته (CLOSED) مجاز نیست
     */

    public void validateFileIsOpen(PatientFile file) {

        if (file.getStatus() == PatientFileStatus.CLOSED) {
            throw new BusinessException(
                    "Cannot perform operation on CLOSED patient file"
            );
        }
    }
}
