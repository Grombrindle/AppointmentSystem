package com.AppointmentSystem.AppointmentSystem.service.interfaces;

import com.AppointmentSystem.AppointmentSystem.dto.Requests.WorkingScheduleRequestDTO;
import com.AppointmentSystem.AppointmentSystem.dto.Responses.AvailableSlotResponse;
import com.AppointmentSystem.AppointmentSystem.model.WorkingSchedule;

import java.time.LocalDate;
import java.util.List;

public interface WorkingScheduleService {
    WorkingSchedule createSchedule(WorkingScheduleRequestDTO requestDTO);

    WorkingSchedule updateSchedule(Long id, WorkingScheduleRequestDTO requestDTO);

    List<WorkingSchedule> getSchedulesByStaff(Long staffId);

    void deleteSchedule(Long id);

    List<AvailableSlotResponse> getAvailableSlots(Long staffId, Long serviceId, LocalDate date);
}