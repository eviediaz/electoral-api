package com.electoral.electoral_api.entity;

import com.electoral.electoral_api.entity.catalog.CatQuestionFormat;
import com.electoral.electoral_api.entity.catalog.CatQuestionTone;
import com.electoral.electoral_api.entity.catalog.CatQuestionTopic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "text", nullable = false, length = Integer.MAX_VALUE)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_topic")
    private CatQuestionTopic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tone")
    private CatQuestionTone tone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_format")
    private CatQuestionFormat format;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_option_set")
    private OptionSet optionSet;
}