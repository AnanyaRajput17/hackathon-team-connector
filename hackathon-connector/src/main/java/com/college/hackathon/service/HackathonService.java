package com.college.hackathon.service;

import com.college.hackathon.dto.CreateHackathonRequest;
import com.college.hackathon.dto.HackathonResponse;
import com.college.hackathon.exception.ResourceNotFoundException;
import com.college.hackathon.model.Hackathon;
import com.college.hackathon.model.User;
import com.college.hackathon.repository.HackathonRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HackathonService {
    private final HackathonRepository hackathonRepository;
    private final UserService userService;

    public HackathonService(HackathonRepository hackathonRepository, UserService userService) {
        this.hackathonRepository = hackathonRepository;
        this.userService = userService;
    }

    public List<HackathonResponse> getAllHackathons() {
        return hackathonRepository.findAll().stream()
                .map(HackathonResponse::from).collect(Collectors.toList());
    }

    public HackathonResponse getHackathon(Long id) {
        return HackathonResponse.from(hackathonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found")));
    }

    public HackathonResponse createHackathon(CreateHackathonRequest request, String creatorEmail) {
        User creator = userService.getUserByEmail(creatorEmail);
        Hackathon hackathon = Hackathon.builder()
                .name(request.name)
                .description(request.description)
                .theme(request.theme)
                .venue(request.venue)
                .startDate(request.startDate)
                .endDate(request.endDate)
                .registrationDeadline(request.registrationDeadline)
                .maxTeamSize(request.maxTeamSize)
                .minTeamSize(request.minTeamSize)
                .createdBy(creator)
                .build();
        return HackathonResponse.from(hackathonRepository.save(hackathon));
    }

    public List<HackathonResponse> getUpcomingHackathons() {
        return hackathonRepository.findByStatus(Hackathon.Status.UPCOMING)
                .stream().map(HackathonResponse::from).collect(Collectors.toList());
    }
}