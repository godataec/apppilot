package com.bancointernacional.plataformaBI.services.serviceImpl;

import com.bancointernacional.plataformaBI.Enums.AppStatus;
import com.bancointernacional.plataformaBI.ErrorManagment.CustomException;
import com.bancointernacional.plataformaBI.models.DTO.ProcessInsurancePolicyDTO;
import com.bancointernacional.plataformaBI.models.DTO.ProcessQuestionaryDTO;
import com.bancointernacional.plataformaBI.models.entities.Answer;
import com.bancointernacional.plataformaBI.models.entities.ProcessInsurancePolicy;
import com.bancointernacional.plataformaBI.models.entities.ProcessQuestionary;
import com.bancointernacional.plataformaBI.models.entities.Questionnaire;
import com.bancointernacional.plataformaBI.repositories.*;
import com.bancointernacional.plataformaBI.services.serviceInterface.AnswerService;
import com.bancointernacional.plataformaBI.services.serviceInterface.ProcessQuestionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProcessQuestionaryServiceImpl implements ProcessQuestionaryService {

    @Autowired
    AnswerService answerService;

    @Autowired
    ProcessQuestionaryRepository processQuestionaryRepository;

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Autowired
    ProcessInsurancePolicyRepository processInsurancePolicyRepository;

    @Autowired
    private AnswerRespository answerRespository;

    @Autowired
    private AttachFileRespository attachFileRespository;


    @Override
    public List<ProcessQuestionaryDTO> findAllProcessQuestionary() {
        List<ProcessQuestionaryDTO> listProcessQuestionaries = processQuestionaryRepository.findAll()
                .stream()
                .map(u -> ProcessQuestionaryDTO.build(u))
                .collect(Collectors.toList());
        if (listProcessQuestionaries == null || listProcessQuestionaries.size() == 0) {
            throw new CustomException("Lista de Procesos de cuestionarios vacia", HttpStatus.NOT_FOUND);
        }
        return listProcessQuestionaries;
    }

    @Override
    public Optional<ProcessQuestionaryDTO> findProcessQuestionaryForId(UUID idProcessQuestionary) {
        Optional<ProcessQuestionaryDTO> foundProcessQuestionary = processQuestionaryRepository.findDTOByIdQuest(idProcessQuestionary);
        if (foundProcessQuestionary.isPresent()) {
            return foundProcessQuestionary;
        }
        throw new CustomException("Proceso de cuestionarios, no encontrado!", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<ProcessQuestionaryDTO> findProcessQuestionnaryForidPolicy(Long idPolicy) {
        return processQuestionaryRepository.findByIdPolicy(idPolicy)
                .stream()
                .map(u -> {
                    return ProcessQuestionaryDTO.build(u);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProcessQuestionaryDTO> saveProcessQuestionary(ProcessQuestionaryDTO processQuestionary) {

        ProcessQuestionary processQuestionaryforSave = new ProcessQuestionary();
        Optional<Questionnaire> foundQuestionnaire = questionnaireRepository.findById(processQuestionary.getIdQuestionnaire());
        Optional<ProcessInsurancePolicy> foundProcessPolicy = processInsurancePolicyRepository.findById(processQuestionary.getIdPolicy());

        if (!foundQuestionnaire.isPresent())
            throw new CustomException("No se encontro el cuestionario!", HttpStatus.NOT_FOUND);

        if (!foundProcessPolicy.isPresent())
            throw new CustomException("No se encontro el proceso de polizas", HttpStatus.NOT_FOUND);


        processQuestionaryforSave.setDateBeginQuest(processQuestionary.getDateBeginQuest());
        processQuestionaryforSave.setDateEndQuest(processQuestionary.getDateEndQuest());
        processQuestionaryforSave.setStatus(processQuestionary.getStatus());
        processQuestionaryforSave.setQuestionnaire(foundQuestionnaire.get());
        processQuestionaryforSave.setProcessInsurancePolicy(foundProcessPolicy.get());

        ProcessQuestionary processQuestionaryResult = processQuestionaryRepository.save(processQuestionaryforSave);

        if (processQuestionaryResult.getIdQuest().toString().equals(""))
            throw new CustomException("No se pudo guardar el cuestionario para la poliza", HttpStatus.NOT_FOUND);

        return Optional.of(ProcessQuestionaryDTO.build(processQuestionaryResult));

    }

    @Override
    public Optional<ProcessQuestionaryDTO> updateProcessQuestionary(ProcessQuestionary processQuestionary) {


        Optional<ProcessQuestionary> processQuestionaryBd = processQuestionaryRepository.findByIdQuest(processQuestionary.getIdQuest());
        if (processQuestionaryBd.isPresent()) {
            ProcessQuestionary pQuestionaryBD = processQuestionaryBd.get();
            pQuestionaryBD.setDateBeginQuest(processQuestionary.getDateBeginQuest());
            pQuestionaryBD.setDateEndQuest(processQuestionary.getDateEndQuest());
            pQuestionaryBD.setStatus(processQuestionary.getStatus());

            ProcessQuestionary processQuestionaryResult = processQuestionaryRepository.save(pQuestionaryBD);

            return Optional.of(ProcessQuestionaryDTO.build(processQuestionaryResult));
        }

        throw new CustomException("No se pudo actualizar el proceso de poliza", HttpStatus.NOT_FOUND);


    }

    @Override
    public ProcessQuestionaryDTO deleteProcessQuestionary(UUID idProcessQuestionary) {

        Optional<ProcessQuestionary> processQuestionaryFound = processQuestionaryRepository.findByIdQuest(idProcessQuestionary);

        if (processQuestionaryFound.isPresent()) {
            processQuestionaryRepository.delete(processQuestionaryFound.get());
            return ProcessQuestionaryDTO.build(processQuestionaryFound.get());
        }

        throw new CustomException("No se pudo borrar el proceso", HttpStatus.NOT_FOUND);

    }

    @Override
    public List<ProcessInsurancePolicyDTO> getActivePolicies(String idProcess, String idPolicy, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Object[]> ResultSet = processQuestionaryRepository.findActivePoliciesInProcessQuestionaire(idProcess, idPolicy, offset, pageSize);
        List<ProcessInsurancePolicyDTO> listOfPolicies = convertPolicyToDTO(ResultSet);
        return listOfPolicies;
    }

    @Override
    public Boolean validateClosedAnswers(UUID idQuest) {
        ProcessQuestionary processQuestionary = processQuestionaryRepository.findById(idQuest).get();
        Set<String> status = new HashSet<>();
        status.add(AppStatus.CLOSED.name());
        status.add(AppStatus.RESOLVED.name());
        List<Answer> resolvedOrClosedAnswers = answerRespository.findByIdQuestAndStatusIn(processQuestionary,status);
        Questionnaire questionnaire = questionnaireRepository.findById(processQuestionary.getQuestionnaire().getIdquestionnaire()).get();
        return  resolvedOrClosedAnswers.size() >= questionnaire.getTotalquestion() ;
    }

    private List<ProcessInsurancePolicyDTO> convertPolicyToDTO(List<Object[]> results) {
        return results.stream()
                .map(result -> new ProcessInsurancePolicyDTO(
                        result[0] instanceof BigDecimal ? Long.valueOf(String.valueOf(result[0])) : (Long) result[0],
                        convertTimestampToLocalDate(Timestamp.valueOf(String.valueOf(result[1]) ) ),
                        convertTimestampToLocalDate(Timestamp.valueOf(String.valueOf(result[2]))),
                        (String) result[3]
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessQuestionaryDTO> getProcessQuestByCharacters(String idProcess, String idPolicy, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Object[]> ResultSet = processQuestionaryRepository.findProcessQuestionaryForCharacters(idProcess, idPolicy, offset, pageSize);
        List<ProcessQuestionaryDTO> listProcessQuestionaries = convertToDTO(ResultSet);
        for (ProcessQuestionaryDTO processQuestionaryDTO : listProcessQuestionaries) {
            Integer answers = answerService.countSolvedOrCompleteAnswersByIdQuest(processQuestionaryDTO.getIdQuest());
            if(Objects.nonNull(answers)){
                Integer percentage = answers * 100 / processQuestionaryDTO.getPercentage();
                processQuestionaryDTO.setPercentage(percentage);
            }else{
                processQuestionaryDTO.setPercentage(0);
            }
        }
        return listProcessQuestionaries;
    }

    @Override
    public Integer getProcessQuestByCharactersWithoutPages(String idProcess, String idPolicy) {
        return processQuestionaryRepository.findProcessQuestionaryForCharacterswithoutPages(idProcess, idPolicy);
    }

    public List<ProcessQuestionaryDTO> convertToDTO(List<Object[]> results) {
        return results.stream()
                .map(result -> new ProcessQuestionaryDTO(
                        result[0] instanceof String ? UUID.fromString((String) result[0]) : (UUID) result[0],
                        (String) result[1],
                        convertTimestampToLocalDate((Timestamp) result[2]),
                        convertTimestampToLocalDate((Timestamp) result[3]),
                        (String) result[4],
                        ((Integer) result[5]),
                        ((BigDecimal) result[6]).longValue(),
                        result[7] instanceof String ? UUID.fromString((String) result[7]) : (UUID) result[7],
                        (String) result[8],
                        (String) result[9],
                        (Integer) result[10]
                ))
                .collect(Collectors.toList());
    }

    private LocalDate convertTimestampToLocalDate(Timestamp timestamp) {
        return timestamp != null ? timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    public void deleteLogicalProcessQuestionary(UUID processQuestionaryId) {
        try{
            Optional<ProcessQuestionary> processQuestionary = processQuestionaryRepository.findById(processQuestionaryId);
            if(processQuestionary.isPresent()){
                ProcessQuestionary processQuestionaryBD = processQuestionary.get();
                if(!processQuestionaryBD.getStatus().equals(AppStatus.CLOSED.name())){
                    //Se necesita estandarizar el uso de UUIDS, Strings y Longs
                    //attachFileRespository.deleteAllByIdPolicy(processQuestionaryId);
                    answerRespository.deleteByIdQuest(String.valueOf(processQuestionaryId));
                    processQuestionaryRepository.deleteAllpProcessQuestByIdPolicy(processQuestionaryId.toString());
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
