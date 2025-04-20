package com.example.kkogpt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SkillCommandServiceTest {

    private BotTraitsService botTraitsService;
    private ConversationMemoryService memoryService;
    private SkillCommandService skillCommandService;

    @BeforeEach
    void setUp() {
        botTraitsService = mock(BotTraitsService.class);
        memoryService = mock(ConversationMemoryService.class);
        skillCommandService = new SkillCommandService(botTraitsService, memoryService);
    }

    @Test
    void testTurnMemoryOn() {
        String result = skillCommandService.handle("user1", "memory on");
        verify(botTraitsService).updateMemoryEnabled("user1", true);
        assertThat(result).isEqualTo("memory on");
    }

    @Test
    void testTurnMemoryOff() {
        String result = skillCommandService.handle("user1", "memory off");
        verify(botTraitsService).updateMemoryEnabled("user1", false);
        assertThat(result).isEqualTo("memory off");
    }

    @Test
    void testResetMemory() {
        String result = skillCommandService.handle("user1", "memory reset");
        verify(memoryService).clearAll("user1");
        assertThat(result).isEqualTo("memory reset");
    }

    @Test
    void testUpdateToneStyle() {
        String result = skillCommandService.handle("user1", "귀여운 말투 써줘");
        verify(botTraitsService).updateTone("user1", "귀여운 말투");
        assertThat(result).isEqualTo("말투 변경 >> '귀여운 말투'");
    }

    @Test
    void testUpdatePersonality() {
        String result = skillCommandService.handle("user1", "귀여운 성격으로 해줘");
        verify(botTraitsService).updatePersonality("user1", "귀여운 성격");
        assertThat(result).isEqualTo("성격 변경 >> '귀여운 성격'");
    }

    @Test
    void testUpdateNickname() {
        String result = skillCommandService.handle("user1", "내 이름은 응준");
        verify(botTraitsService).updateNickname("user1", "응준");
        assertThat(result).isEqualTo("응준이라고 기억할게!");
    }

    @Test
    void testUpdateBotName() {
        String result = skillCommandService.handle("user1", "너의 이름은 머스크야");
        verify(botTraitsService).updateBotName("user1", "머스크");
        assertThat(result).isEqualTo("내 이름은 '머스크'");
    }
}
