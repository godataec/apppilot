package com.bancointernacional.plataformaBI.services.serviceImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.sql.Timestamp;

import java.util.stream.Collectors;

import com.bancointernacional.plataformaBI.Enums.AppStatus;
import com.bancointernacional.plataformaBI.models.entities.ProcessQuestionary;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancointernacional.plataformaBI.models.DTO.ProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.models.DTO.ProcessPolicyDTO;
import com.bancointernacional.plataformaBI.models.entities.InsurancePolicy;
import com.bancointernacional.plataformaBI.models.entities.ProcessInsurancePolicy;
import com.bancointernacional.plataformaBI.models.entities.ProcessManager;
import com.bancointernacional.plataformaBI.repositories.AttachFileRespository;
import com.bancointernacional.plataformaBI.repositories.InsurancePolicyRepository;
import com.bancointernacional.plataformaBI.repositories.ProcessInsurancePolicyRepository;
import com.bancointernacional.plataformaBI.repositories.ProcessManagerRepository;
import com.bancointernacional.plataformaBI.repositories.ProcessQuestionaryRepository;
import com.bancointernacional.plataformaBI.services.serviceInterface.ProcessInsurancePolicyService;

@Slf4j
@Service
public class ProcessInsurancePolicyServiceImpl implements ProcessInsurancePolicyService {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private ProcessInsurancePolicyRepository processPolicyRespository;

    @Autowired
    private ProcessManagerRepository processRespository;

    @Autowired
    private InsurancePolicyRepository policyRepository;

    @Autowired
    private ProcessQuestionaryRepository processQuestionaryRepository;

    @Autowired
    private AttachFileRespository attachFileRespository;
    @Autowired
    private ProcessQuestionaryServiceImpl processQuestionaryServiceImpl;


    @Override
    @Transactional(readOnly = true)
    public List<ProcessInsurancePolicyDTO> findAll() {

        List<ProcessInsurancePolicy> processPolicy = (List<ProcessInsurancePolicy>) processPolicyRespository.findAll();

        return processPolicy
                .stream()
                .map(u -> ProcessInsurancePolicyDTO.build(u))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessInsurancePolicyDTO> findByIdPolicy(Long idPolicy) {

        return processPolicyRespository
                .findById(idPolicy)
                .map(u -> {
                    return ProcessInsurancePolicyDTO.build(u);
                });


    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessInsurancePolicyDTO> findByIdProcess(UUID idProcess) {

        return processPolicyRespository
                .findByIdProcessJPL(idProcess)
                .stream()
                .map(u -> {
                    return ProcessInsurancePolicyDTO.buildByidProcess(u);
                })
                .collect(Collectors.toList());


    }


    @Override
    @Transactional
    public ProcessInsurancePolicy savePocessPolicy(ProcessInsurancePolicyDTO processPolicy) {

        System.out.println("Id de poliza:" + processPolicy.getIdInsurance());
        Optional<ProcessManager> processFound = processRespository.findByIdProcess(processPolicy.getIdProcess());
        Optional<InsurancePolicy> policyFound = policyRepository.findByIdInsurance(processPolicy.getIdInsurance());

        if (processFound.isPresent() && policyFound.isPresent()) {
            System.out.println("Logra entrar?");
            ProcessInsurancePolicy processPolicyForCreate = new ProcessInsurancePolicy(
                    processPolicy.getIdPolicy(),
                    processPolicy.getDateBegin(),
                    processPolicy.getDateEnd(),
                    processPolicy.getStatus(),
                    processFound.get(),
                    policyFound.get());

            ProcessInsurancePolicy processPolicyCreated = processPolicyRespository.save(processPolicyForCreate);

            return processPolicyCreated;

        }

        return null;


    }

    @Override
    @Transactional
    public Optional<ProcessInsurancePolicyDTO> updateProcessPolicy(ProcessInsurancePolicy processPolicy, Long idPolicy) {
        try {
            Optional<ProcessInsurancePolicy> processPolicyBD = processPolicyRespository.findByIdPolicy(idPolicy);

            if (processPolicyBD.isPresent()) {
                ProcessInsurancePolicy processPolicyExisting = processPolicyBD.get();
                processPolicyExisting.setDateBegin(processPolicy.getDateBegin());
                processPolicyExisting.setDateEnd(processPolicy.getDateEnd());
                processPolicyExisting.setStatus(processPolicy.getStatus());

                ProcessInsurancePolicy processPolicyUpdated = processPolicyRespository.save(processPolicyExisting);
                Optional<ProcessInsurancePolicyDTO> optionalProcessPolicy = Optional.of(ProcessInsurancePolicyDTO.build(processPolicyUpdated));
                return optionalProcessPolicy;
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el Poliza de proceso con ID: " + idPolicy + " - " + e.getMessage());
        }


    }

    @Override
    @Transactional
    public Optional<ProcessInsurancePolicyDTO> removeProcessPolicy(Long idPolicy) {

        try {
            Optional<ProcessInsurancePolicy> processPolicyFound = processPolicyRespository.findByIdPolicy(idPolicy);
            if (processPolicyFound.isPresent()) {
                //borrado en casacada de archivos y procesos de cuestionarios
                attachFileRespository.deleteAllByIdPolicy(idPolicy);
                processQuestionaryRepository.deleteAllpProcessQuestByIdPolicy(String.valueOf(idPolicy));
                processPolicyRespository.deleteById((processPolicyFound.get().getIdPolicy()));
                return Optional.of(ProcessInsurancePolicyDTO.buildByidProcess(processPolicyFound.get()));
            } else {
                throw new RuntimeException("Error al eliminar la poliza con proceso  : " + idPolicy + " no se ha encontrado.");
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar la poliza con proceso  : " + idPolicy + " no se ha encontrado.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessPolicyDTO> getProcessPolicyByCharacters(String characters, int page, int pageSize,
                                                               String dateBegin, String dateEnd, BigInteger year) {

        int offset = (page - 1) * pageSize;
        List<Object[]> ResultSet = null;
        if(Objects.nonNull(year)){
            ResultSet = processPolicyRespository.findProcessPolicyForCharactersYear(characters, offset, pageSize, dateBegin, dateEnd, year);
        } else {
            ResultSet = processPolicyRespository.findProcessPolicyForCharacters(characters, offset, pageSize, dateBegin, dateEnd, year);
        }
        List<ProcessPolicyDTO> resultList = convertToDTO(ResultSet);
        for(ProcessPolicyDTO processPolicyDTO : resultList) {
            Double totalValueQuestionaries = questionnaireService.getQuestionariesAssignedToPolicy(processPolicyDTO.getIdInsurance().longValue());
            List<ProcessQuestionary> processQuestionaries = processQuestionaryRepository.findByProcessQuestionaryByPolicyIdAAndStatusComplete(processPolicyDTO.getIdPolicy());
            processPolicyDTO.setPercentage((processQuestionaries.size() * 100) / totalValueQuestionaries);
        }
        return resultList;

    }

    @Override
    public Integer getProcessPolicyByCharactersWithoutPages(String characters, String dateBegin,
                                                            String dateEnd) {
        return processPolicyRespository.findProcessPolicyForCharacterswithoutPages(characters, dateBegin, dateEnd);
    }

    public List<ProcessPolicyDTO> convertToDTO(List<Object[]> results) {
        return results.stream()
                .map(result -> new ProcessPolicyDTO(
                        ((BigDecimal) result[0]).longValue(),
                        (String) result[1],
                        convertTimestampToLocalDate((Timestamp) result[2]),
                        convertTimestampToLocalDate((Timestamp) result[3]),
                        result[4] instanceof String ? UUID.fromString((String) result[4]) : (UUID) result[4],
                        ((Integer) result[5]),
                        (String) result[6],
                        (String) result[7],
                        (String) result[8],
                        0.0
                ))
                .collect(Collectors.toList());
    }

    private LocalDate convertTimestampToLocalDate(Timestamp timestamp) {
        return timestamp != null ? timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }


    public void deleteLogicalProcessPolicy(Long idProcessPolicy) {
        try{
            Optional<ProcessInsurancePolicy> processPolicyFound = processPolicyRespository.findByIdPolicy(idProcessPolicy);
            if (processPolicyFound.isPresent()) {
                ProcessInsurancePolicy processPolicy = processPolicyFound.get();
                if(!processPolicy.getStatus().equals(AppStatus.CLOSED.name())){
                    List<ProcessQuestionary> pq = processQuestionaryRepository.findByIdPolicy(processPolicy.getIdPolicy());
                    pq.forEach(
                            processQuestionary -> {
                                processQuestionaryServiceImpl.deleteLogicalProcessQuestionary(processQuestionary.getIdQuest());
                            }
                    );
                    processPolicyRespository.deleteByPolicyId(idProcessPolicy);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
