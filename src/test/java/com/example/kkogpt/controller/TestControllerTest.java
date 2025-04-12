package com.example.kkogpt.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /test → 200 OK + body='good'")
    void testTestMessageEndpoint() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("good"));
    }
}