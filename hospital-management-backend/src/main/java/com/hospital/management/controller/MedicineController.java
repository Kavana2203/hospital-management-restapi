package com.hospital.management.controller;

import com.hospital.management.dto.MedicineRegistrationRequest;
import com.hospital.management.model.Medicine;
import com.hospital.management.repository.MedicineRepository;
import com.hospital.management.service.MedicineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addMedicine(@Valid @RequestBody MedicineRegistrationRequest request, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }

        Medicine medicine = medicineService.addMedicine(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Medicine registered successfully");
        response.put("name", medicine.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMedicine(@PathVariable long id){
        Medicine medicine = medicineService.getMedicineById(id);
        return ResponseEntity.ok(medicine);
    }

    @GetMapping
    public ResponseEntity<?> getAllMedicines(){
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMedicine(@PathVariable long id){
        medicineService.deleteMedicine(id);
        return ResponseEntity.ok().build();
    }
}
