package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.model.period.PolicyQuestionary;
import com.bancointernacional.plataformaBI.domain.model.period.UserQuestion;
import com.bancointernacional.plataformaBI.domain.model.template.Question;
import com.bancointernacional.plataformaBI.repository.period.UserQuestionRepository;
import com.bancointernacional.plataformaBI.repository.template.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class UserQuestionAsyncService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserQuestionRepository userQuestionRepository;

    /**
     * Asynchronously creates UserQuestion records for all questions related to a questionary
     * when a new PolicyQuestionary is created
     *
     * @param policyQuestionaryId The ID of the created PolicyQuestionary
     * @param questionaryId The ID of the questionary containing the questions
     * @return CompletableFuture with the count of created UserQuestion records
     */
    @Async
    public CompletableFuture<Integer> createUserQuestionsForPolicyQuestionary(
            PolicyQuestionary policyQuestionaryId,
            BigInteger questionaryId) {

        log.info("Starting async creation of UserQuestion records for PolicyQuestionary: {} and Questionary: {}",
                policyQuestionaryId, questionaryId);

        try {
            // Get all questions for the questionary
            List<Question> questions = questionRepository.findActiveByQuestionaryId(questionaryId);

            if (questions.isEmpty()) {
                log.warn("No questions found for questionary ID: {}", questionaryId);
                return CompletableFuture.completedFuture(0);
            }

            int createdCount = 0;

            for (Question question : questions) {
                try {
                    int exists = userQuestionRepository.existsByPolicyQuestionaryIdAndQuestionId(
                            policyQuestionaryId.getPolicyQuestionaryId().toString(),
                            BigInteger.valueOf(question.getIdQuestion())
                    );

                    if (exists==0) {
                        UserQuestion userQuestion = UserQuestion.builder()
                                .policyQuestionary(policyQuestionaryId)
                                .questionId(BigInteger.valueOf(question.getIdQuestion()))
                                .userAssigned(null) // Will be assigned later when user takes the questionnaire
                                .questionStatus("ACTIVE") // Initially not answered
                                .isDeleted(false)
                                .createdAt(LocalDate.now())
                                .updatedAt(LocalDate.now())
                                .build();

                        userQuestionRepository.save(userQuestion);
                        createdCount++;

                        log.debug("Created UserQuestion for Question ID: {} and PolicyQuestionary ID: {}",
                                question.getIdQuestion(), policyQuestionaryId);
                    } else {
                        log.debug("UserQuestion already exists for Question ID: {} and PolicyQuestionary ID: {}",
                                question.getIdQuestion(), policyQuestionaryId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error creating UserQuestion for Question ID: {} and PolicyQuestionary ID: {}. Error: {}",
                            question.getIdQuestion(), policyQuestionaryId, e.getMessage());
                }
            }

            log.info("Successfully created {} UserQuestion records for PolicyQuestionary: {} from {} total questions",
                    createdCount, policyQuestionaryId, questions.size());

            return CompletableFuture.completedFuture(createdCount);

        } catch (Exception e) {
            log.error("Error in async UserQuestion creation for PolicyQuestionary: {} and Questionary: {}. Error: {}",
                    policyQuestionaryId, questionaryId, e.getMessage(), e);
            return CompletableFuture.completedFuture(0);
        }
    }

    /**
     * Get the count of UserQuestion records for a PolicyQuestionary
     */
    public int getUserQuestionCount(UUID policyQuestionaryId) {
        try {
            List<UserQuestion> userQuestions = userQuestionRepository.findByPolicyQuestionaryId(policyQuestionaryId.toString());
            return userQuestions.size();
        } catch (Exception e) {
            log.error("Error getting UserQuestion count for PolicyQuestionary: {}. Error: {}",
                    policyQuestionaryId, e.getMessage());
            return 0;
        }
    }
}
