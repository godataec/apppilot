package com.bancointernacional.plataformaBI.repository.template;

import com.bancointernacional.plataformaBI.domain.model.template.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    @Query(value = "SELECT p.* FROM policy p " +
            "WHERE p.is_deleted = 0 " +
            "AND p.policy_id NOT IN (" +
            "    SELECT DISTINCT pp.policy_id " +
            "    FROM process_policy pp " +
            "    WHERE pp.policy_id IS NOT NULL " +
            "    AND pp.is_deleted = 0" +
            ")", nativeQuery = true)
    List<Policy> findUnassignedPolicies();

    @Query(value = "SELECT p.* FROM policy p  WHERE p.is_deleted = 0  AND (" +
            "p.policy_id NOT IN  ( SELECT pp.policy_id FROM process_policy pp " +
            "WHERE pp.process_id = :processId AND pp.is_deleted = 0 ))", nativeQuery = true)
    List<Policy> findUnassignedPoliciesExcludingProcess(@Param("processId") String processId);
}
