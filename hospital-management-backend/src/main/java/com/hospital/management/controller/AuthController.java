package com.hospital.management.controller;

import com.hospital.management.dto.DoctorRegistrationRequest;
import com.hospital.management.dto.LoginRequest;
import com.hospital.management.dto.PatientRegistrationRequest;
import com.hospital.management.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody PatientRegistrationRequest request, BindingResult result){

        //check validations
        if(result.hasErrors()){
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }

        authService.registerPatient(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Patient registered successfully");
        response.put("username", request.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register-doctor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> RegisterPatient(@Valid @RequestBody DoctorRegistrationRequest request, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
        }

        authService.registerDoctor(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Doctor registered successfully");
        response.put("username", request.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            request.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok("Login successful "+loginRequest.getUsername());
        } catch (ServletException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
