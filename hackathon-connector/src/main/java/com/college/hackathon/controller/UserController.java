package com.college.hackathon.controller;

import com.college.hackathon.dto.ApiResponse;
import com.college.hackathon.dto.UpdateProfileRequest;
import com.college.hackathon.dto.UserResponse;
import com.college.hackathon.model.User;
import com.college.hackathon.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getProfile(id)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.ok(userService.getProfile(user.getId())));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateProfileRequest request) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.ok("Profile updated",
                userService.updateProfile(user.getId(), request)));
    }
}