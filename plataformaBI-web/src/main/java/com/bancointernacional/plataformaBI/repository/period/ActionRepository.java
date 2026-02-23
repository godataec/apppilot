package com.bancointernacional.plataformaBI.repository.period;

import com.bancointernacional.plataformaBI.domain.model.period.Action;
import com.bancointernacional.plataformaBI.domain.model.period.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActionRepository extends JpaRepository<Action, UUID> {

    /**
     * Find all actions by process (JPA Query)
     */
    List<Action> findByProcess(Process process);

    /**
     * Find all actions by process ID (Native Query)
     */
    @Query(value = "SELECT * FROM process_action pa WHERE pa.process_id = :processId ORDER BY pa.created_at DESC", nativeQuery = true)
    List<Action> findByProcessId(@Param("processId") String processId);

    /**
     * Find actions by action type
     */
    @Query(value = "SELECT * FROM process_action pa WHERE pa.action_type = :actionType ORDER BY pa.created_at DESC", nativeQuery = true)
    List<Action> findByActionType(@Param("actionType") String actionType);

    @Query(value = "SELECT * FROM process_action pa WHERE pa.action_id = :actionId ORDER BY pa.created_at DESC", nativeQuery = true)
    Optional<Action> findByActionId(@Param("actionId") String actionId);
}
