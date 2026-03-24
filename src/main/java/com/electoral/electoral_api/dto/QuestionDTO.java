package com.electoral.electoral_api.dto;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class QuestionDTO {
    private UUID id;
    private String text;
    private String topic;
    private String tone;
    private String format;
    private Short optionSet;
}