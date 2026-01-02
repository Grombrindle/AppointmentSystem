package com.AppointmentSystem.AppointmentSystem.service.impl;

import com.AppointmentSystem.AppointmentSystem.enums.UserRole;
import com.AppointmentSystem.AppointmentSystem.exception.NotFoundException;
import com.AppointmentSystem.AppointmentSystem.model.User;
import com.AppointmentSystem.AppointmentSystem.repository.UserRepository;
import com.AppointmentSystem.AppointmentSystem.service.interfaces.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerCustomer(String name, String email, String password,
            String phoneNumber, String address) {
        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User customer = new User();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(passwordEncoder.encode(password));
        customer.setRole(UserRole.CUSTOMER);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);
        customer.setActive(true);

        return userRepository.save(customer);
    }

    @Override
    public User getCustomerById(Long id) {
        return userRepository.findById(id)
                .filter(user -> user.getRole() == UserRole.CUSTOMER)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
    }
}