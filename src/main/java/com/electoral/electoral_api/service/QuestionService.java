package com.electoral.electoral_api.service;

import com.electoral.electoral_api.entity.OptionSetItem;
import com.electoral.electoral_api.entity.Question;
import com.electoral.electoral_api.entity.catalog.CatQuestionFormat;
import com.electoral.electoral_api.entity.catalog.CatQuestionTopic;
import com.electoral.electoral_api.repository.OptionSetItemRepository;
import com.electoral.electoral_api.repository.QuestionRepository;
import com.electoral.electoral_api.dto.QuestionDTO;
import com.electoral.electoral_api.dto.OptionSetItemDTO;
import com.electoral.electoral_api.specification.QuestionSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final OptionSetItemRepository optionSetItemRepository;

    public QuestionService(QuestionRepository questionRepository,
                           OptionSetItemRepository optionSetItemRepository) {
        this.questionRepository = questionRepository;
        this.optionSetItemRepository = optionSetItemRepository;
    }

    public List<QuestionDTO> getQuestions(Integer limit, Short formatId, Short toneId, List<Short> topicIds) {
        Specification<Question> spec = Specification
                .where(QuestionSpecification.hasFormat(formatId))
                .and(QuestionSpecification.hasTone(toneId))
                .and(QuestionSpecification.hasTopics(topicIds));

        var stream = questionRepository.findAll(spec)
                .stream();

        if (limit != null) {
            stream = stream.limit(limit);
        }

        return stream
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    private QuestionDTO toDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setText(question.getText());

        if (question.getTopic() != null) {
            dto.setTopic(question.getTopic().getCode());
        }
        if (question.getFormat() != null) {
            dto.setFormat(question.getFormat().getCode());
        }
        if (question.getTone() != null) {
            dto.setTone(question.getTone().getCode());
        }
        if (question.getOptionSet() != null) {
            dto.setOptionSet(question.getOptionSet().getId());
        }
        return dto;
    }

    public long countQuestions(Short toneId, List<Short> topicIds) {
        Specification<Question> spec = Specification
                .where(QuestionSpecification.hasTone(toneId))
                .and(QuestionSpecification.hasTopics(topicIds));

        return questionRepository.count(spec);
    }
}
