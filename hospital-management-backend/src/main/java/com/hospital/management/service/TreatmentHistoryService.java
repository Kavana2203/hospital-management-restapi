package com.hospital.management.service;

import com.hospital.management.dto.PrescribedMedicineRegistrationRequest;
import com.hospital.management.dto.TreatmentHistoryRegistrationRequest;
import com.hospital.management.model.*;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.MedicineRepository;
import com.hospital.management.repository.TreatmentHistoryRepository;
import com.hospital.management.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TreatmentHistoryService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private TreatmentHistoryRepository treatmentHistoryRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    public void completeAppointmentAndSaveTreatment(Long appointmentId, TreatmentHistoryRegistrationRequest request) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found"));

        if (!appointment.getDoctor().getId().equals(authUtils.getLoggedInUser().getId())) {
            throw new IllegalStateException("You are not allowed to cancel this appointment.");
        }

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Appointment status must be SCHEDULED to complete.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime scheduled = appointment.getAppointmentDateTime();

        System.out.println("NOW:       " + now);
        System.out.println("SCHEDULED: " + scheduled);

        if (now.isBefore(scheduled)) {
            throw new IllegalStateException("Cannot complete appointment before its scheduled time.");
        }

//        if (LocalDateTime.now().isBefore(appointment.getAppointmentDateTime())) {
//            throw new IllegalStateException("Cannot complete appointment before its scheduled time.");
//        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        TreatmentHistory treatmentHistory = new TreatmentHistory();
        treatmentHistory.setAppointment(appointment);
        treatmentHistory.setDiagnosis(request.getDiagnosis());

        List<PrescribedMedicine> prescribedMedicines = new ArrayList<>();
        for (PrescribedMedicineRegistrationRequest pmDTO : request.getPrescribedMedicines()) {
            Medicine medicine = medicineRepository.findById(pmDTO.getMedicineId())
                    .orElseThrow(() -> new IllegalArgumentException("Medicine not found: " + pmDTO.getMedicineId()));

            PrescribedMedicine pm = new PrescribedMedicine();
            pm.setMedicine(medicine);
            pm.setDosage(pmDTO.getDosage());
            pm.setInstructions(pmDTO.getInstructions());
            pm.setTreatmentHistory(treatmentHistory);
            prescribedMedicines.add(pm);
        }

        treatmentHistory.setPrescribedMedicines(prescribedMedicines);
        treatmentHistoryRepository.save(treatmentHistory);

    }

    public List<TreatmentHistory> getPatientTreatmentHistory() {
        User loggedInUser = authUtils.getLoggedInUser();
        Patient patient = patientService.getPatient(loggedInUser.getId());

        return treatmentHistoryRepository.findByAppointment_Patient_Id(patient.getId());
    }

    public List<TreatmentHistory> getPatientTreatmentHistoryForDoctor(Long id) {
        User loggedInUser = authUtils.getLoggedInUser();
        Doctor doctor = doctorService.getDoctor(loggedInUser.getId());

        Patient patient = patientService.getPatient(id);
        return treatmentHistoryRepository.findByAppointment_Patient_Id(patient.getId());
    }
}
