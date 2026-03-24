package com.electoral.electoral_api.repository.catalog;
import com.electoral.electoral_api.entity.catalog.CatQuestionTone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ToneRepository extends JpaRepository<CatQuestionTone, Short> {
}
