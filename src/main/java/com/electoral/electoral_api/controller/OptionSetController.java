package com.electoral.electoral_api.controller;

import com.electoral.electoral_api.dto.OptionSetDTO;
import com.electoral.electoral_api.service.OptionSetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/option-sets")
@CrossOrigin(origins = "*")
public class OptionSetController {

    private final OptionSetService optionSetService;

    public OptionSetController(OptionSetService optionSetService) {
        this.optionSetService = optionSetService;
    }

    @GetMapping
    public ResponseEntity<List<OptionSetDTO>> getAllOptionSets() {
        return ResponseEntity.ok(optionSetService.getAllOptionSets());
    }
}