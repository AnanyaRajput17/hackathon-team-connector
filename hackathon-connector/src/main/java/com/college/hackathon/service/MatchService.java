package com.college.hackathon.service;

import com.college.hackathon.dto.UserResponse;
import com.college.hackathon.model.Skill;
import com.college.hackathon.model.Team;
import com.college.hackathon.model.User;
import com.college.hackathon.repository.TeamRepository;
import com.college.hackathon.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final UserService userService;

    public MatchService(UserRepository userRepository, TeamRepository teamRepository,
                        UserService userService) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    public List<UserResponse> suggestTeammates(Long hackathonId, String currentUserEmail) {
        User currentUser = userService.getUserByEmail(currentUserEmail);
        List<Team> teamsInHackathon = teamRepository.findByHackathonId(hackathonId);
        Set<Skill> neededSkills = teamsInHackathon.stream()
                .flatMap(t -> t.getRequiredSkills().stream())
                .collect(Collectors.toSet());
        if (neededSkills.isEmpty()) return List.of();
        return userRepository.findBySkillsIn(neededSkills).stream()
                .filter(u -> !u.getId().equals(currentUser.getId()))
                .filter(u -> teamRepository.findTeamsByMemberId(u.getId()).stream()
                        .noneMatch(t -> t.getHackathon().getId().equals(hackathonId)))
                .sorted(Comparator.comparingInt(u -> -countSkillOverlap(u, neededSkills)))
                .limit(10)
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    private int countSkillOverlap(User user, Set<Skill> neededSkills) {
        return (int) user.getSkills().stream().filter(neededSkills::contains).count();
    }
}