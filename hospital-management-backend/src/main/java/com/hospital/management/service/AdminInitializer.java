package com.hospital.management.service;

import com.hospital.management.model.Role;
import com.hospital.management.model.User;
import com.hospital.management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String username = "admin";

            if (!userRepository.existsByUsername(username)) {
                User admin = new User();
                admin.setUsername(username);
                admin.setPassword(passwordEncoder.encode("Admin@123"));
                admin.setEnabled(true);
                admin.setRoles(Set.of(Role.ADMIN));

                userRepository.save(admin);
                System.out.println("Default admin created: username=admin, password=Admin@123");
            }
        };
    }
}

