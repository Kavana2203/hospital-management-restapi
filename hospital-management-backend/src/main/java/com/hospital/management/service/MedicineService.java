package com.hospital.management.service;

import com.hospital.management.dto.MedicineRegistrationRequest;
import com.hospital.management.model.Medicine;
import com.hospital.management.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public Medicine addMedicine(MedicineRegistrationRequest request){
        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setBrand(request.getBrand());
        medicine.setDescription(request.getDescription());

        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(Long id){
        medicineRepository.deleteById(id);
    }

    public Medicine getMedicineById(long id) {
        return medicineRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Medicine not exists"));
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }
}
