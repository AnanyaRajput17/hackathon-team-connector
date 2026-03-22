package com.college.hackathon.repository;

import com.college.hackathon.model.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
    List<Hackathon> findByStatus(Hackathon.Status status);
    List<Hackathon> findByThemeContainingIgnoreCase(String theme);
    List<Hackathon> findByCreatedById(Long userId);
}