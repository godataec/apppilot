package com.bancointernacional.plataformaBI.domain.dto.template.policy.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO implements Serializable {
    private Long idInsurance;
    private String nameInsurance;
    private String status;
    private String description;
}
