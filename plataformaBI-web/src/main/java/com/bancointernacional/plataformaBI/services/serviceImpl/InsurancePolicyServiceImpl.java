package com.bancointernacional.plataformaBI.services.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancointernacional.plataformaBI.models.DTO.InsurancePolicyDTO;
import com.bancointernacional.plataformaBI.models.entities.InsurancePolicy;
import com.bancointernacional.plataformaBI.repositories.InsurancePolicyRepository;
import com.bancointernacional.plataformaBI.services.serviceInterface.InsurancePolicyService;

@Service
public class InsurancePolicyServiceImpl implements InsurancePolicyService {


    @Autowired
    private InsurancePolicyRepository insuranRepository;


    @Override
    @Transactional(readOnly = true)
    public List<InsurancePolicyDTO> findAll() {
        
        List<InsurancePolicy> policies = (List<InsurancePolicy>) insuranRepository.findAll();

        return policies
                        .stream()
                        .map(u -> InsurancePolicyDTO.build(u))
                        .collect(Collectors.toList());



    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InsurancePolicyDTO> findByIdInsurance(Long idInsurance) {
        return insuranRepository
                .findByIdInsurance(idInsurance)
                .map(u -> {
                    return InsurancePolicyDTO.build(u);
                });
    }


     @Override
    @Transactional(readOnly = true)
    public List<InsurancePolicyDTO> findProcessProcessPolicyAvalible(UUID idProcess) {
        
        return insuranRepository
                            .findByIdProcessAvalible(idProcess)
                            .stream()
                            .map(u -> { return InsurancePolicyDTO.build(u);})
                            .collect(Collectors.toList());
    }

}
