package com.electoral.electoral_api.repository.catalog;
import com.electoral.electoral_api.entity.catalog.CatQuestionTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<CatQuestionTopic, Short> {
}
