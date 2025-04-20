package com.example.kkogpt.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GptServiceTest {

    @Mock
    WebClient webClient;
    @Mock
    WebClient.RequestBodyUriSpec uriSpec;
    @Mock
    WebClient.RequestBodySpec bodySpec;
    @Mock
    WebClient.RequestHeadersSpec headersSpec;
    @Mock
    WebClient.ResponseSpec responseSpec;

    @InjectMocks
    GptService gptService;


    @Test
    void requestGptResponseJsonType() {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode mockJson = objectMapper.createObjectNode();

        ObjectNode messageNode = objectMapper.createObjectNode();
        messageNode.put("content", "OK");

        ObjectNode choiceNode = objectMapper.createObjectNode();
        choiceNode.set("message", messageNode);

        mockJson.putArray("choices").add(choiceNode);

        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri("/chat/completions")).thenReturn(bodySpec);
        when(bodySpec.bodyValue(any())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(JsonNode.class)).thenReturn(Mono.just(mockJson));

        // when
        String result = gptService.request("Hello");

        // then
        assertThat(result).isEqualTo("OK");
    }

    @Test
    void requestGptResponseTimeOut() {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode mockJson = objectMapper.createObjectNode();

        ObjectNode messageNode = objectMapper.createObjectNode();
        messageNode.put("content", "OK");

        ObjectNode choiceNode = objectMapper.createObjectNode();
        choiceNode.set("message", messageNode);

        mockJson.putArray("choices").add(choiceNode);

        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri("/chat/completions")).thenReturn(bodySpec);
        when(bodySpec.bodyValue(any())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(JsonNode.class)).thenReturn(Mono.just(mockJson));

        // when
        String result = gptService.request("Hello");

        // then
        assertThat(result).isEqualTo("OK");
    }
}