package com.college.hackathon.controller;

import com.college.hackathon.dto.*;
import com.college.hackathon.service.TeamRequestService;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class TeamRequestController {

    private final TeamRequestService requestService;

    public TeamRequestController(TeamRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TeamRequestResponse>> send(
            @RequestBody JoinRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Request sent",
                        requestService.sendRequest(dto, userDetails.getUsername())));
    }

    @PutMapping("/respond")
    public ResponseEntity<ApiResponse<TeamRequestResponse>> respond(
            @RequestBody RespondToRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.ok("Response recorded",
                requestService.respondToRequest(dto, userDetails.getUsername())));
    }

    @GetMapping("/incoming")
    public ResponseEntity<ApiResponse<List<TeamRequestResponse>>> incoming(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.ok(
                requestService.getIncomingRequests(userDetails.getUsername())));
    }

    @GetMapping("/sent")
    public ResponseEntity<ApiResponse<List<TeamRequestResponse>>> sent(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.ok(
                requestService.getMySentRequests(userDetails.getUsername())));
    }
}