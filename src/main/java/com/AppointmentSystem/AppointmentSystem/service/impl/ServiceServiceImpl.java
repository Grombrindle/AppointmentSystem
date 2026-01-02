package com.AppointmentSystem.AppointmentSystem.service.impl;

import com.AppointmentSystem.AppointmentSystem.dto.Requests.ServiceRequestDTO;
import com.AppointmentSystem.AppointmentSystem.enums.UserRole;
import com.AppointmentSystem.AppointmentSystem.exception.NotFoundException;
import com.AppointmentSystem.AppointmentSystem.exception.ValidationException;
import com.AppointmentSystem.AppointmentSystem.model.Service;
import com.AppointmentSystem.AppointmentSystem.model.User;
import com.AppointmentSystem.AppointmentSystem.repository.ServiceRepository;
import com.AppointmentSystem.AppointmentSystem.repository.UserRepository;
import com.AppointmentSystem.AppointmentSystem.service.interfaces.ServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository, UserRepository userRepository) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Service createService(ServiceRequestDTO requestDTO) {
        User provider = userRepository.findById(requestDTO.getProviderId())
                .orElseThrow(() -> new NotFoundException("Staff provider not found"));

        // Validate that provider is STAFF
        if (provider.getRole() != UserRole.STAFF) {
            throw new ValidationException("Provider must have STAFF role. User has role: " + provider.getRole());
        }

        Service service = new Service();
        service.setName(requestDTO.getName());
        service.setDescription(requestDTO.getDescription());
        service.setDuration(requestDTO.getDuration());
        service.setPrice(requestDTO.getPrice());
        service.setProvider(provider);

        return serviceRepository.save(service);
    }

    @Override
    public Service updateService(Long id, ServiceRequestDTO requestDTO) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service not found"));

        if (requestDTO.getProviderId() != null &&
                !requestDTO.getProviderId().equals(service.getProvider().getId())) {
            User newProvider = userRepository.findById(requestDTO.getProviderId())
                    .orElseThrow(() -> new NotFoundException("New provider not found"));

            // Validate that new provider is STAFF
            if (newProvider.getRole() != UserRole.STAFF) {
                throw new ValidationException("Provider must have STAFF role. User has role: " + newProvider.getRole());
            }
            service.setProvider(newProvider);
        }

        service.setName(requestDTO.getName());
        service.setDescription(requestDTO.getDescription());
        service.setDuration(requestDTO.getDuration());
        service.setPrice(requestDTO.getPrice());

        return serviceRepository.save(service);
    }

    @Override
    public Service getServiceById(Long id) {
        return serviceRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Service not found"));
    }

    @Override
    public Page<Service> getAllActiveServices(Pageable pageable) {
        return serviceRepository.findByIsActiveTrue(pageable);
    }

    @Override
    public List<Service> getServicesByProvider(Long providerId) {
        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new NotFoundException("Provider not found"));

        if (provider.getRole() != UserRole.STAFF) {
            throw new ValidationException("User is not a STAFF member");
        }

        return serviceRepository.findByProviderIdAndIsActiveTrue(providerId);
    }

    @Override
    public void deactivateService(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service not found"));
        service.setActive(false);
        serviceRepository.save(service);
    }

    @Override
    public boolean isServiceAvailable(Long serviceId) {
        return serviceRepository.findByIdAndIsActiveTrue(serviceId).isPresent();
    }
}