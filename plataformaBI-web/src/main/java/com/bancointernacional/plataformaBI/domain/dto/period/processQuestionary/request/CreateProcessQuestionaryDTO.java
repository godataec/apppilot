package com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProcessQuestionaryDTO implements Serializable {

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status must not exceed 20 characters")
    private String status;

    @NotNull(message = "Questionary ID is required")
    private BigInteger questionaryId;

    @NotNull(message = "Process Policy ID is required")
    private UUID processPolicyId;
}
