package com.AppointmentSystem.AppointmentSystem.service;

import com.AppointmentSystem.AppointmentSystem.enums.UserRole;
import com.AppointmentSystem.AppointmentSystem.exception.NotFoundException;
import com.AppointmentSystem.AppointmentSystem.model.User;
import com.AppointmentSystem.AppointmentSystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StaffService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerStaff(String name, String email, String password, String specialty, String licenseNumber) {
        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User staff = new User();
        staff.setName(name);
        staff.setEmail(email);
        staff.setPassword(passwordEncoder.encode(password));
        staff.setRole(UserRole.STAFF);
        staff.setSpecialty(specialty);
        staff.setLicenseNumber(licenseNumber);
        staff.setActive(true);

        return userRepository.save(staff);
    }

    public User getStaffById(Long id) {
        return userRepository.findById(id)
                .filter(user -> user.getRole() == UserRole.STAFF)
                .orElseThrow(() -> new NotFoundException("Staff member not found"));
    }
}