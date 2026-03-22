
package com.college.hackathon.service;

import com.college.hackathon.dto.*;
        import com.college.hackathon.exception.BadRequestException;
import com.college.hackathon.exception.ResourceNotFoundException;
import com.college.hackathon.model.*;
        import com.college.hackathon.repository.UserRepository;
import com.college.hackathon.security.JwtUtils;
import org.springframework.security.authentication.*;
        import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils, AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authManager = authManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email)) {
            throw new BadRequestException("Email already registered");
        }
        User user = User.builder()
                .name(request.name)
                .email(request.email)
                .password(passwordEncoder.encode(request.password))
                .college(request.college)
                .build();
        userRepository.save(user);
        String token = jwtUtils.generateToken(user.getEmail());
        return new AuthResponse(token, UserResponse.from(user));
    }

    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email, request.password));
        User user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String token = jwtUtils.generateToken(user.getEmail());
        return new AuthResponse(token, UserResponse.from(user));
    }
}