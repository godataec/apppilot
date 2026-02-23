package com.bancointernacional.plataformaBI.repositories;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bancointernacional.plataformaBI.models.DTO.ProcessPolicyDTO;
import com.bancointernacional.plataformaBI.models.entities.ProcessInsurancePolicy;





public interface ProcessInsurancePolicyRepository extends CrudRepository<ProcessInsurancePolicy, Long> {


    Optional<ProcessInsurancePolicy> findByIdPolicy(Long idPolicy);

    //Borrado en Cascada
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM PROCESSINSURANCEPOLICY WHERE ISDELETED=0 AND IDPROCESS = ?1", nativeQuery = true)
    int deleteAllByIdProcess(UUID idProcess);


    //Busqueda de Process Insurance Policy por idProcess
    @Query(value = "SELECT * FROM PROCESSINSURANCEPOLICY pip WHERE pip.ISDELETED=0 AND pip.IDPROCESS = :idProcess", nativeQuery = true)
    List<ProcessInsurancePolicy> findByIdProcess(@Param("idProcess") UUID idProcess);

    @Query("SELECT p FROM ProcessInsurancePolicy p WHERE p.isDeleted = false AND p.process.idProcess = :idProcess")
    List<ProcessInsurancePolicy> findByIdProcessJPL(@Param("idProcess") UUID idProcess);

    @Query(value = "SELECT pip.IDPOLICY, ip.NAMEINSURANCE, pip.DATEBEGIN, pip.DATEEND, pip.IDPROCESS, " +
            "pip.IDINSURANCE, pip.STATUS, ip.DESCRIPTION, pm.PROCESSDESCRIPTION FROM PROCESSINSURANCEPOLICY pip " +
            "JOIN INSURANCEPOLICY ip ON pip.IDINSURANCE = ip.IDINSURANCE " +
            "JOIN PROCESSMANAGER pm ON pip.IDPROCESS = pm.IDPROCESS WHERE pip.ISDELETED = 0 " +
            "AND (:idProcess IS NULL OR pip.IDPROCESS LIKE CONCAT('%', :idProcess, '%')) " +
            "AND (:dateBegin IS NULL OR pip.DATEBEGIN >= :dateBegin) " +
            "AND (:dateEnd IS NULL OR pip.DATEBEGIN <= :dateEnd) " +
            "OR (:year IS NULL OR (YEAR(pip.DATEBEGIN) = :year OR YEAR(pip.DATEEND) = :year)) " +
            "ORDER BY pip.IDPROCESS OFFSET :offset ROWS FETCH NEXT :page ROWS ONLY", nativeQuery = true)
    List<Object[]> findProcessPolicyForCharacters(
            @Param("idProcess") String idProcess,
            @Param("offset") int offset,
            @Param("page") int page,
            @Param("dateBegin") String dateBegin,
            @Param("dateEnd") String dateEnd,
            @Param("year") BigInteger year);

    @Query(value = "SELECT pip.IDPOLICY, ip.NAMEINSURANCE, pip.DATEBEGIN, pip.DATEEND, pip.IDPROCESS, " +
            "pip.IDINSURANCE, pip.STATUS, ip.DESCRIPTION, pm.PROCESSDESCRIPTION FROM PROCESSINSURANCEPOLICY pip " +
            "JOIN INSURANCEPOLICY ip ON pip.IDINSURANCE = ip.IDINSURANCE " +
            "JOIN PROCESSMANAGER pm ON pip.IDPROCESS = pm.IDPROCESS WHERE pip.ISDELETED = 0 " +
            "AND (:idProcess IS NULL OR pip.IDPROCESS LIKE CONCAT('%', :idProcess, '%')) " +
            "AND (:dateBegin IS NULL OR pip.DATEBEGIN >= :dateBegin) " +
            "AND (:dateEnd IS NULL OR pip.DATEBEGIN <= :dateEnd) " +
            "AND (:year IS NULL OR (YEAR(pip.DATEBEGIN) = :year OR YEAR(pip.DATEEND) = :year)) " +
            "ORDER BY pip.IDPROCESS OFFSET :offset ROWS FETCH NEXT :page ROWS ONLY", nativeQuery = true)
    List<Object[]> findProcessPolicyForCharactersYear(
            @Param("idProcess") String idProcess,
            @Param("offset") int offset,
            @Param("page") int page,
            @Param("dateBegin") String dateBegin,
            @Param("dateEnd") String dateEnd,
            @Param("year") BigInteger year);

    
    @Query(value="SELECT COUNT(*)"+
    "FROM PROCESSINSURANCEPOLICY a "+
    "JOIN INSURANCEPOLICY b ON a.IDINSURANCE = b.IDINSURANCE "+
    "JOIN PROCESSMANAGER c ON a.IDPROCESS = c.IDPROCESS " +
    "WHERE a.ISDELETED=0 AND (?1 IS NULL OR a.IDPROCESS LIKE %?1%) " +
    "AND (?2 IS NULL OR (?2 <= a.DATEBEGIN AND a.DATEBEGIN <= ?3 )) ", nativeQuery = true)
    Integer findProcessPolicyForCharacterswithoutPages(
            String idProcess, 
            String dateBegin, 
            String dateEnd);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PROCESSINSURANCEPOLICY SET ISDELETED = 1 WHERE IDPOLICY = ?1", nativeQuery = true)
    void deleteByPolicyId(Long idProcessPolicy);

}
