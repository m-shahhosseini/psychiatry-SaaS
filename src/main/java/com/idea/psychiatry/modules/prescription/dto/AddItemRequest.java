package com.idea.psychiatry.modules.prescription.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

// برای اضافه کردن آیتم به نسخه موجود
public record AddItemRequest(
        @Valid
        @NotEmpty(message = "at least one item is required")
        List<PrescriptionItemRequest> items
) {}
