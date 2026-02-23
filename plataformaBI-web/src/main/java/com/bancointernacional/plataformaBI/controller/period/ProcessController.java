package com.bancointernacional.plataformaBI.controller.period;


import com.bancointernacional.plataformaBI.domain.dto.period.process.request.CreateProcessDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.process.request.UpdateProcessDTO;
import com.bancointernacional.plataformaBI.domain.dto.period.process.response.ProcessDTO;
import com.bancointernacional.plataformaBI.service.ProcessService;
import com.bancointernacional.plataformaBI.service.AttachmentService;
import com.bancointernacional.plataformaBI.domain.model.period.ProcessPolicy;
import com.bancointernacional.plataformaBI.repository.period.ProcessPolicyRepository;
import com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigInteger;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/process")
@RestController
public class ProcessController {

    @Autowired
    ProcessService processService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    ProcessPolicyRepository processPolicyRepository;

    @PostMapping
    public ResponseEntity<?> postProcess(
            @Valid @RequestBody CreateProcessDTO process,
            BindingResult result) {
        if(result.hasErrors()){
            return validation(result);
        }
        ProcessDTO o = processService.save(process);
        o.setPercentage(processService.calculateProcessPercentage(o.getProcessId().toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(o);
    }

    @PutMapping("/{processId}")
    public ResponseEntity<?> updateProcess(
            @PathVariable UUID processId,
            @Valid @RequestBody UpdateProcessDTO updateProcessDTO,
            BindingResult result) {

        if(result.hasErrors()){
            return validation(result);
        }

        try {
            updateProcessDTO.setProcessId(processId);
            ProcessDTO updatedProcess = processService.updateProcess(updateProcessDTO);
            updatedProcess.setPercentage(processService.calculateProcessPercentage(updatedProcess.getProcessId().toString()));
            return ResponseEntity.ok(updatedProcess);

        } catch (IllegalArgumentException | IllegalStateException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterBy(
            @RequestParam(name = "subsidiary", defaultValue = "1") BigInteger subsidiary,
            @RequestParam(name = "character", required = false) String character,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int pageSize,
            @RequestParam(name = "dateBegin", required = false) String dateBegin,
            @RequestParam(name = "dateEnd", required = false) String dateEnd,
            @RequestParam(name = "year", required = false) BigInteger year) {

        try {
            List<ProcessDTO> procesos = processService.filterBy(subsidiary,character, page, pageSize, dateBegin, dateEnd, year);
            Integer totalCount = processService.filterByWithoutPagination(subsidiary,character, dateBegin, dateEnd);
            Map<String, Object> response = new HashMap<>();
            response.put("processes", procesos);
            response.put("total", totalCount);
            return ResponseEntity.ok(response);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{processId}")
    public ResponseEntity<Object> deleteProcess(@PathVariable UUID processId) {
        try {
            processService.softDeleteProcess(processId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllActiveProcesses() {
        try {
            List<ProcessDTO> activeProcesses = processService.findAllActive();

            Map<String, Object> response = new HashMap<>();
            response.put("processes", activeProcesses);
            response.put("total", activeProcesses.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error retrieving active processes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/{processId}/validate-closure")
    public ResponseEntity<?> validateProcessClosure(@PathVariable UUID processId) {
        try {
            Map<String, Object> validationResult = new HashMap<>();
            Map<String, Boolean> validations = new HashMap<>();
            Map<String, String> messages = new HashMap<>();

            double processPercentage = processService.calculateProcessPercentage(processId.toString());
            boolean isPercentageComplete = processPercentage >= 100.0;
            validations.put("percentageComplete", isPercentageComplete);

            if (!isPercentageComplete) {
                messages.put("percentage", "El procentaje del periodo es " + processPercentage + "%. Debe ser 100% para su cierre.");
            }

            List<AttachmentDTO> processAttachments = attachmentService.getInfoFilesByProcessId(processId);
            boolean hasProcessAttachment = processAttachments.size() >= 1;
            validations.put("hasProcessAttachment", hasProcessAttachment);

            if (!hasProcessAttachment) {
                messages.put("processAttachment", "El periodo debe manejar al menos un archivo adjunto.");
            }

            List<ProcessPolicy> processPolicies = processPolicyRepository.findByProcessId(processId.toString());
            int totalPolicyAttachments = 0;

            for (ProcessPolicy policy : processPolicies) {
                List<AttachmentDTO> policyAttachments = attachmentService.getInfoFilesByProcessPolicyId(policy.getProcessPolicyId());
                totalPolicyAttachments += policyAttachments.size();
            }

            boolean hasPolicyAttachments = totalPolicyAttachments >= 2;
            validations.put("hasPolicyAttachments", hasPolicyAttachments);

            if (!hasPolicyAttachments) {
                messages.put("policyAttachments", "Las polizas anexadas al periodo deben tener al menos 2 archivos adjuntos. Actualmente manejan: " + totalPolicyAttachments + ".");
            }

            boolean canClose = isPercentageComplete && hasProcessAttachment && hasPolicyAttachments;

            validationResult.put("canClose", canClose);
            validationResult.put("validations", validations);
            validationResult.put("messages", messages);
            validationResult.put("processPercentage", processPercentage);
            validationResult.put("processAttachmentsCount", processAttachments.size());
            validationResult.put("policyAttachmentsCount", totalPolicyAttachments);

            return ResponseEntity.ok(validationResult);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error validating process closure: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
