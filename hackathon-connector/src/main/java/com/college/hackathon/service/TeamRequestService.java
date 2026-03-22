package com.college.hackathon.service;

import com.college.hackathon.dto.*;
import com.college.hackathon.exception.*;
import com.college.hackathon.model.*;
import com.college.hackathon.repository.TeamRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamRequestService {

    private final TeamRequestRepository requestRepository;
    private final TeamService teamService;
    private final UserService userService;

    public TeamRequestService(TeamRequestRepository requestRepository,
                              TeamService teamService, UserService userService) {
        this.requestRepository = requestRepository;
        this.teamService = teamService;
        this.userService = userService;
    }

    @Transactional
    public TeamRequestResponse sendRequest(JoinRequestDTO dto, String senderEmail) {
        User sender = userService.getUserByEmail(senderEmail);
        Team team = teamService.getTeamEntity(dto.teamId);
        if (requestRepository.existsBySenderIdAndTeamId(sender.getId(), dto.teamId)) {
            throw new BadRequestException("You already sent a request to this team");
        }
        if (team.getMembers().contains(sender)) {
            throw new BadRequestException("You are already a member of this team");
        }
        if (!team.getIsOpen()) {
            throw new BadRequestException("This team is not accepting new members");
        }
        TeamRequest request = TeamRequest.builder()
                .sender(sender).team(team).message(dto.message).build();
        return TeamRequestResponse.from(requestRepository.save(request));
    }

    @Transactional
    public TeamRequestResponse respondToRequest(RespondToRequestDTO dto, String leaderEmail) {
        User leader = userService.getUserByEmail(leaderEmail);
        TeamRequest request = requestRepository.findById(dto.requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        if (!request.getTeam().getLeader().getId().equals(leader.getId())) {
            throw new UnauthorizedException("Only the team leader can respond to requests");
        }
        request.setStatus(dto.status);
        request.setRespondedAt(LocalDateTime.now());
        requestRepository.save(request);
        if (dto.status == TeamRequest.Status.ACCEPTED) {
            teamService.addMember(request.getTeam().getId(), request.getSender());
        }
        return TeamRequestResponse.from(request);
    }

    public List<TeamRequestResponse> getIncomingRequests(String leaderEmail) {
        User leader = userService.getUserByEmail(leaderEmail);
        return requestRepository.findPendingRequestsForLeader(leader.getId())
                .stream().map(TeamRequestResponse::from).collect(Collectors.toList());
    }

    public List<TeamRequestResponse> getMySentRequests(String email) {
        User user = userService.getUserByEmail(email);
        return requestRepository.findBySenderId(user.getId())
                .stream().map(TeamRequestResponse::from).collect(Collectors.toList());
    }
}
