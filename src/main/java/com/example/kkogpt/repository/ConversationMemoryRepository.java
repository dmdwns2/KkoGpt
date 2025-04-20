package com.example.kkogpt.repository;

import com.example.kkogpt.domain.ConversationMemory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationMemoryRepository extends JpaRepository<ConversationMemory, Long> {

    //최근 대화 n개 조회 (시간 역순) -> 최근 대화 히스토리 조회용
    List<ConversationMemory> findTop10ByUserIdOrderByCreatedAtDesc(String userId);

    void deleteAllByUserId(String userId);
}