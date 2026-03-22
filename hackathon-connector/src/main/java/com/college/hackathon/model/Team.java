package com.college.hackathon.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teams")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "hackathon_id", nullable = false)
    private Hackathon hackathon;

    @ManyToOne
    @JoinColumn(name = "leader_id", nullable = false)
    private User leader;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "team_members",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members = new HashSet<>();

    // Skills the team is looking for
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "team_required_skills",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> requiredSkills = new HashSet<>();

    @Column(name = "is_open")
    private Boolean isOpen = true; // accepting new members?

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL)
    private ChatRoom chatRoom;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
