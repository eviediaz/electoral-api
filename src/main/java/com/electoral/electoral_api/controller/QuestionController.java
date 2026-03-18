package com.electoral.electoral_api.controller;

import com.electoral.electoral_api.entity.Question;
import com.electoral.electoral_api.entity.catalog.CatQuestionFormat;
import com.electoral.electoral_api.entity.catalog.CatQuestionTopic;
import com.electoral.electoral_api.service.QuestionService;
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
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/format/{formatId}")
    public ResponseEntity<List<Question>> getByFormat(@PathVariable CatQuestionFormat formatId) {
        return ResponseEntity.ok(questionService.getQuestionsByFormat(formatId));
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<Question>> getByTopic(@PathVariable CatQuestionTopic topicId) {
        return ResponseEntity.ok(questionService.getQuestionsByTopic(topicId));
    }
}