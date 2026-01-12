// package com.AppointmentSystem.AppointmentSystem.controller;

// import java.time.LocalDate;
// import java.util.List;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.AppointmentSystem.AppointmentSystem.dto.Requests.GeminiRequestData;
// import com.AppointmentSystem.AppointmentSystem.repository.AppointmentRepository;
// import com.AppointmentSystem.AppointmentSystem.service.interfaces.GeminiService;
// import com.AppointmentSystem.AppointmentSystem.service.interfaces.WorkingScheduleService;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/ai")
// @RequiredArgsConstructor
// public class GeminiController {

//         private final GeminiService geminiService;
//         private final AppointmentRepository appointmentRepository;
//         private final WorkingScheduleService workingScheduleService;

//         @GetMapping("/suggest")
//         public ResponseEntity<String> suggestAppointment(
//                         @RequestParam LocalDate date,
//                         @RequestParam int serviceDuration) {

//                 var schedule = workingScheduleService.getScheduleForDay(date.getDayOfWeek());

//                 var appointments = appointmentRepository.findByDate(date);

//                 List<String> busySlots = appointments.stream()
//                                 .map(a -> a.getStartTime() + " - " + a.getEndTime())
//                                 .toList();

//                 GeminiRequestData data = new GeminiRequestData(
//                                 date,
//                                 schedule.getStartTime(),
//                                 schedule.getEndTime(),
//                                 serviceDuration,
//                                 busySlots);

//                 return ResponseEntity.ok(
//                                 geminiService.suggestBestAppointment(data));
//         }
// }
package com.AppointmentSystem.AppointmentSystem.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.AppointmentSystem.AppointmentSystem.dto.Requests.GeminiRequestData;
import com.AppointmentSystem.AppointmentSystem.dto.Responses.AvailableSlotResponse;
import com.AppointmentSystem.AppointmentSystem.service.interfaces.GeminiService;
import com.AppointmentSystem.AppointmentSystem.service.interfaces.WorkingScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class GeminiController {

        private final GeminiService geminiService;
        private final WorkingScheduleService workingScheduleService;

        @GetMapping("/suggest")
        public ResponseEntity<String> suggestBestAppointment(
                        @RequestParam Long staffId,
                        @RequestParam Long serviceId,
                        @RequestParam LocalDate date) {

                // ✅ Use existing business logic
                List<AvailableSlotResponse> slots = workingScheduleService.getAvailableSlots(
                                staffId, serviceId, date);

                if (slots.isEmpty()) {
                        return ResponseEntity.ok(
                                        "No available slots for the selected date");
                }

                GeminiRequestData data = new GeminiRequestData(
                                date,
                                slots);

                return ResponseEntity.ok(
                                geminiService.suggestBestAppointment(data));
        }
}
