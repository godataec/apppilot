package com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyQuestionaryDetailDTO implements Serializable {

    private UUID idQuest;
    private String nameQuest;
    private LocalDate dateBeginQuest;
    private LocalDate dateEndQuest;
    private String status;
    private Integer idQuestionnaire;
    private Long idPolicy;
    private UUID idProcess;
    private String processDescription;
    private String nameInsurance;
    private Double percentage;
}
