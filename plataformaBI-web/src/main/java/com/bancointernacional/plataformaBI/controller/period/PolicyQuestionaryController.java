package com.bancointernacional.plataformaBI.controller.period;

import com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.request.CreateProcessQuestionaryDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.request.UpdateProcessQuestionaryDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.response.PolicyQuestionaryDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.response.PolicyQuestionaryDetailDTO;
import com.bancointernacional.plataformaBI.service.PolicyQuestionaryService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Tag(name = "Policy Questionnaires", description = "API for managing questionnaire assignments to process policies")
@RequestMapping("/policy-questionnaires")
@RestController
public class PolicyQuestionaryController {

    @Autowired
    private PolicyQuestionaryService policyQuestionaryService;

    /**
     * Assign a single questionnaire to a process policy
     */
    @Operation(
        summary = "Assign single questionnaire to process policy",
        description = "Creates a new assignment of a single questionnaire to a specific process policy. " +
                     "This endpoint validates that the questionnaire exists and is not already assigned to the process policy."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Questionnaire successfully assigned to process policy",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PolicyQuestionaryDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request data or questionnaire already assigned",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Questionnaire not found",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        )
    })
    @PostMapping("/assign-single")
    public ResponseEntity<?> assignQuestionaryToProcess(
            @Valid @RequestBody CreateProcessQuestionaryDTO createDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body("Validation errors: " + bindingResult.getAllErrors());
        }

        try {
            PolicyQuestionaryDTO assignedQuestionary = policyQuestionaryService
                    .assignQuestionaryToProcess(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(assignedQuestionary);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error assigning questionnaire: " + e.getMessage());
        }
    }

    /**
     * Assign multiple questionnaires to process policies (bulk assignment)
     */
    @Operation(
        summary = "Assign multiple questionnaires to process policies",
        description = "Creates new assignments of multiple questionnaires to specific process policies. " +
                     "This endpoint validates that each questionnaire exists and is not already assigned to the respective process policy. " +
                     "Returns a list of successfully assigned questionnaires and any errors encountered."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "All questionnaires successfully assigned to process policies",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = PolicyQuestionaryDTO.class))
            )
        ),
        @ApiResponse(
            responseCode = "206",
            description = "Some questionnaires assigned successfully, others failed (partial success)",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "object"))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request data or empty request body",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        )
    })
    @PostMapping("/assign")
    public ResponseEntity<?> assignQuestionnariesToProcess(
            @Valid @RequestBody List<CreateProcessQuestionaryDTO> createDTOList,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body("Validation errors: " + bindingResult.getAllErrors());
        }

        if (createDTOList == null || createDTOList.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Request body cannot be empty. Please provide at least one questionnaire assignment.");
        }

        try {
            List<PolicyQuestionaryDTO> assignedQuestionnaires = new ArrayList<>();
            List<String> errors = new ArrayList<>();

            for (int i = 0; i < createDTOList.size(); i++) {
                CreateProcessQuestionaryDTO createDTO = createDTOList.get(i);
                try {
                    PolicyQuestionaryDTO assignedQuestionary = policyQuestionaryService
                            .assignQuestionaryToProcess(createDTO);
                    assignedQuestionnaires.add(assignedQuestionary);
                } catch (RuntimeException e) {
                    log.error("Error assigning questionnaire at index {}: {}", i, e.getMessage(), e);
                    errors.add("Item " + i + ": " + e.getMessage());
                }
            }

            if (!errors.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("successfulAssignments", assignedQuestionnaires);
                response.put("errors", errors);
                response.put("message", "Some assignments failed. Check the errors list for details.");
                response.put("totalRequested", createDTOList.size());
                response.put("successfulCount", assignedQuestionnaires.size());
                response.put("errorCount", errors.size());
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(assignedQuestionnaires);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error assigning questionnaires: " + e.getMessage());
        }
    }

    /**
     * Get all questionnaires assigned to a specific process policy
     */
    @Operation(
        summary = "Get questionnaires by process policy",
        description = "Retrieves all questionnaires that have been assigned to a specific process policy, " +
                     "including their current status and assignment details."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved assigned questionnaires",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = PolicyQuestionaryDTO.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        )
    })
    @GetMapping("/by-process-policy/{processPolicyId}")
    public ResponseEntity<?> getQuestionariesByProcessPolicy(
            @Parameter(
                description = "The UUID of the process policy for which to retrieve assigned questionnaires",
                required = true,
                example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID processPolicyId) {

        try {
            List<PolicyQuestionaryDTO> questionnaires = policyQuestionaryService
                    .getQuestionariesByProcessPolicy(processPolicyId);
            return ResponseEntity.ok(questionnaires);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving questionnaires: " + e.getMessage());
        }
    }

    /**
     * Get detailed questionnaires assigned to a specific process policy (enhanced response)
     */
    @Operation(
        summary = "Get detailed questionnaires by process policy",
        description = "Retrieves all questionnaires assigned to a specific process policy with comprehensive details " +
                     "including questionnaire info, process details, policy information, and assignment status."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved detailed assigned questionnaires",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = PolicyQuestionaryDetailDTO.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        )
    })
    @GetMapping("/detailed/by-process-policy/{processPolicyId}")
    public ResponseEntity<?> getDetailedQuestionariesByProcessPolicy(
            @Parameter(
                description = "The UUID of the process policy for which to retrieve detailed assigned questionnaires",
                required = true,
                example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID processPolicyId) {

        try {
            List<PolicyQuestionaryDetailDTO> questionnaires = policyQuestionaryService
                    .getDetailedQuestionariesByProcessPolicy(processPolicyId);
            return ResponseEntity.ok(questionnaires);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving detailed questionnaires: " + e.getMessage());
        }
    }

    /**
     * Remove a questionnaire assignment from a process policy
     */
    @Operation(
        summary = "Remove questionnaire assignment",
        description = "Removes (soft deletes) a questionnaire assignment from a process policy. " +
                     "The assignment is marked as deleted but not physically removed from the database."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Questionnaire assignment successfully removed",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Questionnaire assignment not found",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        )
    })
    @DeleteMapping("/{policyQuestionaryId}")
    public ResponseEntity<?> removeQuestionaryFromProcess(
            @Parameter(
                description = "The UUID of the policy questionary assignment to remove",
                required = true,
                example = "550e8400-e29b-41d4-a716-446655440001"
            )
            @PathVariable UUID policyQuestionaryId) {

        try {
            policyQuestionaryService.removeQuestionaryFromProcess(policyQuestionaryId);
            log.info("Successfully soft deleted PolicyQuestionary with ID: {}", policyQuestionaryId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing questionnaire assignment: " + e.getMessage());
        }
    }

    /**
     * Update the status of a questionnaire assignment
     */
    @Operation(
        summary = "Update questionnaire assignment status",
        description = "Updates the status of an existing questionnaire assignment to a process policy. " +
                     "Common statuses include ACTIVE, COMPLETED, PENDING, etc."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Questionnaire assignment status successfully updated",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PolicyQuestionaryDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Questionnaire assignment not found",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        )
    })
    @PutMapping("/{policyQuestionaryId}/status")
    public ResponseEntity<?> updateQuestionaryStatus(
            @Parameter(
                description = "The UUID of the policy questionary assignment to update",
                required = true,
                example = "550e8400-e29b-41d4-a716-446655440001"
            )
            @PathVariable UUID policyQuestionaryId,

            @Parameter(
                description = "The new status for the questionnaire assignment",
                required = true,
                example = "COMPLETED"
            )
            @RequestParam String status) {

        try {
            PolicyQuestionaryDTO updatedQuestionary = policyQuestionaryService
                    .updateQuestionaryStatus(policyQuestionaryId, status);
            return ResponseEntity.ok(updatedQuestionary);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating questionnaire status: " + e.getMessage());
        }
    }

    /**
     * Update a questionnaire assignment (comprehensive update)
     */
    @Operation(
        summary = "Update questionnaire assignment",
        description = "Updates a questionnaire assignment with comprehensive data including start date, end date, and status. " +
                     "Only provided fields will be updated (partial update supported)."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Questionnaire assignment successfully updated",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PolicyQuestionaryDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request data",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Questionnaire assignment not found",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        )
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateProcessQuestionary(
            @Valid @RequestBody UpdateProcessQuestionaryDTO updateDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body("Validation errors: " + bindingResult.getAllErrors());
        }

        try {
            PolicyQuestionaryDTO updatedQuestionary = policyQuestionaryService
                    .updateProcessQuestionary(updateDTO);
            return ResponseEntity.ok(updatedQuestionary);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating questionnaire assignment: " + e.getMessage());
        }
    }

    /**
     * Get UserQuestion statistics for a PolicyQuestionary
     */
    @Operation(
        summary = "Get UserQuestion statistics for a PolicyQuestionary",
        description = "Retrieves the count of UserQuestion records that were automatically created " +
                     "for a specific PolicyQuestionary assignment."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved UserQuestion statistics",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "object"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        )
    })
    @GetMapping("/{policyQuestionaryId}/user-questions/stats")
    public ResponseEntity<?> getUserQuestionStats(
            @Parameter(
                description = "The UUID of the policy questionary for which to retrieve UserQuestion statistics",
                required = true,
                example = "550e8400-e29b-41d4-a716-446655440001"
            )
            @PathVariable UUID policyQuestionaryId) {

        try {
            int userQuestionCount = policyQuestionaryService.getUserQuestionCount(policyQuestionaryId);

            Map<String, Object> stats = new HashMap<>();
            stats.put("policyQuestionaryId", policyQuestionaryId);
            stats.put("userQuestionCount", userQuestionCount);
            stats.put("message", "UserQuestion records automatically created for this PolicyQuestionary");

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving UserQuestion statistics: " + e.getMessage());
        }
    }

    /**
     * Soft delete policy questionary
     */
    @Operation(summary = "Delete policy questionary",
               description = "Soft deletes a policy questionary by setting is_deleted to true")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Policy questionary deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Policy questionary not found",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/soft-delete/{policyQuestionaryId}")
    public ResponseEntity<Object> deletePolicyQuestionary(
            @Parameter(description = "Policy questionary ID", required = true)
            @PathVariable String policyQuestionaryId) {
        log.info("Request to soft delete PolicyQuestionary with ID: {}", policyQuestionaryId);
        try {
            policyQuestionaryService.softDeletePolicyQuestionary(policyQuestionaryId);
            log.info("Successfully soft deleted PolicyQuestionary with ID: {}", policyQuestionaryId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error soft deleting PolicyQuestionary with ID: {}", policyQuestionaryId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Filter policy questionnaires with the same parameters as ProcessPolicyController
     */
    @Operation(
        summary = "Filter policy questionnaires",
        description = "Retrieves policy questionnaires with filtering capabilities similar to process policies. " +
                     "Supports filtering by subsidiary, process characters, date ranges, and year with pagination."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Policy questionnaires retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = PolicyQuestionaryDTO.class))
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid date format or parameters"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    @GetMapping("/filter")
    public ResponseEntity<?> filterBy(
            @Parameter(description = "Subsidiary ID for filtering", example = "1")
            @RequestParam(name = "subsidiary", defaultValue = "1") Long subsidiary,

            @Parameter(description = "Process character/ID pattern to search for", example = "PROC-001")
            @RequestParam(name = "idProcess", required = false) String processId,

            @Parameter(description = "Process character/ID pattern to search for", example = "PROC-001")
            @RequestParam(name = "idPolicy", required = false) String processPolicyId,

            @Parameter(description = "Page number for pagination", example = "1")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @Parameter(description = "Page size for pagination", example = "5")
            @RequestParam(name = "size", defaultValue = "5") int pageSize) {

        try {
            log.info("Filtering policy questionnaires with parameters: subsidiary={}, processId={}, page={}, size={}, processPolicyId={}",
                    subsidiary, processId, page, pageSize, processPolicyId);
            List<PolicyQuestionaryDTO> policyQuestionnaires = policyQuestionaryService.getPolicyQuestionariesFilterBy(
                    subsidiary, processId, page, pageSize,processPolicyId);
            Integer totalCount = policyQuestionaryService.getPolicyQuestionariesFilterByWithoutPages(
                    subsidiary, processId, processPolicyId);
            Map<String, Object> response = new HashMap<>();
            response.put("policyQuestionnaires", policyQuestionnaires);
            response.put("total", totalCount);
            log.info("Successfully retrieved {} policy questionnaires out of {} total",
                    policyQuestionnaires.size(), totalCount);
            return ResponseEntity.ok(response);
        } catch (DateTimeParseException e) {
            log.error("Invalid date format in filter request: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid date format. Please use YYYY-MM-DD format.");
        } catch (Exception e) {
            log.error("Error filtering policy questionnaires: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


    @GetMapping("/user/{UserUUID}")
    public ResponseEntity<?> filterBy(@PathVariable(value ="UserUUID") UUID UserUUID) {
        try {
            List<PolicyQuestionaryDetailDTO> policyQuestionnaires = policyQuestionaryService.getPolicyQuestionariesByUserId(UserUUID);
            Map<String, Object> response = new HashMap<>();
            response.put("policyQuestionnaires", policyQuestionnaires);
            return ResponseEntity.ok(response);
        } catch (DateTimeParseException e) {
            log.error("Invalid date format in filter request: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid date format. Please use YYYY-MM-DD format.");
        } catch (Exception e) {
            log.error("Error filtering policy questionnaires: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


}
