package com.example.kkogpt.service;


import com.example.kkogpt.domain.BotTraits;
import com.example.kkogpt.domain.ChatMessage;
import com.example.kkogpt.domain.ConversationMemory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageBuilder {

    public List<ChatMessage> build(BotTraits traits, List<ConversationMemory> history, String userMessage) {
        List<ChatMessage> messages = new ArrayList<>();

        // system 메시지 구성
        StringBuilder systemPrompt = new StringBuilder();
        systemPrompt.append(String.format("너는 %s 말투를 사용하는 %s 성격의 챗봇이야.", traits.getToneStyle(), traits.getPersonality()));

        if (traits.getNickname() != null && !traits.getNickname().isBlank()) {
            systemPrompt.append(" 사용자의 이름은 '").append(traits.getNickname()).append("'이야.");
        }

        if (traits.getBotName() != null && !traits.getBotName().isBlank()) {
            systemPrompt.append(" 너의 이름은 '").append(traits.getBotName()).append("'이야.");
        }

        messages.add(new ChatMessage("system", systemPrompt.toString()));

        // 이전 대화 히스토리
        for (ConversationMemory m : history) {
            messages.add(new ChatMessage("user", m.getMessage()));
            messages.add(new ChatMessage("assistant", m.getResponse()));
        }

        // 현재 발화
        messages.add(new ChatMessage("user", userMessage));

        return messages;
    }
}