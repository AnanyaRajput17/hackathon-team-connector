package com.college.hackathon.dto;

import com.college.hackathon.model.TeamRequest;
import jakarta.validation.constraints.NotNull;

public class RespondToRequestDTO {
    @NotNull public Long requestId;
    @NotNull public TeamRequest.Status status;
}