package com.bancointernacional.plataformaBI.domain.dto.azure;

import lombok.Data;

@Data
public class AzureRoleAssignmentDTO {
    private String appRoleId;
    private String resourceId;
}
