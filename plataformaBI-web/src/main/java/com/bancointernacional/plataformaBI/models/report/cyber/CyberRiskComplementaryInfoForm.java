package com.bancointernacional.plataformaBI.models.report.cyber;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class for CyberRisk Complementary Information form.
 * This class contains all the data fields needed to populate the Thymeleaf template.
 */

@Getter
@Setter
@NoArgsConstructor
public class CyberRiskComplementaryInfoForm {

    @JsonProperty("Cyberriskcompletes1p1r1")
    private String cyberriskcompletes1p1r1;

    @JsonProperty("Cyberriskcompletes1p1r2")
    private String cyberriskcompletes1p1r2;

    @JsonProperty("Cyberriskcompletes1p1r3")
    private String cyberriskcompletes1p1r3;

    @JsonProperty("Cyberriskcompletes1p1r4")
    private String cyberriskcompletes1p1r4;

    @JsonProperty("Cyberriskcompletes1p1r5")
    private String cyberriskcompletes1p1r5;

    @JsonProperty("Cyberriskcompletes1p2")
    private String cyberriskcompletes1p2;

    @JsonProperty("Cyberriskcompletes1p3")
    private String cyberriskcompletes1p3;

    @JsonProperty("Cyberriskcompletes1p4")
    private String cyberriskcompletes1p4;

    @JsonProperty("ElaboradoPor")
    private String elaboradoPor;

    @JsonProperty("Fuente")
    private String fuente;

    @JsonProperty("FechaElaboracion")
    private String fechaElaboracion;
}