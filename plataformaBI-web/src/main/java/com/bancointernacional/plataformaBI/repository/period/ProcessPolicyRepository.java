package com.bancointernacional.plataformaBI.repository.period;

import com.bancointernacional.plataformaBI.domain.model.period.ProcessPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bancointernacional.plataformaBI.domain.model.period.Process;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProcessPolicyRepository extends JpaRepository<ProcessPolicy, UUID> {

    @Query(value = "SELECT pp.process_policy_id, p.policy_name, pp.start_date, pp.end_date, pp.process_id, " +
            "pp.policy_id, pp.process_policy_status, p.policy_description, pc.process_description " +
            "FROM process_policy pp " +
            "JOIN policy p ON pp.policy_id = p.policy_id " +
            "JOIN process pc ON pp.process_id = pc.process_id WHERE pp.is_deleted = 0 " +
            "AND (:subsidiaryId IS NULL OR pc.subsidiary_id = :subsidiaryId) " +
            "AND (:idProcess IS NULL OR pp.process_id LIKE CONCAT('%', :idProcess, '%')) " +
            "AND (:dateBegin IS NULL OR pp.start_date >= :dateBegin) " +
            "AND (:dateEnd IS NULL OR pp.end_date <= :dateEnd) " +
            "AND (:year IS NULL OR (YEAR(pp.start_date) = :year OR YEAR(pp.end_date) = :year)) " +
            "ORDER BY pp.process_id OFFSET :offset ROWS FETCH NEXT :page ROWS ONLY", nativeQuery = true)
    List<Object[]> findProcessPolicyAgregatedDataByFilters(
            @Param("subsidiaryId") Long subsidiary,
            @Param("idProcess") String idProcess,
            @Param("offset") int offset,
            @Param("page") int page,
            @Param("dateBegin") String dateBegin,
            @Param("dateEnd") String dateEnd,
            @Param("year") BigInteger year);


    @Query(value = "SELECT pp.process_policy_id, p.policy_name, pp.start_date, pp.end_date, pp.process_id, " +
            "pp.policy_id, pp.process_policy_status, p.policy_description, pc.process_description FROM process_policy pp " +
            "JOIN policy p ON pp.policy_id = p.policy_id " +
            "JOIN process pc ON pp.process_id = pc.process_id WHERE pp.is_deleted = 0 " +
            "AND (:subsidiaryId IS NULL OR pc.subsidiary_id = :subsidiaryId) " +
            "AND (:idProcess IS NULL OR pp.process_id LIKE CONCAT('%', :idProcess, '%')) " +
            "AND (:dateBegin IS NULL OR pp.start_date >= :dateBegin) " +
            "AND (:dateEnd IS NULL OR pp.end_date <= :dateEnd) " +
            "AND (:year IS NULL OR (YEAR(pp.start_date) = :year OR YEAR(pp.end_date) = :year)) " +
            "ORDER BY pp.process_id OFFSET :offset ROWS FETCH NEXT :page ROWS ONLY", nativeQuery = true)
    List<Object[]> findProcessPolicyAgregatedDataShirnk(
            @Param("subsidiaryId") Long subsidiary,
            @Param("idProcess") String idProcess,
            @Param("offset") int offset,
            @Param("page") int page,
            @Param("dateBegin") String dateBegin,
            @Param("dateEnd") String dateEnd,
            @Param("year") BigInteger year);

    /**
     * Find process information by process policy ID
     */
    @Query(value = "SELECT * FROM process_policy pp WHERE pp.process_policy_id = :processPolicyId AND pp.is_deleted = 0",nativeQuery = true)
    Optional<ProcessPolicy> findProcessPolicyByProcessPolicyId(@Param("processPolicyId") String processPolicyId);

    @Query(value = "SELECT * FROM process_policy pp WHERE pp.process_id = :processId AND pp.is_deleted = 0",nativeQuery = true)
    List<ProcessPolicy> findByProcessId(@Param("processId")String processId);

    /**
     * Update process policy status for all policies with the given process ID
     * @param processId The process ID to filter by
     * @param newStatus The new status to set
     * @return Number of rows updated
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE process_policy SET process_policy_status = :newStatus, updated_at = GETDATE() " +
            "WHERE process_id = :processId AND is_deleted = 0", nativeQuery = true)
    int updateProcessPolicyStatusByProcessId(@Param("processId") String processId,
                                           @Param("newStatus") String newStatus);

    @Query(value = "SELECT * FROM process_policy WHERE is_deleted = 0 AND process_policy_status != 'CLOSED'", nativeQuery = true)
    List<ProcessPolicy> findActiveProcessPolicies();
}
