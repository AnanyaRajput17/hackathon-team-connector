package com.college.hackathon.dto;

import com.college.hackathon.model.Hackathon;
import java.time.LocalDate;

public class HackathonResponse {
    public Long id;
    public String name;
    public String description;
    public String theme;
    public String venue;
    public LocalDate startDate;
    public LocalDate endDate;
    public LocalDate registrationDeadline;
    public Integer maxTeamSize;
    public Integer minTeamSize;
    public String status;
    public UserResponse createdBy;

    public static HackathonResponse from(Hackathon h) {
        HackathonResponse r = new HackathonResponse();
        r.id = h.getId();
        r.name = h.getName();
        r.description = h.getDescription();
        r.theme = h.getTheme();
        r.venue = h.getVenue();
        r.startDate = h.getStartDate();
        r.endDate = h.getEndDate();
        r.registrationDeadline = h.getRegistrationDeadline();
        r.maxTeamSize = h.getMaxTeamSize();
        r.minTeamSize = h.getMinTeamSize();
        r.status = h.getStatus().name();
        if (h.getCreatedBy() != null) r.createdBy = UserResponse.from(h.getCreatedBy());
        return r;
    }
}