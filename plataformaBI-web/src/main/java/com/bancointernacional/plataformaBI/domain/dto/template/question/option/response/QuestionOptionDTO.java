package com.bancointernacional.plataformaBI.domain.dto.template.question.option.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Question option response DTO containing option details for a specific question")
public class QuestionOptionDTO implements Serializable {

    @Schema(description = "Unique identifier for the question option", example = "1")
    private Integer optionId;

    @Schema(description = "Question identifier this option belongs to", example = "5")
    private Integer questionId;

    @Schema(description = "Display text for the option", example = "Yes, I agree")
    private String optionText;

    @Schema(description = "Whether this option is deleted", example = "false")
    private Boolean isDeleted;
}
