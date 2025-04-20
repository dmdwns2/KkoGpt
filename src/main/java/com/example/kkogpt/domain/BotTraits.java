package com.example.kkogpt.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bot_traits")
public class BotTraits {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "tone_style", nullable = false)
    private String toneStyle;

    @Column(name = "personality", nullable = false)
    private String personality;

    @Column(name = "memory_enabled", nullable = false)
    @Setter
    private boolean memoryEnabled;

    @Column(length = 50)
    @Setter
    private String nickname;
    @Column(length = 50)
    @Setter
    private String botName;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    public void changeTone(String newTone) {
        this.toneStyle = newTone;
    }

    public void changePersonality(String newPersonality) {
        this.personality = newPersonality;
    }
}