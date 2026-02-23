package com.bancointernacional.plataformaBI.models.DTO;

import org.springframework.http.HttpStatus;

import com.bancointernacional.plataformaBI.ErrorManagment.CustomException;
import com.bancointernacional.plataformaBI.models.entities.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class QuestionDTO {


    Integer idQuestion;
    
    String typeQuestion;

    String descriptionQuestion;

    String questionText;

    String questionJson;

    Integer parentId;

    String documentId;

    Integer idQuestionnaire;


    public static QuestionDTO build(Question question){

        if(question == null)
        {
             throw new CustomException("Entity Question no valido", HttpStatus.NOT_FOUND);
        }

    
        QuestionDTO buildQuestion =  QuestionDTO.builder()
                                                .idQuestion(question.getIdQuestion())
                                                .typeQuestion(question.getTypequestion())
                                                .descriptionQuestion(question.getDescriptionquestion())
                                                .questionText(question.getQuestiontext())
                                                .questionJson(question.getQuestionjson())
                                                .parentId(question.getParentid())
                                                .documentId(question.getDocumentid())
                                                .idQuestionnaire(question.getQuestionnaire().getIdquestionnaire())
                                                .build();


        return buildQuestion;

    }
}
