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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KkoGptController.class)
class KkoGptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GptService gptService;

    @Test
    void handleSkill() throws Exception {
        // given
        String gptAnswer = "response gpt";
        Mockito.when(gptService.request(anyString())).thenReturn(gptAnswer);

        String payload = """
                    {
                      "userRequest": {
                        "utterance": "hi"
                      }
                    }
                """;

        // when, then
        mockMvc.perform(post("/skill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json; charset=UTF-8"))
                .andExpect(jsonPath("$.version").value("2.0"))
                .andExpect(jsonPath("$.template.outputs[0].simpleText.text").value("response gpt"));
    }
}