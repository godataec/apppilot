package com.bancointernacional.plataformaBI.repository.settings;

import com.bancointernacional.plataformaBI.domain.model.settings.SubsidiaryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubsidiaryUserRepository extends JpaRepository<SubsidiaryUser, UUID> {
    
    /**
     * Find user by OpenID
     */
    Optional<SubsidiaryUser> findByOpenIdUser(String openIdUser);
    
    /**
     * Find all active users (not deleted)
     */
    @Query("SELECT su FROM SubsidiaryUser su WHERE su.isDeleted = false")
    List<SubsidiaryUser> findAllActive();
    
    /**
     * Find users by user status
     */
    @Query("SELECT su FROM SubsidiaryUser su WHERE su.userStatus = :userStatus AND su.isDeleted = false")
    List<SubsidiaryUser> findByUserStatus(@Param("userStatus") String userStatus);
    
    /**
     * Find users by subsidiary ID
     */
    @Query("SELECT su FROM SubsidiaryUser su WHERE su.subsidiary.subsidiaryId = :subsidiaryId AND su.isDeleted = false")
    List<SubsidiaryUser> findBySubsidiaryId(@Param("subsidiaryId") Long subsidiaryId);
    
    /**
     * Find users by subsidiary ID with subsidiary details fetched
     */
    @Query("SELECT su FROM SubsidiaryUser su JOIN FETCH su.subsidiary s WHERE s.subsidiaryId = :subsidiaryId AND su.isDeleted = false AND s.isDeleted = false")
    List<SubsidiaryUser> findBySubsidiaryIdWithSubsidiary(@Param("subsidiaryId") Long subsidiaryId);
    
    /**
     * Find user by email
     */
    @Query("SELECT su FROM SubsidiaryUser su WHERE su.email = :email AND su.isDeleted = false")
    Optional<SubsidiaryUser> findByEmail(@Param("email") String email);
    
    /**
     * Find users by role
     */
    @Query("SELECT su FROM SubsidiaryUser su WHERE su.role = :role AND su.isDeleted = false")
    List<SubsidiaryUser> findByRole(@Param("role") String role);
    
    /**
     * Check if user exists by OpenID
     */
    @Query("SELECT CASE WHEN COUNT(su) > 0 THEN true ELSE false END FROM SubsidiaryUser su WHERE su.openIdUser = :openIdUser AND su.isDeleted = false")
    boolean existsByOpenIdUser(@Param("openIdUser") String openIdUser);
    
    /**
     * Check if user exists by email
     */
    @Query("SELECT CASE WHEN COUNT(su) > 0 THEN true ELSE false END FROM SubsidiaryUser su WHERE su.email = :email AND su.isDeleted = false")
    boolean existsByEmail(@Param("email") String email);
}
