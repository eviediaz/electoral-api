package com.electoral.electoral_api.service;

import com.electoral.electoral_api.dto.UserAnswerDTO;
import com.electoral.electoral_api.entity.UserAnswer;
import com.electoral.electoral_api.repository.UserAnswerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;

    public UserAnswerService(UserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

    public List<UserAnswerDTO> getUserAnswers(UUID userId) {
        return userAnswerRepository.findAllWithDetailsByUserId(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private UserAnswerDTO toDTO(UserAnswer ua) {
        UserAnswerDTO dto = new UserAnswerDTO();
        dto.setUserAnswerId(ua.getId());
        dto.setQuestionId(ua.getQuestion().getId());
        dto.setQuestionText(ua.getQuestion().getText());

        if (ua.getQuestion().getTopic() != null) {
            dto.setTopicCode(ua.getQuestion().getTopic().getCode());
        }
        if (ua.getQuestion().getFormat() != null) {
            dto.setFormatCode(ua.getQuestion().getFormat().getCode());
        }
        if (ua.getOptionSelected() != null) {
            dto.setSelectedOptionId(ua.getOptionSelected().getId());
            dto.setSelectedOptionText(ua.getOptionSelected().getText());
            dto.setSelectedOptionValue((int) ua.getOptionSelected().getValue());
        }
        if (ua.getQuestion().getOptionSet() != null) {
            dto.setOptionSetId(ua.getQuestion().getOptionSet().getId());
        }
        return dto;
    }
}