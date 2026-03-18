package com.electoral.electoral_api.repository;

import com.electoral.electoral_api.entity.CandidateSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CandidateSourceRepository extends JpaRepository<CandidateSource, UUID> {
    List<CandidateSource> findByIdCandidate(UUID candidateId);
}