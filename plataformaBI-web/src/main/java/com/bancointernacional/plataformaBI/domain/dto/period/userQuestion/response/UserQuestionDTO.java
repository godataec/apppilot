package com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.response;

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
@Schema(description = "UserQuestion response DTO")
public class UserQuestionDTO {

    @Schema(description = "User question ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID userQuestionId;

    @Schema(description = "Policy questionary ID", example = "550e8400-e29b-41d4-a716-446655440001")
    private String policyQuestionaryId;

    @Schema(description = "Question ID", example = "1")
    private BigInteger questionId;

    @Schema(description = "User assigned ID", example = "550e8400-e29b-41d4-a716-446655440002")
    private UUID userAssigned;

    @Schema(description = "Question status", example = "PENDING")
    private String questionStatus;

    @Schema(description = "Is deleted flag", example = "false")
    private boolean isDeleted;

    @Schema(description = "Creation date", example = "2025-08-10")
    private LocalDate createdAt;

    @Schema(description = "Update date", example = "2025-08-10")
    private LocalDate updatedAt;
}
