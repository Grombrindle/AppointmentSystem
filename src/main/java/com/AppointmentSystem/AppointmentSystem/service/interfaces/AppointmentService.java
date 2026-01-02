package com.AppointmentSystem.AppointmentSystem.service.interfaces;

import com.AppointmentSystem.AppointmentSystem.dto.Requests.AppointmentRequestDTO;
import com.AppointmentSystem.AppointmentSystem.dto.Requests.UpdateAppointmentStatusRequestDTO;
import com.AppointmentSystem.AppointmentSystem.enums.AppointmentStatus;
import com.AppointmentSystem.AppointmentSystem.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment bookAppointment(AppointmentRequestDTO requestDTO, Long customerId);

    Appointment updateAppointmentStatus(Long appointmentId, UpdateAppointmentStatusRequestDTO requestDTO);

    Optional<Appointment> getAppointmentById(Long id);

    void deleteAppointment(Long id);

    List<Appointment> getAppointmentsByCustomer(Long customerId);

    List<Appointment> getAppointmentsByStaff(Long staffId);

    List<Appointment> getPendingAppointments();

    List<Appointment> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end);

    void cancelAppointment(Long appointmentId, String reason);
}