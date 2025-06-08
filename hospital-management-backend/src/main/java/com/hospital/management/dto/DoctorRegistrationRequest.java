package com.hospital.management.dto;


import com.hospital.management.model.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class DoctorRegistrationRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Specialization must be specified")
    private String specialization;

    @NotNull(message = "Qualification must be specified")
    private String qualification;

    @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Phone number is invalid")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email invalid")
    private String email;

    @NotNull(message = "Experinece years is required")
    @Min(value = 0, message = "Experience years must be 0 or more")
    private int experienceYears;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
