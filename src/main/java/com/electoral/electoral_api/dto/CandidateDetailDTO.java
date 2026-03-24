package com.electoral.electoral_api.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CandidateDetailDTO {
    private UUID id;
    private String name;
    private String party;
    private String photoUrl;
    private String bioSummary;
    private String type;
    private List<CandidateSentenceDTO> sentences;
    private List<CandidateTrajectoryDTO> trajectory;
}
