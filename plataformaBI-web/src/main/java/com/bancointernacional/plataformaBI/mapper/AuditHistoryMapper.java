package com.bancointernacional.plataformaBI.mapper;

import com.bancointernacional.plataformaBI.domain.dto.audit.response.AuditHistoryDTO;
import com.bancointernacional.plataformaBI.domain.model.audit.AuditHistory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuditHistoryMapper {

    public AuditHistoryDTO toDTO(AuditHistory auditHistory) {
        if (auditHistory == null) {
            return null;
        }

        return AuditHistoryDTO.builder()
                .auditId(auditHistory.getAuditId())
                .entityName(auditHistory.getEntityName())
                .action(auditHistory.getAction())
                .entityId(auditHistory.getEntityId())
                .jsonRequest(auditHistory.getJsonRequest())
                .actionDate(auditHistory.getActionDate())
                .createdAt(auditHistory.getCreatedAt())
                .build();
    }

    public List<AuditHistoryDTO> toDTOList(List<AuditHistory> auditHistories) {
        if (auditHistories == null) {
            return null;
        }

        return auditHistories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
