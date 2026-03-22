package com.college.hackathon.dto;

import jakarta.validation.constraints.NotBlank;

public class ChatMessageRequest {
    @NotBlank public String content;
}