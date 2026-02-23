package com.bancointernacional.plataformaBI.domain.dto.audit.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditHistoryDTO {

    private UUID auditId;
    private String entityName;
    private String action;
    private String entityId;
    private String jsonRequest;
    private LocalDateTime actionDate;
    private LocalDateTime createdAt;
}
