package com.hospital.management.repository;

import com.hospital.management.model.Appointment;
import com.hospital.management.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByDoctorIdAndAppointmentDateTimeAndStatus(
            Long doctorId,
            LocalDateTime appointmentTime,
            AppointmentStatus status
    );

    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
}
