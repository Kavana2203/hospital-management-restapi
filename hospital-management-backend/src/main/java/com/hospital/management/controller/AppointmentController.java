package com.hospital.management.controller;

import com.hospital.management.dto.AppointmentRegistrationRequest;
import com.hospital.management.model.Appointment;
import com.hospital.management.model.Medicine;
import com.hospital.management.service.AppointmentService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> addAppointment(@Valid @RequestBody AppointmentRegistrationRequest request, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }

        Appointment appointment = appointmentService.addAppointment(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointment registered successfully");
        response.put("id", appointment.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllAppointments(){
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }
}
