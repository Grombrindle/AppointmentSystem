package com.AppointmentSystem.AppointmentSystem.service.interfaces;

import com.AppointmentSystem.AppointmentSystem.model.User;

public interface StaffService {
    User registerStaff(String name, String email, String password,
            String specialty, String licenseNumber);

    User getStaffById(Long id);
}