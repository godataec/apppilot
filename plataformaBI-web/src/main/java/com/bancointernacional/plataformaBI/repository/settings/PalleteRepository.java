package com.bancointernacional.plataformaBI.repository.settings;

import com.bancointernacional.plataformaBI.domain.model.settings.Pallete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PalleteRepository extends JpaRepository<Pallete, Long> {

    /**
     * Find all active color palettes (not soft deleted)
     */
    @Query("SELECT p FROM Pallete p WHERE p.isDeleted = false")
    List<Pallete> findAllActive();

    /**
     * Find all active color palettes by subsidiary
     */
    @Query("SELECT p FROM Pallete p WHERE p.subsidiaryId = :subsidiaryId AND p.isDeleted = false")
    List<Pallete> findAllActiveBySubsidiary(Long subsidiaryId);
}
