package com.bancointernacional.plataformaBI.repository.period;

import com.bancointernacional.plataformaBI.domain.model.period.PolicyQuestionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Repository
public interface PolicyQuestionaryRepository extends JpaRepository<PolicyQuestionary, UUID> {

    /**
     * Find all policy questionnaires by process policy ID
     */
    @Query(value = "SELECT * FROM policy_questionary pq WHERE pq.process_policy_id = :processPolicyId AND pq.is_deleted = 0", nativeQuery = true)
    List<PolicyQuestionary> findByProcessPolicyId(@Param("processPolicyId") String processPolicyId);

    /**
     * Find questionnaire IDs already assigned to a process policy
     */
    @Query(value = "SELECT pq.questionary_id FROM policy_questionary pq WHERE pq.process_policy_id = :processPolicyId AND pq.is_deleted = 0", nativeQuery = true)
    List<BigInteger> findAssignedQuestionaryIdsByProcessPolicyId(@Param("processPolicyId") String processPolicyId);

    /**
     * Soft delete policy questionary by setting is_deleted = 1
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE policy_questionary SET is_deleted = 1, updated_at = GETDATE() WHERE policy_questionary_id = :policyQuestionaryId", nativeQuery = true)
    void softDeleteById(@Param("policyQuestionaryId") String policyQuestionaryId);

    /**
     * Find policy questionnaires with filtering capabilities similar to process policies
     */
    @Query(value = "SELECT pq.policy_questionary_id, q.questionary_name, pq.start_date, pq.end_date, " +
            "pp.process_id, pq.process_policy_id, pq.policy_questionary_status, q.description, " +
            "pc.process_description, p.policy_name, q.questionary_id, pc.start_date as pcstdate, pc.end_date as pceddate, q.total_number " +
            "FROM policy_questionary pq " +
            "JOIN questionary q ON pq.questionary_id = q.questionary_id " +
            "JOIN process_policy pp ON pq.process_policy_id = pp.process_policy_id " +
            "JOIN process pc ON pp.process_id = pc.process_id " +
            "JOIN policy p ON pp.policy_id = p.policy_id " +
            "WHERE pq.is_deleted = 0 " +
            "AND pp.is_deleted = 0 " +
            "AND pc.is_deleted = 0 " +
            "AND (:subsidiaryId IS NULL OR pc.subsidiary_id = :subsidiaryId) " +
            "AND (:idProcess IS NULL OR pp.process_id LIKE CONCAT('%', :idProcess, '%')) " +
            "AND (:processPolicyId IS NULL OR pp.process_policy_id LIKE CONCAT('%', :processPolicyId, '%')) " +
            "ORDER BY pp.process_id OFFSET :offset ROWS FETCH NEXT :page ROWS ONLY", nativeQuery = true)
    List<Object[]> findPolicyQuestionaryFilteredData(
            @Param("subsidiaryId") Long subsidiary,
            @Param("idProcess") String idProcess,
            @Param("offset") int offset,
            @Param("page") int page,
            @Param("processPolicyId") String processPolicyId);

    /**
     * Count policy questionnaires without pagination for filtering
     */
    @Query(value = "SELECT COUNT(*) " +
            "FROM policy_questionary pq " +
            "JOIN questionary q ON pq.questionary_id = q.questionary_id " +
            "JOIN process_policy pp ON pq.process_policy_id = pp.process_policy_id " +
            "JOIN process pc ON pp.process_id = pc.process_id " +
            "JOIN policy p ON pp.policy_id = p.policy_id " +
            "WHERE pq.is_deleted = 0 " +
            "AND pp.is_deleted = 0 " +
            "AND pc.is_deleted = 0 " +
            "AND (:subsidiaryId IS NULL OR pc.subsidiary_id = :subsidiaryId) " +
            "AND (:idProcess IS NULL OR pp.process_id LIKE CONCAT('%', :idProcess, '%')) " +
            "AND (:processPolicyId IS NULL OR pp.process_policy_id LIKE CONCAT('%', :processPolicyId, '%')) ", nativeQuery = true)
    Integer countPolicyQuestionaryFilteredData(
            @Param("subsidiaryId") Long subsidiary,
            @Param("idProcess") String idProcess,
            @Param("processPolicyId") String processPolicyId);


    @Query(value = "SELECT COUNT(*) " +
            "FROM policy_questionary pq " +
            "JOIN questionary q ON pq.questionary_id = q.questionary_id " +
            "JOIN process_policy pp ON pq.process_policy_id = pp.process_policy_id " +
            "JOIN process pc ON pp.process_id = pc.process_id " +
            "JOIN policy p ON pp.policy_id = p.policy_id " +
            "WHERE pq.is_deleted = 0 " +
            "AND pp.is_deleted = 0 " +
            "AND pc.is_deleted = 0 " +
            "WHERE pq.process_policy_id := processPolicyId and pq.policy_questionary_status IN ('RESOLVED', 'CLOSED')", nativeQuery = true)
    long countQuestionarysResolvedOrColsedByProcessPolicyId(@Param("processPolicyId") String processPolicyId);

    @Query(value = "SELECT COUNT(*) " +
            "FROM policy_questionary pq " +
            "JOIN questionary q ON pq.questionary_id = q.questionary_id " +
            "JOIN process_policy pp ON pq.process_policy_id = pp.process_policy_id " +
            "JOIN process pc ON pp.process_id = pc.process_id " +
            "JOIN policy p ON pp.policy_id = p.policy_id " +
            "WHERE pq.is_deleted = 0 " +
            "AND pp.is_deleted = 0 " +
            "AND pc.is_deleted = 0 " +
            "WHERE pq.process_policy_id := processPolicyId", nativeQuery = true)
    long countQuestionariesByProcessPolicyId(String string);

    @Query(value = "SELECT DISTINCT pq.policy_questionary_id,  pq.start_date, pq.end_date, pq.policy_questionary_status, " +
            "pq.questionary_id, pq.process_policy_id, pq.is_deleted, pq.created_at, pq.updated_at  " +
            "FROM policy_questionary pq " +
            "JOIN questionary q ON pq.questionary_id = q.questionary_id " +
            "JOIN process_policy pp ON pq.process_policy_id = pp.process_policy_id " +
            "JOIN process pc ON pp.process_id = pc.process_id " +
            "JOIN policy p ON pp.policy_id = p.policy_id " +
            "JOIN user_question uq ON uq.policy_questionary_id = pq.policy_questionary_id " +
            "WHERE pq.is_deleted = 0 " +
            "AND pp.is_deleted = 0 " +
            "AND pc.is_deleted = 0 " +
            "AND uq.is_deleted=0 AND  uq.user_assigned = :userId", nativeQuery = true)
    List<PolicyQuestionary> findAssignedQuestionariesByUserId(@Param("userId") String userId);
}
