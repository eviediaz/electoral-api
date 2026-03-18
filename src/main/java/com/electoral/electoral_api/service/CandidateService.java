package com.electoral.electoral_api.service;

import com.electoral.electoral_api.entity.Candidate;
import com.electoral.electoral_api.entity.catalog.CatCandidateType;
import com.electoral.electoral_api.repository.CandidateRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public List<Candidate> getCandidatesByType(Short typeId) {
        return candidateRepository.findByType_Id(typeId);
    }

    public Optional<Candidate> getCandidateById(UUID id) {
        return candidateRepository.findById(id);
    }
}