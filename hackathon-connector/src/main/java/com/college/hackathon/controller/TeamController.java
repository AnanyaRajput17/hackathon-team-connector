package com.college.hackathon.controller;

import com.college.hackathon.dto.*;
import com.college.hackathon.service.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final MatchService matchService;

    public TeamController(TeamService teamService, MatchService matchService) {
        this.teamService = teamService;
        this.matchService = matchService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TeamResponse>> createTeam(
            @Valid @RequestBody CreateTeamRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Team created",
                        teamService.createTeam(request, userDetails.getUsername())));
    }

    @GetMapping("/hackathon/{hackathonId}")
    public ResponseEntity<ApiResponse<List<TeamResponse>>> getByHackathon(
            @PathVariable Long hackathonId) {
        return ResponseEntity.ok(ApiResponse.ok(teamService.getTeamsByHackathon(hackathonId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeamResponse>> getTeam(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(teamService.getTeam(id)));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<TeamResponse>>> getMyTeams(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.ok(teamService.getMyTeams(userDetails.getUsername())));
    }

    @GetMapping("/suggest/{hackathonId}")
    public ResponseEntity<ApiResponse<List<UserResponse>>> suggest(
            @PathVariable Long hackathonId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.ok(
                matchService.suggestTeammates(hackathonId, userDetails.getUsername())));
    }
}