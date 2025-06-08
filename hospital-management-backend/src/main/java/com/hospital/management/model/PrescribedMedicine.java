package com.hospital.management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "prescribed_medicines")
public class PrescribedMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "treatment_id")
    @JsonBackReference
    private TreatmentHistory treatmentHistory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private String dosage;       // e.g., "1 tablet"
    private String instructions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TreatmentHistory getTreatmentHistory() {
        return treatmentHistory;
    }

    public void setTreatmentHistory(TreatmentHistory treatmentHistory) {
        this.treatmentHistory = treatmentHistory;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
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
