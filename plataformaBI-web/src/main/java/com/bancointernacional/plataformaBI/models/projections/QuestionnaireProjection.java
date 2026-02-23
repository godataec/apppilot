package com.bancointernacional.plataformaBI.models.projections;

public interface QuestionnaireProjection {

    String getAssignedQuestionnaireID();

    String getQuestionnaireName();

    String getStatus();

    String getDateStart();

    String getDateEnd();
}