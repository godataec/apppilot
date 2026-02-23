package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.request.CreateProcessQuestionaryDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.request.UpdateProcessQuestionaryDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.response.PolicyQuestionaryDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processQuestionary.response.PolicyQuestionaryDetailDTO;
import com.bancointernacional.plataformaBI.domain.model.period.PolicyQuestionary;
import com.bancointernacional.plataformaBI.domain.model.period.Process;
import com.bancointernacional.plataformaBI.domain.model.period.ProcessPolicy;
import com.bancointernacional.plataformaBI.domain.model.settings.SubsidiaryUser;
import com.bancointernacional.plataformaBI.domain.model.template.Policy;
import com.bancointernacional.plataformaBI.domain.model.template.Questionary;
import com.bancointernacional.plataformaBI.domain.projections.QuestionnaireProjection;
import com.bancointernacional.plataformaBI.exception.ResourceNotFoundException;
import com.bancointernacional.plataformaBI.mapper.PolicyQuestionaryMapper;
import com.bancointernacional.plataformaBI.repository.period.*;
import com.bancointernacional.plataformaBI.repository.settings.SubsidiaryUserRepository;
import com.bancointernacional.plataformaBI.repository.template.QuestionaryRepository;
import com.bancointernacional.plataformaBI.repository.template.PolicyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PolicyQuestionaryService {

    @Autowired
    private PolicyQuestionaryRepository policyQuestionaryRepository;

    @Autowired
    private QuestionaryRepository questionaryRepository;

    @Autowired
    private PolicyQuestionaryMapper policyQuestionaryMapper;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ProcessPolicyRepository processPolicyRepository;

    @Autowired
    private UserQuestionAsyncService userQuestionAsyncService;
    @Autowired
    private UserQuestionRepository userQuestionRepository;
    @Autowired
    private UserAnswerRepository userAnswerRepository;
    @Autowired
    private SubsidiaryUserRepository subsidiaryUserRepository;

    /**
     * Create a new assignment of questionnaire to a process policy
     */
    public PolicyQuestionaryDTO assignQuestionaryToProcess(CreateProcessQuestionaryDTO createDTO) {
        Optional<Questionary> questionary = questionaryRepository.findById(createDTO.getQuestionaryId().intValue());
        if (!questionary.isPresent()) {
            throw new RuntimeException("Questionary with ID " + createDTO.getQuestionaryId() + " not found");
        }
        Optional<ProcessPolicy> processPolicy = processPolicyRepository.findProcessPolicyByProcessPolicyId(createDTO.getProcessPolicyId().toString());
        if (!processPolicy.isPresent()) {
            throw new RuntimeException("Process Policy with ID " + createDTO.getProcessPolicyId() + " not found");
        }
        List<PolicyQuestionary> existingAssignments = policyQuestionaryRepository
                .findByProcessPolicyId(createDTO.getProcessPolicyId().toString());

        boolean alreadyAssigned = existingAssignments.stream()
                .anyMatch(pq -> pq.getQuestionaryId().equals(createDTO.getQuestionaryId()));

        if (alreadyAssigned) {
            throw new RuntimeException("Questionary is already assigned to this process policy");
        }
        PolicyQuestionary policyQuestionary = policyQuestionaryMapper.toEntity(createDTO);
        policyQuestionary.setProcessPolicy(processPolicy.get());

        PolicyQuestionary savedPolicyQuestionary = policyQuestionaryRepository.save(policyQuestionary);
        userQuestionAsyncService.createUserQuestionsForPolicyQuestionary(
                savedPolicyQuestionary,
                createDTO.getQuestionaryId()
        ).thenAccept(createdCount -> {
            log.info("Async process completed: Created {} UserQuestion records for PolicyQuestionary {}",
                    createdCount, savedPolicyQuestionary.getPolicyQuestionaryId());
        }).exceptionally(throwable -> {
            log.error("Error in async UserQuestion creation: {}", throwable.getMessage(), throwable);
            return null;
        });
        PolicyQuestionaryDTO responseDTO = policyQuestionaryMapper.toDTO(savedPolicyQuestionary);
        responseDTO.setQuestionaryName(questionary.get().getQuestionaryName());

        return responseDTO;
    }

    /**
     * Get all questionnaires assigned to a specific process policy
     */
    public List<PolicyQuestionaryDTO> getQuestionariesByProcessPolicy(UUID processPolicyId) {
        List<PolicyQuestionary> policyQuestionaries = policyQuestionaryRepository
                .findByProcessPolicyId(processPolicyId.toString());

        return policyQuestionaries.stream()
                .map(pq -> {
                    PolicyQuestionaryDTO dto = policyQuestionaryMapper.toDTO(pq);
                    // Get questionnaire name
                    Optional<Questionary> questionary = questionaryRepository.findById(pq.getQuestionaryId().intValue());
                    if (questionary.isPresent()) {
                        dto.setQuestionaryName(questionary.get().getQuestionaryName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Remove a questionnaire assignment from a process policy
     */
    public void removeQuestionaryFromProcess(UUID policyQuestionaryId) {
        Optional<PolicyQuestionary> policyQuestionary = policyQuestionaryRepository.findById(policyQuestionaryId);
        if (!policyQuestionary.isPresent()) {
            throw new RuntimeException("Policy Questionary assignment not found");
        }

        PolicyQuestionary entity = policyQuestionary.get();
        entity.setDeleted(true);
        policyQuestionaryRepository.save(entity);
    }

    /**
     * Update the status of a questionnaire assignment
     */
    public PolicyQuestionaryDTO updateQuestionaryStatus(UUID policyQuestionaryId, String newStatus) {
        Optional<PolicyQuestionary> policyQuestionary = policyQuestionaryRepository.findById(policyQuestionaryId);
        if (!policyQuestionary.isPresent()) {
            throw new RuntimeException("Policy Questionary assignment not found");
        }

        PolicyQuestionary entity = policyQuestionary.get();
        entity.setPolicyQuestionaryStatus(newStatus);
        PolicyQuestionary savedEntity = policyQuestionaryRepository.save(entity);

        PolicyQuestionaryDTO responseDTO = policyQuestionaryMapper.toDTO(savedEntity);

        Optional<Questionary> questionary = questionaryRepository.findById(entity.getQuestionaryId().intValue());
        if (questionary.isPresent()) {
            responseDTO.setQuestionaryName(questionary.get().getQuestionaryName());
        }

        return responseDTO;
    }

    /**
     * Update a questionnaire assignment (comprehensive update)
     */
    public PolicyQuestionaryDTO updateProcessQuestionary(UpdateProcessQuestionaryDTO updateDTO) {
        Optional<PolicyQuestionary> policyQuestionary = policyQuestionaryRepository.findById(updateDTO.getPolicyQuestionaryId());
        if (!policyQuestionary.isPresent()) {
            throw new RuntimeException("Policy Questionary assignment not found");
        }

        PolicyQuestionary entity = policyQuestionary.get();

        if (updateDTO.getStartDate() != null) {
            entity.setStartDate(updateDTO.getStartDate());
        }
        if (updateDTO.getEndDate() != null) {
            entity.setEndDate(updateDTO.getEndDate());
        }
        if (updateDTO.getStatus() != null && !updateDTO.getStatus().trim().isEmpty()) {
            entity.setPolicyQuestionaryStatus(updateDTO.getStatus());
        }

        entity.setUpdatedAt(java.time.LocalDate.now());
        PolicyQuestionary savedEntity = policyQuestionaryRepository.save(entity);

        PolicyQuestionaryDTO responseDTO = policyQuestionaryMapper.toDTO(savedEntity);

        Optional<Questionary> questionary = questionaryRepository.findById(entity.getQuestionaryId().intValue());
        if (questionary.isPresent()) {
            responseDTO.setQuestionaryName(questionary.get().getQuestionaryName());
        }

        return responseDTO;
    }

    /**
     * Get detailed questionnaires assigned to a specific process policy with comprehensive information
     */
    public List<PolicyQuestionaryDetailDTO> getDetailedQuestionariesByProcessPolicy(UUID processPolicyId) {
        List<PolicyQuestionary> policyQuestionaries = policyQuestionaryRepository
                .findByProcessPolicyId(processPolicyId.toString());

        return policyQuestionaries.stream()
                .map(this::convertToDetailDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert PolicyQuestionary to ProcessQuestionaryDetailDTO with all related information
     */
    private PolicyQuestionaryDetailDTO convertToDetailDTO(PolicyQuestionary policyQuestionary) {
        PolicyQuestionaryDetailDTO dto = new PolicyQuestionaryDetailDTO();
        dto.setIdQuest(policyQuestionary.getPolicyQuestionaryId());
        dto.setDateBeginQuest(policyQuestionary.getStartDate());
        dto.setDateEndQuest(policyQuestionary.getEndDate());
        dto.setStatus(policyQuestionary.getPolicyQuestionaryStatus());
        dto.setIdQuestionnaire(policyQuestionary.getQuestionaryId().intValue());

        Optional<Questionary> questionary = questionaryRepository.findById(policyQuestionary.getQuestionaryId().intValue());
        if (questionary.isPresent()) {
            dto.setNameQuest(questionary.get().getQuestionaryName());
            dto.setIdPolicy(questionary.get().getInsurancePolicy().longValue());

            Optional<Policy> policy = policyRepository.findById(questionary.get().getInsurancePolicy().longValue());
            if (policy.isPresent()) {
                dto.setNameInsurance(policy.get().getPolicyName());
            }
        }

        if (policyQuestionary.getProcessPolicy() != null && policyQuestionary.getProcessPolicy().getProcess() != null) {
            Process process = policyQuestionary.getProcessPolicy().getProcess();
            dto.setIdProcess(process.getProcessId());
            dto.setProcessDescription(process.getProcessDescription());
        }

        dto.setPercentage(null);

        return dto;
    }

    /**
     * Get UserQuestion count for a PolicyQuestionary
     */
    public int getUserQuestionCount(UUID policyQuestionaryId) {
        return userQuestionAsyncService.getUserQuestionCount(policyQuestionaryId);
    }

    /**
     * Soft delete policy questionary by setting is_deleted = 1
     */
    @Transactional
    public void softDeletePolicyQuestionary(String policyQuestionaryId) {
        log.info("Soft deleting PolicyQuestionary with ID: {}", policyQuestionaryId);
        Optional<PolicyQuestionary> policyQuestionaryOpt = policyQuestionaryRepository.findById(UUID.fromString(policyQuestionaryId));
        if (!policyQuestionaryOpt.isPresent()) {
            throw new ResourceNotFoundException("PolicyQuestionary not found with ID: " + policyQuestionaryId);
        }
        PolicyQuestionary policyQuestionary = policyQuestionaryOpt.get();
        if (policyQuestionary.isDeleted()) {
            throw new IllegalStateException("PolicyQuestionary is already deleted");
        }
        policyQuestionaryRepository.softDeleteById(policyQuestionaryId);
        userQuestionRepository.softDeleteByPolicyQuestionaryId(policyQuestionaryId);
        userAnswerRepository.softDeleteByPolicyQuestionaryId(policyQuestionaryId);
        log.info("Successfully soft deleted PolicyQuestionary with ID: {}", policyQuestionaryId);
    }

    /**
     * Get policy questionnaires with filtering capabilities
     */
    public List<PolicyQuestionaryDTO> getPolicyQuestionariesFilterBy(Long subsidiary, String character, int page, int pageSize, String processPolicyId) {
        int offset = (page - 1) * pageSize;
        List<Object[]> resultSet = policyQuestionaryRepository.findPolicyQuestionaryFilteredData(subsidiary, character, offset, pageSize, processPolicyId);
        return convertToDTO(resultSet);
    }

    /**
     * Get policy questionnaires count without pagination for filtering
     */
    public Integer getPolicyQuestionariesFilterByWithoutPages(Long subsidiary, String character, String processPolicyId) {
        return policyQuestionaryRepository.countPolicyQuestionaryFilteredData(subsidiary, character,processPolicyId);
    }

    /**
     * Converts the query result set (Object[]) to PolicyQuestionaryDTO list
     * Query returns: policy_questionary_id, questionary_name, start_date, end_date, process_id,
     * process_policy_id, policy_questionary_status, questionary_description, process_description, policy_name
     */
    private List<PolicyQuestionaryDTO> convertToDTO(List<Object[]> resultSet) {
        List<PolicyQuestionaryDTO> policyQuestionaryDTOs = new ArrayList<>();
        if (resultSet == null || resultSet.isEmpty()) {
            return policyQuestionaryDTOs;
        }
        for (Object[] row : resultSet) {
            PolicyQuestionaryDTO dto = new PolicyQuestionaryDTO();
            dto.setPolicyQuestionaryId(row[0] != null ? UUID.fromString(row[0].toString()) : null);
            dto.setQuestionaryName(row[1] != null ? row[1].toString() : null);
            dto.setStartDate(row[2] != null ? convertToLocalDate(row[2]) : null);
            dto.setEndDate(row[3] != null ? convertToLocalDate(row[3]) : null);
            dto.setProcessId(row[4] != null ? row[4].toString() : null);
            dto.setProcessPolicyId(row[5] != null ?row[5].toString() : null);
            dto.setStatus(row[6] != null ? row[6].toString() : null);
            dto.setQuestionaryDescription(row[7] != null ? row[7].toString() : null);
            dto.setProcessDescription(row[8] != null ? row[8].toString() : null);
            dto.setPolicyName(row[9] != null ? row[9].toString() : null);
            dto.setQuestionaryId(row[10] != null ? BigInteger.valueOf((int)row[10]) : null);
            dto.setProcessStartDate(row[11] != null ? convertToLocalDate(row[11]) : null);
            dto.setProcessEndDate(row[12] != null ? convertToLocalDate(row[12]) : null);
            long solvedQuestions = userQuestionRepository.countQuestionaryProgress(dto.getPolicyQuestionaryId().toString());
            dto.setPercentage(solvedQuestions*100/(int)row[13]);
            policyQuestionaryDTOs.add(dto);
        }
        return policyQuestionaryDTOs;
    }

    private LocalDate convertToLocalDate(Object dateObj) {
        if (dateObj == null) return null;

        if (dateObj instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) dateObj).toLocalDateTime().toLocalDate();
        } else if (dateObj instanceof java.sql.Date) {
            return ((java.sql.Date) dateObj).toLocalDate();
        } else if (dateObj instanceof LocalDate) {
            return (LocalDate) dateObj;
        } else if (dateObj instanceof java.util.Date) {
            return new java.sql.Date(((java.util.Date) dateObj).getTime()).toLocalDate();
        }
        try {
            return LocalDate.parse(dateObj.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert " + dateObj.getClass() + " to LocalDate: " + dateObj);
        }
    }

    public List<PolicyQuestionaryDetailDTO> getPolicyQuestionariesByUserId(UUID userUUID) {
        Optional<SubsidiaryUser> optionalUser = subsidiaryUserRepository.findByOpenIdUser(userUUID.toString());
        if(optionalUser.isPresent()){
            List<PolicyQuestionary> pendingQuestionnaires = policyQuestionaryRepository.findAssignedQuestionariesByUserId(optionalUser.get().getUserId().toString());
            return pendingQuestionnaires.stream()
                    .map(this::convertToDetailDTO)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
