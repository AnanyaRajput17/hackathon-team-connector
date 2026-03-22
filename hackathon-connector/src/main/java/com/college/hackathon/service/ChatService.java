package com.college.hackathon.service;

import com.college.hackathon.dto.ChatMessageResponse;
import com.college.hackathon.exception.ResourceNotFoundException;
import com.college.hackathon.model.ChatMessage;
import com.college.hackathon.model.ChatRoom;
import com.college.hackathon.model.User;
import com.college.hackathon.repository.ChatMessageRepository;
import com.college.hackathon.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatMessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    public ChatService(ChatMessageRepository messageRepository,
                       ChatRoomRepository chatRoomRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userService = userService;
    }

    @Transactional
    public ChatMessageResponse saveMessage(Long teamId, String senderEmail, String content) {
        ChatRoom room = chatRoomRepository.findByTeamId(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat room not found"));
        User sender = userService.getUserByEmail(senderEmail);
        ChatMessage message = ChatMessage.builder()
                .chatRoom(room).sender(sender).content(content).build();
        return ChatMessageResponse.from(messageRepository.save(message));
    }

    public List<ChatMessageResponse> getChatHistory(Long teamId) {
        ChatRoom room = chatRoomRepository.findByTeamId(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat room not found"));
        return messageRepository.findByChatRoomIdOrderBySentAtAsc(room.getId())
                .stream().map(ChatMessageResponse::from).collect(Collectors.toList());
    }
}