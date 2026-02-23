package com.bancointernacional.plataformaBI.domain.dto.period.process.action.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessActionDTO implements Serializable {

    @NotNull(message = "Process ID is required")
    private UUID processId;

    @NotBlank(message = "Action reason is required")
    private String actionReason;

    @NotBlank(message = "Action type is required")
    private String actionType;

    private LocalDate processStartDate;

    private LocalDate processEndDate;
}
