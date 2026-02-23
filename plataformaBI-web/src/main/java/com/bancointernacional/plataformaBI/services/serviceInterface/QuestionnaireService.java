package com.bancointernacional.plataformaBI.services.serviceInterface;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.DTO.QuestionnaireDTO;
import com.bancointernacional.plataformaBI.models.DTO.projection.QuestionnaireProjectionDTO;


public interface QuestionnaireService {

    Double getQuestionariesAssignedToPolicy(Long idInsurance);

    List<QuestionnaireDTO> findAll();

    Optional<QuestionnaireDTO> findByidQuestionnaire(Integer idQuestionnaire);

    List<QuestionnaireDTO> findQuestionnaireAvalible(Long idPolicy);

    List<QuestionnaireProjectionDTO> findQuestionnairesForUser(UUID userUUID);

    boolean deleteQuestionnaire(Integer idQuestionnaire);
}
