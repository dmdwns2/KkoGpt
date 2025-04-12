package com.example.kkogpt.controller.api;

import com.example.kkogpt.service.GptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<String> request(@RequestBody Map<String, String> body) {
        String userMessage = body.get("message");
        String reply = gptService.request(userMessage);
        return ResponseEntity.ok(reply);
    }
}