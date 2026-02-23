package com.bancointernacional.plataformaBI.controller.period;

import com.bancointernacional.plataformaBI.domain.dto.period.process.action.request.ProcessActionDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.process.action.response.ActionResponseDTO;
import com.bancointernacional.plataformaBI.service.ActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/process-actions")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Process Action Management", description = "APIs for managing process actions")
public class ActionController {

    private final ActionService actionService;

    @PostMapping
    @Operation(
            summary = "Create a new process action",
            description = "Creates a new action for a process. If action type is 'reopened', it will update the process reopened status."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Action created successfully",
                    content = @Content(schema = @Schema(implementation = ActionResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Process not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<ActionResponseDTO> createAction(
            @Valid @RequestBody @Parameter(description = "Action data") ProcessActionDTO processActionDTO) {

        log.info("Received request to create action for process: {}", processActionDTO.getProcessId());
        ActionResponseDTO response = actionService.createAction(processActionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/process/{processId}")
    @Operation(
            summary = "Get all actions by process ID",
            description = "Retrieves all actions associated with a specific process"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Actions retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ActionResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Process not found"
            )
    })
    public ResponseEntity<List<ActionResponseDTO>> getActionsByProcessId(
            @PathVariable @Parameter(description = "Process ID") UUID processId) {

        log.info("Received request to get actions for process: {}", processId);
        List<ActionResponseDTO> actions = actionService.getActionsByProcessId(processId);
        return ResponseEntity.ok(actions);
    }

    @GetMapping("/{actionId}")
    @Operation(
            summary = "Get action by ID",
            description = "Retrieves a specific action by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Action retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ActionResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Action not found"
            )
    })
    public ResponseEntity<ActionResponseDTO> getActionById(
            @PathVariable @Parameter(description = "Action ID") UUID actionId) {

        log.info("Received request to get action: {}", actionId);
        ActionResponseDTO action = actionService.getActionById(actionId);
        return ResponseEntity.ok(action);
    }
}
