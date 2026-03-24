package com.electoral.electoral_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CandidateSentenceDTO {
    private String description;
    private String status;
    private String sourceUrl;
    private LocalDate date;
}
