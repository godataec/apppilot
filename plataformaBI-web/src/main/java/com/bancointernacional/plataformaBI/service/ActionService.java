package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.dto.period.process.action.request.ProcessActionDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.process.action.response.ActionResponseDTO;
import com.bancointernacional.plataformaBI.domain.model.period.Action;
import com.bancointernacional.plataformaBI.domain.model.period.PolicyQuestionary;
import com.bancointernacional.plataformaBI.domain.model.period.Process;
import com.bancointernacional.plataformaBI.domain.model.period.ProcessPolicy;
import com.bancointernacional.plataformaBI.enums.ActionTypes;
import com.bancointernacional.plataformaBI.enums.AppStatus;
import com.bancointernacional.plataformaBI.exception.ResourceNotFoundException;
import com.bancointernacional.plataformaBI.repository.period.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bancointernacional.plataformaBI.service.usecase.ActionUseCase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActionService implements ActionUseCase {

    private final ActionRepository actionRepository;
    private final ProcessRepository processRepository;
    private final ProcessPolicyRepository processPolicyRepository;
    private final PolicyQuestionaryRepository policyQuestionaryRepository;
    private final UserQuestionRepository userQuestionRepository;
    private final UserAnswerRepository userAnswerRepository;

    /**
     * Create a new action
     */
    @Override
    @Transactional
    public ActionResponseDTO createAction(ProcessActionDTO processActionDTO) {
        log.info("Creating action for process ID: {}", processActionDTO.getProcessId());
        Process process = processRepository.findActiveById(processActionDTO.getProcessId().toString())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Process not found with ID: " + processActionDTO.getProcessId()));
        boolean processWasReopened = false;
        if (ActionTypes.REOPEN.getWord().equalsIgnoreCase(processActionDTO.getActionType())) {
            process.setProcessStatus(AppStatus.ACTIVE.toString());
            process.setReopened(true);
            process.setUpdatedAt(LocalDateTime.now());
            process.setStartDate(processActionDTO.getProcessStartDate().atStartOfDay());
            processRepository.save(process);
            processWasReopened = true;
            log.info("Process {} has been marked as reopened", processActionDTO.getProcessId());
        } else if (ActionTypes.CLOSED.getWord().equalsIgnoreCase(processActionDTO.getActionType())) {
            process.setProcessStatus(AppStatus.CLOSED.toString());
            process.setReopened(false);
            process.setUpdatedAt(LocalDateTime.now());
            processRepository.save(process);
            log.info("Process {} has been marked as closed", processActionDTO.getProcessId());
        }
        Action action = Action.builder()
                .process(process)
                .actionReason(processActionDTO.getActionReason())
                .actionType(processActionDTO.getActionType())
                .createdAt(LocalDate.now())
                .build();
        Action savedAction = actionRepository.save(action);
        if (savedAction.getActionId() != null && ActionTypes.REOPEN.getWord().equalsIgnoreCase(processActionDTO.getActionType())) {
            List<ProcessPolicy> processPolicies = processPolicyRepository.findByProcessId(process.getProcessId().toString());
            for (ProcessPolicy processPolicy : processPolicies) {
                if (processPolicy.getProcessPolicyId() != null) {
                    processPolicy.setProcessPolicyStatus(AppStatus.ACTIVE.toString());
                    processPolicyRepository.save(processPolicy);
                    List<PolicyQuestionary> policyQuestionaries = policyQuestionaryRepository.findByProcessPolicyId(processPolicy.getProcessPolicyId().toString());
                    for (PolicyQuestionary policyQuestionary : policyQuestionaries) {
                        policyQuestionary.setPolicyQuestionaryStatus(AppStatus.ACTIVE.toString());
                        if (processActionDTO.getProcessStartDate() != null) {
                            policyQuestionary.setStartDate(processActionDTO.getProcessStartDate());
                        }
                        if (processActionDTO.getProcessEndDate() != null) {
                            policyQuestionary.setEndDate(processActionDTO.getProcessEndDate());
                        }
                        policyQuestionaryRepository.save(policyQuestionary);
                        userQuestionRepository.updateUserQuestionStatusByPolicyQuestionaryId(policyQuestionary.getPolicyQuestionaryId().toString(), AppStatus.ACTIVE.toString());
                        userAnswerRepository.updateUserAnswersStatusByPolicyQuestionaryId(policyQuestionary.getPolicyQuestionaryId().toString(), AppStatus.ACTIVE.toString());

                    }
                }
            }
        } else if (savedAction.getActionId() != null && ActionTypes.CLOSED.getWord().equalsIgnoreCase(processActionDTO.getActionType())) {
            List<ProcessPolicy> processPolicies = processPolicyRepository.findByProcessId(process.getProcessId().toString());
            for (ProcessPolicy processPolicy : processPolicies) {
                if (processPolicy.getProcessPolicyId() != null) {
                    processPolicy.setProcessPolicyStatus(AppStatus.CLOSED.toString());
                    processPolicy.setUpdatedAt(LocalDateTime.now().toLocalDate());
                    processPolicyRepository.save(processPolicy);
                    List<PolicyQuestionary> policyQuestionaries = policyQuestionaryRepository.findByProcessPolicyId(processPolicy.getProcessPolicyId().toString());
                    for (PolicyQuestionary policyQuestionary : policyQuestionaries) {
                        policyQuestionary.setPolicyQuestionaryStatus(AppStatus.CLOSED.toString());
                        policyQuestionaryRepository.save(policyQuestionary);
                        userQuestionRepository.updateUserQuestionStatusByPolicyQuestionaryId(policyQuestionary.getPolicyQuestionaryId().toString(), AppStatus.CLOSED.toString());
                        userAnswerRepository.updateUserAnswersStatusByPolicyQuestionaryId(policyQuestionary.getPolicyQuestionaryId().toString(), AppStatus.CLOSED.toString());
                    }
                }
            }
            log.info("Process {} has been marked as closed and all related policies and questionaries updated", processActionDTO.getProcessId());
        } else {

        }
        log.info("Successfully created action with ID: {}", savedAction.getActionId());
        return mapToResponseDTO(savedAction, processWasReopened);
    }

    /**
     * Get all actions by process ID
     */
    @Override
    public List<ActionResponseDTO> getActionsByProcessId(UUID processId) {
        log.info("Fetching actions for process ID: {}", processId);
        List<Action> actions = actionRepository.findByProcessId(processId.toString());
        return actions.stream()
                .map(action -> mapToResponseDTO(action, false))
                .collect(Collectors.toList());
    }

    /**
     * Get action by ID
     */
    @Override
    public ActionResponseDTO getActionById(UUID actionId) {
        log.info("Fetching action with ID: {}", actionId);
        Action action = actionRepository.findByActionId(actionId.toString())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Action not found with ID: " + actionId));
        return mapToResponseDTO(action, false);
    }

    /**
     * Map Action entity to ActionResponseDTO
     */
    private ActionResponseDTO mapToResponseDTO(Action action, boolean processReopened) {
        return ActionResponseDTO.builder()
                .actionId(action.getActionId())
                .processId(action.getProcess() != null ? action.getProcess().getProcessId() : null)
                .actionReason(action.getActionReason())
                .actionType(action.getActionType())
                .createdAt(action.getCreatedAt())
                .processReopened(processReopened)
                .build();
    }
}
