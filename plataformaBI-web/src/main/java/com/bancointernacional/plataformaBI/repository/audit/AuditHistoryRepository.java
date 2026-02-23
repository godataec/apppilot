package com.bancointernacional.plataformaBI.repository.audit;

import com.bancointernacional.plataformaBI.domain.model.audit.AuditHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditHistoryRepository extends JpaRepository<AuditHistory, UUID> {

    @Query("SELECT ah FROM AuditHistory ah WHERE ah.entityName = :entityName AND ah.isDeleted = false ORDER BY ah.actionDate DESC")
    List<AuditHistory> findByEntityNameOrderByActionDateDesc(@Param("entityName") String entityName);

    @Query("SELECT ah FROM AuditHistory ah WHERE ah.entityId = :entityId AND ah.isDeleted = false ORDER BY ah.actionDate DESC")
    List<AuditHistory> findByEntityIdOrderByActionDateDesc(@Param("entityId") String entityId);

    @Query("SELECT ah FROM AuditHistory ah WHERE ah.action = :action AND ah.isDeleted = false ORDER BY ah.actionDate DESC")
    List<AuditHistory> findByActionOrderByActionDateDesc(@Param("action") String action);

    @Query("SELECT ah FROM AuditHistory ah WHERE ah.actionDate BETWEEN :startDate AND :endDate AND ah.isDeleted = false ORDER BY ah.actionDate DESC")
    List<AuditHistory> findByActionDateBetweenOrderByActionDateDesc(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT ah FROM AuditHistory ah WHERE ah.entityName = :entityName AND ah.entityId = :entityId AND ah.isDeleted = false ORDER BY ah.actionDate DESC")
    List<AuditHistory> findByEntityNameAndEntityIdOrderByActionDateDesc(@Param("entityName") String entityName, @Param("entityId") String entityId);
}
