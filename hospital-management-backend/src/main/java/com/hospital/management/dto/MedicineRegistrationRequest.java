package com.hospital.management.dto;

import jakarta.validation.constraints.NotBlank;

public class MedicineRegistrationRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Name is required")
    private String brand;

    @NotBlank(message = "Name is required")
    private String description;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
