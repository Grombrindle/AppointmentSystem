package com.AppointmentSystem.AppointmentSystem.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.AppointmentSystem.AppointmentSystem.dto.Requests.GeminiRequestData;
import com.AppointmentSystem.AppointmentSystem.service.interfaces.GeminiService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.enabled:true}")
    private boolean enabled;

    // TEMP: hard-coded for testing
    private String apiKey = "AIzaSyDSY06T6yxNl9ooUA2xjmjl_hi3EF3oCbk";

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    void verifyKey() {
        if (!enabled) {
            throw new IllegalStateException("Gemini is disabled in config");
        }
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Gemini API key is missing");
        }
        log.info("Gemini ENABLED and API key LOADED");
    }

    @Override
    public String suggestBestAppointment(GeminiRequestData data) {
        String prompt = buildPrompt(data);
        return callGemini(prompt); // NO MOCK, REAL ONLY
    }

    private String buildPrompt(GeminiRequestData data) {

        String slots = data.getAvailableSlots().stream()
                .map(s -> s.getStartTime() + " - " + s.getEndTime())
                .toList()
                .toString();

        return """
                You are an appointment scheduling assistant.

                Date: %s
                Available slots:
                %s

                Suggest the best available appointment time and explain why.
                """
                .formatted(data.getDate(), slots);
    }

    private String callGemini(String prompt) {

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        String url = apiUrl + "?key=" + apiKey;

        Map<?, ?> response = restTemplate.postForObject(url, request, Map.class);

        if (response == null || !response.containsKey("candidates")) {
            throw new RuntimeException("Invalid response from Gemini API");
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");

        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");

        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

        return parts.get(0).get("text").toString();
    }
}
