package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.request.UpdateUserQuestionDTO;
import com.bancointernacional.plataformaBI.domain.dto.template.question.response.QuestionDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.request.UpdateUserQuestionAssignmentDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.response.UserDetailedQuestionDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.userQuestion.response.UserQuestionDTO;
import com.bancointernacional.plataformaBI.domain.dto.settings.UserDTO;
import com.bancointernacional.plataformaBI.domain.model.period.UserQuestion;
import com.bancointernacional.plataformaBI.domain.model.settings.SubsidiaryUser;
import com.bancointernacional.plataformaBI.domain.model.template.Question;
import com.bancointernacional.plataformaBI.exception.ResourceNotFoundException;
import com.bancointernacional.plataformaBI.repository.period.UserQuestionRepository;
import com.bancointernacional.plataformaBI.repository.settings.SubsidiaryUserRepository;
import com.bancointernacional.plataformaBI.repository.template.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserQuestionService {

    private final UserQuestionRepository userQuestionRepository;
    private final QuestionRepository questionRepository;
    private final SubsidiaryUserRepository subsidiaryUserRepository;

    /**
     * Update the user assigned to a user question
     */
    @Transactional
    public UserQuestionDTO updateUserAssignment(UpdateUserQuestionAssignmentDTO updateDTO) {
        log.info("Updating user assignment for UserQuestion ID: {}", updateDTO.getUserQuestionId());
        UserQuestion userQuestion = userQuestionRepository.findById(updateDTO.getUserQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "UserQuestion not found with ID: " + updateDTO.getUserQuestionId()));
        if (userQuestion.isDeleted()) {
            throw new IllegalStateException("Cannot update deleted UserQuestion");
        }
        userQuestion.setUserAssigned(updateDTO.getUserAssigned());
        userQuestion.setUpdatedAt(LocalDate.now());
        UserQuestion savedUserQuestion = userQuestionRepository.save(userQuestion);
        log.info("Successfully updated user assignment for UserQuestion ID: {}", updateDTO.getUserQuestionId());
        return mapToDTO(savedUserQuestion);
    }

    @Transactional
    public UserQuestionDTO updateUserQuestionStatus(UpdateUserQuestionDTO updateDTO) {
        log.info("Updating user assignment for UserQuestion ID: {}", updateDTO.getUserQuestionId());
        UserQuestion userQuestion = userQuestionRepository.findById(updateDTO.getUserQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "UserQuestion not found with ID: " + updateDTO.getUserQuestionId()));
        if (userQuestion.isDeleted()) {
            throw new IllegalStateException("Cannot update deleted UserQuestion");
        }
        userQuestion.setQuestionStatus(updateDTO.getQuestionStatus());
        userQuestion.setUpdatedAt(LocalDate.now());
        UserQuestion savedUserQuestion = userQuestionRepository.save(userQuestion);
        log.info("Successfully updated question status for UserQuestion ID: {}", updateDTO.getUserQuestionId());
        return mapToDTO(savedUserQuestion);
    }

    /**
     * Get user question by ID
     */
    public UserQuestionDTO getUserQuestionById(UUID userQuestionId) {
        log.info("Fetching UserQuestion with ID: {}", userQuestionId);
        UserQuestion userQuestion = userQuestionRepository.findById(userQuestionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "UserQuestion not found with ID: " + userQuestionId));
        return mapToDTO(userQuestion);
    }

    /**
     * Get all user questions by policy questionary ID
     */
    public List<UserQuestionDTO> getUserQuestionsByPolicyQuestionaryId(String policyQuestionaryId) {
        log.info("Fetching UserQuestions for PolicyQuestionary ID: {}", policyQuestionaryId);
        List<UserQuestion> userQuestions = userQuestionRepository.findByPolicyQuestionaryId(policyQuestionaryId);
        return userQuestions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Soft delete user question
     */
    @Transactional
    public void softDeleteUserQuestion(UUID userQuestionId) {
        log.info("Soft deleting UserQuestion with ID: {}", userQuestionId);
        UserQuestion userQuestion = userQuestionRepository.findById(userQuestionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "UserQuestion not found with ID: " + userQuestionId));
        if (userQuestion.isDeleted()) {
            throw new IllegalStateException("UserQuestion is already deleted");
        }
        userQuestion.setDeleted(true);
        userQuestion.setUpdatedAt(LocalDate.now());
        userQuestionRepository.save(userQuestion);
        log.info("Successfully soft deleted UserQuestion with ID: {}", userQuestionId);
    }

    /**
     * Map UserQuestion entity to DTO
     */
    private UserQuestionDTO mapToDTO(UserQuestion userQuestion) {
        return UserQuestionDTO.builder()
                .userQuestionId(userQuestion.getUserQuestionId())
                .policyQuestionaryId(userQuestion.getPolicyQuestionary() != null ?
                        userQuestion.getPolicyQuestionary().getPolicyQuestionaryId().toString() : null)
                .questionId(userQuestion.getQuestionId())
                .userAssigned(userQuestion.getUserAssigned())
                .questionStatus(userQuestion.getQuestionStatus())
                .isDeleted(userQuestion.isDeleted())
                .createdAt(userQuestion.getCreatedAt())
                .updatedAt(userQuestion.getUpdatedAt())
                .build();
    }

    /**
     * Get paginated list of user questions with detailed information
     */
    public List<UserDetailedQuestionDTO> getPaginatedQuestions( int page, int pageSize,
            String policyQuestionaryId) {
        log.info("Fetching paginated user questions - page: {}, size: {}, subsidiary: {}, character: {}, policyQuestionaryId: {}, userAssigned: {}",
                page, pageSize, policyQuestionaryId);
        int offset = (page-1) * pageSize;
        List<UserQuestion> userQuestions = userQuestionRepository.getPaginatedListOfQuestions( offset, pageSize, policyQuestionaryId);
        return userQuestions.stream()
                .map(this::mapToDetailedDTO)
                .collect(Collectors.toList());
    }

    public List<UserDetailedQuestionDTO> getPaginatedQuestions( int page, int pageSize,
                                                                String policyQuestionaryId, String userAssigned) {
        log.info("Fetching paginated user questions - page: {}, size: {}, subsidiary: {}, character: {}, policyQuestionaryId: {}, userAssigned: {}",
                page, pageSize, policyQuestionaryId);
        int offset = (page-1) * pageSize;
        SubsidiaryUser user = subsidiaryUserRepository.findByOpenIdUser(userAssigned)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userAssigned));
        List<UserQuestion> userQuestions = userQuestionRepository.getPaginatedListOfQuestions( offset, pageSize, policyQuestionaryId, user.getUserId().toString());
        return userQuestions.stream()
                .map(this::mapToDetailedDTO)
                .collect(Collectors.toList());
    }

    /**
     * Map UserQuestion entity to UserDetailedQuestionDTO with aggregated data
     */
    private UserDetailedQuestionDTO mapToDetailedDTO(UserQuestion userQuestion) {
        try {
            // Fetch question details if questionId exists
            QuestionDTO questionDTO = null;
            if (userQuestion.getQuestionId() != null) {
                try {
                    Question question = questionRepository.findById(userQuestion.getQuestionId().intValue())
                            .orElse(null);
                    if (question != null) {
                        questionDTO = mapQuestionToDTO(question);
                    }
                } catch (Exception e) {
                    log.warn("Could not fetch question details for questionId: {}", userQuestion.getQuestionId(), e);
                }
            }

            // Fetch user details if userAssigned exists
            UserDTO userDTO = null;
            if (userQuestion.getUserAssigned() != null) {
                try {
                    SubsidiaryUser subsidiaryUser = subsidiaryUserRepository.findById(userQuestion.getUserAssigned())
                            .orElse(null);
                    if (subsidiaryUser != null && !subsidiaryUser.isDeleted()) {
                        userDTO = mapSubsidiaryUserToDTO(subsidiaryUser);
                    } else {
                        log.debug("SubsidiaryUser not found or deleted for userId: {}", userQuestion.getUserAssigned());
                        // Create minimal UserDTO with just the ID if user not found
                        userDTO = UserDTO.builder()
                                .userId(userQuestion.getUserAssigned())
                                .name("Unknown User")
                                .build();
                    }
                } catch (Exception e) {
                    log.warn("Could not fetch user details for userId: {}", userQuestion.getUserAssigned(), e);
                    // Create minimal UserDTO on error
                    userDTO = UserDTO.builder()
                            .userId(userQuestion.getUserAssigned())
                            .name("Error Loading User")
                            .build();
                }
            }

            return UserDetailedQuestionDTO.builder()
                    .userQuestionId(userQuestion.getUserQuestionId())
                    .policyQuestionaryId(userQuestion.getPolicyQuestionary() != null ?
                            userQuestion.getPolicyQuestionary().getPolicyQuestionaryId().toString() : null)
                    .question(questionDTO)
                    .userAssigned(userDTO)
                    .questionStatus(userQuestion.getQuestionStatus())
                    .isDeleted(userQuestion.isDeleted())
                    .createdAt(userQuestion.getCreatedAt())
                    .updatedAt(userQuestion.getUpdatedAt())
                    .build();

        } catch (Exception e) {
            log.error("Error mapping UserQuestion to UserDetailedQuestionDTO for ID: {}",
                    userQuestion.getUserQuestionId(), e);

            // Return minimal DTO on error
            return UserDetailedQuestionDTO.builder()
                    .userQuestionId(userQuestion.getUserQuestionId())
                    .policyQuestionaryId(userQuestion.getPolicyQuestionary() != null ?
                            userQuestion.getPolicyQuestionary().getPolicyQuestionaryId().toString() : null)
                    .questionStatus(userQuestion.getQuestionStatus())
                    .isDeleted(userQuestion.isDeleted())
                    .createdAt(userQuestion.getCreatedAt())
                    .updatedAt(userQuestion.getUpdatedAt())
                    .build();
        }
    }

    /**
     * Map Question entity to QuestionDTO
     */
    private QuestionDTO mapQuestionToDTO(Question question) {
        return new QuestionDTO(
                question.getIdQuestion(),
                question.getQuestionType(),
                question.getDescription(),
                question.getQuestionText(),
                question.getQuestionJson(),
                question.getParentId() != null ? question.getParentId().intValue() : null,
                question.getDocumentId(),
                question.getQuestionaryId() != null ? question.getQuestionaryId().intValue() : null
        );
    }

    /**
     * Map SubsidiaryUser entity to UserDTO
     */
    private UserDTO mapSubsidiaryUserToDTO(SubsidiaryUser subsidiaryUser) {
        return UserDTO.builder()
                .userId(subsidiaryUser.getUserId())
                .openIdUser(subsidiaryUser.getOpenIdUser())
                .name(subsidiaryUser.getUserName())
                .lastName(subsidiaryUser.getUserLastName())
                .email(subsidiaryUser.getEmail())
                .status(subsidiaryUser.getUserStatus())
                .role(subsidiaryUser.getRole())
                .build();
    }

    /**
     * Get total count for pagination
     */
    public long getTotalCountForPagination(String policyQuestionaryId) {
        return userQuestionRepository.countPaginatedListOfQuestions(policyQuestionaryId);
    }

    public long getTotalCountForPagination(String policyQuestionaryId, String userAssigned) {
        log.info("Counting total user questions for PolicyQuestionary ID: {} and UserAssigned: {}", policyQuestionaryId, userAssigned);
        SubsidiaryUser user = subsidiaryUserRepository.findByOpenIdUser(userAssigned)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userAssigned));
        return userQuestionRepository.countPaginatedListOfQuestions(policyQuestionaryId, user.getUserId().toString());
    }
}
