package com.example.kkogpt.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GptHealthIndicatorTest {

    // given
    WebClient webClient = mock(WebClient.class);
    WebClient.RequestHeadersUriSpec uriSpec =
            mock(WebClient.RequestHeadersUriSpec.class);
    WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
    GptHealthIndicator indicator = new GptHealthIndicator(webClient);

    @Test
    void healthUpThenGptReachable() {
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri("/chat/completions")).thenReturn(uriSpec);
        when(uriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("pong"));

        // when
        Health health = indicator.health();

        // then
        assertThat(health.getStatus().getCode()).isEqualTo("UP");
        assertThat(health.getDetails()).containsEntry("gpt", "reachable");
    }

    @Test
    void healthDownGptNoResponse() {
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri("/chat/completions")).thenReturn(uriSpec);
        when(uriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(null));

        // when
        Health health = indicator.health();

        // then
        assertThat(health.getStatus().getCode()).isEqualTo("DOWN");
        assertThat(health.getDetails()).containsEntry("gpt", "no-response");
    }

    @Test
    void healthDownGptUnreachable() {
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri("/chat/completions")).thenReturn(uriSpec);
        when(uriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("GPT error")));


        // when
        Health health = indicator.health();

        // then
        assertThat(health.getStatus().getCode()).isEqualTo("DOWN");
        assertThat(health.getDetails()).containsEntry("gpt", "unreachable");
    }
}