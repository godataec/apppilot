package com.bancointernacional.plataformaBI.domain.projections;

public interface QuestionnaireProjection {

    String getAssignedQuestionnaireID();

    String getQuestionnaireName();

    String getStatus();

    String getDateStart();

    String getDateEnd();
}