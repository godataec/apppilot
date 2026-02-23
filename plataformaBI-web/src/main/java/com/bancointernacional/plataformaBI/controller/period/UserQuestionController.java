package com.bancointernacional.plataformaBI.controller.period;

import com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.request.UpdateUserQuestionAssignmentDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.request.UpdateUserQuestionDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.response.UserDetailedQuestionDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.response.UserQuestionDTO;
import com.bancointernacional.plataformaBI.service.UserQuestionService;
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
import java.util.*;

@Slf4j
@Tag(name = "User Questions", description = "API for managing user question assignments")
@RequestMapping("/user-questions")
@RestController
public class UserQuestionController {

    @Autowired
    private UserQuestionService userQuestionService;

    /**
     * Update the user assigned to a user question
     */
    @Operation(summary = "Update user assignment",
               description = "Updates the user assigned to a specific user question")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User assignment updated successfully",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UserQuestionDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "User question not found",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/assignment")
    public ResponseEntity<Object> updateUserAssignment(
            @Valid @RequestBody UpdateUserQuestionAssignmentDTO updateDTO,
            BindingResult bindingResult) {

        log.info("Request to update user assignment for UserQuestion ID: {}", updateDTO.getUserQuestionId());

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

            log.warn("Validation errors in update user assignment request: {}", errors);
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            UserQuestionDTO updatedUserQuestion = userQuestionService.updateUserAssignment(updateDTO);
            log.info("Successfully updated user assignment for UserQuestion ID: {}", updateDTO.getUserQuestionId());
            return ResponseEntity.ok(updatedUserQuestion);
        } catch (Exception e) {
            log.error("Error updating user assignment for UserQuestion ID: {}", updateDTO.getUserQuestionId(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


    /**
     * Update the user assigned to a user question
     */
    @Operation(summary = "Update user assignment",
            description = "Updates the user assigned to a specific user question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User assignment updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserQuestionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User question not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/status")
    public ResponseEntity<Object> updateUserQuestion(
            @Valid @RequestBody UpdateUserQuestionDTO updateDTO,
            BindingResult bindingResult) {

        log.info("Request to update question status for UserQuestion ID: {}", updateDTO.getUserQuestionId());

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));

            log.warn("Validation errors in update questions status request: {}", errors);
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            UserQuestionDTO updatedUserQuestion = userQuestionService.updateUserQuestionStatus(updateDTO);
            log.info("Successfully updated user question status for UserQuestion ID: {}", updateDTO.getUserQuestionId());
            return ResponseEntity.ok(updatedUserQuestion);
        } catch (Exception e) {
            log.error("Error updating user question status for UserQuestion ID: {}", updateDTO.getUserQuestionId(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


    /**
     * Get user question by ID
     */
    @Operation(summary = "Get user question by ID",
               description = "Retrieves a user question by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User question found",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UserQuestionDTO.class))),
        @ApiResponse(responseCode = "404", description = "User question not found",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{userQuestionId}")
    public ResponseEntity<Object> getUserQuestionById(
            @Parameter(description = "User question ID", required = true)
            @PathVariable UUID userQuestionId) {

        log.info("Request to get UserQuestion with ID: {}", userQuestionId);

        try {
            UserQuestionDTO userQuestion = userQuestionService.getUserQuestionById(userQuestionId);
            log.info("Successfully retrieved UserQuestion with ID: {}", userQuestionId);
            return ResponseEntity.ok(userQuestion);
        } catch (Exception e) {
            log.error("Error retrieving UserQuestion with ID: {}", userQuestionId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Get all user questions by policy questionary ID
     */
    @Operation(summary = "Get user questions by policy questionary ID",
               description = "Retrieves all user questions for a specific policy questionary")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User questions retrieved successfully",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = UserQuestionDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/policy-questionary/{policyQuestionaryId}")
    public ResponseEntity<Object> getUserQuestionsByPolicyQuestionaryId(
            @Parameter(description = "Policy questionary ID", required = true)
            @PathVariable String policyQuestionaryId) {

        log.info("Request to get UserQuestions for PolicyQuestionary ID: {}", policyQuestionaryId);

        try {
            List<UserQuestionDTO> userQuestions = userQuestionService.getUserQuestionsByPolicyQuestionaryId(policyQuestionaryId);
            log.info("Successfully retrieved {} UserQuestions for PolicyQuestionary ID: {}",
                    userQuestions.size(), policyQuestionaryId);
            return ResponseEntity.ok(userQuestions);
        } catch (Exception e) {
            log.error("Error retrieving UserQuestions for PolicyQuestionary ID: {}", policyQuestionaryId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Soft delete user question
     */
    @Operation(summary = "Delete user question",
               description = "Soft deletes a user question by setting is_deleted to true")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User question deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User question not found",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{userQuestionId}")
    public ResponseEntity<Object> deleteUserQuestion(
            @Parameter(description = "User question ID", required = true)
            @PathVariable UUID userQuestionId) {

        log.info("Request to delete UserQuestion with ID: {}", userQuestionId);

        try {
            userQuestionService.softDeleteUserQuestion(userQuestionId);
            log.info("Successfully deleted UserQuestion with ID: {}", userQuestionId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting UserQuestion with ID: {}", userQuestionId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterBy(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int pageSize,
            @RequestParam(name = "policyQuestionaryId",required=false) String policyQuestionaryId,
            @RequestParam(name = "userAssigned", required = false) String userAssigned) {

        try {
            List<UserDetailedQuestionDTO> userDetailedQuestions = new ArrayList<>();
            Long totalCount = 0L;
            if (userAssigned != null) {
                userDetailedQuestions = userQuestionService.getPaginatedQuestions(page, pageSize, policyQuestionaryId, userAssigned);
                totalCount = userQuestionService.getTotalCountForPagination(policyQuestionaryId, userAssigned);
            }else{
                userDetailedQuestions = userQuestionService.getPaginatedQuestions(page, pageSize, policyQuestionaryId);
                totalCount = userQuestionService.getTotalCountForPagination(policyQuestionaryId);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("questions", userDetailedQuestions);
            response.put("total", totalCount);
            return ResponseEntity.ok(response);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }

}
