package com.college.hackathon.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hackathons")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String theme;
    private String venue;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "registration_deadline")
    private LocalDate registrationDeadline;

    @Column(name = "max_team_size")
    private Integer maxTeamSize = 4;

    @Column(name = "min_team_size")
    private Integer minTeamSize = 2;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Enumerated(EnumType.STRING)
    private Status status = Status.UPCOMING;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status { UPCOMING, ONGOING, COMPLETED }
}
