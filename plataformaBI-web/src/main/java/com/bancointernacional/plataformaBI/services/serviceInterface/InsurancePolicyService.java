package com.bancointernacional.plataformaBI.services.serviceInterface;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.DTO.InsurancePolicyDTO;



public interface InsurancePolicyService  {


    public List<InsurancePolicyDTO> findAll();
    public Optional<InsurancePolicyDTO> findByIdInsurance(Long Insurance);

    List<InsurancePolicyDTO> findProcessProcessPolicyAvalible(UUID idProcess);

    
}
