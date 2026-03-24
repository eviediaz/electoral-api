package com.electoral.electoral_api.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CandidateTrajectoryDTO {
    private String role;
    private String institution;
    private String partyAtTime;
    private LocalDate startDate;
    private LocalDate endDate;
}
