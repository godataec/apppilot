package com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.response;

import com.bancointernacional.plataformaBI.domain.dto.period.process.response.ProcessDTO;
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
@Schema(description = "Process detail question DTO containing policy and process information")
public class ProcessDetailPolicyDTO implements Serializable {

    @Schema(description = "Policy unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID idPolicy;

    @Schema(description = "Policy name", example = "COVID-19 Policy")
    private String namePolicy;

    @Schema(description = "Policy start date", example = "2025-01-01")
    private LocalDate dateBegin;

    @Schema(description = "Policy end date", example = "2025-12-31")
    private LocalDate dateEnd;

    @Schema(description = "Process unique identifier", example = "6c0ab514-774d-485d-b806-1c1d6fb87d4f")
    private UUID idProcess;

    @Schema(description = "Insurance company identifier", example = "1")
    private Long idInsurance;

    @Schema(description = "Process status", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE", "PENDING", "COMPLETED"})
    private String status;

    @Schema(description = "Policy description", example = "Health insurance policy for COVID-19 coverage")
    private String description;

    @Schema(description = "Process description", example = "Processing COVID-19 claims and questionnaires")
    private String descriptionProcess;

    @Schema(description = "Completion percentage", example = "75.5", minimum = "0", maximum = "100")
    private Long percentage;

    @Schema(description = "Process details object containing additional process information")
    private ProcessDTO process;
}
