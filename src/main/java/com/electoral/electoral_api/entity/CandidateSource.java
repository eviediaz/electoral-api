package com.electoral.electoral_api.entity;

import com.electoral.electoral_api.entity.catalog.CatSourceType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

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
    private Candidate idCandidate;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Candidate getIdCandidate() {
        return idCandidate;
    }

    public void setIdCandidate(Candidate idCandidate) {
        this.idCandidate = idCandidate;
    }

    public CatSourceType getIdSourceType() {
        return idSourceType;
    }

    public void setIdSourceType(CatSourceType idSourceType) {
        this.idSourceType = idSourceType;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getContentRaw() {
        return contentRaw;
    }

    public void setContentRaw(String contentRaw) {
        this.contentRaw = contentRaw;
    }

    public Map<String, Object> getContentStructured() {
        return contentStructured;
    }

    public void setContentStructured(Map<String, Object> contentStructured) {
        this.contentStructured = contentStructured;
    }

    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

}