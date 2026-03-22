package com.college.hackathon.service;

import com.college.hackathon.dto.*;
import com.college.hackathon.exception.ResourceNotFoundException;
import com.college.hackathon.model.*;
import com.college.hackathon.model.User;
import com.college.hackathon.repository.SkillRepository;
import com.college.hackathon.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public UserService(UserRepository userRepository, SkillRepository skillRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public UserResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (request.name != null) user.setName(request.name);
        if (request.bio != null) user.setBio(request.bio);
        if (request.college != null) user.setCollege(request.college);
        if (request.githubUrl != null) user.setGithubUrl(request.githubUrl);
        if (request.linkedinUrl != null) user.setLinkedinUrl(request.linkedinUrl);
        if (request.skillIds != null) {
            Set<Skill> skills = new HashSet<>(skillRepository.findAllById(request.skillIds));
            user.setSkills(skills);
        }
        return UserResponse.from(userRepository.save(user));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}