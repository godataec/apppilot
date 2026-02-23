package com.bancointernacional.plataformaBI.service;


import com.bancointernacional.plataformaBI.domain.dto.period.process.response.ProcessDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.request.CreateProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.request.UpdateProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.response.ProcessDetailPolicyDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.response.ProcessPolicyDTO;
import com.bancointernacional.plataformaBI.domain.model.period.PolicyQuestionary;
import com.bancointernacional.plataformaBI.domain.model.period.Process;
import com.bancointernacional.plataformaBI.domain.model.period.ProcessPolicy;
import com.bancointernacional.plataformaBI.domain.model.template.Policy;
import com.bancointernacional.plataformaBI.enums.AppStatus;
import com.bancointernacional.plataformaBI.mapper.ProcessPolicyMapper;
import com.bancointernacional.plataformaBI.repository.period.PolicyQuestionaryRepository;
import com.bancointernacional.plataformaBI.repository.period.ProcessPolicyRepository;
import com.bancointernacional.plataformaBI.repository.period.ProcessRepository;
import com.bancointernacional.plataformaBI.repository.period.UserQuestionRepository;
import com.bancointernacional.plataformaBI.repository.template.PolicyRepository;
import com.bancointernacional.plataformaBI.repository.template.QuestionaryRepository;
import com.bancointernacional.plataformaBI.service.usecase.ProcessPolicyUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProcessPolicyService implements ProcessPolicyUseCase {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ProcessPolicyRepository processPolicyRepository;

    @Autowired
    private ProcessPolicyMapper processPolicyMapper;
    @Autowired
    private UserQuestionRepository userQuestionRepository;
    @Autowired
    private PolicyQuestionaryRepository policyQuestionaryRepository;
    @Autowired
    private PolicyQuestionaryService policyQuestionaryService;
    @Autowired
    private QuestionaryRepository questionaryRepository;

    @Override
    public ProcessPolicyDTO saveProcessPolicy(CreateProcessInsurancePolicyDTO processPolicy) {

        Optional<Process> processFound = processRepository.findActiveById(processPolicy.getProcessId().toString());
        Optional<Policy> policyFound = policyRepository.findById(processPolicy.getInsurancePolicyId().longValue());
        log.debug("Policy found: {}", policyFound.isPresent());

        if (processFound.isPresent() && policyFound.isPresent()) {
            log.info("Both entities found, creating ProcessPolicy for Process ID: {} and Policy ID: {}",
                    processPolicy.getProcessId(), processPolicy.getInsurancePolicyId());
            ProcessPolicy processPolicyForCreate = new ProcessPolicy();
            processPolicyForCreate.setInsurancePolicy(processPolicy.getInsurancePolicyId());
            processPolicyForCreate.setProcess(processFound.get());
            processPolicyForCreate.setProcessPolicyStatus(processPolicy.getProcessInsurancePolicyStatus());
            processPolicyForCreate.setDateBegin(processPolicy.getProcessInsurancePolicyBeingDate());
            processPolicyForCreate.setDateEnd(processPolicy.getProcessInsurancePolicyBeingDate());
            ProcessPolicy processPolicyCreated = processPolicyRepository.save(processPolicyForCreate);
            return processPolicyMapper.toDTO(processPolicyCreated);
        }
        return null;
    }

    @Override
    public List<ProcessPolicyDTO> getProcessPoliciesFilterBy(Long subsidiary, String character, int page, int pageSize, String dateBegin, String dateEnd, BigInteger year) {
        int offset = (page - 1) * pageSize;
        List<Object[]> ResultSet = null;
        if (Objects.nonNull(year)) {
            ResultSet = processPolicyRepository.findProcessPolicyAgregatedDataByFilters(subsidiary, character, offset, pageSize, dateBegin, dateEnd, year);
        } else {
            ResultSet = processPolicyRepository.findProcessPolicyAgregatedDataShirnk(subsidiary, character, offset, pageSize, dateBegin, dateEnd, year);
        }
        List<ProcessPolicyDTO> resultList = convertToDTO(ResultSet);
        /*TODO calculate total value of completed answers*/
        /*for(ProcessPolicyDTO processPolicyDTO : resultList) {
            Double totalValueQuestionaries = questionnaireService.getQuestionariesAssignedToPolicy(processPolicyDTO.getIdInsurance().longValue());
            List<ProcessQuestionary> processQuestionaries = policyQuestionaryRepository.findByProcessQuestionaryByPolicyIdAAndStatusComplete(processPolicyDTO.getIdPolicy());
            processPolicyDTO.setPercentage((processQuestionaries.size() * 100) / totalValueQuestionaries);
        }*/
        return resultList;
    }

    @Override
    public Integer getProcessPoliciesFilterByWithoutPages(Long subsidiary, String character, String dateBegin, String dateEnd) {
        return 0;
    }

    /**
     * Converts the query result set (Object[]) to ProcessPolicyDTO list
     * Query returns: process_policy_id, policy_name, start_date, end_date, process_id, policy_id, status, description, process_description
     *
     * @param resultSet List of Object arrays from the database query
     * @return List of ProcessPolicyDTO objects
     */
    private List<ProcessPolicyDTO> convertToDTO(List<Object[]> resultSet) {
        List<ProcessPolicyDTO> processPolicyDTOs = new ArrayList<>();

        if (resultSet == null || resultSet.isEmpty()) {
            return processPolicyDTOs;
        }

        for (Object[] row : resultSet) {
            ProcessPolicyDTO dto = new ProcessPolicyDTO();

            // Map fields based on query column order
            // row[0] = process_policy_id (UUID as String)
            dto.setIdPolicy(row[0] != null ? UUID.fromString(row[0].toString()) : null);
            dto.setNamePolicy(row[1] != null ? row[1].toString() : null);
            dto.setDateBegin(row[2] != null ? convertToLocalDate(row[2]) : null);
            dto.setDateEnd(row[3] != null ? convertToLocalDate(row[3]) : null);
            dto.setIdProcess(row[4] != null ? UUID.fromString(row[4].toString()) : null);
            // row[5] = policy_id (Long)
            dto.setIdInsurance(row[5] != null ? ((Number) row[5]).longValue() : null);
            dto.setStatus(row[6] != null ? row[6].toString() : null);
            dto.setDescription(row[7] != null ? row[7].toString() : null);
            dto.setDescriptionProcess(row[8] != null ? row[8].toString() : null);

            // Initialize percentage to 0.0 as it will be calculated later
            dto.setPercentage(0.0);

            processPolicyDTOs.add(dto);
        }

        return processPolicyDTOs;
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

        // Fallback - try to parse as string
        try {
            return LocalDate.parse(dateObj.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert " + dateObj.getClass() + " to LocalDate: " + dateObj);
        }
    }

    @Override
    public void softDeleteProcessPolicy(UUID processPolicyId) {
        log.info("Soft deleting ProcessPolicy with ID: {}", processPolicyId);

        try {
            Optional<ProcessPolicy> processPolicyOpt = processPolicyRepository.findById(processPolicyId);
            if (!processPolicyOpt.isPresent()) {
                throw new RuntimeException("ProcessPolicy not found with ID: " + processPolicyId);
            }
            ProcessPolicy processPolicy = processPolicyOpt.get();
            if (processPolicy.isDeleted()) {
                throw new IllegalStateException("ProcessPolicy is already deleted");
            }
            processPolicy.setDeleted(true);
            processPolicy.setUpdatedAt(LocalDate.now());
            processPolicyRepository.save(processPolicy);
            List<PolicyQuestionary> policyQuestionaries = policyQuestionaryRepository.findByProcessPolicyId(processPolicyId.toString());
            for (PolicyQuestionary policyQuestionary : policyQuestionaries) {
                policyQuestionaryService.softDeletePolicyQuestionary(policyQuestionary.getPolicyQuestionaryId().toString());
            }
            log.info("Successfully soft deleted ProcessPolicy with ID: {}", processPolicyId);

        } catch (Exception e) {
            log.error("Error soft deleting ProcessPolicy with ID: {}", processPolicyId, e);
            throw new RuntimeException("Error deleting ProcessPolicy: " + e.getMessage());
        }
    }

    /**
     * Get process policies with detailed process information
     */
    public List<ProcessDetailPolicyDTO> getProcessPoliciesDetailFilterBy(Long subsidiary, String character, int page, int pageSize, String dateBegin, String dateEnd, BigInteger year) {
        log.info("Fetching detailed process policies with filters - subsidiary: {}, character: {}, page: {}, pageSize: {}, dateBegin: {}, dateEnd: {}, year: {}",
                subsidiary, character, page, pageSize, dateBegin, dateEnd, year);
        int offset = (page - 1) * pageSize;
        List<Object[]> resultSet = null;
        if (Objects.nonNull(year)) {
            resultSet = processPolicyRepository.findProcessPolicyAgregatedDataByFilters(subsidiary, character, offset, pageSize, dateBegin, dateEnd, year);
        } else {
            resultSet = processPolicyRepository.findProcessPolicyAgregatedDataShirnk(subsidiary, character, offset, pageSize, dateBegin, dateEnd, year);
        }
        List<ProcessDetailPolicyDTO> resultList = convertToDetailDTO(resultSet);
        log.info("Successfully fetched {} detailed process policies", resultList.size());
        return resultList;
    }

    /**
     * Converts the query result set (Object[]) to ProcessDetailPolicyDTO list with process data
     * Query returns: process_policy_id, policy_name, start_date, end_date, process_id, policy_id, status, description, process_description
     *
     * @param resultSet List of Object arrays from the database query
     * @return List of ProcessDetailPolicyDTO objects with process information
     */
    private List<ProcessDetailPolicyDTO> convertToDetailDTO(List<Object[]> resultSet) {
        List<ProcessDetailPolicyDTO> processDetailPolicyDTOs = new ArrayList<>();
        if (resultSet == null || resultSet.isEmpty()) {
            return processDetailPolicyDTOs;
        }
        for (Object[] row : resultSet) {
            ProcessDetailPolicyDTO dto = ProcessDetailPolicyDTO.builder()
                    .idPolicy(row[0] != null ? UUID.fromString(row[0].toString()) : null)
                    .namePolicy(row[1] != null ? row[1].toString() : null)
                    .dateBegin(row[2] != null ? convertToLocalDate(row[2]) : null)
                    .dateEnd(row[3] != null ? convertToLocalDate(row[3]) : null)
                    .idProcess(row[4] != null ? UUID.fromString(row[4].toString()) : null)
                    .idInsurance(row[5] != null ? ((Number) row[5]).longValue() : null)
                    .status(row[6] != null ? row[6].toString() : null)
                    .description(row[7] != null ? row[7].toString() : null)
                    .descriptionProcess(row[8] != null ? row[8].toString() : null)
                    .percentage(0L)
                    .build();

            if (dto.getIdProcess() != null) {
                try {
                    Optional<Process> processOpt = processRepository.findActiveById(dto.getIdProcess().toString());
                    if (processOpt.isPresent()) {
                        Process process = processOpt.get();
                        ProcessDTO processDTO = new ProcessDTO(
                                process.getProcessId(),
                                process.getProcessDescription(),
                                process.getStartDate().toLocalDate(),
                                process.getEndDate().toLocalDate(),
                                process.getProcessStatus(),
                                process.isReopened(),
                                0L
                        );
                        dto.setProcess(processDTO);
                    } else {
                        log.warn("Process not found for ID: {}", dto.getIdProcess());
                        // Set minimal process data if process not found
                        ProcessDTO processDTO = new ProcessDTO(
                                dto.getIdProcess(),
                                "Process Not Found",
                                null,
                                null,
                                "UNKNOWN",
                                false,
                                0L
                        );
                        dto.setProcess(processDTO);
                    }
                } catch (Exception e) {
                    log.error("Error fetching process data for ID: {}", dto.getIdProcess(), e);
                    ProcessDTO processDTO = new ProcessDTO(
                            dto.getIdProcess(),
                            "Error Loading Process",
                            null,
                            null,
                            "ERROR",
                            false,
                            0L
                    );
                    dto.setProcess(processDTO);
                }
            }
            dto.setPercentage(calculateProcessPolicyPercentage(dto.getIdPolicy()));
            processDetailPolicyDTOs.add(dto);
        }
        return processDetailPolicyDTOs;
    }

    private Long calculateProcessPolicyPercentage(UUID processPolicyId) {
        log.info("Calculating percentage for ProcessPolicy with ID: {}", processPolicyId);
        try {
            List<PolicyQuestionary> policyQuestionaries = policyQuestionaryRepository.findByProcessPolicyId(processPolicyId.toString());
            if (policyQuestionaries.isEmpty()) {
                return 0L;
            }
            long totalQuestionaries = policyQuestionaries.size();
            long completedQuestionaries = policyQuestionaries.stream()
                    .filter(policy -> AppStatus.RESOLVED.name().equals(policy.getPolicyQuestionaryStatus()) ||
                            AppStatus.CLOSED.name().equals(policy.getPolicyQuestionaryStatus()))
                    .count();
            return (completedQuestionaries * 100) / totalQuestionaries;
        } catch (Exception e) {
            log.error("Error calculating percentage for ProcessPolicy ID: {}", processPolicyId, e);
            throw new RuntimeException("Error calculating percentage: " + e.getMessage());
        }
    }

    @Override
    public ProcessPolicyDTO updateProcessPolicy(UpdateProcessInsurancePolicyDTO updateProcessPolicy) {
        log.info("Updating ProcessPolicy with ID: {}", updateProcessPolicy.getProcessPolicyId());

        try {
            Optional<ProcessPolicy> processPolicyOpt = processPolicyRepository.findById(updateProcessPolicy.getProcessPolicyId());
            if (!processPolicyOpt.isPresent()) {
                throw new RuntimeException("ProcessPolicy not found with ID: " + updateProcessPolicy.getProcessPolicyId());
            }

            ProcessPolicy processPolicy = processPolicyOpt.get();
            if (processPolicy.isDeleted()) {
                throw new IllegalStateException("Cannot update a deleted ProcessPolicy");
            }
            if (updateProcessPolicy.getProcessId() != null) {
                Optional<Process> processFound = processRepository.findActiveById(updateProcessPolicy.getProcessId().toString());
                if (!processFound.isPresent()) {
                    throw new RuntimeException("Process not found with ID: " + updateProcessPolicy.getProcessId());
                }
                processPolicy.setProcess(processFound.get());
            }
            if (updateProcessPolicy.getInsurancePolicyId() != null) {
                Optional<Policy> policyFound = policyRepository.findById(updateProcessPolicy.getInsurancePolicyId().longValue());
                if (!policyFound.isPresent()) {
                    throw new RuntimeException("Insurance Policy not found with ID: " + updateProcessPolicy.getInsurancePolicyId());
                }
                processPolicy.setInsurancePolicy(updateProcessPolicy.getInsurancePolicyId());
            }
            if (updateProcessPolicy.getProcessInsurancePolicyStatus() != null) {
                processPolicy.setProcessPolicyStatus(updateProcessPolicy.getProcessInsurancePolicyStatus());
            }
            if (updateProcessPolicy.getProcessInsurancePolicyBeingDate() != null) {
                processPolicy.setDateBegin(updateProcessPolicy.getProcessInsurancePolicyBeingDate());
            }
            if (updateProcessPolicy.getProcessInsurancePolicyEndDate() != null) {
                processPolicy.setDateEnd(updateProcessPolicy.getProcessInsurancePolicyEndDate());
            }
            processPolicy.setUpdatedAt(LocalDate.now());
            ProcessPolicy updatedProcessPolicy = processPolicyRepository.save(processPolicy);
            log.info("Successfully updated ProcessPolicy with ID: {}", updateProcessPolicy.getProcessPolicyId());
            return processPolicyMapper.toDTO(updatedProcessPolicy);
        } catch (Exception e) {
            log.error("Error updating ProcessPolicy with ID: {}", updateProcessPolicy.getProcessPolicyId(), e);
            throw new RuntimeException("Error updating ProcessPolicy: " + e.getMessage());
        }
    }

@Override
public List<ProcessPolicyDTO> updateProcessPolicies(List<UpdateProcessInsurancePolicyDTO> updateProcessPolicies) {
    log.info("Updating {} ProcessPolicies", updateProcessPolicies.size());

    List<ProcessPolicyDTO> updatedPolicies = new ArrayList<>();
    List<String> errors = new ArrayList<>();

    for (UpdateProcessInsurancePolicyDTO updateProcessPolicy : updateProcessPolicies) {
        try {
            ProcessPolicyDTO updatedPolicy = updateProcessPolicy(updateProcessPolicy);
            updatedPolicies.add(updatedPolicy);
        } catch (Exception e) {
            String errorMessage = String.format("Failed to update ProcessPolicy with ID %s: %s",
                    updateProcessPolicy.getProcessPolicyId(), e.getMessage());
            errors.add(errorMessage);
            log.error(errorMessage, e);
        }
    }

    if (!errors.isEmpty()) {
        String allErrors = String.join("; ", errors);
        log.warn("Some ProcessPolicies failed to update: {}", allErrors);
        throw new RuntimeException("Partial update failure: " + allErrors);
    }

    log.info("Successfully updated all {} ProcessPolicies", updatedPolicies.size());
    return updatedPolicies;
}

}


