package com.bancointernacional.plataformaBI.services.serviceInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bancointernacional.plataformaBI.models.DTO.AttachFileDTO;
import com.bancointernacional.plataformaBI.models.entities.AttachFile;

public interface AttachFileService {

    //permite cargar o almecer archivos a la base de datos
    AttachFile storeService( AttachFileDTO attachFileDTO) throws IOException;


    //Permite descargar archivos de nuestra base de datos
    Optional<AttachFile> getFile(UUID id) throws FileNotFoundException;


    public Optional<AttachFileDTO> getPhotoPreview(UUID idFile) throws FileNotFoundException;

    //permite consultr la lista de archivos cargados a nuestra base de datos
    // List<AttachFileDTO> getAllFiles();

    //Obtiene la informacion de los archivos por medio de la id del Proceso
    List<AttachFileDTO> getInfoFilesByIdProcess(UUID idProcess);

    //Obtiene la informacion de los archivos por medio de la id del poliza
    List<AttachFileDTO> getInfoFilesByIdPolicy(Long idPolicy);

    //Obtiene la informacion de los archivos por medio de la id del proceso de cuestionario
    List<AttachFileDTO> getInfoFilesByIdQuest(UUID idQuest);

    //Obtiene la informacion de los archivos por medio de la id del proceso de cuestionario
    List<AttachFileDTO> getInfoFilesByIdQuestAndIdQuestion(UUID idQuest, Integer idQuestion);

    //Obtiene la informacion de los archivos por medio de la id del cuestionario y del proceso cuestionario

    List<AttachFileDTO> getInfoFilesByIdQuestAndIdQuestionIndividual(UUID idQuest, Integer idQuestion);

    Optional<AttachFileDTO> DeleteFileById(UUID idFile);


}
