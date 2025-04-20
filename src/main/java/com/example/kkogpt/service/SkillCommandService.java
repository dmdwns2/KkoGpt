package com.example.kkogpt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkillCommandService {

    private final BotTraitsService botTraitsService;
    private final ConversationMemoryService memoryService;

    public String handle(String userId, String utterance) {
        if (utterance.contains("memory on")) {
            botTraitsService.updateMemoryEnabled(userId, true);
            return "memory on";
        }

        if (utterance.contains("memory off")) {
            botTraitsService.updateMemoryEnabled(userId, false);
            return "memory off";
        }

        if (utterance.contains("memory reset")) {
            memoryService.clearAll(userId);
            return "memory reset";
        }

        // 말투 변경
        if (utterance.contains("말투")) {
            String extractedTone = extractToneStyle(utterance);
            if (extractedTone != null) {
                botTraitsService.updateTone(userId, extractedTone);
                return "말투 변경 >> '" + extractedTone + "'";
            } else {
                return null;
            }
        }


        // 성격 변경
        if (utterance.contains("성격")) {
            String extractedPersonality = extractPersonality(utterance);
            if (extractedPersonality != null) {
                botTraitsService.updatePersonality(userId, extractedPersonality);
                return "성격 변경 >> '" + extractedPersonality + "'";
            } else {
                return null;
            }
        }

        if (utterance.contains("내 이름은")) {
            String nickname = extractNickname(utterance);
            botTraitsService.updateNickname(userId, nickname);
            return nickname + "이라고 기억할게!";
        }

        if (utterance.contains("너의 이름은")) {
            String botName = extractBotName(utterance);
            botTraitsService.updateBotName(userId, botName);
            return "내 이름은 '" + botName + "'";
        }

        return null;
    }

    private String extractToneStyle(String utterance) {
        int idx = utterance.indexOf("말투");
        if (idx > 0) {
            String before = utterance.substring(0, idx).trim();
            String[] words = before.split(" ");
            if (words.length >= 1) {
                return words[words.length - 1] + " 말투";
            }
        }

        return null;
    }

    private String extractPersonality(String utterance) {
        if (utterance.contains("친절")) return "친절한 성격";
        if (utterance.contains("논리")) return "논리적인 성격";
        if (utterance.contains("차갑") | utterance.contains("차가운")) return "차가운 성격";
        if (utterance.contains("귀엽") | utterance.contains("귀여운")) return "귀여운 성격";
        if (utterance.contains("냉정")) return "냉정한 성격";
        if (utterance.contains("유쾌") || utterance.contains("명랑")) return "유쾌한 성격";

        return null;
    }

    private String extractNickname(String utterance) {
        int idx = utterance.indexOf("내 이름은");
        if (idx != -1) {
            String after = utterance.substring(idx + 5).trim(); // "이름은" 다음부터
            if (after.endsWith("야")) {
                after = after.substring(0, after.length() - 1);
            }
            return after;
        }
        return null;
    }

    private String extractBotName(String utterance) {
        int idx = utterance.indexOf("이름은");
        if (idx != -1) {
            String after = utterance.substring(idx + 3).trim();
            if (after.endsWith("야")) after = after.substring(0, after.length() - 1);
            return after;
        }
        return null;
    }
}