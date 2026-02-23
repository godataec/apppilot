package com.bancointernacional.plataformaBI.repositories;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bancointernacional.plataformaBI.models.entities.InsurancePolicy;

public interface InsurancePolicyRepository  extends CrudRepository<InsurancePolicy, Long>{


    Optional<InsurancePolicy> findByIdInsurance(Long idInsurance);


    //Busqueda de los polizas por proceso por id process que esten disponibles
    @Query(value = "SELECT * FROM INSURANCEPOLICY a "
    +"LEFT JOIN PROCESSINSURANCEPOLICY b ON a.IDINSURANCE = b.IDINSURANCE AND b.IDPROCESS = ?1 "
    +"WHERE a.ISDELETED=0 AND b.IDINSURANCE IS NULL"
    , nativeQuery = true) 
    List<InsurancePolicy> findByIdProcessAvalible (UUID idProcess);
}
