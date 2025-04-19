package com.example.kkogpt.controller.api;

import com.example.kkogpt.service.GptService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/skill")
public class KkoGptController {

    private final GptService gptService;

    @PostMapping
    public ResponseEntity<String> handleSkill(@RequestBody Map<String, Object> payload) throws JsonProcessingException {
        log.info("[kkoGpt] payload: {}", payload);

        String utterance = extractUtterance(payload);
        log.info("[kkoGpt] utterance: {}", utterance);

        String gptResponse = gptService.request(utterance);
        log.info("[kkoGpt] response: {}", gptResponse);

        String kkoResponse = convertKkoResToJson(gptResponse);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8")
                .body(kkoResponse);
    }

    private String extractUtterance(Map<String, Object> payload) {
        @SuppressWarnings("unchecked")
        Map<String, Object> userRequest = (Map<String, Object>) payload.get("userRequest");
        return userRequest.get("utterance").toString();
    }

    private String convertKkoResToJson(String gptText) throws JsonProcessingException {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("version", "2.0");

        Map<String, Object> simpleText = Map.of("text", gptText);
        Map<String, Object> output = Map.of("simpleText", simpleText);
        Map<String, Object> template = Map.of("outputs", List.of(output));

        response.put("template", template);

        return new ObjectMapper().writeValueAsString(response);
    }
}