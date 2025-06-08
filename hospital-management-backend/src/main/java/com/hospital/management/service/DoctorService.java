package com.hospital.management.service;

import com.hospital.management.dto.DoctorRegistrationRequest;
import com.hospital.management.model.Appointment;
import com.hospital.management.model.AppointmentStatus;
import com.hospital.management.model.Doctor;
import com.hospital.management.model.User;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AuthUtils authUtils;

    public Doctor createDoctor(User user, DoctorRegistrationRequest request) {
        Doctor doctor = new Doctor();

        doctor.setUser(user);
        doctor.setFullName(request.getFullName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());

        return doctorRepository.save(doctor);
    }

    public Doctor getDoctor(long id) {
        return doctorRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Doctor doesnot exists"));
    }

    public List<Appointment> getAppointmentsForDoctor() {
        User loggedInUser = authUtils.getLoggedInUser();
        return appointmentRepository.findByDoctorId(loggedInUser.getId());
    }

    public void cancelAppointmentByDoctor(long appointmentId) {
        User loggedInUser = authUtils.getLoggedInUser();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found"));

        if (!appointment.getDoctor().getId().equals(loggedInUser.getId())) {
            throw new IllegalStateException("You are not allowed to cancel this appointment.");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Appointment is already cancelled.");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(appointment.getAppointmentDateTime().minusMinutes(30))) {
            throw new IllegalStateException("Cannot cancel appointment less than 30 minutes before the time.");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }
}
