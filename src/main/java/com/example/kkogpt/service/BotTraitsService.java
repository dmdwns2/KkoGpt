package com.example.kkogpt.service;

import com.example.kkogpt.domain.BotTraits;
import com.example.kkogpt.repository.BotTraitsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotTraitsService {

    private final BotTraitsRepository botTraitsRepository;

    public BotTraits getOrDefault(String userId) {
        return botTraitsRepository.findById(userId)
                .orElseGet(() -> createDefaultTraits(userId));
    }

    public BotTraits createDefaultTraits(String userId) {
        BotTraits traits = BotTraits.builder()
                .userId(userId)
                .toneStyle("기본 말투")
                .personality("친절하고 차분함")
                .memoryEnabled(true)
                .build();

        return botTraitsRepository.save(traits);
    }

    public void updateTone(String userId, String newTone) {
        BotTraits traits = getOrDefault(userId);
        traits.changeTone(newTone);
        botTraitsRepository.save(traits);
    }

    public void updatePersonality(String userId, String newPersonality) {
        BotTraits traits = getOrDefault(userId);
        traits.changePersonality(newPersonality);
        botTraitsRepository.save(traits);
    }

    public void updateMemoryEnabled(String userId, boolean enabled) {
        BotTraits traits = getOrDefault(userId);
        traits.setMemoryEnabled(enabled);
        botTraitsRepository.save(traits);
    }

    public void updateNickname(String userId, String nickname) {
        BotTraits traits = getOrDefault(userId);
        traits.setNickname(nickname);
        botTraitsRepository.save(traits);
    }

    public void updateBotName(String userId, String botName) {
        BotTraits traits = getOrDefault(userId);
        traits.setBotName(botName);
        botTraitsRepository.save(traits);
    }
}