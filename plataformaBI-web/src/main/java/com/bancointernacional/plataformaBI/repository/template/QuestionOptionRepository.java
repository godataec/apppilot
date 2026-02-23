package com.bancointernacional.plataformaBI.repository.template;

import com.bancointernacional.plataformaBI.domain.model.template.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Integer> {

    /**
     * Find all question options by question ID
     */
    @Query("SELECT qo FROM QuestionOption qo WHERE qo.questionId = :questionId AND (qo.isDeleted = false OR qo.isDeleted IS NULL) ")
    QuestionOption findByQuestionId(@Param("questionId") Integer questionId);

    /**
     * Find all question options by list of question IDs
     */
    @Query("SELECT qo FROM QuestionOption qo WHERE qo.questionId IN :questionIds AND (qo.isDeleted = false OR qo.isDeleted IS NULL) ORDER BY qo.questionId")
    List<QuestionOption> findByQuestionIdIn(@Param("questionIds") List<Integer> questionIds);

    /**
     * Find all active question options by question ID
     */
    @Query("SELECT qo FROM QuestionOption qo WHERE qo.questionId = :questionId AND (qo.isDeleted = false OR qo.isDeleted IS NULL) ")
    List<QuestionOption> findActiveByQuestionId(@Param("questionId") Integer questionId);
}
