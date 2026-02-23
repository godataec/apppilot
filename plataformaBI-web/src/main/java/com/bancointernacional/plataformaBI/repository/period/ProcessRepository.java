package com.bancointernacional.plataformaBI.repository.period;

import com.bancointernacional.plataformaBI.domain.model.period.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProcessRepository extends JpaRepository<Process, UUID> {

    @Query(value = "SELECT * FROM process WHERE is_deleted=0 AND process_id = :processId", nativeQuery = true)
    Optional<Process> findActiveById(@Param("processId") String idProcess);

    List<Process> findByStartDateBetween(String dateBegin, String dateEnd);

    @Query(value = "SELECT * FROM process WHERE is_deleted=0 AND  ORDER BY process_id OFFSET ?1 ROWS FETCH NEXT ?2 ROWS ONLY", nativeQuery = true)
    List<Process> findProcessManager(int offset, int page);

    @Query(value = "SELECT * FROM process pm " +
            "WHERE pm.is_deleted=0 AND (:searchTerm IS NULL OR pm.process_description LIKE CONCAT('%', :searchTerm, '%')) " +
            "AND (:dateBegin IS NULL OR (:dateBegin <= pm.start_date AND pm.end_date <= :dateEnd )) " +
            "AND (:year IS NULL OR (YEAR(pm.start_date) = :year OR YEAR(pm.end_date) = :year)) "+
            "AND pm.subsidiary_id = :subsidiaryId "+
            "ORDER BY pm.process_id " +
            "OFFSET :offset ROWS FETCH NEXT :page ROWS ONLY",
            nativeQuery = true)
    List<Process> findProcessForCharacters(
            @Param("searchTerm") String searchTerm,
            @Param("offset") int offset,
            @Param("page") int page,
            @Param("dateBegin") String dateBegin,
            @Param("dateEnd") String dateEnd,
            @Param("year") BigInteger year,
            @Param("subsidiaryId") BigInteger subsidiaryId);



    @Query(value = "SELECT COUNT(*) FROM process " +
            "WHERE is_deleted=0 AND (?1 IS NULL OR process_description LIKE %?1%) " +
            "AND (?2 IS NULL OR (?2 <= start_date AND start_date <= ?3))",
            nativeQuery = true)
    Integer findProcessForCharactersWithoutPagination(String character, String dateBegin, String dateEnd);


    @Modifying
    @Transactional
    @Query(value = "DELETE  FROM process WHERE is_deleted=0 AND process_id = ?1", nativeQuery = true)
    int deleteProcess(String idProcess);


    @Modifying
    @Transactional
    @Query(value = "UPDATE PROCESSMANAGER SET is_deleted = 1 WHERE process_id = ?1", nativeQuery = true)
    void deleteByProcessId(String idProcess);

    /**
     * Soft delete process by setting is_deleted = 1
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE process SET is_deleted = 1, updated_at = GETDATE() WHERE process_id = :processId", nativeQuery = true)
    void softDeleteById(@Param("processId") String processId);

    @Query(value = "SELECT * FROM process WHERE is_deleted=0 AND process_status != 'CLOSED'", nativeQuery = true)
    List<Process> findActiveProcesses();
}
