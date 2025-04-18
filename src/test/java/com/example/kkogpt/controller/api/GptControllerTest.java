package com.example.kkogpt.controller.api;

import com.example.kkogpt.service.GptService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GptController.class)
class GptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GptService gptService;

    @Test
    void requestGptReturnsOk() throws Exception {
        Mockito.when(gptService.request(anyString())).thenReturn("response from gpt");

        mockMvc.perform(post("/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"hello\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("response from gpt"));

    }

    @Test
    void requestGptReturnsError() throws Exception {
        Mockito.when(gptService.request(anyString()))
                .thenThrow(new RuntimeException("gpt is down"));

        mockMvc.perform(post("/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"hello\"}"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().string("gpt request failed"));
    }
}