package com.college.hackathon.dto;

import com.college.hackathon.model.User;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserResponse {
    public Long id;
    public String name;
    public String email;
    public String college;
    public String bio;
    public String githubUrl;
    public String linkedinUrl;
    public Set<SkillResponse> skills;
    public LocalDateTime createdAt;

    public static UserResponse from(User user) {
        UserResponse r = new UserResponse();
        r.id = user.getId();
        r.name = user.getName();
        r.email = user.getEmail();
        r.college = user.getCollege();
        r.bio = user.getBio();
        r.githubUrl = user.getGithubUrl();
        r.linkedinUrl = user.getLinkedinUrl();
        r.createdAt = user.getCreatedAt();
        r.skills = user.getSkills() != null
                ? user.getSkills().stream()
                .map(SkillResponse::from)
                .collect(Collectors.toSet())
                : new HashSet<>();
        return r;
    }
}