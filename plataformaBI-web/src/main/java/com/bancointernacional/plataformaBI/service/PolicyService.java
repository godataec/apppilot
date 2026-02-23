package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.dto.template.policy.response.PolicyDTO;
import com.bancointernacional.plataformaBI.mapper.PolicyMapper;
import com.bancointernacional.plataformaBI.repository.template.PolicyRepository;
import com.bancointernacional.plataformaBI.service.usecase.PolicyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyService implements PolicyUseCase {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyMapper policyMapper;

    @Override
    public List<PolicyDTO> getAllAvailablePolicies() {
        return policyRepository.findAll().stream().map(policyMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<PolicyDTO> getUnassignedPolicies() {
        return policyRepository.findUnassignedPolicies().stream()
                .map(policyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PolicyDTO> getUnassignedPoliciesExcludingProcessPolicy(String processId) {
        return policyRepository.findUnassignedPoliciesExcludingProcess(processId).stream()
                .map(policyMapper::toDTO)
                .collect(Collectors.toList());
    }
}
