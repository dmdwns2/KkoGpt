package com.example.kkogpt.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Slf4j
@Component("gpt")  // JSON 결과에 "gpt" 키로 노출됨
@RequiredArgsConstructor
public class GptHealthIndicator implements HealthIndicator {

    private final WebClient webClient;
    private final OpenAiProperties openAiProperties;
    private static final String GPT_END_POINT = "/gpt";

    @Override
    public Health health() {
        try {
            // 짧은 타임아웃과 최소한의 요청으로 상태 확인
            String pong = webClient
                    .get()
                    .uri(openAiProperties.getApiVersion() + GPT_END_POINT)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(2))
                    .block();

            return (pong != null)
                    ? Health.up().withDetail("gpt", "reachable").build()
                    : Health.down().withDetail("gpt", "no-response").build();

        } catch (Exception ex) {
            log.error("[ERROR] health >>> ", ex);
            return Health.down(ex).withDetail("gpt", "unreachable").build();
        }
    }
}