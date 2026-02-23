package com.bancointernacional.plataformaBI.domain.dto.audit.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditSearchDTO {

    private String entityName;

    private String entityId;

    @Size(max = 20, message = "Action must not exceed 20 characters")
    private String action;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int page = 0;

    private int size = 20;
}
