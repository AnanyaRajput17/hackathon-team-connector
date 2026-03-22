package com.college.hackathon.dto;

import com.college.hackathon.model.Skill;

public class SkillResponse {
    public Long id;
    public String name;
    public String category;

    public static SkillResponse from(Skill skill) {
        SkillResponse r = new SkillResponse();
        r.id = skill.getId();
        r.name = skill.getName();
        r.category = skill.getCategory();
        return r;
    }
}