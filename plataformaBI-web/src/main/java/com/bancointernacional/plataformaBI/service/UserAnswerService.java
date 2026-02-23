package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.dto.period.userAnswer.request.SubmitUserAnswerDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.userAnswer.request.UpdateUserAnswerDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.userAnswer.response.UserAnswerDTO;
import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.model.period.UserQuestion;
import com.bancointernacional.plataformaBI.exception.ResourceNotFoundException;
import com.bancointernacional.plataformaBI.repository.period.UserAnswerRepository;
import com.bancointernacional.plataformaBI.repository.period.UserQuestionRepository;
import com.bancointernacional.plataformaBI.util.AnswerUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;
    private final UserQuestionRepository userQuestionRepository;

    @Autowired
    private AnswerUtility answerUtility;

    /**
     * Submit a new user answer
     */
    @Transactional
    public UserAnswerDTO submitUserAnswer(SubmitUserAnswerDTO submitDTO) {
        log.info("Submitting user answer for UserQuestion ID: {}", submitDTO.getUserQuestionId());
        UserQuestion userQuestion = userQuestionRepository.findById(submitDTO.getUserQuestionId()).orElseThrow(() -> new ResourceNotFoundException("UserQuestion not found with ID: " + submitDTO.getUserQuestionId()));

        int existingAnswers = userAnswerRepository.countByUserQuestionIdAndUserAssigned(submitDTO.getUserQuestionId().toString(), submitDTO.getUserAssigned().toString());
        if (existingAnswers > 0) {
            throw new IllegalStateException("User answer already exists for this user question and user");
        }
        UserAnswer userAnswer = UserAnswer.builder().answerType(
                        submitDTO.getAnswerType())
                .answerText(submitDTO.getAnswerText())
                .answerDescription(submitDTO.getAnswerDescription())
                .cretaedAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .documentId(submitDTO.getDocumentId())
                .answerStatus(submitDTO.getAnswerStatus())
                .userAssigned(submitDTO.getUserAssigned())
                .policyQuestionaryId(submitDTO.getPolicyQuestionaryId())
                .questionId(submitDTO.getQuestionId())
                .userQuestion(userQuestion)
                .createdBy(submitDTO.getCreatedBy())
                .isDeleted(false).build();

        UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);
        log.info("Successfully submitted user answer with ID: {}", savedUserAnswer.getAnswerId());

        return mapToDTO(savedUserAnswer);
    }

    /**
     * Submit a new user answer without duplicate validation (for batch operations)
     */
    @Transactional
    public UserAnswerDTO submitUserAnswerWithoutValidation(SubmitUserAnswerDTO submitDTO) {
        log.info("Submitting user answer without validation for UserQuestion ID: {}", submitDTO.getUserQuestionId());

        // Fetch the UserQuestion entity
        UserQuestion userQuestion = userQuestionRepository.findById(submitDTO.getUserQuestionId()).orElseThrow(() -> new ResourceNotFoundException("UserQuestion not found with ID: " + submitDTO.getUserQuestionId()));

        UserAnswer userAnswer = UserAnswer.builder().answerType(
                        submitDTO.getAnswerType())
                .answerText(submitDTO.getAnswerText())
                .answerDescription(submitDTO.getAnswerDescription())
                .cretaedAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .documentId(submitDTO.getDocumentId())
                .answerStatus(submitDTO.getAnswerStatus())
                .userAssigned(submitDTO.getUserAssigned())
                .policyQuestionaryId(submitDTO.getPolicyQuestionaryId())
                .questionId(submitDTO.getQuestionId())
                .userQuestion(userQuestion)
                .createdBy(submitDTO.getCreatedBy())
                .isDeleted(false).build();
        UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);
        log.info("Successfully submitted user answer with ID: {}", savedUserAnswer.getAnswerId());

        return mapToDTO(savedUserAnswer);
    }

    /**
     * Update an existing user answer
     */
    @Transactional
    public UserAnswerDTO updateUserAnswer(UpdateUserAnswerDTO updateDTO) {
        log.info("Updating user answer with ID: {}", updateDTO.getAnswerId());
        UserAnswer userAnswer = userAnswerRepository.findByIdAndNotDeleted(updateDTO.getAnswerId().toString()).orElseThrow(() -> new ResourceNotFoundException("UserAnswer not found with ID: " + updateDTO.getAnswerId()));
        if (updateDTO.getAnswerText() != null) {
            userAnswer.setAnswerText(updateDTO.getAnswerText());
        }
        if (updateDTO.getAnswerStatus() != null) {
            userAnswer.setAnswerStatus(updateDTO.getAnswerStatus());
        }
        if (updateDTO.getUpdatedBy() != null) {
            userAnswer.setUpdatedBy(updateDTO.getUpdatedBy());
        }
        userAnswer.setUpdatedAt(LocalDate.now());

        //answerUtility.validateBeforeUpdate(userAnswer);
        UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);
        log.info("Successfully updated user answer with ID: {}", updateDTO.getAnswerId());
        return mapToDTO(savedUserAnswer);
    }

    /**
     * Get user answer by ID
     */
    public UserAnswerDTO getUserAnswerById(UUID answerId) {
        log.info("Fetching UserAnswer with ID: {}", answerId);
        UserAnswer userAnswer = userAnswerRepository.findByIdAndNotDeleted(answerId.toString()).orElseThrow(() -> new ResourceNotFoundException("UserAnswer not found with ID: " + answerId));
        return mapToDTO(userAnswer);
    }

    /**
     * Get all user answers by user question ID
     */
    public List<UserAnswerDTO> getUserAnswersByUserQuestionId(UUID userQuestionId) {
        log.info("Fetching UserAnswers for UserQuestion ID: {}", userQuestionId);
        List<UserAnswer> userAnswers = userAnswerRepository.findByUserQuestionId(userQuestionId.toString());
        return userAnswers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Get all user answers by policy questionary ID
     */
    public List<UserAnswerDTO> getUserAnswersByPolicyQuestionaryId(UUID policyQuestionaryId) {
        log.info("Fetching UserAnswers for PolicyQuestionary ID: {}", policyQuestionaryId);
        List<UserAnswer> userAnswers = userAnswerRepository.findByPolicyQuestionaryId(policyQuestionaryId.toString());
        return userAnswers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Get all user answers by user assigned
     */
    public List<UserAnswerDTO> getUserAnswersByUserAssigned(UUID userAssigned) {
        log.info("Fetching UserAnswers for User ID: {}", userAssigned);
        List<UserAnswer> userAnswers = userAnswerRepository.findByUserAssigned(userAssigned.toString());
        return userAnswers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Soft delete user answer
     */
    @Transactional
    public void softDeleteUserAnswer(UUID answerId) {
        log.info("Soft deleting UserAnswer with ID: {}", answerId);
        userAnswerRepository.findByIdAndNotDeleted(answerId.toString()).orElseThrow(() -> new ResourceNotFoundException("UserAnswer not found with ID: " + answerId));
        userAnswerRepository.softDeleteById(answerId.toString());
        log.info("Successfully soft deleted UserAnswer with ID: {}", answerId);
    }

    /**
     * Map UserAnswer entity to DTO
     */
    private UserAnswerDTO mapToDTO(UserAnswer userAnswer) {
      /*  Optional<Question> optionalQuestion = questionRepository.findById(userAnswer.getQuestionId().intValue());
        String descriptionTemp = "";
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            if (!question.getQuestionJson().isEmpty()) {
                try {
                    JSONObject TypeRespJson = new JSONObject(question.getQuestionJson());
                    Iterator<String> keys = TypeRespJson.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        try {
                            JSONObject innerObject = TypeRespJson.getJSONObject(key);
                            if (innerObject.has("code") && innerObject.getString("code").contains(userAnswer.getDocumentId())) {
                                descriptionTemp = innerObject.getString("text");
                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/
        return UserAnswerDTO.builder().answerId(userAnswer.getAnswerId())
                .answerType(userAnswer.getAnswerType())
                .answerText(userAnswer.getAnswerText())
                .answerDescription(userAnswer.getAnswerDescription())
                .createdAt(userAnswer.getCretaedAt())
                .updatedAt(userAnswer.getUpdatedAt())
                .documentId(userAnswer.getDocumentId())
                .answerStatus(userAnswer.getAnswerStatus())
                .userAssigned(userAnswer.getUserAssigned())
                .policyQuestionaryId(userAnswer.getPolicyQuestionaryId())
                .questionId(userAnswer.getQuestionId())
                .userQuestionId(userAnswer.getUserQuestion() != null ? userAnswer.getUserQuestion().getUserQuestionId() : null)
                .updatedBy(userAnswer.getUpdatedBy())
                .createdBy(userAnswer.getCreatedBy())
                .isDeleted(userAnswer.isDeleted()).build();
    }
}
