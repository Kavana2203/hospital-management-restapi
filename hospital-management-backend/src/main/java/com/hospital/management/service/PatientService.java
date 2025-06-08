package com.hospital.management.service;

import com.hospital.management.dto.PatientRegistrationRequest;
import com.hospital.management.model.Appointment;
import com.hospital.management.model.AppointmentStatus;
import com.hospital.management.model.Patient;
import com.hospital.management.model.User;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AuthUtils authUtils;

    public Patient createPatient(User user, PatientRegistrationRequest request) {

        Patient patient = new Patient();

        patient.setUser(user);
        patient.setAddress(request.getAddress());
        patient.setEmail(request.getEmail());
        patient.setGender(request.getGender());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setPhone(request.getPhone());
        patient.setFullName(request.getFullName());

        return patientRepository.save(patient);
    }

    public Patient getPatient(long id) {
        return patientRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Patient doesnot exists"));
    }

    public List<Appointment> getAppointmentsForPatient() {
        User loggedInUser = authUtils.getLoggedInUser();
        return appointmentRepository.findByPatientId(loggedInUser.getId());
    }

    public void cancelAppointmentByPatient(long appointmentId) {
        User loggedInUser = authUtils.getLoggedInUser();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found"));

        if (!appointment.getPatient().getId().equals(loggedInUser.getId())) {
            throw new IllegalStateException("You are not allowed to cancel this appointment.");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Appointment is already cancelled.");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(appointment.getAppointmentDateTime().minusMinutes(30))) {
            throw new IllegalStateException("Cannot cancel appointment less than 30 minutes before the time.");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

}
