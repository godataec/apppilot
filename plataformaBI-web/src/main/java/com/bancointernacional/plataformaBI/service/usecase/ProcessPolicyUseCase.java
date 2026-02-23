package com.bancointernacional.plataformaBI.service.usecase;

import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.request.CreateProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.request.UpdateProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.response.ProcessPolicyDTO;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public interface ProcessPolicyUseCase {
    ProcessPolicyDTO saveProcessPolicy(CreateProcessInsurancePolicyDTO processPolicy);

    List<ProcessPolicyDTO> getProcessPoliciesFilterBy(Long subsidiary, String character, int page, int pageSize, String dateBegin, String dateEnd, BigInteger year);

    Integer getProcessPoliciesFilterByWithoutPages(Long subsidiary, String character, String dateBegin, String dateEnd);

    void softDeleteProcessPolicy(UUID processPolicyId);

    ProcessPolicyDTO updateProcessPolicy(UpdateProcessInsurancePolicyDTO updateProcessPolicy);

    List<ProcessPolicyDTO> updateProcessPolicies(List<UpdateProcessInsurancePolicyDTO> updateProcessPolicies);
}

