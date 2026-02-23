package com.bancointernacional.plataformaBI.domain.dto.azure;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for Azure AD user synchronization")
public class AzureSyncRequestDTO {

    @Schema(
        description = "Force synchronization even if users already exist",
        example = "false",
        defaultValue = "false"
    )
    private boolean forceSync = false;

    @Schema(
        description = "Array of specific group IDs to synchronize (optional, future feature)",
        example = "[\"group-id-1\", \"group-id-2\"]"
    )
    private String[] specificGroups;
}
