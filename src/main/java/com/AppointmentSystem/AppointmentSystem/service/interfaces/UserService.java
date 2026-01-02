package com.AppointmentSystem.AppointmentSystem.service.interfaces;

import com.AppointmentSystem.AppointmentSystem.model.User;

import java.util.Optional;

public interface UserService {
    User createUser(User user);

    User getUserById(Long id);

    Optional<User> getUserByEmail(String email);
}