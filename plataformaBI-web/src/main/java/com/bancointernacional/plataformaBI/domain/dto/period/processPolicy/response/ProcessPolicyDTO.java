package com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPolicyDTO implements Serializable {

    private UUID idPolicy;
    private String namePolicy;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private UUID idProcess;
    private Long idInsurance;
    private String status;
    private String description;
    private String descriptionProcess;
    private Double percentage;
}
