package com.hospital.management.service;

import com.hospital.management.dto.PatientRegistrationRequest;
import com.hospital.management.model.Role;
import com.hospital.management.model.User;
import com.hospital.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerPatient(PatientRegistrationRequest result) {

    }

    public void validateUsername(String username) {
        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("Username already exists.");
        }
    }

    public User createUser(String username, String password, Set<Role> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        user.setEnabled(true);
        return userRepository.save(user);
    }
}
