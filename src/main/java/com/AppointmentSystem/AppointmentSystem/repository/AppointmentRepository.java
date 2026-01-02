package com.AppointmentSystem.AppointmentSystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.AppointmentSystem.AppointmentSystem.enums.AppointmentStatus;
import com.AppointmentSystem.AppointmentSystem.model.Appointment;
import com.AppointmentSystem.AppointmentSystem.model.User;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    // Find appointments within a date range
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end);
    
    // Find appointments by staff and date
    @Query("SELECT a FROM Appointment a WHERE a.staff = :staff AND DATE(a.appointmentDateTime) = DATE(:date)")
    List<Appointment> findByStaffAndDate(@Param("staff") User staff, @Param("date") LocalDateTime date);
    
    // Find overlapping appointments for a staff member
    @Query("SELECT a FROM Appointment a WHERE a.staff = :staff " +
           "AND a.status NOT IN :excludedStatuses " +
           "AND ((a.appointmentDateTime < :endDateTime AND a.endDateTime > :startDateTime))")
    List<Appointment> findOverlappingAppointments(
        @Param("staff") User staff,
        @Param("startDateTime") LocalDateTime startDateTime,
        @Param("endDateTime") LocalDateTime endDateTime,
        @Param("excludedStatuses") List<AppointmentStatus> excludedStatuses
    );
    
    // Find appointments by customer
    List<Appointment> findByCustomer(User customer);
    
    // Find appointments by staff
    List<Appointment> findByStaff(User staff);
    
    // Find appointments by status
    List<Appointment> findByStatus(AppointmentStatus status);
    
    // Find appointments by customer and status
    List<Appointment> findByCustomerAndStatus(User customer, AppointmentStatus status);
}