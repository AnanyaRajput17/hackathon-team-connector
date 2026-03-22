package com.college.hackathon.repository;

import com.college.hackathon.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomIdOrderBySentAtAsc(Long chatRoomId);
    List<ChatMessage> findTop50ByChatRoomIdOrderBySentAtDesc(Long chatRoomId);
}
