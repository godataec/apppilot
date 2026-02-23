package com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.response;

import com.bancointernacional.plataformaBI.domain.dto.template.question.response.QuestionDTO;
import com.bancointernacional.plataformaBI.domain.dto.settings.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailedQuestionDTO implements Serializable {

    @Schema(description = "User question ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID userQuestionId;

    @Schema(description = "Policy questionary ID", example = "550e8400-e29b-41d4-a716-446655440001")
    private String policyQuestionaryId;

    @Schema(description = "Question details")
    private QuestionDTO question;

    @Schema(description = "User assigned details")
    private UserDTO userAssigned;

    @Schema(description = "Question status", example = "PENDING")
    private String questionStatus;

    @Schema(description = "Is deleted flag", example = "false")
    private boolean isDeleted;

    @Schema(description = "Creation date", example = "2025-08-10")
    private LocalDate createdAt;

    @Schema(description = "Update date", example = "2025-08-10")
    private LocalDate updatedAt;
}
