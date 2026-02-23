package com.bancointernacional.plataformaBI.repository.template;

import com.bancointernacional.plataformaBI.domain.model.template.Questionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface QuestionaryRepository extends JpaRepository<Questionary, Integer> {

    /**
     * Find all questionnaires by policy ID
     */
    @Query("SELECT q FROM Questionary q WHERE q.insurancePolicy = :policyId AND q.isDeleted = false")
    List<Questionary> findByPolicyId(@Param("policyId") BigInteger policyId);

    /**
     * Find all active questionnaires by policy ID
     */
    @Query(value = "SELECT * FROM Questionary q WHERE q.policy_id = :policyId AND q.is_deleted = 0 AND q.questionary_status = 'TRUE'", nativeQuery = true)
    List<Questionary> findActiveByPolicyId(@Param("policyId") BigInteger policyId);
}
