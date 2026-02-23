package com.bancointernacional.plataformaBI.domain.dto.period.userAnswer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for updating user answer")
public class UpdateUserAnswerDTO {

    @NotNull(message = "Answer ID is required")
    @Schema(description = "User answer ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID answerId;

    @Schema(description = "Answer text content", example = "Updated answer text")
    private String answerText;

    @Schema(description = "Answer status", example = "APPROVED", allowableValues = {"DRAFT", "SUBMITTED", "APPROVED", "REJECTED"})
    private String answerStatus;

    @Schema(description = "Updated by user", example = "admin@example.com")
    private String updatedBy;
}
