package com.bancointernacional.plataformaBI.services.serviceImpl;


import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bancointernacional.plataformaBI.Enums.AppStatus;
import com.bancointernacional.plataformaBI.models.DTO.ProcessPolicyDTO;
import com.bancointernacional.plataformaBI.models.entities.ProcessQuestionary;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancointernacional.plataformaBI.models.DTO.ProcessmanagerDTO;
import com.bancointernacional.plataformaBI.models.entities.ProcessInsurancePolicy;
import com.bancointernacional.plataformaBI.models.entities.ProcessManager;
import com.bancointernacional.plataformaBI.repositories.AttachFileRespository;
import com.bancointernacional.plataformaBI.repositories.ProcessInsurancePolicyRepository;
import com.bancointernacional.plataformaBI.repositories.ProcessManagerRepository;
import com.bancointernacional.plataformaBI.services.serviceInterface.ProcessInsurancePolicyService;
import com.bancointernacional.plataformaBI.services.serviceInterface.ProcessManagerService;

@Service
public class ProcessManagerServiceImpl implements ProcessManagerService {

    @Autowired
    private ProcessManagerRepository processRepository;

    @Autowired
    private ProcessInsurancePolicyRepository processInsurancePolicyRepository;

    @Autowired
    private AttachFileRespository attachFileRespository;

    @Autowired 
    ProcessInsurancePolicyService processInsurancePolicyService;

    @Override
    @Transactional(readOnly = true)
    public List<ProcessManager> findAll() {
       List<ProcessManager> process =  (List<ProcessManager>)processRepository.findAll();

       return process
                    .stream()
                    .map(u -> new ProcessManager(u))
                    .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessManager> findAllforPage(int page, int pageSize) {


        int offset = (page - 1) * pageSize;
        // Pageable pageable = PageRequest.of(page, pageSize);
        System.out.println("offset calculate:"+offset);
        List<ProcessManager> processPage = processRepository.findProcessManager(offset,pageSize);
        return processPage.stream()
                          .map(u -> new ProcessManager(u))
                          .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessManager> findByDates(String dateBegin, String dateEnd) {
        

        DateTimeFormatter   formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate dateBeginProcess = LocalDate.parse(dateBegin, formatter);
                LocalDate dateEndDateProcess = LocalDate.parse(dateEnd, formatter);
                return processRepository.findByDateBeginBetween(dateBeginProcess.toString(), dateEndDateProcess.toString());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessManager> findByCharacters(String characters, int page, int pageSize, String dateBegin,
            String dateEnd, BigInteger year) {

        int offset = (page - 1) * pageSize;
        
        return processRepository.findProcessForCharacters(characters, offset, pageSize, dateBegin, dateEnd, year);
        

    }

    @Override
    @Transactional(readOnly = true)
    public Integer findByCharactersWithoutPagination(String characters, String dateBegin,
            String dateEnd) {

        return processRepository.findProcessForCharactersWithoutPagination(characters, dateBegin, dateEnd);
        
        
    }




    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessManager> findByidProcess(UUID idProcess) {
        
        return processRepository
                                .findByIdProcess(idProcess)
                                .map(u->new ProcessManager(u));
    }

    @Override
    @Transactional
    public Optional<ProcessmanagerDTO> save(ProcessManager process) {
        
        
        try {
            Optional<ProcessmanagerDTO> resultSave =  Optional.of(ProcessmanagerDTO.build(processRepository.save(process)));
            return resultSave;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el proceso " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public Optional<ProcessManager> update(ProcessManager process, UUID idProcess) {
        Optional<ProcessManager> o = processRepository.findById(idProcess);
        ProcessManager processSave = null;
        if(o.isPresent())
        {
            ProcessManager processConst = o.orElseThrow(null);
            processConst.setDateBegin(process.getDateBegin());
            processConst.setDateEnd(process.getDateEnd());
            processConst.setProcessDescription(process.getProcessDescription());
            processConst.setStatus(process.getStatus());
            processSave = processRepository.save(processConst);
        }
        return Optional.ofNullable(new ProcessManager(processSave));


    }

    @Override
    @Transactional
    public boolean remove(UUID idProcess) {

        Optional<ProcessManager> processFound = processRepository.findByIdProcess(idProcess);
        boolean borradoExito=false;
        if(processFound.isPresent()){
            int resultAttachFile =  attachFileRespository.deleteAllByIdProcess(idProcess);
            List<ProcessInsurancePolicy> insuranceFound = processInsurancePolicyRepository.findByIdProcess(idProcess);

            for (ProcessInsurancePolicy processInsurancePolicy : insuranceFound) {
                processInsurancePolicyService.removeProcessPolicy(processInsurancePolicy.getIdPolicy());
            }

            if(resultAttachFile > 0  || processFound.isPresent())
                if(processRepository.deleteProcess(idProcess) > 0)
                   borradoExito=true;
        }

        return borradoExito;
    }

    @Override
    public void deleteLogicalProcess(UUID idProcess) {
        try{
            Optional<ProcessManager> processFound = processRepository.findByIdProcess(idProcess);
            if (processFound.isPresent()) {
                ProcessManager process = processFound.get();
                if(!process.getStatus().equals(AppStatus.CLOSED.name())){
                    List<ProcessInsurancePolicy> policies = processInsurancePolicyRepository.findByIdProcess(idProcess);
                    policies.forEach(
                            processQuestionary -> {
                                processInsurancePolicyService.deleteLogicalProcessPolicy(processQuestionary.getIdPolicy());
                            }
                    );
                    processRepository.deleteByProcessId(idProcess);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
