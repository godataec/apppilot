package com.bancointernacional.plataformaBI.models.DTO.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireProjectionDTO {
    private String id;
    private String name;
    private String status;
    private String dateStart;
    private String dateEnd;
}
