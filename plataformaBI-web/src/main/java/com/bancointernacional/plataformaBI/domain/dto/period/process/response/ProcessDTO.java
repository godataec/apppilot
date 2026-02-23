package com.bancointernacional.plataformaBI.domain.dto.period.process.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDTO implements Serializable {
    private UUID processId;
    private String processName;
    private LocalDate processStartDate;
    private LocalDate processEndDate;
    private String processStatus;
    private Boolean reopened;
    private Long percentage;
}
