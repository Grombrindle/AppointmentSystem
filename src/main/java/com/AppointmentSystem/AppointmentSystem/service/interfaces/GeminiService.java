package com.AppointmentSystem.AppointmentSystem.service.interfaces;

import com.AppointmentSystem.AppointmentSystem.dto.Requests.GeminiRequestData;

public interface GeminiService {
    String suggestBestAppointment(GeminiRequestData data);
}
