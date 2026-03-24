package com.electoral.electoral_api.repository;

import com.electoral.electoral_api.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, UUID> {
    List<UserAnswer> findByUser_Id(UUID userId);
    Optional<UserAnswer> findByUser_IdAndQuestion_Id(UUID userId, UUID questionId);

    @Query("SELECT ua FROM UserAnswer ua " +
            "JOIN FETCH ua.question q " +
            "JOIN FETCH q.topic " +
            "JOIN FETCH ua.optionSelected " +
            "WHERE ua.user.id = :userId")
    List<UserAnswer> findAllWithDetailsByUserId(@Param("userId") UUID userId);

}