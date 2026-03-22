package com.college.hackathon.repository;

import com.college.hackathon.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByHackathonId(Long hackathonId);
    List<Team> findByLeaderId(Long leaderId);
    List<Team> findByIsOpenTrue();
    boolean existsByNameAndHackathonId(String name, Long hackathonId);

    @Query("SELECT t FROM Team t JOIN t.members m WHERE m.id = :userId")
    List<Team> findTeamsByMemberId(@Param("userId") Long userId);

    @Query("SELECT t FROM Team t WHERE t.hackathon.id = :hackathonId AND t.isOpen = true")
    List<Team> findOpenTeamsByHackathon(@Param("hackathonId") Long hackathonId);
}