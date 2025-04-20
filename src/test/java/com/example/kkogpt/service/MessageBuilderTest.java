package com.example.kkogpt.service;

import com.example.kkogpt.domain.BotTraits;
import com.example.kkogpt.domain.ChatMessage;
import com.example.kkogpt.domain.ConversationMemory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageBuilderTest {

    final MessageBuilder messageBuilder = new MessageBuilder();

    @Test
    void testNickNameAndBotName() {
        // given
        BotTraits traits = BotTraits.builder()
                .toneStyle("친절하게")
                .personality("명랑")
                .nickname("응준")
                .botName("머스크")
                .build();

        List<ConversationMemory> history = List.of();
        String userMessage = "hi";

        // when
        List<ChatMessage> messages = messageBuilder.build(traits, history, userMessage);

        // then
        ChatMessage system = messages.get(0);
        assertEquals("system", system.getRole());
        assertTrue(system.getContent().contains("응준"));
        assertTrue(system.getContent().contains("머스크"));
    }

    @Test
    void testHistory() {
        // given
        ConversationMemory memory1 = ConversationMemory.builder()
                .message("hi")
                .response("bro")
                .build();

        ConversationMemory memory2 = ConversationMemory.builder()
                .message("what's up?")
                .response("good")
                .build();

        BotTraits traits = BotTraits.builder()
                .toneStyle("친절")
                .personality("명랑")
                .build();

        // when
        List<ChatMessage> messages = messageBuilder.build(traits, List.of(memory1, memory2), "name?");

        // then
        assertEquals("user", messages.get(1).getRole());
        assertEquals("hi", messages.get(1).getContent());

        assertEquals("assistant", messages.get(2).getRole());
        assertEquals("bro", messages.get(2).getContent());

        assertEquals("user", messages.get(3).getRole());
        assertEquals("what's up?", messages.get(3).getContent());

        assertEquals("assistant", messages.get(4).getRole());
        assertEquals("good", messages.get(4).getContent());

        assertEquals("user", messages.get(5).getRole());
        assertEquals("name?", messages.get(5).getContent());
    }

}