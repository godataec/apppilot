package com.bancointernacional.plataformaBI.service.usecase;

import com.bancointernacional.plataformaBI.domain.dto.template.policy.response.PolicyDTO;

import java.util.List;

public interface PolicyUseCase {
    List<PolicyDTO> getAllAvailablePolicies();
    List<PolicyDTO> getUnassignedPolicies();
    List<PolicyDTO> getUnassignedPoliciesExcludingProcessPolicy(String processPolicyId);
}
