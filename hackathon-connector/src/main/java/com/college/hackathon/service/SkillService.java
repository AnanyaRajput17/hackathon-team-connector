package com.college.hackathon.service;

import com.college.hackathon.dto.SkillResponse;
import com.college.hackathon.repository.SkillRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(SkillResponse::from).collect(Collectors.toList());
    }
}
