package com.college.hackathon.dto;

import com.college.hackathon.model.Team;
import java.util.Set;
import java.util.stream.Collectors;

public class TeamResponse {
    public Long id;
    public String name;
    public String description;
    public HackathonResponse hackathon;
    public UserResponse leader;
    public Set<UserResponse> members;
    public Set<SkillResponse> requiredSkills;
    public Boolean isOpen;
    public int memberCount;

    public static TeamResponse from(Team team) {
        TeamResponse r = new TeamResponse();
        r.id = team.getId();
        r.name = team.getName();
        r.description = team.getDescription();
        r.hackathon = HackathonResponse.from(team.getHackathon());
        r.leader = UserResponse.from(team.getLeader());
        r.members = team.getMembers().stream()
                .map(UserResponse::from).collect(Collectors.toSet());
        r.requiredSkills = team.getRequiredSkills().stream()
                .map(SkillResponse::from).collect(Collectors.toSet());
        r.isOpen = team.getIsOpen();
        r.memberCount = team.getMembers().size();
        return r;
    }
}