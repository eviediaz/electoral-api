package com.electoral.electoral_api.service;

import com.electoral.electoral_api.entity.Question;
import com.electoral.electoral_api.entity.catalog.CatQuestionFormat;
import com.electoral.electoral_api.entity.catalog.CatQuestionTopic;
import com.electoral.electoral_api.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByFormat(CatQuestionFormat formatId) {
        return questionRepository.findByIdFormat(formatId);
    }

    public List<Question> getQuestionsByTopic(CatQuestionTopic topicId) {
        return questionRepository.findByIdTopic(topicId);
    }
}