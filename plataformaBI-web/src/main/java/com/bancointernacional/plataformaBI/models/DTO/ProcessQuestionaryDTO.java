package com.bancointernacional.plataformaBI.models.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.bancointernacional.plataformaBI.ErrorManagment.CustomException;
import com.bancointernacional.plataformaBI.models.entities.ProcessInsurancePolicy;
import com.bancointernacional.plataformaBI.models.entities.ProcessManager;
import com.bancointernacional.plataformaBI.models.entities.ProcessQuestionary;
import com.bancointernacional.plataformaBI.models.entities.Questionnaire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessQuestionaryDTO {

    UUID idQuest;
    String nameQuest;
    LocalDate dateBeginQuest;
    LocalDate dateEndQuest;
    String status;
    Integer idQuestionnaire;
    Long idPolicy;
    UUID idProcess;
    String processDescription;
    String nameInsurance;
    Integer percentage = 0;

    public static ProcessQuestionaryDTO build(ProcessQuestionary processQuestionary)
    {
        if(processQuestionary == null )
        {
            throw new CustomException("Debe pasar el entity ProcessQuestionary!", HttpStatus.NOT_FOUND);
        }

        Questionnaire questionnaire = processQuestionary.getQuestionnaire();
        ProcessInsurancePolicy processInsurancePolicy = processQuestionary.getProcessInsurancePolicy();
        ProcessManager processManager = processInsurancePolicy.getProcess();

        ProcessQuestionaryDTO processQuestionaryDTO = ProcessQuestionaryDTO.builder()
                                                                            .idQuest(processQuestionary.getIdQuest())
                                                                            .nameQuest(questionnaire.getNamequestionnaire())
                                                                            .dateBeginQuest(processQuestionary.getDateBeginQuest())
                                                                            .dateEndQuest(processQuestionary.getDateEndQuest())
                                                                            .status(processQuestionary.getStatus())
                                                                            .idQuestionnaire(questionnaire.getIdquestionnaire())
                                                                            .idPolicy(processInsurancePolicy.getIdPolicy())
                                                                            .idProcess(processManager.getIdProcess())
                                                                            .processDescription(processManager.getProcessDescription())
                                                                            .nameInsurance(processInsurancePolicy.getInsurancePolicy().getNameInsurance())
                                                                            .build();


        return processQuestionaryDTO;

    }


}
