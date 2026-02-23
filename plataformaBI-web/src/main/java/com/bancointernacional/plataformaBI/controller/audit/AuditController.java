package com.bancointernacional.plataformaBI.controller.audit;

import com.bancointernacional.plataformaBI.domain.dto.audit.request.AuditSearchDTO;
import com.bancointernacional.plataformaBI.domain.dto.audit.response.AuditHistoryDTO;
import com.bancointernacional.plataformaBI.domain.model.audit.AuditHistory;
import com.bancointernacional.plataformaBI.enums.AuditAction;
import com.bancointernacional.plataformaBI.mapper.AuditHistoryMapper;
import com.bancointernacional.plataformaBI.service.audit.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "Audit History", description = "API for managing and querying audit history")
@RequestMapping("/audit")
@RestController
public class AuditController {

    @Autowired
    private AuditService auditService;

    @Autowired
    private AuditHistoryMapper auditHistoryMapper;

    @Operation(summary = "Get audit history by entity name",
               description = "Retrieves all audit records for a specific entity type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Audit history retrieved successfully",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = AuditHistoryDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/entity/{entityName}")
    public ResponseEntity<Object> getAuditHistoryByEntity(
            @Parameter(description = "Entity name", required = true)
            @PathVariable String entityName) {

        log.info("Request to get audit history for entity: {}", entityName);

        try {
            List<AuditHistory> auditHistories = auditService.getAuditHistoryByEntity(entityName);
            List<AuditHistoryDTO> auditHistoryDTOs = auditHistoryMapper.toDTOList(auditHistories);

            Map<String, Object> response = new HashMap<>();
            response.put("auditHistory", auditHistoryDTOs);
            response.put("total", auditHistoryDTOs.size());

            log.info("Successfully retrieved {} audit records for entity: {}", auditHistoryDTOs.size(), entityName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving audit history for entity: {}", entityName, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @Operation(summary = "Get audit history by entity ID",
               description = "Retrieves all audit records for a specific entity instance")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Audit history retrieved successfully",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = AuditHistoryDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/entity-id/{entityId}")
    public ResponseEntity<Object> getAuditHistoryByEntityId(
            @Parameter(description = "Entity ID", required = true)
            @PathVariable String entityId) {

        log.info("Request to get audit history for entity ID: {}", entityId);

        try {
            List<AuditHistory> auditHistories = auditService.getAuditHistoryByEntityId(entityId);
            List<AuditHistoryDTO> auditHistoryDTOs = auditHistoryMapper.toDTOList(auditHistories);

            Map<String, Object> response = new HashMap<>();
            response.put("auditHistory", auditHistoryDTOs);
            response.put("total", auditHistoryDTOs.size());

            log.info("Successfully retrieved {} audit records for entity ID: {}", auditHistoryDTOs.size(), entityId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving audit history for entity ID: {}", entityId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @Operation(summary = "Get audit history by action type",
               description = "Retrieves all audit records for a specific action (CREATE, UPDATE, DELETE)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Audit history retrieved successfully",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = AuditHistoryDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/action/{action}")
    public ResponseEntity<Object> getAuditHistoryByAction(
            @Parameter(description = "Action type (CREATE, UPDATE, DELETE)", required = true)
            @PathVariable String action) {

        log.info("Request to get audit history for action: {}", action);

        try {
            AuditAction auditAction = AuditAction.valueOf(action.toUpperCase());
            List<AuditHistory> auditHistories = auditService.getAuditHistoryByAction(auditAction);
            List<AuditHistoryDTO> auditHistoryDTOs = auditHistoryMapper.toDTOList(auditHistories);

            Map<String, Object> response = new HashMap<>();
            response.put("auditHistory", auditHistoryDTOs);
            response.put("total", auditHistoryDTOs.size());

            log.info("Successfully retrieved {} audit records for action: {}", auditHistoryDTOs.size(), action);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Invalid action type: {}", action);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid action type. Valid actions are: CREATE, UPDATE, DELETE");
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            log.error("Error retrieving audit history for action: {}", action, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @Operation(summary = "Get audit history by date range",
               description = "Retrieves all audit records within a specific date range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Audit history retrieved successfully",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = AuditHistoryDTO.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid date format",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/date-range")
    public ResponseEntity<Object> getAuditHistoryByDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd'T'HH:mm:ss)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date (yyyy-MM-dd'T'HH:mm:ss)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        log.info("Request to get audit history between {} and {}", startDate, endDate);

        try {
            if (startDate.isAfter(endDate)) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Start date must be before end date");
                return ResponseEntity.badRequest().body(error);
            }

            List<AuditHistory> auditHistories = auditService.getAuditHistoryByDateRange(startDate, endDate);
            List<AuditHistoryDTO> auditHistoryDTOs = auditHistoryMapper.toDTOList(auditHistories);

            Map<String, Object> response = new HashMap<>();
            response.put("auditHistory", auditHistoryDTOs);
            response.put("total", auditHistoryDTOs.size());
            response.put("startDate", startDate);
            response.put("endDate", endDate);

            log.info("Successfully retrieved {} audit records between {} and {}",
                    auditHistoryDTOs.size(), startDate, endDate);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving audit history for date range {} to {}", startDate, endDate, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @Operation(summary = "Get audit history by entity name and ID",
               description = "Retrieves all audit records for a specific entity instance")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Audit history retrieved successfully",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = AuditHistoryDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/entity/{entityName}/id/{entityId}")
    public ResponseEntity<Object> getAuditHistoryByEntityAndId(
            @Parameter(description = "Entity name", required = true)
            @PathVariable String entityName,
            @Parameter(description = "Entity ID", required = true)
            @PathVariable String entityId) {

        log.info("Request to get audit history for entity: {} with ID: {}", entityName, entityId);

        try {
            List<AuditHistory> auditHistories = auditService.getAuditHistoryByEntityAndId(entityName, entityId);
            List<AuditHistoryDTO> auditHistoryDTOs = auditHistoryMapper.toDTOList(auditHistories);

            Map<String, Object> response = new HashMap<>();
            response.put("auditHistory", auditHistoryDTOs);
            response.put("total", auditHistoryDTOs.size());
            response.put("entityName", entityName);
            response.put("entityId", entityId);

            log.info("Successfully retrieved {} audit records for entity: {} with ID: {}",
                    auditHistoryDTOs.size(), entityName, entityId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving audit history for entity: {} with ID: {}", entityName, entityId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @Operation(summary = "Manual audit log creation",
               description = "Manually creates an audit log entry")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Audit log created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/log/{action}")
    public ResponseEntity<Object> createManualAuditLog(
            @Parameter(description = "Action type (CREATE, UPDATE, DELETE)", required = true)
            @PathVariable String action,
            @Parameter(description = "Entity name", required = true)
            @RequestParam String entityName,
            @Parameter(description = "Entity ID", required = true)
            @RequestParam String entityId,
            @RequestBody Object requestData) {

        log.info("Request to manually create audit log for {} action on entity: {} with ID: {}",
                action, entityName, entityId);

        try {
            AuditAction auditAction = AuditAction.valueOf(action.toUpperCase());
            auditService.logAction(entityName, entityId, auditAction, requestData);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Audit log created successfully");
            response.put("action", action);
            response.put("entityName", entityName);
            response.put("entityId", entityId);

            log.info("Successfully created manual audit log for {} action on entity: {} with ID: {}",
                    action, entityName, entityId);
            return ResponseEntity.status(201).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Invalid action type: {}", action);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid action type. Valid actions are: CREATE, UPDATE, DELETE");
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            log.error("Error creating manual audit log for {} action on entity: {} with ID: {}",
                    action, entityName, entityId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}
