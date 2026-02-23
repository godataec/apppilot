package com.bancointernacional.plataformaBI.repository.template;

import com.bancointernacional.plataformaBI.domain.model.template.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    /**
     * Find all questions by questionary ID
     */
    @Query("SELECT q FROM Question q WHERE q.questionaryId = :questionaryId AND q.isDeleted = false")
    List<Question> findByQuestionaryId(@Param("questionaryId") BigInteger questionaryId);

    /**
     * Find all active questions by questionary ID
     */
    @Query("SELECT q FROM Question q WHERE q.questionaryId = :questionaryId AND q.isDeleted = false")
    List<Question> findActiveByQuestionaryId(@Param("questionaryId") BigInteger questionaryId);
}
