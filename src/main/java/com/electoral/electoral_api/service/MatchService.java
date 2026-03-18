package com.electoral.electoral_api.service;

import com.electoral.electoral_api.dto.AnswerRequestDTO;
import com.electoral.electoral_api.dto.MatchResultDTO;
import com.electoral.electoral_api.entity.Candidate;
import com.electoral.electoral_api.entity.CandidateSource;
import com.electoral.electoral_api.repository.CandidateRepository;
import com.electoral.electoral_api.repository.CandidateSourceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.postgresql.util.PGobject;
import java.util.*;

@Service
public class MatchService {

    private final CandidateRepository candidateRepository;
    private final CandidateSourceRepository candidateSourceRepository;
    private final ObjectMapper objectMapper;

    public MatchService(CandidateRepository candidateRepository,
                        CandidateSourceRepository candidateSourceRepository,
                        ObjectMapper objectMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateSourceRepository = candidateSourceRepository;
        this.objectMapper = objectMapper;
    }

    public List<MatchResultDTO> calculateMatch(AnswerRequestDTO request) {
        // 1. Agrupar respuestas del usuario por tema
        Map<String, Integer> userValuesByTopic = new HashMap<>();
        for (AnswerRequestDTO.AnswerItemDTO answer : request.getAnswers()) {
            userValuesByTopic.merge(answer.getTopic(), answer.getValue(), Integer::sum);
        }

        // Normalizar a promedio por tema
        Map<String, List<Integer>> topicValues = new HashMap<>();
        for (AnswerRequestDTO.AnswerItemDTO answer : request.getAnswers()) {
            topicValues.computeIfAbsent(answer.getTopic(), k -> new ArrayList<>())
                    .add(answer.getValue());
        }
        Map<String, Double> userAvgByTopic = new HashMap<>();
        topicValues.forEach((topic, values) ->
                userAvgByTopic.put(topic, values.stream()
                        .mapToInt(Integer::intValue).average().orElse(3.0))
        );

        // 2. Obtener candidatos a presidente (type id = 1)
        List<Candidate> candidates = candidateRepository.findByType_Id((short) 1);

        List<MatchResultDTO> results = new ArrayList<>();

        for (Candidate candidate : candidates) {
            // 3. Obtener el plan de gobierno del candidato
            List<CandidateSource> sources = candidateSourceRepository
                    .findByCandidate_Id(candidate.getId());

            if (sources.isEmpty()) continue;

            CandidateSource source = sources.get(0);
            if (source.getContentStructured() == null) continue;

            try {
                String jsonString;
                Object content = source.getContentStructured();

                Map<String, Object> contentMap;

                if (content instanceof Map) {
                    contentMap = (Map<String, Object>) content;
                } else {
                    System.err.println("Tipo inesperado: " + content.getClass().getName());
                    continue;
                }

                // 4. Calcular afinidad por tema
                List<MatchResultDTO.TopicAffinityDTO> topicAffinities = new ArrayList<>();
                double totalScore = 0;
                int topicCount = 0;

                for (Map.Entry<String, Double> entry : userAvgByTopic.entrySet()) {
                    String topic = entry.getKey();
                    Double userValue = entry.getValue();

                    Object topicObj = contentMap.get(topic);
                    if (topicObj == null) continue;

                    Map<String, Object> topicMap = (Map<String, Object>) topicObj;

                    int candidateValue = ((Number) topicMap.get("value")).intValue();
                    String resumen = (String) topicMap.get("resumen");
                    String cita = (String) topicMap.get("cita");

                    // Diferencia máxima posible es 4 (entre 1 y 5)
                    double difference = Math.abs(userValue - candidateValue);
                    double topicScore = ((4 - difference) / 4) * 100;

                    MatchResultDTO.TopicAffinityDTO topicAffinity = new MatchResultDTO.TopicAffinityDTO();
                    topicAffinity.setTopic(topic);
                    topicAffinity.setScore(Math.round(topicScore * 10.0) / 10.0);
                    topicAffinity.setCandidateSummary(resumen);
                    topicAffinity.setCandidateCite(cita);
                    topicAffinities.add(topicAffinity);

                    totalScore += topicScore;
                    topicCount++;
                }

                if (topicCount == 0) continue;

                double affinityScore = Math.round((totalScore / topicCount) * 10.0) / 10.0;

                // 5. Armar resultado
                MatchResultDTO result = new MatchResultDTO();
                result.setCandidateId(candidate.getId());
                result.setCandidateName(candidate.getName());
                result.setCandidateParty(candidate.getParty());
                result.setCandidatePhoto(candidate.getPhotoUrl());
                result.setAffinityScore(affinityScore);
                result.setTopicAffinities(topicAffinities);
                results.add(result);

            } catch (Exception e) {
                System.err.println("Error procesando candidato: " + candidate.getName());
            }
        }

        // 6. Ordenar por score descendente y devolver top 3
        results.sort((a, b) -> Double.compare(b.getAffinityScore(), a.getAffinityScore()));
        return results.size() > 3 ? results.subList(0, 3) : results;
    }
}