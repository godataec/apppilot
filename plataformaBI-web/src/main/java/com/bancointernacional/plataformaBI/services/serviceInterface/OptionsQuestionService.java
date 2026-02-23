package com.bancointernacional.plataformaBI.services.serviceInterface;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.DTO.OptionsQuestionDTO;
import com.bancointernacional.plataformaBI.models.entities.OptionsQuestion;

public interface OptionsQuestionService {

     Optional<OptionsQuestionDTO> getOptionByQuestion(Integer idQuestion);

     List<OptionsQuestion> getOptionsByIdQuestionnaire( Integer idQuestionnaire);
}
