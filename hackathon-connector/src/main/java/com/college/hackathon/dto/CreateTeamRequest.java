package com.college.hackathon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public class CreateTeamRequest {
    @NotBlank public String name;
    public String description;
    @NotNull public Long hackathonId;
    public Set<Long> requiredSkillIds;
}