package com.bancointernacional.plataformaBI.repositories;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bancointernacional.plataformaBI.models.entities.ProcessManager;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ProcessManagerRepository
                extends JpaRepository<ProcessManager, UUID> {

                  Optional<ProcessManager> findByIdProcess(UUID idProcess);  

                  // @Query("SELECT p FROM PROCESSMANAGER p WHERE p.dateBegin >= :dateBegin AND p.dateEnd <= :dateEnd")
                  List<ProcessManager> findByDateBeginBetween( String dateBegin, String dateEnd);
                  

                  @Query(value = "SELECT * FROM PROCESSMANAGER WHERE ISDELETED=0 AND  ORDER BY IDPROCESS OFFSET ?1 ROWS FETCH NEXT ?2 ROWS ONLY", nativeQuery = true)
                  List<ProcessManager> findProcessManager(int offset, int page);
                  
                  @Query(value = "SELECT * FROM PROCESSMANAGER pm " +
                  "WHERE pm.ISDELETED=0 AND (:searchTerm IS NULL OR pm.PROCESSDESCRIPTION LIKE CONCAT('%', :searchTerm, '%')) " +
                  "AND (:dateBegin IS NULL OR (:dateBegin <= pm.DATEBEGIN AND pm.DATEEND <= :dateEnd )) " +
                  "AND (:year IS NULL OR (YEAR(pm.DATEBEGIN) = :year OR YEAR(pm.DATEEND) = :year)) "+
                  "ORDER BY pm.IDPROCESS " +
                  "OFFSET :offset ROWS FETCH NEXT :page ROWS ONLY",
                  nativeQuery = true)
                  List<ProcessManager> findProcessForCharacters(
                          @Param("searchTerm") String searchTerm,
                          @Param("offset") int offset,
                          @Param("page") int page,
                          @Param("dateBegin") String dateBegin,
                          @Param("dateEnd") String dateEnd,
                          @Param("year") BigInteger year);



                  @Query(value = "SELECT COUNT(*) FROM PROCESSMANAGER " +
                  "WHERE ISDELETED=0 AND (?1 IS NULL OR PROCESSDESCRIPTION LIKE %?1%) " +
                  "AND (?2 IS NULL OR (?2 <= DATEBEGIN AND DATEBEGIN <= ?3))", 
                  nativeQuery = true)
                  Integer findProcessForCharactersWithoutPagination(String character, String dateBegin, String dateEnd);


                  @Modifying
                  @Transactional
                  @Query(value = "DELETE  FROM PROCESSMANAGER WHERE ISDELETED=0 AND IDPROCESS = ?1", nativeQuery = true)
                  int deleteProcess(UUID idProcess);


    @Modifying
    @Transactional
    @Query(value = "UPDATE PROCESSMANAGER SET ISDELETED = 1 WHERE IDPROCESS = ?1", nativeQuery = true)
    void deleteByProcessId(UUID idProcess);
}
