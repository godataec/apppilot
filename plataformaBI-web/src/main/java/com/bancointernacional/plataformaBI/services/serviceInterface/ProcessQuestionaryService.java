package com.bancointernacional.plataformaBI.services.serviceInterface;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.DTO.ProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.models.DTO.ProcessPolicyDTO;
import com.bancointernacional.plataformaBI.models.DTO.ProcessQuestionaryDTO;
import com.bancointernacional.plataformaBI.models.entities.ProcessQuestionary;

import javax.validation.Valid;

public interface ProcessQuestionaryService {

    List<ProcessQuestionaryDTO> findAllProcessQuestionary();
    Optional<ProcessQuestionaryDTO> findProcessQuestionaryForId(UUID idProcessQuestionary);
    List<ProcessQuestionaryDTO> findProcessQuestionnaryForidPolicy(Long idPolicy);

    Optional<ProcessQuestionaryDTO> saveProcessQuestionary(ProcessQuestionaryDTO processQuestionary);
    Optional<ProcessQuestionaryDTO> updateProcessQuestionary(ProcessQuestionary processQuestionary);

    ProcessQuestionaryDTO deleteProcessQuestionary(UUID idProcessQuestionary);

    List<ProcessQuestionaryDTO> getProcessQuestByCharacters(String idProcess, String idPolicy ,int page, int pageSize);
    
    Integer getProcessQuestByCharactersWithoutPages(String idProcess, String idPolicy);

    void deleteLogicalProcessQuestionary(UUID processQuestionaryId);

    List<ProcessInsurancePolicyDTO> getActivePolicies(String idProcess, String idPolicy, int page, int pageSize);

    Boolean validateClosedAnswers(@Valid UUID idQuest);
}
