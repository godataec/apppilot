package com.bancointernacional.plataformaBI.domain.dto.utility;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailDTO implements Serializable {
    private String userId;
    private String message;
    private String email;
    private String subject;
}
