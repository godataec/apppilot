package com.bancointernacional.plataformaBI.models.DTO;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.UUID;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.bancointernacional.plataformaBI.ErrorManagment.CustomException;
import com.bancointernacional.plataformaBI.models.entities.Answer;
import com.bancointernacional.plataformaBI.models.entities.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {


    UUID idAnswer; 

    private String answerText = "";

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String status;

    private String documentId;
    
    private String idUser;

    private String openIdUser;

    private UUID idQuest;

    private int idQuestion;

    private String answersDescription;

    private String updatedBy;


    public static AnswerDTO build(Answer answer){

        if(answer == null )
        {
            throw new CustomException("Answer Entity no valida!", HttpStatus.NOT_FOUND);
        }

        Question question = answer.getQuestion();
        String descriptionTemp = "";
        if(!question.getQuestionjson().isEmpty())
        {
            try {
                    
                JSONObject TypeRespJson = new JSONObject(question.getQuestionjson());
                Iterator<String> keys = TypeRespJson.keys();
                
                    while (keys.hasNext()){
                    String key = keys.next();  
                        try {
                            JSONObject innerObject = TypeRespJson.getJSONObject(key);
                            if (innerObject.has("code") && innerObject.getString("code").contains(answer.getDocumentId()) ) {
                                descriptionTemp = innerObject.getString("text");
                                break;
                            } 
                        } catch (Exception e) {
                            throw new CustomException("Error al procesar la clave: " + key, HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
            } catch (Exception e) {
                throw new CustomException("Error, No se pudo convertir en Json la pregunta:"+question.getIdQuestion(), HttpStatus.NOT_FOUND);
            }
        }
        

        return new AnswerDTO(
                answer.getIdAnswer(),
                answer.getAnswerText(),
                answer.getCreatedAt(),
                answer.getUpdatedAt(),
                answer.getStatus(),
                answer.getDocumentId(),
                answer.getUser() != null ? answer.getUser().getOpenIdUser() : null,
                answer.getUser() != null ? answer.getUser().getOpenIdUser() : null,
                answer.getProcessQuest().getIdQuest(),
                answer.getQuestion().getIdQuestion(),
                descriptionTemp,
                answer.getUpdatedBy()
        );

    }

}
