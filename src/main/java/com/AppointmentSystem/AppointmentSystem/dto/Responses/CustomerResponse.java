package com.AppointmentSystem.AppointmentSystem.dto.Responses;

import lombok.Data;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isActive;
    
    public static CustomerResponse fromUser(com.AppointmentSystem.AppointmentSystem.model.User user) {
        CustomerResponse response = new CustomerResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAddress(user.getAddress());
        response.setActive(user.isActive());
        return response;
    }
}