package com.electoral.electoral_api.specification;

import com.electoral.electoral_api.entity.Question;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public class QuestionSpecification {

    public static Specification<Question> hasFormat(Short idFormat) {
        return (root, query, cb) ->
                idFormat == null ? null : cb.equal(root.get("format").get("id"), idFormat);
    }

    public static Specification<Question> hasTone(Short idTone) {
        return (root, query, cb) ->
                idTone == null ? null : cb.equal(root.get("tone").get("id"), idTone);
    }

    public static Specification<Question> hasTopics(List<Short> topicIds) {
        return (root, query, cb) ->
                (topicIds == null || topicIds.isEmpty()) ? null :
                        root.get("topic").get("id").in(topicIds);
    }
}