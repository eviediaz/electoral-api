package com.electoral.electoral_api.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAnswerDTO {
    private UUID userAnswerId;
    private UUID questionId;
    private String questionText;
    private String topicCode;
    private String formatCode;
    private Short selectedOptionId;
    private String selectedOptionText;
    private Integer selectedOptionValue;
    private Short optionSetId;
}
