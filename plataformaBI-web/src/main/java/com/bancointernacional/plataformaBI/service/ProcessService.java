package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.model.period.ProcessPolicy;
import com.bancointernacional.plataformaBI.enums.AppStatus;
import com.bancointernacional.plataformaBI.domain.dto.period.process.request.CreateProcessDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.process.request.UpdateProcessDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.process.response.ProcessDTO;
import com.bancointernacional.plataformaBI.domain.model.period.Process;
import com.bancointernacional.plataformaBI.exception.ResourceNotFoundException;
import com.bancointernacional.plataformaBI.mapper.ProcessMapper;
import com.bancointernacional.plataformaBI.repository.period.ProcessPolicyRepository;
import com.bancointernacional.plataformaBI.repository.period.ProcessRepository;
import com.bancointernacional.plataformaBI.service.usecase.ProcessUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProcessService implements ProcessUseCase {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ProcessMapper processMapper;
    @Autowired
    private ProcessPolicyRepository processPolicyRepository;
    @Autowired
    private ProcessPolicyService processPolicyService;

    @Override
    public List<ProcessDTO> findAll() {
        return processRepository.findAll().stream().map(process -> {
            ProcessDTO dto = processMapper.toDTO(process);
            Long percentage = calculateProcessPercentage(process.getProcessId().toString());
            dto.setPercentage(percentage);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProcessDTO> filterBy(BigInteger subsidiary ,String characters, int page, int pageSize, String dateBegin, String dateEnd, BigInteger year) {
        int offset = (page - 1) * pageSize;
        List<Process> processes = processRepository.findProcessForCharacters(characters, offset, pageSize, dateBegin, dateEnd, year,subsidiary);
        return processes.stream().map(process -> {
            ProcessDTO dto = processMapper.toDTO(process);
            Long percentage = calculateProcessPercentage(process.getProcessId().toString());
            dto.setPercentage(percentage);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Integer filterByWithoutPagination(BigInteger subsidiary ,String characters, String dateBegin, String dateEnd) {
        return processRepository.findProcessForCharactersWithoutPagination(characters,dateBegin,dateEnd);
    }

    @Override
    public Optional<ProcessDTO> findByProcessId(UUID idProcess) {
        return processRepository.findById(idProcess).map(process -> {
            ProcessDTO dto = processMapper.toDTO(process);
            Long percentage = calculateProcessPercentage(process.getProcessId().toString());
            dto.setPercentage(percentage);
            return dto;
        });
    }

    @Override
    public ProcessDTO save(CreateProcessDTO process) {
        try {
            Process processToSave = new Process();
            processToSave.setProcessDescription(process.getProcessName());
            processToSave.setProcessStatus(AppStatus.ACTIVE.name());
            processToSave.setStartDate(process.getProcessStartDate().atStartOfDay()); // Convert LocalDate to LocalDateTime
            processToSave.setEndDate(process.getProcessEndDate().atTime(23, 59, 59)); // End of day for end date
            processToSave.setSubsidiaryId(1L); // Assuming a default subsidiary ID, this should be replaced with actual logic to get the subsidiary ID
            processToSave.setCreatedAt(LocalDateTime.now());
            processToSave.setUpdatedAt(LocalDateTime.now());
            processToSave.setDeleted(false);
            processToSave.setReopened(false);
            Process processSaved =  processRepository.save(processToSave);
            return processMapper.toDTO(processSaved);
        } catch (Exception e) {
            log.error("Error creating process: ", e);
            throw new RuntimeException("Error al crear el proceso " + e.getMessage());
        }
    }

    @Override
    public ProcessDTO update(ProcessDTO process, UUID idProcess) {
        Optional<Process> o = processRepository.findById(idProcess);
        if (o.isPresent()) {
            Process processConst = o.get();
            processConst.setStartDate(process.getProcessStartDate().atStartOfDay());
            processConst.setEndDate(process.getProcessEndDate().atTime(23, 59, 59));
            processConst.setProcessDescription(process.getProcessName());
            processConst.setProcessStatus(process.getProcessStatus());
            processConst.setUpdatedAt(LocalDateTime.now());

            Process processSave = processRepository.save(processConst);
            return processMapper.toDTO(processSave);
        }
        return null;
    }

    @Override
    public ProcessDTO updateProcess(UpdateProcessDTO updateProcessDTO) {
        try {
            Optional<Process> processOptional = processRepository.findById(updateProcessDTO.getProcessId());

            if (!processOptional.isPresent()) {
                throw new ResourceNotFoundException("Process not found with ID: " + updateProcessDTO.getProcessId());
            }
            Process existingProcess = processOptional.get();
            if (existingProcess.isDeleted()) {
                throw new IllegalStateException("Cannot update a deleted process");
            }
            existingProcess.setProcessDescription(updateProcessDTO.getProcessName());
            existingProcess.setStartDate(updateProcessDTO.getProcessStartDate().atStartOfDay());
            existingProcess.setEndDate(updateProcessDTO.getProcessEndDate().atTime(23, 59, 59));
            existingProcess.setProcessStatus(updateProcessDTO.getProcessStatus());
            existingProcess.setUpdatedAt(LocalDateTime.now());
            Process updatedProcess = processRepository.save(existingProcess);
            return processMapper.toDTO(updatedProcess);

        } catch (Exception e) {
            log.error("Error updating process with ID: {}", updateProcessDTO.getProcessId(), e);
            throw new RuntimeException("Error updating process: " + e.getMessage());
        }
    }

    @Override
    public void deleteLogicalProcess(UUID idProcess) {
    }

    /**
     * Soft delete process by setting is_deleted = 1
     */
    @Transactional
    public void softDeleteProcess(UUID processId) {
        log.info("Soft deleting Process with ID: {}", processId);
        Optional<Process> processOpt = processRepository.findById(processId);
        if (!processOpt.isPresent()) {
            throw new ResourceNotFoundException("Process not found with ID: " + processId);
        }
        Process process = processOpt.get();
        if (process.isDeleted()) {
            throw new IllegalStateException("Process is already deleted");
        }
        processRepository.softDeleteById(processId.toString());
        List<ProcessPolicy> processPolicies = processPolicyRepository.findByProcessId(processId.toString());
        for (ProcessPolicy policy : processPolicies) {
            processPolicyService.softDeleteProcessPolicy(policy.getProcessPolicyId());
        }
        log.info("Successfully soft deleted Process with ID: {}", processId);
    }

    /**
     * Get all active processes (not soft deleted)
     */
    public List<ProcessDTO> findAllActive() {
        log.info("Retrieving all active processes (not soft deleted)");
        try {
            return processRepository.findAll().stream()
                    .filter(process -> !process.isDeleted())
                    .map(processMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error retrieving active processes: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving active processes: " + e.getMessage());
        }
    }

    public Long calculateProcessPercentage(String processId) {
        log.info("Calculating percentage for process with ID: {}", processId);
        try {
            List<ProcessPolicy> processPolicies = processPolicyRepository.findByProcessId(processId);
            if (processPolicies.isEmpty()) {
                return 0L;
            }
            long totalPolicies = processPolicies.size();
            long completedPolicies = processPolicies.stream()
                    .filter(policy -> AppStatus.RESOLVED.name().equals(policy.getProcessPolicyStatus()) ||
                            AppStatus.CLOSED.name().equals(policy.getProcessPolicyStatus()))
                    .count();
            return (completedPolicies * 100) / totalPolicies;
        } catch (Exception e) {
            log.error("Error calculating process percentage: {}", e.getMessage(), e);
            throw new RuntimeException("Error calculating process percentage: " + e.getMessage());
        }
    }
}
