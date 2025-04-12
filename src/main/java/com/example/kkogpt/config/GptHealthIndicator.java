package com.example.kkogpt.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class GptHealthIndicator implements HealthIndicator {

    private final WebClient webClient;
    private static final String GPT_END_POINT = "/chat/completions";

    @Override
    public Health health() {
        try {
            String pong = webClient
                    .get()
                    .uri(GPT_END_POINT)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(2))
                    .block();
            log.info("[SUCCESS] health");

            return (pong != null)
                    ? Health.up().withDetail("gpt", "reachable").build()
                    : Health.down().withDetail("gpt", "no-response").build();

        } catch (Exception ex) {
            log.error("[ERROR] health >>> ", ex);
            return Health.down(ex).withDetail("gpt", "unreachable").build();
        }
    }
}