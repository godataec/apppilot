package com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyQuestionaryDTO implements Serializable {

    private UUID policyQuestionaryId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private BigInteger questionaryId;
    private String questionaryName;
    private String processPolicyId;
    private String processId;
    private LocalDate createdAt;
    private String questionaryDescription;
    private String processDescription;
    private String policyName;
    private LocalDate processStartDate;
    private LocalDate processEndDate;
    private Long percentage;
}
