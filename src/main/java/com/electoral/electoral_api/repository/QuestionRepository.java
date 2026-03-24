package com.electoral.electoral_api.repository;

import com.electoral.electoral_api.entity.Question;
import com.electoral.electoral_api.entity.catalog.CatQuestionFormat;
import com.electoral.electoral_api.entity.catalog.CatQuestionTone;
import com.electoral.electoral_api.entity.catalog.CatQuestionTopic;
import jakarta.annotation.Nullable;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID>,
        JpaSpecificationExecutor<Question> {
    @NullMarked
    @EntityGraph(attributePaths = {"topic", "tone", "format", "optionSet"})
    List<Question> findAll(@Nullable Specification<Question> spec);
}