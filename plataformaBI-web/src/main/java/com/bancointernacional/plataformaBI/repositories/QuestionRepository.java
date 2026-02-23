package com.bancointernacional.plataformaBI.repositories;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import com.bancointernacional.plataformaBI.models.projections.QuestionnaireProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bancointernacional.plataformaBI.models.entities.Question;
import com.bancointernacional.plataformaBI.models.entities.Questionnaire;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends CrudRepository<Question, Integer> {

    List<Question> findByQuestionnaire(Questionnaire questionnaire);

    @Transactional
    @Query(value = "SELECT * " +
            "FROM QUESTION a " +
            "JOIN QUESTIONNAIRE b ON b.IDQUESTIONNAIRE = a.IDQUESTIONNAIRE " +
            "JOIN PROCESSQUESTIONARY c ON c.IDQUESTIONNAIRE = b.IDQUESTIONNAIRE " +
            "WHERE ISDELETED=0 AND IDQUEST = ?1", nativeQuery = true)
    List<Question> findQuestionsByIdQuestionnaire(String idQuest);

    @Query(value = "SELECT * " +
            "FROM QUESTION a " +
            "JOIN QUESTIONNAIRE b ON b.IDQUESTIONNAIRE = a.IDQUESTIONNAIRE " +
            "JOIN PROCESSQUESTIONARY c ON c.IDQUESTIONNAIRE = b.IDQUESTIONNAIRE " +
            "WHERE ISDELETED=0 AND (?1 IS NULL OR c.IDQUEST LIKE %?1%) " +
            "ORDER BY a.IDQUESTION " +
            "OFFSET ?2 ROWS FETCH NEXT ?3 ROWS ONLY", nativeQuery = true)
    List<Question> findQuestionForCharacters(String idQuest, int offset, int page);

    @Query(value = "SELECT * FROM ( " +
            "SELECT a.*, " +
            "COUNT(CASE WHEN a.TYPEQUESTION <> 'DES' THEN 1 END) OVER() AS total_count, " +
            "a.IDQUESTION AS question_id " +
            "FROM QUESTION a " +
            "JOIN QUESTIONNAIRE b ON b.IDQUESTIONNAIRE = a.IDQUESTIONNAIRE " +
            "JOIN PROCESSQUESTIONARY c ON c.IDQUESTIONNAIRE = b.IDQUESTIONNAIRE " +
            "WHERE a.ISDELETED=0 AND (?1 IS NULL OR c.IDQUEST LIKE %?1%) " +
            "AND (a.PARENTID IS NULL OR a.PARENTID = '') " +
            ") AS subquery " +
            "ORDER BY question_id " +
            "OFFSET ?2 ROWS FETCH NEXT ?3 ROWS ONLY ", nativeQuery = true)
    List<Question> findQuestionForCharacterswithDes(String idQuest, int offset, int page);


    @Query(value = "SELECT COUNT(*) FROM ( " +
            "SELECT a.*, " +
            "COUNT(CASE WHEN a.TYPEQUESTION <> 'DES' THEN 1 END) OVER() AS total_count, " +
            "a.IDQUESTION AS question_id " +
            "FROM QUESTION a " +
            "JOIN QUESTIONNAIRE b ON b.IDQUESTIONNAIRE = a.IDQUESTIONNAIRE " +
            "JOIN PROCESSQUESTIONARY c ON c.IDQUESTIONNAIRE = b.IDQUESTIONNAIRE " +
            "WHERE a.ISDELETED=0 AND (?1 IS NULL OR c.IDQUEST LIKE %?1%) " +
            "AND (a.PARENTID IS NULL OR a.PARENTID = '') " +
            ") AS subquery ", nativeQuery = true)
    Integer findQuestionForCharacterswithoutPages(String idQuest);


    @Query(value = "SELECT * FROM QUESTION a WHERE a.ISDELETED=0 AND a.PARENTID = ?1", nativeQuery = true)
    List<Question> findSubQuestions(Integer idQuest);


    @Query(value = "SELECT * FROM ( " +
            "SELECT a.*, " +
            "COUNT(CASE WHEN a.TYPEQUESTION <> 'DES' THEN 1 END) OVER() AS total_count, " +
            "a.IDQUESTION AS question_id " +
            "FROM QUESTION a " +
            "JOIN QUESTIONNAIRE b ON b.IDQUESTIONNAIRE = a.IDQUESTIONNAIRE " +
            "JOIN PROCESSQUESTIONARY c ON c.IDQUESTIONNAIRE = b.IDQUESTIONNAIRE " +
            "WHERE a.ISDELETED=0 AND (?1 IS NULL OR c.IDQUEST LIKE %?1%) " +
            "AND (a.PARENTID IS NULL OR a.PARENTID = '') " +
            ") AS subquery " +
            "ORDER BY question_id " +
            "OFFSET ?2 ROWS FETCH NEXT ?3 ROWS ONLY ", nativeQuery = true)
    List<Question> findQuestionForQuestionnaireIdAndUserID(String idQuest, int offset, int page);


    @Query(value = "SELECT DISTINCT q.* FROM QUESTION q " +
            "JOIN ANSWER a ON q.IDQUESTION = a.IDQUESTION " +
            "WHERE q.ISDELETED=0 AND a.IDUSER = :userId AND a.IDQUEST = :questId " +
            "UNION " +
            "SELECT q.* FROM QUESTION q WHERE q.IDQUESTION IN (" +
            "  SELECT DISTINCT q.PARENTID FROM QUESTION q " +
            "  JOIN ANSWER a ON q.IDQUESTION = a.IDQUESTION " +
            "  WHERE a.IDUSER = :userId AND a.IDQUEST = :questId )" +
            "ORDER BY q.IDQUESTION " +
            "OFFSET :offset  ROWS FETCH NEXT :pageSize ROWS ONLY ", nativeQuery = true)
    Set<Question> findQuestionnaireNameAndQuestIdByUserId(
            @Param("userId") String userId, @Param("questId") String questId, @Param("offset") int offset,@Param("pageSize") int pageSize);

    @Query(value = "SELECT COUNT(DISTINCT q.IDQUESTION) FROM QUESTION q " +
            "JOIN ANSWER a ON q.IDQUESTION = a.IDQUESTION " +
            "WHERE q.ISDELETED=0 AND a.IDUSER = :userId " +
            "AND a.IDQUEST = :questId ",
    nativeQuery = true)
    Integer countQuestionnaireNameAndQuestIdByUserId(@Param("userId") String userId, @Param("questId") String questId);

}
