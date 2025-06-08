package com.hospital.management.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class TreatmentHistoryRegistrationRequest {

    @NotBlank(message = "Diagnosis cannot be blank")
    private String diagnosis;

    @NotEmpty(message = "At least one prescribed medicine is required")
    private List<@Valid PrescribedMedicineRegistrationRequest> prescribedMedicines;

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<PrescribedMedicineRegistrationRequest> getPrescribedMedicines() {
        return prescribedMedicines;
    }

    public void setPrescribedMedicines(List<PrescribedMedicineRegistrationRequest> prescribedMedicines) {
        this.prescribedMedicines = prescribedMedicines;
    }
}
