package com.electoral.electoral_api.service;

import com.electoral.electoral_api.dto.*;
import com.electoral.electoral_api.entity.Candidate;
import com.electoral.electoral_api.entity.CandidateSentence;
import com.electoral.electoral_api.entity.CandidateTrajectory;
import com.electoral.electoral_api.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateSentenceRepository candidateSentenceRepository;
    private final CandidateTrajectoryRepository candidateTrajectoryRepository;

    public CandidateService(CandidateRepository candidateRepository,
                            CandidateSentenceRepository candidateSentenceRepository,
                            CandidateTrajectoryRepository candidateTrajectoryRepository) {
        this.candidateRepository = candidateRepository;
        this.candidateSentenceRepository = candidateSentenceRepository;
        this.candidateTrajectoryRepository = candidateTrajectoryRepository;
    }

    public List<CandidateDTO> getAllCandidates() {
        return candidateRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CandidateDTO> getCandidatesByType(Short typeId) {
        return candidateRepository.findByType_Id(typeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CandidateDetailDTO> getCandidateById(UUID id) {
        return candidateRepository.findById(id)
                .map(this::toDetailDTO);
    }

    private CandidateDTO toDTO(Candidate candidate) {
        CandidateDTO dto = new CandidateDTO();
        dto.setId(candidate.getId());
        dto.setName(candidate.getName());
        dto.setParty(candidate.getParty());
        dto.setPhotoUrl(candidate.getPhotoUrl());
        dto.setBioSummary(candidate.getBioSummary());
        if (candidate.getType() != null) {
            dto.setType(candidate.getType().getDescription());
        }
        // indica si tiene sentencias sin traerlas todas
        boolean hasSentences = !candidateSentenceRepository
                .findByCandidate_Id(candidate.getId()).isEmpty();
        dto.setHasSentences(hasSentences);
        return dto;
    }

    private CandidateDetailDTO toDetailDTO(Candidate candidate) {
        CandidateDetailDTO dto = new CandidateDetailDTO();
        dto.setId(candidate.getId());
        dto.setName(candidate.getName());
        dto.setParty(candidate.getParty());
        dto.setPhotoUrl(candidate.getPhotoUrl());
        dto.setBioSummary(candidate.getBioSummary());
        if (candidate.getType() != null) {
            dto.setType(candidate.getType().getDescription());
        }

        dto.setSentences(
                candidateSentenceRepository.findByCandidate_Id(candidate.getId())
                        .stream()
                        .map(this::toSentenceDTO)
                        .collect(Collectors.toList())
        );

        dto.setTrajectory(
                candidateTrajectoryRepository
                        .findByCandidate_IdOrderByStartDateDesc(candidate.getId())
                        .stream()
                        .map(this::toTrajectoryDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    private CandidateSentenceDTO toSentenceDTO(CandidateSentence sentence) {
        CandidateSentenceDTO dto = new CandidateSentenceDTO();
        dto.setDescription(sentence.getDescription());
        dto.setSourceUrl(sentence.getSourceUrl());
        dto.setDate(sentence.getDate());
        if (sentence.getStatus() != null) {
            dto.setStatus(sentence.getStatus().getDescription());
        }
        return dto;
    }

    private CandidateTrajectoryDTO toTrajectoryDTO(CandidateTrajectory trajectory) {
        CandidateTrajectoryDTO dto = new CandidateTrajectoryDTO();
        dto.setRole(trajectory.getRole());
        dto.setInstitution(trajectory.getInstitution());
        dto.setPartyAtTime(trajectory.getPartyAtTime());
        dto.setStartDate(trajectory.getStartDate());
        dto.setEndDate(trajectory.getEndDate());
        return dto;
    }
}