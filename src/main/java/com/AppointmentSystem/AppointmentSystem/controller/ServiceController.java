package com.AppointmentSystem.AppointmentSystem.controller;

import com.AppointmentSystem.AppointmentSystem.dto.Requests.ServiceRequestDTO;
import com.AppointmentSystem.AppointmentSystem.dto.Responses.ApiResponse;
import com.AppointmentSystem.AppointmentSystem.dto.Responses.ServiceResponse;
import com.AppointmentSystem.AppointmentSystem.model.Service;
import com.AppointmentSystem.AppointmentSystem.service.interfaces.*;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
public class ServiceController extends BaseController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse<ServiceResponse>> createService(
            @Valid @RequestBody ServiceRequestDTO requestDTO) {
        Service service = serviceService.createService(requestDTO);
        return created(ServiceResponse.from(service), "Service created successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ApiResponse<ServiceResponse>> updateService(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRequestDTO requestDTO) {
        Service service = serviceService.updateService(id, requestDTO);
        return ok(ServiceResponse.from(service), "Service updated successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceResponse>> getService(@PathVariable Long id) {
        Service service = serviceService.getServiceById(id);
        return ok(ServiceResponse.from(service), "Service retrieved");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ServiceResponse>>> getAllServices(Pageable pageable) {
        Page<Service> services = serviceService.getAllActiveServices(pageable);
        Page<ServiceResponse> response = services.map(ServiceResponse::from);
        return ok(response, "Services retrieved");
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<Page<ServiceResponse>>> getAvailableServices(Pageable pageable) {
        Page<Service> services = serviceService.getAllActiveServices(pageable);
        Page<ServiceResponse> response = services.map(ServiceResponse::from);
        return ok(response, "Available services retrieved");
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> getServicesByProvider(@PathVariable Long providerId) {
        var services = serviceService.getServicesByProvider(providerId);
        var response = services.stream().map(ServiceResponse::from).toList();
        return ok(response, "Services retrieved for provider");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateService(@PathVariable Long id) {
        serviceService.deactivateService(id);
        return ok(null, "Service deactivated successfully");
    }
}