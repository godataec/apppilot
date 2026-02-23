package com.bancointernacional.plataformaBI.domain.dto.period.process.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProcessDTO implements Serializable {
    private String processName;
    private LocalDate processStartDate;
    private LocalDate processEndDate;
    private String processStatus;
}
