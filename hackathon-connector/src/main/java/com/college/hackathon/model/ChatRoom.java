package com.college.hackathon.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_rooms")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
