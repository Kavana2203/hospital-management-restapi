package com.hospital.management.service;

import com.hospital.management.dto.DoctorRegistrationRequest;
import com.hospital.management.dto.PatientRegistrationRequest;
import com.hospital.management.model.Doctor;
import com.hospital.management.model.Patient;
import com.hospital.management.model.User;
import com.hospital.management.model.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    public void registerPatient(PatientRegistrationRequest request) {
        userService.validateUsername(request.getUsername());
        User user = userService.createUser(request.getUsername(), request.getPassword(), Set.of(Role.PATIENT));
        Patient patient = patientService.createPatient(user, request);
    }

    public void registerDoctor(@Valid DoctorRegistrationRequest request) {
        userService.validateUsername(request.getUsername());
        User user = userService.createUser(request.getUsername(), request.getPassword(), Set.of(Role.DOCTOR));
        Doctor doctor = doctorService.createDoctor(user, request);
    }
}
