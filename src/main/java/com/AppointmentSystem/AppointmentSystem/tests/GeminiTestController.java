package com.AppointmentSystem.AppointmentSystem.tests;

import com.AppointmentSystem.AppointmentSystem.tests.GeminiTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class GeminiTestController {

    private final GeminiTestService GeminiTestService;

    public GeminiTestController(GeminiTestService geminiTestService) {
        this.GeminiTestService = geminiTestService;
    }

    @GetMapping("/gemini")
    public String testGemini() {
        return GeminiTestService.testGemini();
    }
}