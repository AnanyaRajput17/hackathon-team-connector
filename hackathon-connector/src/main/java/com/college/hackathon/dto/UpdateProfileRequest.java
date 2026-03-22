package com.college.hackathon.dto;

import java.util.Set;

public class UpdateProfileRequest {
    public String name;
    public String bio;
    public String college;
    public String githubUrl;
    public String linkedinUrl;
    public Set<Long> skillIds;
}