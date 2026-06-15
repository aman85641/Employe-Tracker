package com.employee.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.employee.tracker.model.Role;
import com.employee.tracker.model.User;
import com.employee.tracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EmployeeTrackerApplication {
    @jakarta.annotation.PostConstruct
    public void init() {
        // Set the default timezone to IST to ensure check-in/out times match local time
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("Asia/Kolkata"));
    }

    public static void main(String[] args) {
        SpringApplication.run(EmployeeTrackerApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@admin.com")) {
                User admin = new User("Admin", "admin@admin.com", passwordEncoder.encode("admin123"), Role.ROLE_ADMIN);
                userRepository.save(admin);
            }
        };
    }
}
