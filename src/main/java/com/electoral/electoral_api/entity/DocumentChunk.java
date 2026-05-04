package com.electoral.electoral_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "document_chunks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_chunks_id_seq")
    @SequenceGenerator(name = "document_chunks_id_seq", sequenceName = "document_chunks_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "metadata", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata;

    @Column(name = "dimension", length = 255)
    private String dimension;

    @Column(name = "embedding", columnDefinition = "vector")
    private String embedding;
}
