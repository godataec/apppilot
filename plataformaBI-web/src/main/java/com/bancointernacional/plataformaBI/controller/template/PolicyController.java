package com.bancointernacional.plataformaBI.controller.template;

import com.bancointernacional.plataformaBI.domain.dto.template.policy.response.PolicyDTO;
import com.bancointernacional.plataformaBI.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;
import java.util.List;

@RequestMapping("/policy")
@RestController
public class PolicyController {

    @Autowired
    PolicyService policyService;

    @GetMapping("")
    public ResponseEntity<?> findAllAvailablePolicies() {
        try {
            List<PolicyDTO> policy = policyService.getAllAvailablePolicies();
            return ResponseEntity.ok(policy);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/unassigned")
    public ResponseEntity<?> findUnassignedPolicies(
            @RequestParam(name = "processId", required = false) String processId) {
        try {
            List<PolicyDTO> unassignedPolicies;
            if (processId != null && !processId.trim().isEmpty()) {
                // Validate UUID format for processPolicyId
                try {
                    java.util.UUID.fromString(processId);
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest().body("Invalid process-policy-id format. Must be a valid UUID.");
                }
                unassignedPolicies = policyService.getUnassignedPoliciesExcludingProcessPolicy(processId);
            } else {
                unassignedPolicies = policyService.getUnassignedPolicies();
            }
            return ResponseEntity.ok(unassignedPolicies);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving unassigned policies: " + e.getMessage());
        }
    }
}
