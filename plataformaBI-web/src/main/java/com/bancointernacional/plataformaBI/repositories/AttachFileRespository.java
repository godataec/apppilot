package com.bancointernacional.plataformaBI.repositories;

import java.math.BigInteger;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bancointernacional.plataformaBI.models.DTO.AttachFileDTO;
import com.bancointernacional.plataformaBI.models.entities.AttachFile;

public interface AttachFileRespository extends JpaRepository <AttachFile, UUID> {

    //Borrar archivos adjuntados al proceso
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ATTACHFILE WHERE IDPROCESS = :idProcess", nativeQuery = true)
    int deleteAllByIdProcess(@Param("idProcess") UUID idProcess);

    //Borrar archivos vinculados a la poliza 
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ATTACHFILE WHERE IDPOLICY = :idPolicy", nativeQuery = true)
    int deleteAllByIdPolicy(@Param("idPolicy") Long idPolicy);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ATTACHFILE WHERE IDQUEST = :questionnaireId", nativeQuery = true)
    int deleteAllByIdQuest(@Param("questionnaireId") Integer questionnaireId);


    //busqueda de los archivos por medio de un id de proceso
    @Query("SELECT new com.bancointernacional.plataformaBI.models.DTO.AttachFileDTO(a.idFile, a.nameFile, a.dateUploadFile, a.typeFile, a.process.idProcess) FROM AttachFile a WHERE a.process.idProcess = :idProcess")
     List<AttachFileDTO> findByIdProcess(@Param("idProcess") UUID idProcess);

    //busqueda de la info de archivos por medio de un id de poliza
    @Query("SELECT new com.bancointernacional.plataformaBI.models.DTO.AttachFileDTO(a.idFile, a.nameFile, a.dateUploadFile, a.typeFile, a.process.idProcess, a.processInsurancePolicy.idPolicy) FROM AttachFile a WHERE a.processInsurancePolicy.idPolicy = :idPolicy")
     List<AttachFileDTO> findByIdPolicy(@Param("idPolicy") Long idPolicy); 


    //busqueda de la info de archivos por medio de un id de Proceso de Cuestionario
    @Query("SELECT new com.bancointernacional.plataformaBI.models.DTO.AttachFileDTO(a.idFile, a.nameFile, a.dateUploadFile, a.typeFile, a.process.idProcess, a.processInsurancePolicy.idPolicy, a.processQuestionary.idQuest) "+
    "FROM AttachFile a "+
    "WHERE a.processQuestionary.idQuest = :idQuest "+
    "AND a.question.idQuestion IS NULL")
    List<AttachFileDTO> findByIdQuest(@Param("idQuest") UUID idQuest); 

    //busqueda de la info de archivos por medio de un id de Proceso de Cuestionario y de Pregunta
    @Query("SELECT new com.bancointernacional.plataformaBI.models.DTO.AttachFileDTO(a.idFile, a.nameFile, a.dateUploadFile, a.typeFile, a.process.idProcess, a.processInsurancePolicy.idPolicy, a.processQuestionary.idQuest, a.question.idQuestion) "+ 
    "FROM AttachFile a "+
    "WHERE a.processQuestionary.idQuest LIKE ?1 "+
    "AND a.question.idQuestion LIKE ?2")
    List<AttachFileDTO> findByIdQuestAndidQuestion(@Param("idQuest") UUID idQuest, @Param("idQuestion") Integer idQuestion);


    // Consulta archivos de imagenes previas
    @Query("SELECT new com.bancointernacional.plataformaBI.models.DTO.AttachFileDTO( " +  
       "a.idFile, a.nameFile, a.dateUploadFile, a.typeFile, a.photopreview, " +  
       "a.process.idProcess, a.processInsurancePolicy.idPolicy, a.processQuestionary.idQuest, a.question.idQuestion) " +  
       "FROM AttachFile a " +  
       "WHERE a.idFile = :idFile")  
    Optional<AttachFileDTO> findByIdImagePreview(@Param("idFile") UUID idFile);

     
     //Descarga de Archivo por id de Archivo
     @Query(value = "SELECT * FROM ATTACHFILE WHERE IDFILE = ?1", nativeQuery = true)
     Optional<AttachFile> findByIdFile(UUID idFile);

     @Query("SELECT new com.bancointernacional.plataformaBI.models.DTO.AttachFileDTO(a.idFile, a.nameFile, a.dateUploadFile, a.typeFile, a.process.idProcess) FROM AttachFile a WHERE a.idFile = :idFile")
     Optional<AttachFileDTO> findInfoByIdFile(@Param("idFile") UUID idFile);

     


    

}
