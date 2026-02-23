package com.bancointernacional.plataformaBI.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bancointernacional.plataformaBI.models.DTO.ProcessQuestionaryDTO;
import com.bancointernacional.plataformaBI.models.entities.ProcessQuestionary;
import org.springframework.data.repository.query.Param;


public interface ProcessQuestionaryRepository extends JpaRepository <ProcessQuestionary, UUID> {


    @Query(value = "SELECT * FROM PROCESSQUESTIONARY WHERE ISDELETED=0 AND IDPOLICY =:id AND STATUS = 'RESOLVED' OR STATUS = 'CLOSED'", nativeQuery = true)
    List<ProcessQuestionary> findByProcessQuestionaryByPolicyIdAAndStatusComplete(@Param("id") Long id);

    Optional<ProcessQuestionaryDTO> findDTOByIdQuest(UUID idQuest);

    Optional<ProcessQuestionary> findByIdQuest(UUID idQuest);

    @Query(value = "SELECT * FROM PROCESSQUESTIONARY WHERE ISDELETED=0 AND IDQUEST = ?1", nativeQuery = true)
    Optional<ProcessQuestionary> findByIdQuest(String idQuest);
    //Busqueda de Process Insurance Policy por idPolicy
    @Query(value = "SELECT * FROM PROCESSQUESTIONARY WHERE ISDELETED=0 AND IDPOLICY = ?1", nativeQuery = true)
    List<ProcessQuestionary> findByIdPolicy(Long idPolicy);


    //Borrar procesos de cuestionarios por id de poliza
    @Modifying
    @Transactional
    @Query(value = "UPDATE PROCESSQUESTIONARY SET ISDELETED = 1 WHERE IDQUEST = ?1", nativeQuery = true)
    int deleteAllpProcessQuestByIdPolicy(String idPolicy);



    @Query(value="SELECT a.IDQUEST, b.NAMEQUESTIONNAIRE, a.DATEBEGINQUEST, a.DATEENDQUEST, a.STATUS, b.IDQUESTIONNAIRE, c.IDPOLICY, d.IDPROCESS, d.PROCESSDESCRIPTION, e.NAMEINSURANCE, b.TOTALQUESTION "+
    "FROM PROCESSQUESTIONARY a "+
    "JOIN QUESTIONNAIRE b ON a.IDQUESTIONNAIRE = b.IDQUESTIONNAIRE "+
    "JOIN PROCESSINSURANCEPOLICY c ON a.IDPOLICY = c.IDPOLICY " +
    "JOIN PROCESSMANAGER d ON c.IDPROCESS = d.IDPROCESS " +
    "JOIN INSURANCEPOLICY e ON c.IDINSURANCE = e.IDINSURANCE "+
    "WHERE a.ISDELETED=0 AND (?1 IS NULL OR c.IDPROCESS LIKE %?1%) "+
    "AND (?2 IS NULL OR a.IDPOLICY LIKE %?2%) "+
    "ORDER BY d.IDPROCESS " +
    "OFFSET ?3 ROWS FETCH NEXT ?4 ROWS ONLY", nativeQuery = true)
    List<Object[]> findProcessQuestionaryForCharacters(
        String idProcess,
        String idPolicy,
        int offset, 
        int page);


    @Query(value="SELECT COUNT(*)"+
    "FROM PROCESSQUESTIONARY a "+
    "JOIN QUESTIONNAIRE b ON a.IDQUESTIONNAIRE = b.IDQUESTIONNAIRE "+
    "JOIN PROCESSINSURANCEPOLICY c ON a.IDPOLICY = c.IDPOLICY " +
    "JOIN PROCESSMANAGER d ON c.IDPROCESS = d.IDPROCESS " +
    "JOIN INSURANCEPOLICY e ON c.IDINSURANCE = e.IDINSURANCE "+
    "WHERE a.ISDELETED=0 AND (?1 IS NULL OR c.IDPROCESS LIKE %?1%) "+
    "AND (?2 IS NULL OR a.IDPOLICY LIKE %?2%) ", nativeQuery = true)
    Integer findProcessQuestionaryForCharacterswithoutPages(
            String idQuest,
            String idPolicy
            );

    @Query(value="  SELECT DISTINCT a.IDPOLICY,  b.DATEBEGIN, b.DATEEND, b.STATUS " +
            "FROM PROCESSQUESTIONARY a " +
            "JOIN PROCESSINSURANCEPOLICY b ON a.IDPOLICY = b.IDPOLICY "+
            "WHERE a.ISDELETED = 0 AND a.STATUS = 'ACTIVE' "+
            "AND (?1 IS NULL OR a.IDQUEST LIKE %?1%) "+
            "AND (?2 IS NULL OR b.IDPOLICY LIKE %?2%) "+
            "ORDER BY a.IDPOLICY " +
            "OFFSET ?3 ROWS FETCH NEXT ?4 ROWS ONLY", nativeQuery = true)
    List<Object[]> findActivePoliciesInProcessQuestionaire(String idProcess, String idPolicy, int offset, int pageSize);

}
