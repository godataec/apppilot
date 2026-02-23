package com.bancointernacional.plataformaBI.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import com.bancointernacional.plataformaBI.models.entities.ProcessQuestionary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bancointernacional.plataformaBI.models.entities.Answer;
import com.bancointernacional.plataformaBI.models.entities.OptionsQuestion;
import org.springframework.data.repository.query.Param;

public interface AnswerRespository extends CrudRepository<Answer, UUID> {


    Optional<Answer> findByIdAnswer(UUID idAnswer);

    @Query(value = "SELECT COUNT(*) FROM ANSWER WHERE ISDELETED=0 AND IDQUEST = :idQuest AND STATUS = 'RESOLVED' OR STATUS = 'CLOSED'", nativeQuery = true)
    Integer countCompletedAnswersByQuestId(@Param("idQuest") String idQuest);

    @Query(value = "SELECT * FROM ANSWER WHERE ISDELETED=0 AND IDQUEST = ?1", nativeQuery = true)
    List<Answer> findAnswersByIdQuest(UUID idQuest);

    @Query(value = "SELECT * "+
                   "FROM ANSWER a "+             
                   "JOIN PROCESSQUESTIONARY b ON a.IDQUEST = b.IDQUEST "+             
                   "JOIN QUESTIONNAIRE c ON c.IDQUESTIONNAIRE = b.IDQUESTIONNAIRE "+             
                   "WHERE a.ISDELETED=0 AND (?1 IS NULL OR a.IDQUEST LIKE %?1%) "+
                   "AND (?2 IS NULL OR c.IDQUESTIONNAIRE LIKE %?2%) ", nativeQuery = true)          
    List<Answer> findAnswersByIdQuestAndIdQuestionnaire(String idQuest, Long idQuestionnaire);

    @Query(value = "SELECT * FROM ANSWER WHERE ISDELETED=0 AND IDQUEST = ?1 AND ID = ?2", nativeQuery = true)
    List<Answer> findAnswersByIdQuestAndIdUser(UUID idQuest, UUID idUser);

    //consulta respuestas por proceso y pregunta
    @Query(value = "SELECT * FROM ANSWER WHERE ISDELETED=0 AND IDQUEST = ?1 AND IDQUESTION = ?2 ", nativeQuery = true)
    List<Answer> findByIdQuestAndIdQuestion(String idQuest, Long idQuestion );

    @Modifying
    @Transactional
    @Query(value = "UPDATE ANSWER SET ISDELETED = 1 WHERE IDQUEST = ?1 AND IDQUESTION = ?2 ", nativeQuery = true)
    int deleteByIdQuestAndIdQuestion(String idQuest, Long idQuestion );

    @Modifying
    @Transactional
    @Query(value = "UPDATE ANSWER SET ISDELETED = 1 WHERE IDQUEST = ?1", nativeQuery = true)
    int deleteByIdQuest(String idQuest);

    @Query("SELECT a FROM Answer a WHERE a.isDeleted = false AND a.processQuest = :idQuest AND a.status IN :statuses")
    List<Answer> findByIdQuestAndStatusIn(@Param("idQuest") ProcessQuestionary idQuest, @Param("statuses") Set<String> statuses);

}
