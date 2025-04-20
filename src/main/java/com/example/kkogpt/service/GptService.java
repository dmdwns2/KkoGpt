package com.example.kkogpt.service;

import com.example.kkogpt.domain.ChatMessage;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GptService {

    private final WebClient webClient;

    public String chat(List<ChatMessage> messages) {
        List<Map<String, String>> messagePayload = messages.stream()
                .map(m -> Map.of("role", m.getRole(), "content", m.getContent()))
                .toList();

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", messagePayload
        );

        try {
            return webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .timeout(Duration.ofSeconds(10))
                    .map(json -> json.get("choices").get(0).get("message").get("content").asText())
                    .block();
        } catch (Exception e) {
            System.err.println("[error] chat" + e.getMessage());
            return "잠시만";
        }
    }

    public String request(String userMessage) {
        return chat(List.of(new ChatMessage("user", userMessage)));
    }
}