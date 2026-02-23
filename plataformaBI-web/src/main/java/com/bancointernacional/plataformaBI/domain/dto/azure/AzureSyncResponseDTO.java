package com.bancointernacional.plataformaBI.domain.dto.azure;

import com.bancointernacional.plataformaBI.domain.dto.settings.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object for Azure AD user synchronization")
public class AzureSyncResponseDTO {

    @Schema(
        description = "Indicates if the synchronization was successful",
        example = "true"
    )
    private boolean success;

    @Schema(
        description = "Human-readable message describing the result",
        example = "Successfully synchronized users from Azure AD"
    )
    private String message;

    @Schema(
        description = "Total number of users synchronized",
        example = "5"
    )
    private int totalUsers;

    @Schema(description = "List of synchronized users")
    private List<UserDTO> users;

    @Schema(
        description = "Timestamp when the synchronization was completed",
        example = "1694181234567"
    )
    private long timestamp;

    @Schema(
        description = "Azure AD client ID used for synchronization",
        example = "78f50437-3021-4ca2-8430-4ef9f2a66907"
    )
    private String clientId;

    @Schema(
        description = "Number of groups processed during synchronization",
        example = "3"
    )
    private int processedGroups;

    @Schema(
        description = "Number of users that failed to synchronize",
        example = "0"
    )
    private int failedUsers;
}
