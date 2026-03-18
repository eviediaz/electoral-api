package com.electoral.electoral_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MatchResultDTO {
    private UUID candidateId;
    private String candidateName;
    private String candidateParty;
    private String candidatePhoto;
    private Double affinityScore;
    private List<TopicAffinityDTO> topicAffinities;

    @Getter
    @Setter
    public static class TopicAffinityDTO {
        private String topic;
        private Double score;
        private String candidateSummary;
        private String candidateCite;
    }
}