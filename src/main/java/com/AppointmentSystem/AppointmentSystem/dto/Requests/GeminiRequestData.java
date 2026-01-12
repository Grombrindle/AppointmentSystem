// package com.AppointmentSystem.AppointmentSystem.dto.Requests;

// import java.time.LocalDate;
// import java.time.LocalTime;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import java.util.List;

// import com.AppointmentSystem.AppointmentSystem.dto.Responses.AvailableSlotResponse;

// @Getter
// @AllArgsConstructor
// public class GeminiRequestData {

//     public GeminiRequestData(LocalDate date2, List<AvailableSlotResponse> slots) {
//         //TODO Auto-generated constructor stub
//     }
//     private LocalDate date;
//     private LocalTime workingStart;
//     private LocalTime workingEnd;
//     private int serviceDurationMinutes;
//     private List<String> existingAppointments;
// }
package com.AppointmentSystem.AppointmentSystem.dto.Requests;

import java.time.LocalDate;
import java.util.List;

import com.AppointmentSystem.AppointmentSystem.dto.Responses.AvailableSlotResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeminiRequestData {

    private LocalDate date;
    private List<AvailableSlotResponse> availableSlots;
}
