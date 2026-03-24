package com.electoral.electoral_api.controller;


import com.electoral.electoral_api.dto.UserAnswerDTO;
import com.electoral.electoral_api.service.UserAnswerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/answers")
@CrossOrigin(origins = "*")

public class AnswerController {
    private final UserAnswerService userAnswerService;

    public AnswerController(UserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAnswerDTO>> getUserAnswers(
            @PathVariable UUID userId) {
        return ResponseEntity.ok(userAnswerService.getUserAnswers(userId));
    }
}


