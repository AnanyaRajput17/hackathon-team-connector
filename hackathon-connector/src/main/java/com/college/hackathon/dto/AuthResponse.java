package com.college.hackathon.dto;

public class AuthResponse {
    public String token;
    public String type = "Bearer";
    public UserResponse user;

    public AuthResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }
}