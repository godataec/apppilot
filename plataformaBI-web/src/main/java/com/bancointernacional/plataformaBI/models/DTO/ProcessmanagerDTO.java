package com.bancointernacional.plataformaBI.models.DTO;

import java.util.UUID;

import com.bancointernacional.plataformaBI.models.entities.ProcessManager;

public class ProcessmanagerDTO {
    UUID idProcess;
    String processDescription;
    String dateBegin;
    String dateEnd;
    String status;




    public ProcessmanagerDTO(UUID idProcess, String processDescription, String dateBegin, String dateEnd, String status) {
        this.idProcess = idProcess;
        this.processDescription = processDescription;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
    }

    public UUID getIdProcess() {
        return idProcess;
    }

    public void setIdProcess(UUID idProcess) {
        this.idProcess = idProcess;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    public static ProcessmanagerDTO build(ProcessManager process){

        if( process == null){
            throw new RuntimeException("Debe pasar el entity Process!");
        }

        return new ProcessmanagerDTO(process.getIdProcess(),
                              process.getProcessDescription(),
                              process.getDateBegin(),
                              process.getDateEnd(),
                              process.getStatus());

    }


}
