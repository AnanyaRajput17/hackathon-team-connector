package com.college.hackathon.dto;

import jakarta.validation.constraints.NotNull;

public class JoinRequestDTO {
    @NotNull public Long teamId;
    public String message;
}