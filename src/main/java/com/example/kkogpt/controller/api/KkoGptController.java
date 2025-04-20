package com.example.kkogpt.controller.api;

import com.example.kkogpt.domain.BotTraits;
import com.example.kkogpt.domain.ChatMessage;
import com.example.kkogpt.domain.ConversationMemory;
import com.example.kkogpt.service.*;
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
    private final BotTraitsService botTraitsService;
    private final ConversationMemoryService memoryService;
    private final MessageBuilder messageBuilder;
    private final SkillCommandService skillCommandService;

    @PostMapping
    public ResponseEntity<String> handleSkill(@RequestBody Map<String, Object> payload) throws JsonProcessingException {
        log.info("[kkoGpt] payload: {}", payload);

        String userId = extractUserId(payload);
        String utterance = extractUtterance(payload);
        log.info("[kkoGpt] userId: {}, utterance: {}", userId, utterance);

        // 명령어 처리
        String commandResponse = skillCommandService.handle(userId, utterance);
        if (commandResponse != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8")
                    .body(convertKkoResToJson(commandResponse));
        }

        // 사용자 성격/말투 조회
        BotTraits traits = botTraitsService.getOrDefault(userId);

        // 기억이 켜져 있다면 대화 히스토리 조회
        List<ConversationMemory> history = traits.isMemoryEnabled()
                ? memoryService.getRecent(userId, 10)
                : List.of();

        // GPT 메시지 구성
        List<ChatMessage> messages = messageBuilder.build(traits, history, utterance);

        // GPT 응답 생성
        String gptResponse = gptService.chat(messages);
        log.info("[kkoGpt] response: {}", gptResponse);

        // 기억이 켜져 있으면 저장
        if (traits.isMemoryEnabled()) {
            memoryService.save(userId, utterance, gptResponse);
        }

        // 카카오 응답 형태로 변환
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

    private String extractUserId(Map<String, Object> payload) {
        @SuppressWarnings("unchecked")
        Map<String, Object> userRequest = (Map<String, Object>) payload.get("userRequest");
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) userRequest.get("user");
        return user.get("id").toString();
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