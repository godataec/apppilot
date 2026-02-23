package com.bancointernacional.plataformaBI.domain.dto.azure;

import lombok.Data;
import java.util.List;

@Data
public class AzureServicePrincipalDTO {
    private List<AzureAppRoleDTO> appRoles;
}
