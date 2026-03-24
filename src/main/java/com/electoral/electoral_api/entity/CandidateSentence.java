package com.electoral.electoral_api.entity;

import com.electoral.electoral_api.entity.catalog.CatSentenceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter

@Entity
@Table(name = "candidate_sentence")
public class CandidateSentence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_candidate")
    private Candidate candidate;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status")
    private CatSentenceStatus status;

    @Column(name = "source_url", length = Integer.MAX_VALUE)
    private String sourceUrl;

    @Column(name = "date")
    private LocalDate date;

}