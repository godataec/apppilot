package com.bancointernacional.plataformaBI.domain.dto.template.questionary.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionaryDTO implements Serializable {
    private Integer idquestionnaire;
    private String namequestionnaire;
    private String description;
    private LocalDate createdAt;
    private String status;
    private String sourceFile;
    private Long idInsurance;
    private Integer totalQuestions;
}
