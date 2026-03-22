package com.college.hackathon.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class CreateHackathonRequest {
    @NotBlank public String name;
    public String description;
    public String theme;
    public String venue;
    @NotNull public LocalDate startDate;
    @NotNull public LocalDate endDate;
    public LocalDate registrationDeadline;
    @Min(2) public Integer maxTeamSize = 4;
    @Min(1) public Integer minTeamSize = 2;
}