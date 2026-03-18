package com.electoral.electoral_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CandidateDTO {
    private UUID id;
    private String name;
    private String party;
    private String photoUrl;
    private String bioSummary;
    private String type;
}