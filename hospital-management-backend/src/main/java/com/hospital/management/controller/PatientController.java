package com.hospital.management.controller;

import com.hospital.management.dto.AppointmentRegistrationRequest;
import com.hospital.management.model.Appointment;
import com.hospital.management.model.TreatmentHistory;
import com.hospital.management.service.PatientService;
import com.hospital.management.service.TreatmentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/patient")
@PreAuthorize("hasRole('PATIENT')")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private TreatmentHistoryService treatmentHistoryService;

    @GetMapping("/appointments")
    public List<Appointment> getMyAppointments() {
        return patientService.getAppointmentsForPatient();
    }

    @PutMapping("/cancel-appointment/{id}")
    public ResponseEntity<?> cancelAppointmentByPatient(@PathVariable long id){
        patientService.cancelAppointmentByPatient(id);
        return ResponseEntity.ok("Appointment cancelled successfully.");
    }

    @GetMapping("/treatment-history")
    public ResponseEntity<List<TreatmentHistory>> getTreatmentHistory() {
        List<TreatmentHistory> history = treatmentHistoryService.getPatientTreatmentHistory();
        return ResponseEntity.ok(history);
    }
}
