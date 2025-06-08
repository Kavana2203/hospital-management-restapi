package com.hospital.management.repository;

import com.hospital.management.model.PrescribedMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescribedMedicineRepository extends JpaRepository<PrescribedMedicine, Long> {
}
