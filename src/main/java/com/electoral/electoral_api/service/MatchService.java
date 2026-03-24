package com.electoral.electoral_api.service;

import org.springframework.transaction.annotation.Transactional;
import com.electoral.electoral_api.dto.AnswerRequestDTO;
import com.electoral.electoral_api.dto.MatchResultDTO;
import com.electoral.electoral_api.entity.*;
import com.electoral.electoral_api.repository.*;
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
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final OptionSetItemRepository optionSetItemRepository;
    private final UserAnswerRepository userAnswerRepository;

    public MatchService(CandidateRepository candidateRepository,
                        CandidateSourceRepository candidateSourceRepository,
                        ObjectMapper objectMapper,
                        UserRepository userRepository,
                        QuestionRepository questionRepository,
                        OptionSetItemRepository optionSetItemRepository,
                        UserAnswerRepository userAnswerRepository) {
        this.candidateRepository = candidateRepository;
        this.candidateSourceRepository = candidateSourceRepository;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.optionSetItemRepository = optionSetItemRepository;
        this.userAnswerRepository = userAnswerRepository;
    }

    private String mapTopicCode(String code) {
        Map<String, String> topicMap = Map.of(
                "se", "seguridad",
                "ec", "economia",
                "sa", "salud",
                "ed", "educacion",
                "ju", "justicia"
        );
        return topicMap.getOrDefault(code.toLowerCase(), code);
    }

    private void saveUserAnswers(AnswerRequestDTO request) {
        // Busca el usuario
            User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        for (AnswerRequestDTO.AnswerItemDTO answerItem : request.getAnswers()) {
            // Busca la pregunta
            Question question = questionRepository.findById(answerItem.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));

            // Busca la opción seleccionada
            OptionSetItem optionSetItem = optionSetItemRepository
                    .findById(answerItem.getOptionId().shortValue())
                    .orElseThrow(() -> new RuntimeException("Opción no encontrada"));

            // Verifica si ya existe una respuesta previa para esta pregunta
            Optional<UserAnswer> existing = userAnswerRepository
                    .findByUser_IdAndQuestion_Id(request.getUserId(), answerItem.getQuestionId());

            if (existing.isPresent()) {
                // Actualiza la respuesta existente
                UserAnswer answer = existing.get();
                answer.setOptionSelected(optionSetItem);
                userAnswerRepository.save(answer);
            } else {
                // Crea una nueva respuesta
                UserAnswer answer = new UserAnswer();
                answer.setUser(user);
                answer.setQuestion(question);
                answer.setOptionSelected(optionSetItem);
                userAnswerRepository.save(answer);
            }
        }
    }

    @Transactional
    public List<MatchResultDTO> calculateMatch(AnswerRequestDTO request) {
        saveUserAnswers(request);

        List<UserAnswer> allAnswers = userAnswerRepository
                .findAllWithDetailsByUserId(request.getUserId());

        // 1. Agrupar respuestas del usuario por tema
        Map<String, List<Integer>> topicValues = new HashMap<>();

        for (UserAnswer answer : allAnswers) {
            if (answer.getQuestion().getTopic() == null) continue;

            String topicCode = answer.getQuestion().getTopic().getCode().toLowerCase();
            int value = answer.getOptionSelected().getValue();

            topicValues.computeIfAbsent(topicCode, k -> new ArrayList<>())
                    .add(value);
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
                    String topic = mapTopicCode(entry.getKey());
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

        results.sort((a, b) -> Double.compare(b.getAffinityScore(), a.getAffinityScore()));
        return results.size() > 3 ? results.subList(0, 3) : results;
    }


}