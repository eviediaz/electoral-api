package com.electoral.electoral_api.controller;

import com.electoral.electoral_api.entity.catalog.CatQuestionTone;
import com.electoral.electoral_api.entity.catalog.CatQuestionTopic;
import com.electoral.electoral_api.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/catalog")
@CrossOrigin(origins = "*")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/tone")
    public ResponseEntity<List<CatQuestionTone>> getAllTones() {
        return ResponseEntity.ok(catalogService.getAllTones());
    }

    @GetMapping("/topic")
    public ResponseEntity<List<CatQuestionTopic>> getAllTopics() {
        return ResponseEntity.ok(catalogService.getAllTopics());
    }
}