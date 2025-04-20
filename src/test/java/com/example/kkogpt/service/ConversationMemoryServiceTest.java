package com.example.kkogpt.service;

import com.example.kkogpt.domain.ConversationMemory;
import com.example.kkogpt.repository.ConversationMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class ConversationMemoryServiceTest {

    private ConversationMemoryRepository memoryRepository;
    private ConversationMemoryService memoryService;

    @BeforeEach
    void setUp() {
        memoryRepository = mock(ConversationMemoryRepository.class);
        memoryService = new ConversationMemoryService(memoryRepository);
    }

    @Test
    void testSaveConversationMemory() {
        // given
        String userId = "user1";
        String message = "Hello";
        String response = "Hi there";

        // when
        memoryService.save(userId, message, response);

        // then
        verify(memoryRepository).save(argThat(memory ->
                memory.getUserId().equals(userId) &&
                        memory.getMessage().equals(message) &&
                        memory.getResponse().equals(response)
        ));
    }

    @Test
    void testReturnReversedRecentMemoryList() {
        // given
        List<ConversationMemory> mockList = new ArrayList<>();
        mockList.add(ConversationMemory.builder().message("3").build());
        mockList.add(ConversationMemory.builder().message("2").build());
        mockList.add(ConversationMemory.builder().message("1").build());

        when(memoryRepository.findTop10ByUserIdOrderByCreatedAtDesc("user1"))
                .thenReturn(mockList);

        // when
        List<ConversationMemory> result = memoryService.getRecent("user1", 10);

        // then
        assertThat(result.get(0).getMessage()).isEqualTo("1");
        assertThat(result.get(2).getMessage()).isEqualTo("3");
    }

    @Test
    void testClearAllUserMemory() {
        // when
        memoryService.clearAll("user1");

        // then
        verify(memoryRepository).deleteAllByUserId("user1");
    }

    @Test
    void testThrowExceptionForUnsupportedLimit() {
        // when / then
        try {
            memoryService.getRecent("user1", 5);
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage()).isEqualTo("[error] getRecent");
        }
    }
}
