package com.college.hackathon.controller;

import com.college.hackathon.dto.*;
import com.college.hackathon.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/{teamId}/history")
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> getHistory(
            @PathVariable Long teamId) {
        return ResponseEntity.ok(ApiResponse.ok(chatService.getChatHistory(teamId)));
    }

    @org.springframework.messaging.handler.annotation.MessageMapping("/chat.send/{teamId}")
    public void sendMessage(@DestinationVariable Long teamId,
                            @Payload ChatMessageRequest messageRequest,
                            Principal principal) {
        ChatMessageResponse saved = chatService.saveMessage(
                teamId, principal.getName(), messageRequest.content);
        messagingTemplate.convertAndSend("/topic/team/" + teamId, saved);
    }
}