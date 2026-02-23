package com.bancointernacional.plataformaBI.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancointernacional.plataformaBI.models.DTO.InsurancePolicyDTO;
import com.bancointernacional.plataformaBI.services.serviceImpl.InsurancePolicyServiceImpl;


@RestController
@RequestMapping("/insurancePolicy")
public class InsurancePolicyController {

     @Autowired
     private InsurancePolicyServiceImpl policyService;

    @GetMapping
    public List<InsurancePolicyDTO> getPolicies(){

        return policyService.findAll();
    }

    @GetMapping("/{idPolicy}")
    public ResponseEntity<?> getPolicy(@PathVariable (name = "idPolicy") Long idPolicy){

        Optional<InsurancePolicyDTO> foundpolicy = policyService.findByIdInsurance(idPolicy);

        if(foundpolicy.isPresent()){
            return ResponseEntity.ok(foundpolicy.orElseThrow(null));
        }
        return ResponseEntity.notFound().build();

    }

     @GetMapping("idProcessAvalible/{idProcess}")
    public ResponseEntity<?> getProcessPolicyAvaliblewithidProcess(@PathVariable(value ="idProcess") UUID idProcess){

        List<InsurancePolicyDTO> responseProcessQuestionary = policyService.findProcessProcessPolicyAvalible(idProcess);
        System.out.println("responseProcessQuestionary:"+responseProcessQuestionary);
            return new ResponseEntity<>(responseProcessQuestionary, HttpStatus.OK);
    }

}
