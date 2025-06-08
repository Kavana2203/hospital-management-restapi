package com.hospital.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PrescribedMedicineRegistrationRequest {

    @NotNull(message = "Medicine ID must not be null")
    private Long medicineId;

    @NotBlank(message = "Dosage is required")
    private String dosage;

    @NotBlank(message = "Dosage is required")
    private String instructions; // optional

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
