package com.bancointernacional.plataformaBI.service.audit;

import com.bancointernacional.plataformaBI.domain.model.audit.AuditHistory;
import com.bancointernacional.plataformaBI.enums.AuditAction;
import com.bancointernacional.plataformaBI.repository.audit.AuditHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AuditService {

    @Autowired
    private AuditHistoryRepository auditHistoryRepository;

    @Autowired
    private ObjectMapper objectMapper;



    @Async
    public void logCreate(String entityName, String entityId, Object requestData) {
        log.info("Logging CREATE action for entity: {} with ID: {}", entityName, entityId);
        saveAuditLog(entityName, entityId, AuditAction.CREATE, requestData);
    }

    @Async
    public void logUpdate(String entityName, String entityId, Object requestData) {
        log.info("Logging UPDATE action for entity: {} with ID: {}", entityName, entityId);
        saveAuditLog(entityName, entityId, AuditAction.UPDATE, requestData);
    }

    @Async
    public void logDelete(String entityName, String entityId, Object requestData) {
        log.info("Logging DELETE action for entity: {} with ID: {}", entityName, entityId);
        saveAuditLog(entityName, entityId, AuditAction.DELETE, requestData);
    }

    @Async
    public void logAction(String entityName, String entityId, AuditAction action, Object requestData) {
        log.info("Logging {} action for entity: {} with ID: {}", action.getValue(), entityName, entityId);
        saveAuditLog(entityName, entityId, action, requestData);
    }

    private void saveAuditLog(String entityName, String entityId, AuditAction action, Object requestData) {
        try {
            String jsonRequest = convertToJson(requestData);

            AuditHistory auditHistory = AuditHistory.builder()
                    .entityName(entityName)
                    .entityId(entityId)
                    .action(action.getValue())
                    .jsonRequest(jsonRequest)
                    .actionDate(LocalDateTime.now())
                    .isDeleted(false)
                    .build();

            auditHistoryRepository.save(auditHistory);
            log.info("Successfully saved audit log for {} action on entity: {} with ID: {}",
                    action.getValue(), entityName, entityId);

        } catch (Exception e) {
            log.error("Failed to save audit log for {} action on entity: {} with ID: {}",
                    action.getValue(), entityName, entityId, e);
        }
    }

    private String convertToJson(Object requestData) {
        if (requestData == null) {
            return "{}";
        }

        try {
            if (requestData instanceof String) {
                return (String) requestData;
            }
            return objectMapper.writeValueAsString(requestData);
        } catch (JsonProcessingException e) {
            log.warn("Failed to convert object to JSON, using toString(): {}", e.getMessage());
            return requestData.toString();
        }
    }

    public List<AuditHistory> getAuditHistoryByEntity(String entityName) {
        log.info("Retrieving audit history for entity: {}", entityName);
        return auditHistoryRepository.findByEntityNameOrderByActionDateDesc(entityName);
    }

    public List<AuditHistory> getAuditHistoryByEntityId(String entityId) {
        log.info("Retrieving audit history for entity ID: {}", entityId);
        return auditHistoryRepository.findByEntityIdOrderByActionDateDesc(entityId);
    }

    public List<AuditHistory> getAuditHistoryByAction(AuditAction action) {
        log.info("Retrieving audit history for action: {}", action.getValue());
        return auditHistoryRepository.findByActionOrderByActionDateDesc(action.getValue());
    }

    public List<AuditHistory> getAuditHistoryByEntityAndId(String entityName, String entityId) {
        log.info("Retrieving audit history for entity: {} with ID: {}", entityName, entityId);
        return auditHistoryRepository.findByEntityNameAndEntityIdOrderByActionDateDesc(entityName, entityId);
    }

    public List<AuditHistory> getAuditHistoryByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Retrieving audit history between {} and {}", startDate, endDate);
        return auditHistoryRepository.findByActionDateBetweenOrderByActionDateDesc(startDate, endDate);
    }

    public void logCreateWithUUID(String entityName, UUID entityId, Object requestData) {
        logCreate(entityName, entityId.toString(), requestData);
    }

    public void logUpdateWithUUID(String entityName, UUID entityId, Object requestData) {
        logUpdate(entityName, entityId.toString(), requestData);
    }

    public void logDeleteWithUUID(String entityName, UUID entityId, Object requestData) {
        logDelete(entityName, entityId.toString(), requestData);
    }
}
