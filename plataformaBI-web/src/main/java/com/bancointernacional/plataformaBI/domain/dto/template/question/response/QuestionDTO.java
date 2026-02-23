package com.bancointernacional.plataformaBI.domain.dto.template.question.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO implements Serializable {
    private Integer idQuestion;
    private String typeQuestion;
    private String descriptionQuestion;
    private String questionText;
    private String questionJson;
    private Integer parentId;
    private String documentId;
    private Integer idQuestionnaire;
}
