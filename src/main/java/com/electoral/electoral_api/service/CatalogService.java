package com.electoral.electoral_api.service;
import com.electoral.electoral_api.repository.catalog.ToneRepository;
import com.electoral.electoral_api.repository.catalog.TopicRepository;
import com.electoral.electoral_api.entity.catalog.CatQuestionTone;
import com.electoral.electoral_api.entity.catalog.CatQuestionTopic;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CatalogService {

    private final ToneRepository toneRepository;
    private final TopicRepository topicRepository;

    public CatalogService(ToneRepository toneRepository, TopicRepository topicRepository) {
        this.toneRepository = toneRepository;
        this.topicRepository = topicRepository;
    }

    public List<CatQuestionTone> getAllTones() {
        return toneRepository.findAll();
    }

    public List<CatQuestionTopic> getAllTopics() {
        return topicRepository.findAll();
    }
}
