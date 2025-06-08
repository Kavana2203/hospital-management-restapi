package com.hospital.management.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "treatment_history")
public class TreatmentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private String diagnosis;

    @OneToMany(mappedBy = "treatmentHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PrescribedMedicine> prescribedMedicines;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<PrescribedMedicine> getPrescribedMedicines() {
        return prescribedMedicines;
    }

    public void setPrescribedMedicines(List<PrescribedMedicine> prescribedMedicines) {
        this.prescribedMedicines = prescribedMedicines;
    }
}
