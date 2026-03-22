package com.college.hackathon.service;

import com.college.hackathon.dto.*;
import com.college.hackathon.exception.BadRequestException;
import com.college.hackathon.exception.ResourceNotFoundException;
import com.college.hackathon.model.*;
import com.college.hackathon.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final HackathonRepository hackathonRepository;
    private final SkillRepository skillRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    public TeamService(TeamRepository teamRepository, HackathonRepository hackathonRepository,
                       SkillRepository skillRepository, ChatRoomRepository chatRoomRepository,
                       UserService userService) {
        this.teamRepository = teamRepository;
        this.hackathonRepository = hackathonRepository;
        this.skillRepository = skillRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userService = userService;
    }

    @Transactional
    public TeamResponse createTeam(CreateTeamRequest request, String leaderEmail) {
        User leader = userService.getUserByEmail(leaderEmail);
        Hackathon hackathon = hackathonRepository.findById(request.hackathonId)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
        if (teamRepository.existsByNameAndHackathonId(request.name, request.hackathonId)) {
            throw new BadRequestException("Team name already taken for this hackathon");
        }
        Set<Skill> requiredSkills = request.requiredSkillIds != null
                ? new HashSet<>(skillRepository.findAllById(request.requiredSkillIds))
                : new HashSet<>();
        Team team = Team.builder()
                .name(request.name)
                .description(request.description)
                .hackathon(hackathon)
                .leader(leader)
                .requiredSkills(requiredSkills)
                .build();
        team.getMembers().add(leader);
        team = teamRepository.save(team);
        ChatRoom chatRoom = ChatRoom.builder().team(team).build();
        chatRoomRepository.save(chatRoom);
        return TeamResponse.from(team);
    }

    public List<TeamResponse> getTeamsByHackathon(Long hackathonId) {
        return teamRepository.findByHackathonId(hackathonId)
                .stream().map(TeamResponse::from).collect(Collectors.toList());
    }

    public TeamResponse getTeam(Long teamId) {
        return TeamResponse.from(teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found")));
    }

    public List<TeamResponse> getMyTeams(String email) {
        User user = userService.getUserByEmail(email);
        return teamRepository.findTeamsByMemberId(user.getId())
                .stream().map(TeamResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public TeamResponse addMember(Long teamId, User newMember) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        if (team.getMembers().size() >= team.getHackathon().getMaxTeamSize()) {
            throw new BadRequestException("Team is already full");
        }
        team.getMembers().add(newMember);
        if (team.getMembers().size() >= team.getHackathon().getMaxTeamSize()) {
            team.setIsOpen(false);
        }
        return TeamResponse.from(teamRepository.save(team));
    }

    public Team getTeamEntity(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    }
}