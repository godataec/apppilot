package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.dto.template.questionary.response.QuestionaryDTO;
import com.bancointernacional.plataformaBI.domain.model.template.Questionary;
import com.bancointernacional.plataformaBI.repository.period.PolicyQuestionaryRepository;
import com.bancointernacional.plataformaBI.repository.template.QuestionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionaryService {

    @Autowired
    private QuestionaryRepository questionaryRepository;

    @Autowired
    private PolicyQuestionaryRepository policyQuestionaryRepository;

    /**
     * Get available questionnaires for a specific policy that are not yet assigned to a process policy
     *
     * @param policyId The ID of the policy
     * @param processPolicyId The ID of the process policy
     * @return List of available questionnaires not assigned to the process policy
     */
    public List<QuestionaryDTO> getAvailableQuestionnariesForPolicy(Long policyId, UUID processPolicyId) {
        BigInteger policyIdBigInt = BigInteger.valueOf(policyId);
        List<Questionary> allQuestionnaires = questionaryRepository.findActiveByPolicyId(policyIdBigInt);
        List<BigInteger> assignedQuestionaryIds = policyQuestionaryRepository
                .findAssignedQuestionaryIdsByProcessPolicyId(processPolicyId.toString());
        List<Questionary> availableQuestionnaires = allQuestionnaires.stream()
                .filter(questionary -> !assignedQuestionaryIds.contains(BigInteger.valueOf(questionary.getQuestionaryId())))
                .collect(Collectors.toList());
        return availableQuestionnaires.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert Questionary entity to QuestionaryDTO
     */
    private QuestionaryDTO convertToDTO(Questionary questionary) {
        QuestionaryDTO dto = new QuestionaryDTO();
        dto.setIdquestionnaire(questionary.getQuestionaryId());
        dto.setNamequestionnaire(questionary.getQuestionaryName());
        dto.setDescription(questionary.getDescription());
        dto.setCreatedAt(questionary.getCreatedAt());
        dto.setStatus(questionary.getQuestionaryStatus());
        dto.setSourceFile(questionary.getSourceFile());
        dto.setIdInsurance(questionary.getInsurancePolicy() != null ? questionary.getInsurancePolicy().longValue() : null);
        dto.setTotalQuestions(questionary.getTotalquestion());
        return dto;
    }

    /**
     * Get all questionnaires assigned to a specific policy
     */
    public List<QuestionaryDTO> getQuestionariesByPolicyId(Long policyId) {
        BigInteger policyIdBigInt = BigInteger.valueOf(policyId);
        List<Questionary> questionnaires = questionaryRepository.findByPolicyId(policyIdBigInt);

        return questionnaires.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get total number of questionnaires assigned to a policy
     */
    public Double getQuestionariesAssignedToPolicy(Long policyId) {
        BigInteger policyIdBigInt = BigInteger.valueOf(policyId);
        List<Questionary> questionnaires = questionaryRepository.findByPolicyId(policyIdBigInt);
        return (double) questionnaires.size();
    }

    /**
     * Get all active questionnaires (not soft deleted)
     */
    public List<QuestionaryDTO> getAllActiveQuestionnaires() {
        List<Questionary> allQuestionnaires = questionaryRepository.findAll();
        return allQuestionnaires.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
