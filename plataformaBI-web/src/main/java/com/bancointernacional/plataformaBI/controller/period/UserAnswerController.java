package com.bancointernacional.plataformaBI.controller.period;

import com.bancointernacional.plataformaBI.domain.dto.period.userAnswer.request.SubmitUserAnswerDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.userAnswer.request.UpdateUserAnswerDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.userAnswer.response.UserAnswerDTO;
import com.bancointernacional.plataformaBI.service.UserAnswerService;
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
import java.util.*;

@Slf4j
@Tag(name = "User Answers", description = "API for managing user answers to questions")
@RequestMapping("/user-answers")
@RestController
public class UserAnswerController {

    @Autowired
    private UserAnswerService userAnswerService;

    @Operation(summary = "Submit user answer",
               description = "Submits a new answer from a user for a specific question")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User answer submitted successfully",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UserAnswerDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<Object> submitUserAnswer(
            @Valid @RequestBody SubmitUserAnswerDTO submitDTO,
            BindingResult bindingResult) {

        log.info("Request to submit user answer for UserQuestion ID: {}", submitDTO.getUserQuestionId());

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

            log.warn("Validation errors in submit user answer request: {}", errors);
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            UserAnswerDTO userAnswer = userAnswerService.submitUserAnswer(submitDTO);
            log.info("Successfully submitted user answer with ID: {}", userAnswer.getAnswerId());
            return ResponseEntity.status(HttpStatus.CREATED).body(userAnswer);
        } catch (Exception e) {
            log.error("Error submitting user answer for UserQuestion ID: {}", submitDTO.getUserQuestionId(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Submit user answer",
            description = "Submits a new answer from a user for a specific question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User answer submitted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserAnswerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/batch")
    public ResponseEntity<?> submitUsersAnswerers(
            @Valid @RequestBody List<SubmitUserAnswerDTO> submissionsDTO,
            BindingResult bindingResult) {

        log.info("Request to submit user answer : {}", submissionsDTO);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));

            log.warn("Validation errors in submit user answer request: {}", errors);
            return ResponseEntity.badRequest().body(errors);
        }
        List<UserAnswerDTO> userAnswers = new ArrayList<>();
        for (SubmitUserAnswerDTO submissionDTO : submissionsDTO) {
            try {
                UserAnswerDTO userAnswer = userAnswerService.submitUserAnswerWithoutValidation(submissionDTO);
                log.info("Successfully submitted user answer with ID: {}", userAnswer.getAnswerId());
                userAnswers.add(userAnswer);
            } catch (Exception e) {
                log.error("Error submitting user answer for UserQuestion ID: {}", submissionDTO.getUserQuestionId(), e);
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userAnswers);
    }

    @Operation(summary = "Update user answer",
               description = "Updates an existing user answer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User answer updated successfully",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UserAnswerDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "User answer not found",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @PutMapping
    public ResponseEntity<Object> updateUserAnswer(
            @Valid @RequestBody UpdateUserAnswerDTO updateDTO,
            BindingResult bindingResult) {

        log.info("Request to update user answer with ID: {}", updateDTO.getAnswerId());

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

            log.warn("Validation errors in update user answer request: {}", errors);
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            UserAnswerDTO updatedUserAnswer = userAnswerService.updateUserAnswer(updateDTO);
            log.info("Successfully updated user answer with ID: {}", updateDTO.getAnswerId());
            return ResponseEntity.ok(updatedUserAnswer);
        } catch (Exception e) {
            log.error("Error updating user answer with ID: {}", updateDTO.getAnswerId(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Get user answer by ID",
               description = "Retrieves a user answer by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User answer found",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UserAnswerDTO.class))),
        @ApiResponse(responseCode = "404", description = "User answer not found",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{answerId}")
    public ResponseEntity<Object> getUserAnswerById(
            @Parameter(description = "User answer ID", required = true)
            @PathVariable UUID answerId) {

        log.info("Request to get UserAnswer with ID: {}", answerId);

        try {
            UserAnswerDTO userAnswer = userAnswerService.getUserAnswerById(answerId);
            log.info("Successfully retrieved UserAnswer with ID: {}", answerId);
            return ResponseEntity.ok(userAnswer);
        } catch (Exception e) {
            log.error("Error retrieving UserAnswer with ID: {}", answerId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Get user answers by user question ID",
               description = "Retrieves all user answers for a specific user question")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User answers retrieved successfully",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = UserAnswerDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/user-question/{userQuestionId}")
    public ResponseEntity<Object> getUserAnswersByUserQuestionId(
            @Parameter(description = "User question ID", required = true)
            @PathVariable UUID userQuestionId) {

        log.info("Request to get UserAnswers for UserQuestion ID: {}", userQuestionId);

        try {
            List<UserAnswerDTO> userAnswers = userAnswerService.getUserAnswersByUserQuestionId(userQuestionId);
            log.info("Successfully retrieved {} UserAnswers for UserQuestion ID: {}",
                    userAnswers.size(), userQuestionId);
            return ResponseEntity.ok(userAnswers);
        } catch (Exception e) {
            log.error("Error retrieving UserAnswers for UserQuestion ID: {}", userQuestionId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Get user answers by policy questionary ID",
               description = "Retrieves all user answers for a specific policy questionary")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User answers retrieved successfully",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = UserAnswerDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/policy-questionary/{policyQuestionaryId}")
    public ResponseEntity<Object> getUserAnswersByPolicyQuestionaryId(
            @Parameter(description = "Policy questionary ID", required = true)
            @PathVariable UUID policyQuestionaryId) {

        log.info("Request to get UserAnswers for PolicyQuestionary ID: {}", policyQuestionaryId);

        try {
            List<UserAnswerDTO> userAnswers = userAnswerService.getUserAnswersByPolicyQuestionaryId(policyQuestionaryId);
            log.info("Successfully retrieved {} UserAnswers for PolicyQuestionary ID: {}",
                    userAnswers.size(), policyQuestionaryId);
            return ResponseEntity.ok(userAnswers);
        } catch (Exception e) {
            log.error("Error retrieving UserAnswers for PolicyQuestionary ID: {}", policyQuestionaryId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Get user answers by user assigned",
               description = "Retrieves all user answers for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User answers retrieved successfully",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = UserAnswerDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/user/{userAssigned}")
    public ResponseEntity<Object> getUserAnswersByUserAssigned(
            @Parameter(description = "User assigned ID", required = true)
            @PathVariable UUID userAssigned) {

        log.info("Request to get UserAnswers for User ID: {}", userAssigned);

        try {
            List<UserAnswerDTO> userAnswers = userAnswerService.getUserAnswersByUserAssigned(userAssigned);
            log.info("Successfully retrieved {} UserAnswers for User ID: {}",
                    userAnswers.size(), userAssigned);
            return ResponseEntity.ok(userAnswers);
        } catch (Exception e) {
            log.error("Error retrieving UserAnswers for User ID: {}", userAssigned, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Delete user answer",
               description = "Soft deletes a user answer by setting is_deleted to true")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User answer deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User answer not found",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{answerId}")
    public ResponseEntity<Object> deleteUserAnswer(
            @Parameter(description = "User answer ID", required = true)
            @PathVariable UUID answerId) {

        log.info("Request to delete UserAnswer with ID: {}", answerId);

        try {
            userAnswerService.softDeleteUserAnswer(answerId);
            log.info("Successfully deleted UserAnswer with ID: {}", answerId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting UserAnswer with ID: {}", answerId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
