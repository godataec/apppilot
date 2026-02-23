package com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProcessInsurancePolicyDTO implements Serializable {
    private UUID processId;
    private BigInteger insurancePolicyId;
    private String processInsurancePolicyStatus;
    private LocalDate processInsurancePolicyBeingDate;
    private LocalDate processInsurancePolicyEndDate;
}
