package com.electoral.electoral_api.repository;

import com.electoral.electoral_api.entity.Question;
import com.electoral.electoral_api.entity.catalog.CatQuestionFormat;
import com.electoral.electoral_api.entity.catalog.CatQuestionTone;
import com.electoral.electoral_api.entity.catalog.CatQuestionTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findByIdToneAndIdFormat(CatQuestionTone idTone, CatQuestionFormat idFormat);

    List<Question> findByIdFormat(CatQuestionFormat idFormat);
    List<Question> findByIdTopic(CatQuestionTopic idTopic);
}   