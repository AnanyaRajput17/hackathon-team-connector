package com.college.hackathon.repository;

import com.college.hackathon.model.TeamRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamRequestRepository extends JpaRepository<TeamRequest, Long> {
    List<TeamRequest> findByTeamId(Long teamId);
    List<TeamRequest> findBySenderId(Long senderId);
    List<TeamRequest> findByTeamIdAndStatus(Long teamId, TeamRequest.Status status);
    boolean existsBySenderIdAndTeamId(Long senderId, Long teamId);

    @Query("SELECT r FROM TeamRequest r WHERE r.team.leader.id = :leaderId AND r.status = 'PENDING'")
    List<TeamRequest> findPendingRequestsForLeader(@Param("leaderId") Long leaderId);
}