package com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.request;

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
@Schema(description = "DTO for updating user assignment in UserQuestion")
public class UpdateUserQuestionAssignmentDTO {

    @NotNull(message = "User question ID is required")
    @Schema(description = "User question ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID userQuestionId;

    @NotNull(message = "User assigned is required")
    @Schema(description = "ID of the user to be assigned", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID userAssigned;
}
