package com.bancointernacional.plataformaBI.controller.period;

import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.request.CreateProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.request.UpdateProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.response.ProcessDetailPolicyDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.processPolicy.response.ProcessPolicyDTO;
import com.bancointernacional.plataformaBI.service.ProcessPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/process-policy")
@RestController
public class ProcessPolicyController {

    @Autowired
    private ProcessPolicyService processPolicyService;

    @PostMapping
    public ResponseEntity<?> postProcessPolicy(
            @RequestBody List<CreateProcessInsurancePolicyDTO> processPolicies,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Error de validación: " + result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {
            List<ProcessPolicyDTO> createdPolicies = new ArrayList<>();
            for (CreateProcessInsurancePolicyDTO processPolicy : processPolicies) {
                createdPolicies.add(processPolicyService.saveProcessPolicy(processPolicy));
            }
            return new ResponseEntity<>(createdPolicies, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping("/filter")
    public ResponseEntity<?> filterBy(
            @RequestParam(name = "subsidiary", defaultValue = "1") Long subsidiary,
            @RequestParam(name = "character", required = false) String character,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int pageSize,
            @RequestParam(name = "dateBegin", required = false) String dateBegin,
            @RequestParam(name = "dateEnd", required = false) String dateEnd,
            @RequestParam(name = "year", required = false) BigInteger year) {

        try {
            List<ProcessDetailPolicyDTO> policies = processPolicyService.getProcessPoliciesDetailFilterBy(subsidiary, character, page, pageSize, dateBegin, dateEnd, year);
            Map<String, Object> response = new HashMap<>();
            response.put("processPolicies", policies);
            response.put("total", policies.size());
            return ResponseEntity.ok(response);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }

    /**
     * Soft delete process policy
     * @param processPolicyId the UUID of the process policy to delete
     * @return ResponseEntity with appropriate status
     */
    @DeleteMapping("/{processPolicyId}")
    public ResponseEntity<?> softDeleteProcessPolicy(@PathVariable UUID processPolicyId) {
        try {
            processPolicyService.softDeleteProcessPolicy(processPolicyId);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Update a list of process policies
     * @param updateProcessPolicies List of process policies to update
     * @param result Binding result for validation
     * @return ResponseEntity with updated policies or error message
     */
    @PutMapping
    public ResponseEntity<?> updateProcessPolicies(
            @RequestBody List<UpdateProcessInsurancePolicyDTO> updateProcessPolicies,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Error de validación: " + result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        if (updateProcessPolicies == null || updateProcessPolicies.isEmpty()) {
            return new ResponseEntity<>("La lista de políticas a actualizar no puede estar vacía", HttpStatus.BAD_REQUEST);
        }

        try {
            List<ProcessPolicyDTO> updatedPolicies = processPolicyService.updateProcessPolicies(updateProcessPolicies);
            return new ResponseEntity<>(updatedPolicies, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>("Recurso no encontrado: " + e.getMessage(), HttpStatus.NOT_FOUND);
            } else if (e.getMessage().contains("deleted")) {
                return new ResponseEntity<>("Error de estado: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Error interno del servidor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update a single process policy
     * @param processPolicyId UUID of the process policy to update
     * @param updateProcessPolicy Process policy data to update
     * @param result Binding result for validation
     * @return ResponseEntity with updated policy or error message
     */
    @PutMapping("/{processPolicyId}")
    public ResponseEntity<?> updateProcessPolicy(
            @PathVariable UUID processPolicyId,
            @RequestBody UpdateProcessInsurancePolicyDTO updateProcessPolicy,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Error de validación: " + result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        // Set the ID from path parameter
        updateProcessPolicy.setProcessPolicyId(processPolicyId);

        try {
            ProcessPolicyDTO updatedPolicy = processPolicyService.updateProcessPolicy(updateProcessPolicy);
            return new ResponseEntity<>(updatedPolicy, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>("Recurso no encontrado: " + e.getMessage(), HttpStatus.NOT_FOUND);
            } else if (e.getMessage().contains("deleted")) {
                return new ResponseEntity<>("Error de estado: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Error interno del servidor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
