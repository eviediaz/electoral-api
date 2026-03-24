package com.electoral.electoral_api.controller;

import com.electoral.electoral_api.service.QuestionService;
import com.electoral.electoral_api.dto.QuestionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getQuestions(
        @RequestParam(required = false) Integer limit,
        @RequestParam(required = false) Short formatId,
        @RequestParam(required = false) Short toneId,
        @RequestParam(required = false) List<Short> topicIds) {
        return ResponseEntity.ok(questionService.getQuestions(limit, formatId, toneId, topicIds));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countQuestions(
            @RequestParam(required = false) Short toneId,
            @RequestParam(required = false) List<Short> topicIds) {
        return ResponseEntity.ok(questionService.countQuestions(toneId, topicIds));
    }
}