package com.example.kkogpt.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class KkoController {

    @PostMapping("/skill")
    public ResponseEntity<Map<String, Object>> skill(@RequestBody Map<String, Object> payload) {
        log.info("[kakao] payload : " + payload);

        Map<String, Object> response = Map.of(
                "version", "2.0",
                "template", Map.of(
                        "outputs", List.of(
                                Map.of("simpleText", Map.of(
                                        "text", "hi"
                                ))
                        )
                )
        );
        log.info("[success] skill");
        return ResponseEntity.ok(response);
    }
}
