package com.bancointernacional.plataformaBI.models.DTO;

import lombok.Data;

@Data
public class EmailDTO {
    private String userId;
    private String message;
    private String email;
    private String subject;
}
