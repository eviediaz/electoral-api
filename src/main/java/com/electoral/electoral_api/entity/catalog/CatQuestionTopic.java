package com.electoral.electoral_api.entity.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "cat_question_topic")
public class CatQuestionTopic {
    @Id
    @Column(name = "id", nullable = false)
    private Short id;

    @Column(name = "code", nullable = false, columnDefinition = "bpchar")
    private String code;

    @Column(name = "description")
    private String description;

}