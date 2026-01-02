package com.AppointmentSystem.AppointmentSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AppointmentSystem.AppointmentSystem.model.User;
import com.AppointmentSystem.AppointmentSystem.model.WorkingSchedule;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingScheduleRepository extends JpaRepository<WorkingSchedule, Long> {
    List<WorkingSchedule> findByStaffAndIsHolidayFalse(User staff);
    Optional<WorkingSchedule> findByStaffAndDayOfWeek(User staff, DayOfWeek dayOfWeek);
    List<WorkingSchedule> findByStaffIdAndIsHolidayFalse(Long staffId);
    List<WorkingSchedule> findByStaffAndDayOfWeekAndIsHolidayFalse(User staff, DayOfWeek dayOfWeek);
}