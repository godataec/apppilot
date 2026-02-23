package com.bancointernacional.plataformaBI.models.DTO;

import com.bancointernacional.plataformaBI.models.entities.InsurancePolicy;

public class InsurancePolicyDTO {


    private Long idInsurance;
    private String nameInsurance;
    private String createdAt;
    private String updatedAt;
    private String status;
    private String description;
    
    
    public InsurancePolicyDTO() {
    }


    public InsurancePolicyDTO(Long idInsurance, String nameInsurance, String createdAt, String updatedAt, String status,
            String description) {
        this.idInsurance = idInsurance;
        this.nameInsurance = nameInsurance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.description = description;
    }


    public Long getIdInsurance() {
        return idInsurance;
    }


    public void setIdInsurance(Long idInsurance) {
        this.idInsurance = idInsurance;
    }


    public String getNameInsurance() {
        return nameInsurance;
    }


    public void setNameInsurance(String nameInsurance) {
        this.nameInsurance = nameInsurance;
    }


    public String getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public String getUpdatedAt() {
        return updatedAt;
    }


    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    
    public static InsurancePolicyDTO build(InsurancePolicy insurancepolicy){


        if(insurancepolicy == null){
            throw new RuntimeException("Debe pasar el entity de polizas");
        }

        return new InsurancePolicyDTO(
                        insurancepolicy.getIdInsurance(),
                        insurancepolicy.getNameInsurance(),
                        insurancepolicy.getCreatedAt(),
                        insurancepolicy.getUpdatedAt(),
                        insurancepolicy.getStatus(),
                        insurancepolicy.getDescription()
        );

    }


    
}
