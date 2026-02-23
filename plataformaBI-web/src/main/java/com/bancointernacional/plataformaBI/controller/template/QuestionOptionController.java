package com.bancointernacional.plataformaBI.controller.template;

import com.bancointernacional.plataformaBI.domain.dto.template.question.option.response.QuestionOptionDTO;
import com.bancointernacional.plataformaBI.service.QuestionOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "Question Options", description = "API for managing question options")
@RequestMapping("/question-options")
@RestController
@RequiredArgsConstructor
public class QuestionOptionController {

    private final QuestionOptionService questionOptionService;

    /**
     * Get question options by question ID
     */
    @Operation(summary = "Get question options by question ID",
               description = "Retrieves all active question options for a specific question")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Question options retrieved successfully",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = QuestionOptionDTO.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid question ID",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/question/{questionId}")
    public ResponseEntity<Object> getQuestionOptionsByQuestionId(
            @Parameter(description = "Question ID to retrieve options for", required = true, example = "1")
            @PathVariable Integer questionId) {

        log.info("Request to get question options for question ID: {}", questionId);
        try {
            if (questionId == null || questionId <= 0) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid question ID. Must be a positive integer.");
                return ResponseEntity.badRequest().body(error);
            }
            QuestionOptionDTO questionOptions = questionOptionService.getQuestionOptionsByQuestionId(questionId);
            log.info("Successfully retrieved {} question options for question ID: {}",
                    questionOptions, questionId);
            return ResponseEntity.ok(questionOptions);
        } catch (Exception e) {
            log.error("Error retrieving question options for question ID: {}", questionId, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error retrieving question options: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Get question options by list of question IDs
     */
    @Operation(summary = "Get question options by multiple question IDs",
               description = "Retrieves all active question options for a list of question IDs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Question options retrieved successfully",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = QuestionOptionDTO.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid question IDs list",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/questions/batch")
    public ResponseEntity<Object> getQuestionOptionsByQuestionIds(
            @Parameter(description = "List of question IDs to retrieve options for", required = true)
            @RequestBody List<Integer> questionIds) {

        log.info("Request to get question options for {} question IDs", questionIds != null ? questionIds.size() : 0);

        try {
            if (questionIds == null || questionIds.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Question IDs list cannot be null or empty.");
                return ResponseEntity.badRequest().body(error);
            }
            boolean hasInvalidId = questionIds.stream().anyMatch(id -> id == null || id <= 0);
            if (hasInvalidId) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "All question IDs must be positive integers.");
                return ResponseEntity.badRequest().body(error);
            }
            List<QuestionOptionDTO> questionOptions = questionOptionService.getQuestionOptionsByQuestionIds(questionIds);
            log.info("Successfully retrieved {} question options for {} question IDs",
                    questionOptions.size(), questionIds.size());
            return ResponseEntity.ok(questionOptions);
        } catch (Exception e) {
            log.error("Error retrieving question options for question IDs: {}", questionIds, e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error retrieving question options: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
