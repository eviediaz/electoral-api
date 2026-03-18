package com.electoral.electoral_api.entity;

import com.electoral.electoral_api.entity.catalog.CatSourceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "candidate_source")
public class CandidateSource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_candidate")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_source_type")
    private CatSourceType idSourceType;

    @Column(name = "source_url", length = Integer.MAX_VALUE)
    private String sourceUrl;

    @Column(name = "content_raw", length = Integer.MAX_VALUE)
    private String contentRaw;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content_structured")
    private Map<String, Object> contentStructured;

    @Column(name = "verified_at")
    private Instant verifiedAt;
}