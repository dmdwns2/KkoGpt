package com.example.kkogpt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {
    private String role;    // "system", "user", "assistant"
    private String content;
}