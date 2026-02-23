package com.bancointernacional.plataformaBI.models.DTO;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPolicyDTO {

    private Long idPolicy; 
    private String namePolicy;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private UUID idProcess;
    private Integer idInsurance;
    private String status;
    private String description;
    private String descriptionProcess;
    private Double percentage;

}
