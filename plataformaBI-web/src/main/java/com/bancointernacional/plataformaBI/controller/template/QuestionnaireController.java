package com.bancointernacional.plataformaBI.controller.template;

import com.bancointernacional.plataformaBI.domain.dto.template.questionary.response.QuestionaryDTO;
import com.bancointernacional.plataformaBI.service.QuestionaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Tag(name = "Questionnaires", description = "API for managing questionnaires and their assignments to policies")
@RequestMapping("/questionnaires")
@RestController
public class QuestionnaireController {

    @Autowired
    private QuestionaryService questionaryService;

    /**
     * Get available questionnaires for a policy that are not yet assigned to a specific process policy
     *
     * @param policyId The ID of the policy
     * @param processPolicyId The ID of the process policy
     * @return List of available questionnaires
     */
    @Operation(
        summary = "Get available questionnaires for a policy",
        description = "Retrieves questionnaires that are available for a specific policy but not yet assigned to the given process policy. " +
                     "This endpoint helps identify which questionnaires can still be assigned to a process policy."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved available questionnaires",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = QuestionaryDTO.class))
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request parameters",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        )
    })
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableQuestionnaires(
            @Parameter(
                description = "The ID of the policy for which to retrieve available questionnaires",
                required = true,
                example = "123"
            )
            @RequestParam("policyId") Long policyId,

            @Parameter(
                description = "The UUID of the process policy to check for existing assignments",
                required = true,
                example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @RequestParam("processPolicyId") UUID processPolicyId) {

        try {
            List<QuestionaryDTO> availableQuestionnaires = questionaryService
                    .getAvailableQuestionnariesForPolicy(policyId, processPolicyId);

            return ResponseEntity.ok(availableQuestionnaires);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving available questionnaires: " + e.getMessage());
        }
    }

    /**
     * Get all questionnaires for a specific policy
     *
     * @param policyId The ID of the policy
     * @return List of all questionnaires for the policy
     */
    @Operation(
        summary = "Get all questionnaires by policy ID",
        description = "Retrieves all questionnaires associated with a specific policy, regardless of their assignment status to process policies."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved questionnaires for the policy",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = QuestionaryDTO.class))
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid policy ID",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))
        )
    })
    @GetMapping("/by-policy/{policyId}")
    public ResponseEntity<?> getQuestionnairesByPolicy(
            @Parameter(
                description = "The ID of the policy for which to retrieve all questionnaires",
                required = true,
                example = "123"
            )
            @PathVariable Long policyId) {
        try {
            List<QuestionaryDTO> questionnaires = questionaryService.getQuestionariesByPolicyId(policyId);
            return ResponseEntity.ok(questionnaires);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving questionnaires: " + e.getMessage());
        }
    }

    /**
     * Get all active questionnaires in the database
     */
    @Operation(
        summary = "Get all active questionnaires",
        description = "Retrieves all questionnaires in the database that are not soft deleted. " +
                     "This endpoint returns the complete list of available questionnaires across all policies."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all active questionnaires",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = QuestionaryDTO.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    @GetMapping
    public ResponseEntity<?> getAllActiveQuestionnaires() {
        try {
            List<QuestionaryDTO> questionnaires = questionaryService.getAllActiveQuestionnaires();

            Map<String, Object> response = new HashMap<>();
            response.put("questionnaires", questionnaires);
            response.put("total", questionnaires.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error retrieving questionnaires: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
