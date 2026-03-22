package com.college.hackathon.dto;

import com.college.hackathon.model.ChatMessage;
import java.time.LocalDateTime;

public class ChatMessageResponse {
    public Long id;
    public Long chatRoomId;
    public UserResponse sender;
    public String content;
    public LocalDateTime sentAt;

    public static ChatMessageResponse from(ChatMessage msg) {
        ChatMessageResponse r = new ChatMessageResponse();
        r.id = msg.getId();
        r.chatRoomId = msg.getChatRoom().getId();
        r.sender = UserResponse.from(msg.getSender());
        r.content = msg.getContent();
        r.sentAt = msg.getSentAt();
        return r;
    }
}