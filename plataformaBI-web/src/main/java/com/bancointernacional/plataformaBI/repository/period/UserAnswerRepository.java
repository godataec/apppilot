package com.bancointernacional.plataformaBI.repository.period;

import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.model.period.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, UUID> {

    /**
     * Find all user answers by user question ID (non-deleted) - Native Query
     */
    @Query(value = "SELECT * FROM user_answer ua WHERE ua.user_question_id = :userQuestionId AND ua.is_deleted = 0", nativeQuery = true)
    List<UserAnswer> findByUserQuestionId(@Param("userQuestionId") String userQuestionId);


    /**
     * Find all user answers by user question ID - Raw Native Query (including deleted)
     */
    @Query(value = "SELECT * FROM user_answer ua WHERE ua.user_question_id = :userQuestionId", nativeQuery = true)
    List<UserAnswer> findRawByUserQuestionId(@Param("userQuestionId") String userQuestionId);


    /**
     * Find all user answers by user question entity (non-deleted) - JPA Query
     */
    List<UserAnswer> findByUserQuestionAndIsDeletedFalse(UserQuestion userQuestion);

    /**
     * Find all user answers by policy questionary ID (non-deleted)
     */
    @Query(value = "SELECT * FROM user_answer ua WHERE ua.policy_questionary_id = :policyQuestionaryId AND ua.is_deleted = 0", nativeQuery = true)
    List<UserAnswer> findByPolicyQuestionaryId(@Param("policyQuestionaryId") String policyQuestionaryId);

    /**
     * Find all user answers by user assigned (non-deleted)
     */
    @Query(value = "SELECT * FROM user_answer ua WHERE ua.user_assigned = :userAssigned AND ua.is_deleted = 0", nativeQuery = true)
    List<UserAnswer> findByUserAssigned(@Param("userAssigned") String userAssigned);

    /**
     * Find user answer by ID (non-deleted)
     */
    @Query(value = "SELECT * FROM user_answer ua WHERE ua.user_answer_id = :answerId AND ua.is_deleted = 0", nativeQuery = true)
    Optional<UserAnswer> findByIdAndNotDeleted(@Param("answerId") String answerId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_answer SET answer_status = :newStatus, updated_at = GETDATE() " +
            "WHERE policy_questionary_id = :policy_questionary_id AND is_deleted = 0", nativeQuery = true)
    int updateUserAnswersStatusByPolicyQuestionaryId(@Param("policy_questionary_id") String processId,
                                                       @Param("newStatus") String newStatus);


    /**
     * Soft delete user answer by ID
     */
    @Modifying
    @Query(value = "UPDATE user_answer SET is_deleted = 1, updated_at = GETDATE() WHERE user_answer_id = :answerId", nativeQuery = true)
    void softDeleteById(@Param("answerId") String answerId);

    /**
     * Soft delete user answer by setting is_deleted = 1
     */
    @Modifying
    @Query(value = "UPDATE user_answer SET is_deleted = 1, updated_at = GETDATE() WHERE policy_questionary_id = :policyQuestionaryId", nativeQuery = true)
    void softDeleteByPolicyQuestionaryId(@Param("policyQuestionaryId") String policyQuestionaryId);



    /**
     * Check if user answer exists for specific user question and user
     */
    @Query(value = "SELECT COUNT(*) FROM user_answer ua WHERE ua.user_question_id = :userQuestionId AND ua.user_assigned = :userAssigned AND ua.is_deleted = 0", nativeQuery = true)
    int countByUserQuestionIdAndUserAssigned(@Param("userQuestionId") String userQuestionId, @Param("userAssigned") String userAssigned);

    List<UserAnswer> findByQuestionId(BigInteger questionId);
}
