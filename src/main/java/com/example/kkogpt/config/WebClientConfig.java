package com.example.kkogpt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final OpenAiProperties openAiProperties;

    @Bean
    public WebClient openAiWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(openAiProperties.getBaseUrl() + openAiProperties.getApiVersion())
                .defaultHeader("Authorization", "Bearer " + openAiProperties.getApiKey())
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
