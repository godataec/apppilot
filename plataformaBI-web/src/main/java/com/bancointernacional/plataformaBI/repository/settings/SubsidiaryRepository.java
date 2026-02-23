package com.bancointernacional.plataformaBI.repository.settings;

import com.bancointernacional.plataformaBI.domain.model.settings.Subsidiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubsidiaryRepository extends JpaRepository<Subsidiary, Long> {

    /**
     * Find subsidiary by group name
     * @param groupName the group name to search for
     * @return Optional containing the subsidiary if found
     */
    Optional<Subsidiary> findByGroupName(String groupName);

    /**
     * Find all active subsidiaries (not deleted)
     * @return List of active subsidiaries
     */
    @Query("SELECT s FROM Subsidiary s WHERE s.isDeleted = false")
    List<Subsidiary> findAllActive();

    /**
     * Find subsidiaries by description containing the specified text
     * @param description the text to search for in description
     * @return List of subsidiaries matching the criteria
     */
    @Query("SELECT s FROM Subsidiary s WHERE s.isDeleted = false AND s.description LIKE %:description%")
    List<Subsidiary> findByDescriptionContaining(@Param("description") String description);

    /**
     * Find subsidiary by ID only if it's not deleted
     * @param subsidiaryId the subsidiary ID
     * @return Optional containing the subsidiary if found and not deleted
     */
    @Query("SELECT s FROM Subsidiary s WHERE s.subsidiaryId = :subsidiaryId AND s.isDeleted = false")
    Optional<Subsidiary> findActiveById(@Param("subsidiaryId") Long subsidiaryId);

    /**
     * Check if a subsidiary exists by group name
     * @param groupName the group name to check
     * @return true if subsidiary exists and is not deleted
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Subsidiary s WHERE s.groupName = :groupName AND s.isDeleted = false")
    boolean existsByGroupName(@Param("groupName") String groupName);
}
