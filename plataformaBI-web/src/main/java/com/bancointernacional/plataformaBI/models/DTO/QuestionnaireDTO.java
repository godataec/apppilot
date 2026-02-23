package com.bancointernacional.plataformaBI.models.DTO;

import java.time.LocalDate;

import com.bancointernacional.plataformaBI.models.entities.InsurancePolicy;
import com.bancointernacional.plataformaBI.models.entities.Questionnaire;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionnaireDTO {
    Integer idquestionnaire;
    String namequestionnaire;
    String description;
    LocalDate createdAt;
    String status;
    String sourceFile;
    Long idInsurance;
    Integer totalQuestions;


    public static QuestionnaireDTO build(Questionnaire questionnaire){

        if( questionnaire == null){
            throw new RuntimeException("No se encontro el cuestionario");
        }

        InsurancePolicy insurancePolicy = questionnaire.getInsurancePolicy();

        return new QuestionnaireDTO(questionnaire.getIdquestionnaire(),
                                    questionnaire.getNamequestionnaire(),
                                    questionnaire.getDescription(),
                                    questionnaire.getCreatedAt(),
                                    questionnaire.getStatus(),
                                    questionnaire.getSourceFile(),
                                    insurancePolicy.getIdInsurance(),
                                    questionnaire.getTotalquestion());

    }


}
