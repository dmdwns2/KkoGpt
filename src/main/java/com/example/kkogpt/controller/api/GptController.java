package com.example.kkogpt.controller.api;

import com.example.kkogpt.service.GptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GptController {

    private final GptService gptService;

    @PostMapping("/request")
    public ResponseEntity<String> requestGpt(@RequestBody Map<String, String> body) {
        try {
            String userMessage = body.get("message");
            String reply = gptService.request(userMessage);
            log.info("[SUCCESS] requestGpt");
            return ResponseEntity.ok(reply);
        } catch (Exception e) {
            log.info("[ERROR] requestGpt", e);
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("gpt request failed");
        }
    }
}