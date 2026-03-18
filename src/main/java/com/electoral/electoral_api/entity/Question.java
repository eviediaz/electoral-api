package com.electoral.electoral_api.entity;

import com.electoral.electoral_api.entity.catalog.CatQuestionFormat;
import com.electoral.electoral_api.entity.catalog.CatQuestionTone;
import com.electoral.electoral_api.entity.catalog.CatQuestionTopic;
import jakarta.persistence.*;

import java.util.UUID;

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
    private CatQuestionTopic idTopic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tone")
    private CatQuestionTone idTone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_format")
    private CatQuestionFormat idFormat;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CatQuestionTopic getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(CatQuestionTopic idTopic) {
        this.idTopic = idTopic;
    }

    public CatQuestionTone getIdTone() {
        return idTone;
    }

    public void setIdTone(CatQuestionTone idTone) {
        this.idTone = idTone;
    }

    public CatQuestionFormat getIdFormat() {
        return idFormat;
    }

    public void setIdFormat(CatQuestionFormat idFormat) {
        this.idFormat = idFormat;
    }

}