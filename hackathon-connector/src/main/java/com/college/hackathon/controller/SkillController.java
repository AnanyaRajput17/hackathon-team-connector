package com.college.hackathon.controller;

import com.college.hackathon.dto.*;
import com.college.hackathon.service.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(skillService.getAllSkills()));
    }
}


