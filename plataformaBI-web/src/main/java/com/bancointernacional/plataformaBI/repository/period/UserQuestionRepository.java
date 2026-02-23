package com.bancointernacional.plataformaBI.repository.period;

import com.bancointernacional.plataformaBI.domain.model.period.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestion, UUID> {

    /**
     * Find all user questions by policy questionary ID
     */
    @Query(value = "SELECT * FROM user_question uq WHERE uq.policy_questionary_id = :policyQuestionaryId AND uq.is_deleted = 0", nativeQuery = true)
    List<UserQuestion> findByPolicyQuestionaryId(@Param("policyQuestionaryId") String policyQuestionaryId);

    /**
     * Check if user question already exists for a specific question and policy questionary
     */
    @Query(value = "SELECT COUNT(*) FROM user_question uq WHERE uq.policy_questionary_id = :policyQuestionaryId AND uq.question_id = :questionId AND uq.is_deleted = 0", nativeQuery = true)
    int existsByPolicyQuestionaryIdAndQuestionId(@Param("policyQuestionaryId") String policyQuestionaryId, @Param("questionId") java.math.BigInteger questionId);

    /**
     * Soft delete user question by setting is_deleted = 1
     */
    @Modifying
    @Query(value = "UPDATE user_question SET is_deleted = 1, updated_at = GETDATE() WHERE user_question_id = :userQuestionId", nativeQuery = true)
    void softDeleteById(@Param("userQuestionId") UUID userQuestionId);

    /**
     * Soft delete user question by setting is_deleted = 1
     */
    @Modifying
    @Query(value = "UPDATE user_question SET is_deleted = 1, updated_at = GETDATE() WHERE policy_questionary_id = :policyQuestionaryId", nativeQuery = true)
    void softDeleteByPolicyQuestionaryId(@Param("policyQuestionaryId") String policyQuestionaryId);


    /**
     * Get paginated list of user questions with optional filters
     * Filters are only applied if values are not null
     */
    @Query(value = "SELECT uq.* FROM user_question uq  INNER JOIN policy_questionary pq ON uq.policy_questionary_id = pq.policy_questionary_id " +
            "INNER JOIN question q ON uq.question_id = q.question_id WHERE uq.is_deleted = 0 AND uq.policy_questionary_id = :policyQuestionaryId "+
            "ORDER BY q.question_id ASC OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY", nativeQuery = true)
    List<UserQuestion> getPaginatedListOfQuestions(
            @Param("offset") int offset,
            @Param("pageSize") int pageSize,
            @Param("policyQuestionaryId") String policyQuestionaryId
    );

    @Query(value = "SELECT uq.* FROM user_question uq  INNER JOIN policy_questionary pq ON uq.policy_questionary_id = pq.policy_questionary_id " +
            "INNER JOIN question q ON uq.question_id = q.question_id WHERE uq.is_deleted = 0 AND uq.user_assigned = :userAssigned AND uq.policy_questionary_id = :policyQuestionaryIdd " +
            " ORDER BY q.question_id ASC OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY", nativeQuery = true)
    List<UserQuestion> getPaginatedListOfQuestions(
            @Param("offset") int offset,
            @Param("pageSize") int pageSize,
            @Param("policyQuestionaryIdd") String policyQuestionaryIdd,
            @Param("userAssigned") String userAssigned
    );

    /**
     * Count total records for pagination with same filters
     */
    @Query(value = "SELECT COUNT(*) FROM user_question uq  INNER JOIN policy_questionary pq ON uq.policy_questionary_id = pq.policy_questionary_id " +
            "INNER JOIN question q ON uq.question_id = q.question_id WHERE uq.is_deleted = 0 AND uq.policy_questionary_id = :policyQuestionaryId", nativeQuery = true)
    long countPaginatedListOfQuestions(
            @Param("policyQuestionaryId") String policyQuestionaryId
    );

    @Query(value = "SELECT COUNT(*) FROM user_question uq  INNER " +
            "JOIN policy_questionary pq ON uq.policy_questionary_id = pq.policy_questionary_id " +
            "INNER JOIN question q ON uq.question_id = q.question_id WHERE uq.is_deleted = 0 AND uq.user_assigned = :userAssigned " +
            "AND uq.policy_questionary_id = :policyQuestionaryId", nativeQuery = true)
    long countPaginatedListOfQuestions(@Param("policyQuestionaryId")String policyQuestionaryId,@Param("userAssigned") String userAssigned);

    @Query(value = "SELECT COUNT(*) FROM user_question uq INNER JOIN policy_questionary pq ON uq.policy_questionary_id = pq.policy_questionary_id " +
            "INNER JOIN question q ON uq.question_id = q.question_id WHERE uq.is_deleted = 0 AND uq.policy_questionary_id = :policyQuestionaryId " +
            "AND uq.question_status IN ('RESOLVED', 'CLOSED')", nativeQuery = true)
    long countQuestionaryProgress(
            @Param("policyQuestionaryId") String policyQuestionaryId
    );

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_question SET question_status = :newStatus, updated_at = GETDATE() " +
            "WHERE policy_questionary_id = :policy_questionary_id AND is_deleted = 0", nativeQuery = true)
    int updateUserQuestionStatusByPolicyQuestionaryId(@Param("policy_questionary_id") String processId,
                                                       @Param("newStatus") String newStatus);


}

