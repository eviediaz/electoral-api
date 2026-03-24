package com.electoral.electoral_api.controller;

import com.electoral.electoral_api.dto.CandidateDTO;
import com.electoral.electoral_api.dto.CandidateDetailDTO;
import com.electoral.electoral_api.entity.Candidate;
import com.electoral.electoral_api.entity.catalog.CatCandidateType;
import com.electoral.electoral_api.service.CandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "*")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> getAllCandidates() {
        return ResponseEntity.ok(candidateService.getAllCandidates());
    }

    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<CandidateDTO>> getByType(@PathVariable Short typeId) {
        return ResponseEntity.ok(candidateService.getCandidatesByType(typeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDetailDTO> getById(@PathVariable UUID id) {
        return candidateService.getCandidateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}