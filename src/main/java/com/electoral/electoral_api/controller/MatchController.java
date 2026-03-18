package com.electoral.electoral_api.controller;

import com.electoral.electoral_api.dto.AnswerRequestDTO;
import com.electoral.electoral_api.dto.MatchResultDTO;
import com.electoral.electoral_api.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/match")
@CrossOrigin(origins = "*")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<List<MatchResultDTO>> calculate(
            @RequestBody AnswerRequestDTO request) {
        return ResponseEntity.ok(matchService.calculateMatch(request));
    }
}