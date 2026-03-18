package com.electoral.electoral_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AnswerRequestDTO {
    private UUID userId;
    private List<AnswerItemDTO> answers;

    @Getter
    @Setter
    public static class AnswerItemDTO {
        private UUID questionId;
        private Integer optionId;
        private Integer value;
        private String topic;
    }
}