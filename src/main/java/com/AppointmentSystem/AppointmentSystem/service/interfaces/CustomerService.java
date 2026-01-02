package com.AppointmentSystem.AppointmentSystem.service.interfaces;

import com.AppointmentSystem.AppointmentSystem.model.User;

public interface CustomerService {
    User registerCustomer(String name, String email, String password,
            String phoneNumber, String address);

    User getCustomerById(Long id);
}