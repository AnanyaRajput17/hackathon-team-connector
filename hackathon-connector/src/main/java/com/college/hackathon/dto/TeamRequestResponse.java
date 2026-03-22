package com.college.hackathon.dto;

import com.college.hackathon.model.TeamRequest;
import java.time.LocalDateTime;

public class TeamRequestResponse {
    public Long id;
    public UserResponse sender;
    public TeamResponse team;
    public String message;
    public String status;
    public LocalDateTime createdAt;

    public static TeamRequestResponse from(TeamRequest req) {
        TeamRequestResponse r = new TeamRequestResponse();
        r.id = req.getId();
        r.sender = UserResponse.from(req.getSender());
        r.team = TeamResponse.from(req.getTeam());
        r.message = req.getMessage();
        r.status = req.getStatus().name();
        r.createdAt = req.getCreatedAt();
        return r;
    }
}