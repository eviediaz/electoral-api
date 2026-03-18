package com.electoral.electoral_api.repository;

import com.electoral.electoral_api.entity.MatchResult;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface MatchResultRepository extends JpaRepository<MatchResult, UUID> {
    List<MatchResult> findByIdUser_IdOrderByAffinityScoreDesc(UUID idUserId, Limit limit);
}