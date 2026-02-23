package com.bancointernacional.plataformaBI.services.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancointernacional.plataformaBI.models.DTO.AttachFileDTO;
import com.bancointernacional.plataformaBI.models.DTO.ProcessQuestionaryDTO;
import com.bancointernacional.plataformaBI.models.entities.Answer;
import com.bancointernacional.plataformaBI.models.entities.AttachFile;
import com.bancointernacional.plataformaBI.models.entities.ProcessInsurancePolicy;
import com.bancointernacional.plataformaBI.models.entities.ProcessManager;
import com.bancointernacional.plataformaBI.models.entities.ProcessQuestionary;
import com.bancointernacional.plataformaBI.models.entities.Question;
import com.bancointernacional.plataformaBI.models.entities.Questionnaire;
import com.bancointernacional.plataformaBI.repositories.AnswerRespository;
import com.bancointernacional.plataformaBI.repositories.AttachFileRespository;
import com.bancointernacional.plataformaBI.repositories.ProcessInsurancePolicyRepository;
import com.bancointernacional.plataformaBI.repositories.ProcessManagerRepository;
import com.bancointernacional.plataformaBI.repositories.ProcessQuestionaryRepository;
import com.bancointernacional.plataformaBI.repositories.QuestionRepository;
import com.bancointernacional.plataformaBI.repositories.QuestionnaireRepository;
import com.bancointernacional.plataformaBI.services.serviceInterface.AttachFileService;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionnaireService;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class AttachFileServiceImpl implements AttachFileService {

    @Autowired
    private AttachFileRespository fileRespository;

    @Autowired
    private ProcessManagerRepository processManagerRepository;

    @Autowired
    private ProcessInsurancePolicyRepository processInsurancePolicyRepository;

    @Autowired
    private ProcessQuestionaryRepository processQuestionaryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @SuppressWarnings("null")
    @Override
    public AttachFile storeService(AttachFileDTO attachFileDTO) throws IOException {
        
        Optional<ProcessManager> foundProcessManager = null;
        Optional<ProcessInsurancePolicy> foundProcessPolicy = null;
        Optional<ProcessQuestionary> foundProcessQuestionary = null;
        Optional<Question> foundQuestion = null;
        boolean idPresent= false;

            if(attachFileDTO.getIdProcess() != null)
            {
                foundProcessManager = processManagerRepository.findById(attachFileDTO.getIdProcess());
                if(foundProcessManager.isPresent()) idPresent = true;
            }

            if(attachFileDTO.getIdPolicy() != null)
            {
                foundProcessPolicy =  processInsurancePolicyRepository.findByIdPolicy(attachFileDTO.getIdPolicy());
                idPresent = true;
            }

            if(attachFileDTO.getIdQuest() != null)
            {
                foundProcessQuestionary =  processQuestionaryRepository.findById(attachFileDTO.getIdQuest());
                idPresent = true;
            }

            if(attachFileDTO.getIdQuestion() != null)
            {
                foundQuestion =  questionRepository.findById(attachFileDTO.getIdQuestion());
                idPresent = true;
            }
            
            
            if(idPresent)
            {
                AttachFile attachFile = new AttachFile();
                if(foundProcessManager != null ) attachFile.setProcess(foundProcessManager.get());
                if(foundProcessPolicy != null) attachFile.setProcessInsurancePolicy(foundProcessPolicy.get());
                if(foundProcessQuestionary != null) attachFile.setProcessQuestionary(foundProcessQuestionary.get());
                if(foundQuestion != null) attachFile.setQuestion(foundQuestion.get());
                attachFile.setNameFile(attachFileDTO.getNameFile());
                attachFile.setTypeFile(attachFileDTO.getTypeFile());
                attachFile.setDataFile(attachFileDTO.getDataFile());
                if(attachFile.getProcess()!=null){
                    attachFile.setProcess(foundProcessManager.get());
                }

                //conversion de imagen para respuestas
                if(verifyImage(attachFileDTO.getTypeFile().trim()) &&  foundQuestion != null && foundProcessQuestionary != null )
                    try {
                        attachFile.setPhotopreview(resizeImage(attachFile.getDataFile(), 30, 30));
                    } catch (Exception e) {
                        throw new EntityNotFoundException("No se pudo convertir la Imagen: "+ attachFileDTO.getNameFile()+",Error:"+e);
                    }
                else
                        attachFile.setPhotopreview(null);

                attachFile.setDateUploadFile(attachFileDTO.getDateUploadFile());

                return fileRespository.save(attachFile);
            } else {
                throw new EntityNotFoundException("ProcessManager not found for ID: " + attachFileDTO.getNameFile());
            }

    
    }

    

    @Override
    public Optional<AttachFile> getFile(UUID idFile) throws FileNotFoundException {
        
        Optional<AttachFile> foundFile = fileRespository.findById(idFile);
        if(foundFile.isPresent())
        {
            System.out.println("Logro entrar?");
            return foundFile;
        }
        throw new FileNotFoundException();
        
    }


    @Override
    public Optional<AttachFileDTO> getPhotoPreview(UUID idFile) throws FileNotFoundException {
        
        Optional<AttachFileDTO> foundFile = fileRespository.findByIdImagePreview(idFile);
        if(foundFile.isPresent())
        {
            return foundFile;
        }
        throw new FileNotFoundException("No pudo recuperar la imagen con id" +  idFile);
        
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachFileDTO> getInfoFilesByIdProcess(UUID idProcess) {   
        try {

            return fileRespository.findByIdProcess(idProcess);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar informacion de archivo por idProcess: " + e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachFileDTO> getInfoFilesByIdPolicy(Long idPolicy) {   
        try {

            return fileRespository.findByIdPolicy(idPolicy);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar informacion de archivo por idPolicy: " + e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachFileDTO> getInfoFilesByIdQuest(UUID idQuest) {   
        try {

            return fileRespository.findByIdQuest(idQuest);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar informacion de archivo por idPolicy: " + e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachFileDTO> getInfoFilesByIdQuestAndIdQuestion(UUID idQuest, Integer idQuestionnaire) {   
        try {

            Optional<Questionnaire> questionary = questionnaireRepository.findById(idQuestionnaire);  
            List<AttachFileDTO> listFiles = new ArrayList<>();
            if(questionary.isPresent())
            {   
                List<Question> questions = questionRepository.findByQuestionnaire(questionary.get());
                for(Question question :questions) {
                    System.out.println("idQuest:"+idQuest);
                    System.out.println("question.getIdQuestion():"+question.getIdQuestion());
                    List<AttachFileDTO> filesFoundByQuestion = fileRespository.findByIdQuestAndidQuestion(idQuest, question.getIdQuestion());

                    System.out.println("NArchivos:"+filesFoundByQuestion.size());
                    for(AttachFileDTO file: filesFoundByQuestion) { 
                        System.out.println("fileoutput:"+file); }
                    listFiles.addAll(filesFoundByQuestion);

                }
                    
            }       
            return listFiles;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar informacion de archivos por Preguntas: " + e.getLocalizedMessage());
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<AttachFileDTO> getInfoFilesByIdQuestAndIdQuestionIndividual(UUID idQuest, Integer idQuestion) {   
        try {

                    List<AttachFileDTO> filesFoundByQuestion = fileRespository.findByIdQuestAndidQuestion(idQuest, idQuestion);

            return filesFoundByQuestion;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar informacion de archivos por Preguntas: " + e.getLocalizedMessage());
        }
    }

    @Override
    public Optional<AttachFileDTO> DeleteFileById(UUID idFile) {
       
        try {
             Optional<AttachFileDTO> fileFound =  fileRespository.findInfoByIdFile(idFile);
             if(fileFound.isPresent()){
                fileRespository.deleteById(idFile);
                return fileFound;
             }
             else 
             {
                return Optional.empty();
             } 
       } catch (Exception e) {

            throw new RuntimeException("Error al borrar la cuenta: " + e.getLocalizedMessage());
       }
    }


    public static byte[] resizeImage(byte[] originalImage, int width, int height) throws Exception {
        // Leer la imagen original desde el byte array
        ByteArrayInputStream inputStream = new ByteArrayInputStream(originalImage);
        BufferedImage originalBufferedImage = ImageIO.read(inputStream);

        if (originalBufferedImage == null) {
            throw new RuntimeException("Error al redimensionar imagen");
        }

        // Crear una nueva imagen con las dimensiones especificadas
        BufferedImage resizedBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedBufferedImage.createGraphics();

        // Habilitar suavizado para mejorar la calidad
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(originalBufferedImage, 0, 0, width, height, null);
        graphics2D.dispose();

        // Convertir la nueva imagen a byte[]
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedBufferedImage, "jpeg", outputStream); // Cambia "jpeg" al formato que necesites
        return outputStream.toByteArray();
    }

    boolean verifyImage(String typeFile){

        switch (typeFile) {
            case "png":
            case "jpeg":
            case "jpg":
            return true;

            default : 
            return false;
        }
    }
  

}
