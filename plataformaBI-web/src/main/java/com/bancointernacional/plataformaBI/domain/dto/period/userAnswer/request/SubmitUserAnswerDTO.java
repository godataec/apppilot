package com.bancointernacional.plataformaBI.domain.dto.period.userAnswer.request;

import com.bancointernacional.plataformaBI.enums.AnswerTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for submitting user answer")
public class SubmitUserAnswerDTO {

    @NotNull(message = "User question ID is required")
    @Schema(description = "User question ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID userQuestionId;

    @NotNull(message = "Policy questionary ID is required")
    @Schema(description = "Policy questionary ID", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID policyQuestionaryId;

    @NotNull(message = "Question ID is required")
    @Schema(description = "Question ID", example = "1")
    private BigInteger questionId;

    @NotNull(message = "User assigned is required")
    @Schema(description = "User assigned ID", example = "550e8400-e29b-41d4-a716-446655440002")
    private UUID userAssigned;

    @Schema(description = "Answer text content", example = "This is my answer to the question")
    private String answerText;

    @Schema(description = "Answer description content", example = "This is my answer description to the question")
    private String answerDescription;

    @Schema(description = "Answer type content", example = "This is my answer to the question")
    private String answerType;

    @Size(max = 50, message = "Document ID must not exceed 50 characters")
    @Schema(description = "Document ID", example = "DOC123456")
    private String documentId;

    @Schema(description = "Answer status", example = "SUBMITTED", allowableValues = {"DRAFT", "SUBMITTED", "APPROVED", "REJECTED"})
    private String answerStatus = "SUBMITTED";

    @Schema(description = "Created by user", example = "user@example.com")
    private String createdBy;
}
