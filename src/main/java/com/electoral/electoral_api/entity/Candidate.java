package com.electoral.electoral_api.entity;

import com.electoral.electoral_api.entity.catalog.CatCandidateType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "candidate")
public class Candidate {
    @Id
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "party")
    private String party;

    @Column(name = "photo_url", length = Integer.MAX_VALUE)
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type")
    private CatCandidateType type;

    @Column(name = "bio_summary", length = Integer.MAX_VALUE)
    private String bioSummary;
}