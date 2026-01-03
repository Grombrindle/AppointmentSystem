package com.AppointmentSystem.AppointmentSystem.service;

import com.AppointmentSystem.AppointmentSystem.dto.Requests.ServiceRequestDTO;
import com.AppointmentSystem.AppointmentSystem.enums.UserRole;
import com.AppointmentSystem.AppointmentSystem.exception.NotFoundException;
import com.AppointmentSystem.AppointmentSystem.exception.ValidationException;
import com.AppointmentSystem.AppointmentSystem.model.Service;
import com.AppointmentSystem.AppointmentSystem.model.User;
import com.AppointmentSystem.AppointmentSystem.repository.ServiceRepository;
import com.AppointmentSystem.AppointmentSystem.repository.UserRepository;
import com.AppointmentSystem.AppointmentSystem.service.impl.ServiceServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceServiceImplTest {

    @Test
    void createService_success() {
        ServiceRepository serviceRepo = mock(ServiceRepository.class);
        UserRepository userRepo = mock(UserRepository.class);
        ServiceServiceImpl svc = new ServiceServiceImpl(serviceRepo, userRepo);

        ServiceRequestDTO dto = new ServiceRequestDTO();
        dto.setName("Haircut");
        dto.setDescription("desc");
        dto.setDuration(30);
        dto.setPrice(20.0);
        dto.setProviderId(11L);

        User provider = new User();
        provider.setId(11L);
        provider.setRole(UserRole.STAFF);

        when(userRepo.findById(11L)).thenReturn(Optional.of(provider));

        Service saved = new Service();
        saved.setId(2L);
        when(serviceRepo.save(any(Service.class))).thenReturn(saved);

        Service result = svc.createService(dto);
        assertNotNull(result);
        assertEquals(2L, result.getId());
    }

    @Test
    void createService_providerNotFound_throwsNotFound() {
        ServiceRepository serviceRepo = mock(ServiceRepository.class);
        UserRepository userRepo = mock(UserRepository.class);
        ServiceServiceImpl svc = new ServiceServiceImpl(serviceRepo, userRepo);

        ServiceRequestDTO dto = new ServiceRequestDTO();
        dto.setProviderId(99L);

        when(userRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> svc.createService(dto));
    }

    @Test
    void createService_providerNotStaff_throwsValidation() {
        ServiceRepository serviceRepo = mock(ServiceRepository.class);
        UserRepository userRepo = mock(UserRepository.class);
        ServiceServiceImpl svc = new ServiceServiceImpl(serviceRepo, userRepo);

        ServiceRequestDTO dto = new ServiceRequestDTO();
        dto.setProviderId(12L);

        User provider = new User();
        provider.setId(12L);
        provider.setRole(UserRole.CUSTOMER);

        when(userRepo.findById(12L)).thenReturn(Optional.of(provider));

        assertThrows(ValidationException.class, () -> svc.createService(dto));
    }

    @Test
    void getServiceById_notFound_throwsNotFound() {
        ServiceRepository serviceRepo = mock(ServiceRepository.class);
        UserRepository userRepo = mock(UserRepository.class);
        ServiceServiceImpl svc = new ServiceServiceImpl(serviceRepo, userRepo);

        when(serviceRepo.findByIdAndIsActiveTrue(5L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> svc.getServiceById(5L));
    }
}
