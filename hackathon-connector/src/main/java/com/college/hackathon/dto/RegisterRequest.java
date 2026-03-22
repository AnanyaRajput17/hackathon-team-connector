package com.college.hackathon.dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {
    @NotBlank public String name;
    @Email @NotBlank public String email;
    @Size(min = 6) @NotBlank public String password;
    public String college;
}