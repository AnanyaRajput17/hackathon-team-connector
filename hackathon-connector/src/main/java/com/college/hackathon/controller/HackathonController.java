package com.college.hackathon.controller;

import com.college.hackathon.dto.*;
import com.college.hackathon.service.HackathonService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hackathons")
public class HackathonController {

    private final HackathonService hackathonService;

    public HackathonController(HackathonService hackathonService) {
        this.hackathonService = hackathonService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HackathonResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(hackathonService.getAllHackathons()));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<ApiResponse<List<HackathonResponse>>> getUpcoming() {
        return ResponseEntity.ok(ApiResponse.ok(hackathonService.getUpcomingHackathons()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HackathonResponse>> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(hackathonService.getHackathon(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HackathonResponse>> create(
            @Valid @RequestBody CreateHackathonRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Hackathon created",
                        hackathonService.createHackathon(request, userDetails.getUsername())));
    }
}