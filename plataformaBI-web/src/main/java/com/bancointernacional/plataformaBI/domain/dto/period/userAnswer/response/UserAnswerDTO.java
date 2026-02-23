package com.bancointernacional.plataformaBI.domain.dto.period.userAnswer.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "UserAnswer response DTO")
public class UserAnswerDTO {

    @Schema(description = "User answer ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID answerId;

    @Schema(description = "Answer text content", example = "This is my answer to the question")
    private String answerText;

    @Schema(description = "Answer type content", example = "This is the type answer to the question")
    private String answerType;

    @Schema(description = "Answer description content", example = "This is the description answer to the question")
    private String answerDescription;

    @Schema(description = "Creation date", example = "2025-08-10")
    private LocalDate createdAt;

    @Schema(description = "Update date", example = "2025-08-10")
    private LocalDate updatedAt;

    @Schema(description = "Document ID", example = "DOC123456")
    private String documentId;

    @Schema(description = "Answer status", example = "SUBMITTED")
    private String answerStatus;

    @Schema(description = "User assigned ID", example = "550e8400-e29b-41d4-a716-446655440002")
    private UUID userAssigned;

    @Schema(description = "Policy questionary ID", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID policyQuestionaryId;

    @Schema(description = "Question ID", example = "1")
    private BigInteger questionId;

    @Schema(description = "User question ID", example = "550e8400-e29b-41d4-a716-446655440003")
    private UUID userQuestionId;

    @Schema(description = "Updated by user", example = "admin@example.com")
    private String updatedBy;

    @Schema(description = "Created by user", example = "user@example.com")
    private String createdBy;

    @Schema(description = "Is deleted flag", example = "false")
    private boolean isDeleted;
}
