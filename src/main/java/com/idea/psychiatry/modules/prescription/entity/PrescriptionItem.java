package com.idea.psychiatry.modules.prescription.entity;

import com.idea.psychiatry.shared.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "prescription_items")
@Getter
@Setter
public class PrescriptionItem extends BaseEntity {

    @Column(nullable = false)
    private UUID prescriptionId;

    @Column(nullable = false)
    private String medicationName;

    private String dosage;      // مثال: 10mg

    private String frequency;   // مثال: 2 times a day

    private String duration;    // مثال: 7 days

    private String instructions;

    @Column(nullable = false)
    private Integer sortOrder;
}
