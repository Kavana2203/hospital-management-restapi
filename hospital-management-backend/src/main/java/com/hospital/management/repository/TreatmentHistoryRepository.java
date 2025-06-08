package com.hospital.management.repository;

import com.hospital.management.model.TreatmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentHistoryRepository extends JpaRepository<TreatmentHistory, Long> {
    List<TreatmentHistory> findByAppointment_Patient_Id(Long patientId);
}
