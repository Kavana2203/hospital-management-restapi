package com.hospital.management.service;

import com.hospital.management.dto.AppointmentRegistrationRequest;
import com.hospital.management.model.*;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AuthUtils authUtils;

    public Appointment addAppointment(AppointmentRegistrationRequest request) {

        User loggedInUser = authUtils.getLoggedInUser();

        Patient patient = patientService.getPatient(loggedInUser.getId());
        Doctor doctor = doctorService.getDoctor(request.getDoctorId());

        if(!isSlotAvailable(doctor.getId(), request.getAppointmentDateTime())){
            throw new IllegalArgumentException("Slot not available");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setReason(request.getReason());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        return appointmentRepository.save(appointment);
    }

    private List<LocalTime> getAllowedSlots() {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(13, 0); // exclusive

        while (start.isBefore(end)) {
            slots.add(start);
            start = start.plusMinutes(30);
        }

        return slots;
    }

    private boolean isValidSlot(LocalTime requestedTime) {
        return getAllowedSlots().contains(requestedTime);
    }

    private boolean isSlotAvailable(Long doctorId, LocalDateTime appointmentDateTime) {
        if (!isValidSlot(appointmentDateTime.toLocalTime())) {
            return false;
        }
        return appointmentRepository
                .findByDoctorIdAndAppointmentDateTimeAndStatus(doctorId, appointmentDateTime, AppointmentStatus.SCHEDULED)
                .isEmpty();
    }


    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

}
