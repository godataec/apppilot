package com.bancointernacional.plataformaBI.services.serviceInterface;

import java.util.List;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.DTO.QuestionDTO;
import com.bancointernacional.plataformaBI.models.entities.Question;

public interface QuestionService {

    
    List<QuestionDTO> getQuestionsForidQuestionnaire(String idQuest);

    QuestionDTO getQuestionForId(Integer idQuestion);

    List<QuestionDTO> getQuestionByCharacters(String idQuest, int page, int pageSize);

    Integer getQuestionByCharactersWithoutPages(String idQuest);

    List<QuestionDTO> getQuestionByUserAndQuestionnaireId(String idQuest, int page, int pageSize, String userUUID);

    Integer getQuestionByUserIdAndQuestionnaireIdWithoutPages(String idQuest,String userUUIDString);
}
