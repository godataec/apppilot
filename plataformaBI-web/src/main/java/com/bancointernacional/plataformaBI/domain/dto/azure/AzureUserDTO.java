package com.bancointernacional.plataformaBI.domain.dto.azure;

import lombok.Data;

@Data
public class AzureUserDTO {
    private String id;
    private String givenName;
    private String surname;
    private String mail;
}
