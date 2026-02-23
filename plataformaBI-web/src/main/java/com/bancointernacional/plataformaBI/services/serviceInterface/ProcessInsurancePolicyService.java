package com.bancointernacional.plataformaBI.services.serviceInterface;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.DTO.ProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.models.DTO.ProcessPolicyDTO;
import com.bancointernacional.plataformaBI.models.entities.ProcessInsurancePolicy;

public interface ProcessInsurancePolicyService {

    List<ProcessInsurancePolicyDTO> findAll();
    
    Optional<ProcessInsurancePolicyDTO> findByIdPolicy(Long idPolicy);

    List<ProcessInsurancePolicyDTO> findByIdProcess(UUID idProcess);
    
    ProcessInsurancePolicy savePocessPolicy(ProcessInsurancePolicyDTO processPolicy);
    
    Optional<ProcessInsurancePolicyDTO> updateProcessPolicy(ProcessInsurancePolicy processPolicy, Long idPolicy );
    
    Optional<ProcessInsurancePolicyDTO> removeProcessPolicy (Long idPolicy);

    List<ProcessPolicyDTO> getProcessPolicyByCharacters(String characters, int page, int pageSize, String dateBegin, String dateEnd, BigInteger year);
    
    Integer getProcessPolicyByCharactersWithoutPages(String characters, String dateBegin, String dateEnd);

    void deleteLogicalProcessPolicy(Long idProcessPolicy);
}
