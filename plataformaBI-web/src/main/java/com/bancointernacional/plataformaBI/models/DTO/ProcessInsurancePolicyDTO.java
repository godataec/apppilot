package com.bancointernacional.plataformaBI.models.DTO;

import java.time.LocalDate;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.entities.InsurancePolicy;
import com.bancointernacional.plataformaBI.models.entities.ProcessInsurancePolicy;


public class ProcessInsurancePolicyDTO {

    private Long idPolicy; 
    private String namePolicy; 
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private String status;
    private Long idInsurance;
    private UUID idProcess;
    
    public ProcessInsurancePolicyDTO() {
    }


    public ProcessInsurancePolicyDTO(Long idPolicy, LocalDate dateBegin, LocalDate dateEnd, String status) {
        this.idPolicy = idPolicy;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
    }


    public ProcessInsurancePolicyDTO(Long idPolicy, LocalDate dateBegin, LocalDate dateEnd, String status,
            Long idInsurance, UUID idProcess) {
        this.idPolicy = idPolicy;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
        this.idInsurance = idInsurance;
        this.idProcess = idProcess;
    }

    


    public ProcessInsurancePolicyDTO(Long idPolicy, String namePolicy, LocalDate dateBegin, LocalDate dateEnd,
            String status, Long idInsurance, UUID idProcess) {
        this.idPolicy = idPolicy;
        this.namePolicy = namePolicy;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
        this.idInsurance = idInsurance;
        this.idProcess = idProcess;
    }




    public Long getIdPolicy() {
        return idPolicy;
    }


    public void setIdPolicy(Long idPolicy) {
        this.idPolicy = idPolicy;
    }


    public LocalDate getDateBegin() {
        return dateBegin;
    }


    public void setDateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
    }


    public LocalDate getDateEnd() {
        return dateEnd;
    }


    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    


    public Long getIdInsurance() {
        return idInsurance;
    }


    public void setIdInsurance(Long idInsurance) {
        this.idInsurance = idInsurance;
    }


    public UUID getIdProcess() {
        return idProcess;
    }


    public void setIdProcess(UUID idProcess) {
        this.idProcess = idProcess;
    }

    public String getNamePolicy() {
        return namePolicy;
    }


    public void setNamePolicy(String namePolicy) {
        this.namePolicy = namePolicy;
    }

    

    public static ProcessInsurancePolicyDTO build(ProcessInsurancePolicy processPolicy){

        if( processPolicy == null){
            throw new RuntimeException("Debe pasar el entity Poliza de proceso");
        }

        return new ProcessInsurancePolicyDTO(processPolicy.getIdPolicy(),
                                             processPolicy.getDateBegin(),
                                             processPolicy.getDateEnd(),
                                             processPolicy.getStatus());


    }

    public static ProcessInsurancePolicyDTO buildByidProcess ( ProcessInsurancePolicy processPolicy){

        if(processPolicy == null ){
            throw new RuntimeException("Debe pasar el entity Poliza de proceso");
        }

        InsurancePolicy insurancePolicy = processPolicy.getInsurancePolicy();
        return new ProcessInsurancePolicyDTO(processPolicy.getIdPolicy(),
                                             insurancePolicy.getNameInsurance(),
                                             processPolicy.getDateBegin(),
                                             processPolicy.getDateEnd(),
                                             processPolicy.getStatus(),
                                             processPolicy.getInsurancePolicy().getIdInsurance(),
                                             processPolicy.getProcess().getIdProcess());
    }



    

}
