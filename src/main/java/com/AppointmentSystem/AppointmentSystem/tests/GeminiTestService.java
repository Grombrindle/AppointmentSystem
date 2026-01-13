package com.AppointmentSystem.AppointmentSystem.tests;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiTestService {

    public String testGemini() {
        String apiKey = "AIzaSyDfV18ZgZLZgkaiykgBSCZPfzvLszyGX_A"; // TEMPORARY - replace with your key
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="
                + apiKey;

        // Simple JSON request
        String requestJson = """
                {
                    "contents": [{
                        "parts": [{
                            "text": "Hello, say hello back in one word"
                        }]
                    }]
                }
                """;

        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(url, requestJson, String.class);

            // Parse simple response
            return extractTextFromResponse(response);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String extractTextFromResponse(String jsonResponse) {
        try {
            // Simple extraction - look for the text field
            int start = jsonResponse.indexOf("\"text\":\"") + 8;
            int end = jsonResponse.indexOf("\"", start);
            return jsonResponse.substring(start, end);
        } catch (Exception e) {
            return "Could not parse response: " + jsonResponse;
        }
    }
}