package com.bancointernacional.plataformaBI.controllers;

import java.math.BigInteger;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancointernacional.plataformaBI.models.DTO.ProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.models.DTO.ProcessPolicyDTO;
import com.bancointernacional.plataformaBI.models.entities.ProcessInsurancePolicy;
import com.bancointernacional.plataformaBI.services.serviceImpl.ProcessInsurancePolicyServiceImpl;

@RestController
@RequestMapping("/processinsurancepolicy")
public class ProcessInsurancePolicyController {


    @Autowired
    private ProcessInsurancePolicyServiceImpl processPolicyService;


    @GetMapping
    public ResponseEntity<?> getAllProcesspolicy() {
        try {
            List<ProcessInsurancePolicyDTO> processPocilyList = processPolicyService.findAll();
            return new ResponseEntity<>(processPocilyList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idProcessPolicy}")
    public ResponseEntity<?> getProcessPolicy(@PathVariable("idProcessPolicy") Long idProcessPolicy) {
        try {
            Optional<ProcessInsurancePolicyDTO> processPolicy = processPolicyService.findByIdPolicy(idProcessPolicy);
            return processPolicy.map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("idprocess/{idProcess}")
    public ResponseEntity<?> getProcessPolicyWithIdProcess(@PathVariable(value = "idProcess") UUID idProcess) {
        try {
            List<ProcessInsurancePolicyDTO> processPolicy = processPolicyService.findByIdProcess(idProcess);
            return ResponseEntity.ok(processPolicy);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/findByCharacters")
    public ResponseEntity<?> findByCharactersEspecifics(
            @RequestParam(name = "character", required = false) String character,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int pageSize,
            @RequestParam(name = "year", required = false) BigInteger year,
            @RequestParam(name = "dateBegin", required = false) String dateBegin,
            @RequestParam(name = "dateEnd", required = false) String dateEnd) {
        try {
            List<ProcessPolicyDTO> procesos = processPolicyService.getProcessPolicyByCharacters(character, page, pageSize, dateBegin, dateEnd, year);
            Integer totalCount = processPolicyService.getProcessPolicyByCharactersWithoutPages(character, dateBegin, dateEnd);
            Map<String, Object> response = new HashMap<>();
            response.put("processpolicies", procesos);
            response.put("total", totalCount);
            return ResponseEntity.ok(response);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping
    public ResponseEntity<?> postProcessPolicy(@RequestBody List<ProcessInsurancePolicyDTO> processPolicies, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Error de validación: " + result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {
            List<ProcessInsurancePolicy> createdPolicies = new ArrayList<>();
            for (ProcessInsurancePolicyDTO processPolicy : processPolicies) {
                createdPolicies.add(processPolicyService.savePocessPolicy(processPolicy));
            }
            return new ResponseEntity<>(createdPolicies, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/unit")
    public ResponseEntity<?> postProcessPolicyUnit(@RequestBody ProcessInsurancePolicyDTO processPolicy, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Error de validación: " + result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        ProcessInsurancePolicy createdPolicies = processPolicyService.savePocessPolicy(processPolicy);
        return new ResponseEntity<>(createdPolicies, HttpStatus.CREATED);
    }

    @PutMapping("/{idProcessPolicy}")
    public ResponseEntity<?> putProcessPolicy(@PathVariable("idProcessPolicy") Long idProcessPolicy, @RequestBody ProcessInsurancePolicy porcessPolicy) {
        try {
            Optional<ProcessInsurancePolicyDTO> processPolicyUpdated = processPolicyService.updateProcessPolicy(porcessPolicy, idProcessPolicy);
            return processPolicyUpdated.map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{idProcessPolicy}")
    public ResponseEntity<?> deleteProcessPolicy(@PathVariable("idProcessPolicy") Long idProcessPolicy) {
        processPolicyService.deleteLogicalProcessPolicy(idProcessPolicy);
        return ResponseEntity.noContent().build();
    }

}
