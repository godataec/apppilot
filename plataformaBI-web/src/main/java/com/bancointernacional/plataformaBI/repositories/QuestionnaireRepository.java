package com.bancointernacional.plataformaBI.repositories;
import com.bancointernacional.plataformaBI.models.DTO.QuestionnaireDTO;
import com.bancointernacional.plataformaBI.models.projections.QuestionnaireProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.bancointernacional.plataformaBI.models.entities.Questionnaire;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface QuestionnaireRepository extends JpaRepository <Questionnaire, Integer>{


    @Query(value = "SELECT COUNT (*) FROM Questionnaire q WHERE q.ISDELETED=0 AND q.IDINSURANCE = :insurancePolicyId ", nativeQuery = true)
    Double countQuestionnaireByInsurancePolicy(@Param("insurancePolicyId") Long insurancePolicyId);


    Optional<Questionnaire> findByIdquestionnaire(Integer idquestionnaire);

    //Busqueda de los cuestionarios por id poliza que esten disponibles
    @Query(value = "SELECT * FROM QUESTIONNAIRE a "
    +"JOIN PROCESSINSURANCEPOLICY b ON a.IDINSURANCE = b.IDINSURANCE AND b.IDPOLICY = ?1 "
    +"LEFT JOIN  PROCESSQUESTIONARY c ON a.IDQUESTIONNAIRE = c.IDQUESTIONNAIRE "
    +"WHERE a.ISDELETED=0 AND c.IDPOLICY IS NULL "
    , nativeQuery = true) 
    List<Questionnaire> findByIdPolicyAvalible (Long idPolicy);

    @Query(value = "SELECT DISTINCT q.* FROM dbo.QUESTIONNAIRE q " +
            "JOIN dbo.QUESTION ques ON q.IDQUESTIONNAIRE = ques.IDQUESTIONNAIRE " +
            "LEFT JOIN dbo.ANSWER a ON ques.IDQUESTION = a.IDQUESTION AND a.IDUSER = :userId ",
            nativeQuery = true)
    List<Questionnaire> findPendingQuestionnairesForUser(@Param("userId") String userId);

    @Query(value = "SELECT q.NAMEQUESTIONNAIRE as questionnaireName, a.IDQUEST as assignedQuestionnaireID," +
            " pq.DATEBEGINQUEST as dateStart, pq.DATEENDQUEST as dateEnd, pq.STATUS as status" +
            " FROM QUESTIONNAIRE q JOIN " +
            "PROCESSQUESTIONARY pq ON q.IDQUESTIONNAIRE = pq.IDQUESTIONNAIRE JOIN " +
            "ANSWER a ON pq.IDQUEST = a.IDQUEST WHERE  a.IDUSER = :userId " +
            "GROUP BY  q.NAMEQUESTIONNAIRE, a.IDQUEST, pq.DATEBEGINQUEST, pq.DATEENDQUEST, pq.STATUS  ORDER BY  q.NAMEQUESTIONNAIRE", nativeQuery = true)
    List<QuestionnaireProjection> findQuestionnaireNameAndQuestIdByUserId(@Param("userId") String userId);


    @Query(value = "UPDATE  dbo.QUESTIONNAIRE q SET q.IDELETED = 1  WHERE ISDELETED=0 AND id = :questionnaireId",nativeQuery = true)
    void  deleteQuestionnaireById(@Param("questionnaireId") Integer questionnaireId);

}

