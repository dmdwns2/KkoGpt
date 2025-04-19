package com.example.kkogpt.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class KkoController {

    @PostMapping("/skill-test")
    public ResponseEntity<String> skillTest(@RequestBody Map<String, Object> payload) throws JsonProcessingException {
        log.info("[kakao] payload : " + payload);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("version", "2.0");

        Map<String, Object> simpleText = Map.of("text", "hi");
        Map<String, Object> output = Map.of("simpleText", simpleText);
        Map<String, Object> template = Map.of("outputs", List.of(output));

        response.put("template", template);

        String json = objectMapper.writeValueAsString(response);

        log.info("[success] skillTest");
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8")
                .body(json);
    }
}
