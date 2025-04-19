package com.example.kkogpt.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(KkoController.class)
class KkoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void skill() throws Exception {
        // given
        String requestBody = """
                    {
                      "userRequest": {
                        "utterance": "안녕"
                      }
                    }
                """;

        // when, then
        mockMvc.perform(post("/skill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("2.0"))
                .andExpect(jsonPath("$.template.outputs[0].simpleText.text").exists());
    }
}