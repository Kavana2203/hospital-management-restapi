package com.hospital.management.controller;

import com.hospital.management.dto.TreatmentHistoryRegistrationRequest;
import com.hospital.management.model.Appointment;
import com.hospital.management.model.TreatmentHistory;
import com.hospital.management.service.DoctorService;

import com.hospital.management.service.TreatmentHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctor")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private TreatmentHistoryService treatmentHistoryService;

    @GetMapping("/appointments")
    public List<Appointment> getMyAppointments() {
        return doctorService.getAppointmentsForDoctor();
    }

    @PutMapping("/cancel-appointment/{id}")
    public ResponseEntity<?> cancelAppointmentByPatient(@PathVariable long id){
        doctorService.cancelAppointmentByDoctor(id);
        return ResponseEntity.ok("Appointment cancelled successfully.");
    }

    @PutMapping("/doctor/complete-appointment/{id}")
    public ResponseEntity<?> completeAppointmentAndAddTreatment(@PathVariable Long id, @Valid @RequestBody TreatmentHistoryRegistrationRequest request, BindingResult result) {

        if(result.hasErrors()){
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }

        treatmentHistoryService.completeAppointmentAndSaveTreatment(id,request);
        return ResponseEntity.ok("Appointment completed and treatment recorded.");
    }

    @GetMapping("/treatment-history/{id}")
    public ResponseEntity<List<TreatmentHistory>> getTreatmentHistoryOfPatient(@PathVariable Long id) {
        List<TreatmentHistory> history = treatmentHistoryService.getPatientTreatmentHistoryForDoctor(id);
        return ResponseEntity.ok(history);
    }
}
