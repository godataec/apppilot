package com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProcessQuestionaryDTO implements Serializable {

    @NotNull(message = "Policy Questionary ID is required")
    private UUID policyQuestionaryId;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 20, message = "Status must not exceed 20 characters")
    private String status;
}
