package com.example.kkogpt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "openai") // 설정은 application.properties 에서
public class OpenAiProperties {
    private String baseUrl;
    private String apiVersion;
    private String endpoint;
    private String apiKey;
}