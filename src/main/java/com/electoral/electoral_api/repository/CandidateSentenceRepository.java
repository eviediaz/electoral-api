package com.electoral.electoral_api.repository;

import com.electoral.electoral_api.entity.CandidateSentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CandidateSentenceRepository extends JpaRepository<CandidateSentence, UUID> {
    List<CandidateSentence> findByCandidate_Id(UUID candidateId);
}