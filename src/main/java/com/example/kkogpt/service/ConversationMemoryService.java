package com.example.kkogpt.service;

import com.example.kkogpt.domain.ConversationMemory;
import com.example.kkogpt.repository.ConversationMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationMemoryService {

    private final ConversationMemoryRepository memoryRepository;

    public void save(String userId, String message, String response) {
        ConversationMemory memory = ConversationMemory.builder()
                .userId(userId)
                .message(message)
                .response(response)
                .build();

        memoryRepository.save(memory);
    }

    public List<ConversationMemory> getRecent(String userId, int limit) {
        if (limit == 10) {
            List<ConversationMemory> list = memoryRepository.findTop10ByUserIdOrderByCreatedAtDesc(userId);
            Collections.reverse(list);
            return list;
        }
        throw new UnsupportedOperationException("[error] getRecent");
    }

    public void clearAll(String userId) {
        memoryRepository.deleteAllByUserId(userId);
    }
}