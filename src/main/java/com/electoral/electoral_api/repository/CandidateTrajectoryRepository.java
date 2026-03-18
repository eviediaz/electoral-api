package com.electoral.electoral_api.repository;

import com.electoral.electoral_api.entity.CandidateTrajectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CandidateTrajectoryRepository extends JpaRepository<CandidateTrajectory, UUID> {
    List<CandidateTrajectory> findByIdCandidateOrderByStartDateDesc(UUID candidateId);
}