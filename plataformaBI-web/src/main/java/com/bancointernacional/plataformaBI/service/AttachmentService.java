package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.dto.period.attachment.response.AttachmentDTO;
import com.bancointernacional.plataformaBI.domain.model.period.*;
import com.bancointernacional.plataformaBI.domain.model.period.Process;
import com.bancointernacional.plataformaBI.repository.period.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ProcessPolicyRepository processPolicyRepository;

    @Autowired
    private PolicyQuestionaryRepository policyQuestionaryRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;


    public Attachment storeService(AttachmentDTO attachFileDTO) throws IOException {
        Attachment attachFile = new Attachment();
        if (Objects.nonNull(attachFileDTO.getIdProcess())) attachFile.setProcessId(attachFileDTO.getIdProcess());
        if (Objects.nonNull(attachFileDTO.getIdPolicy())) attachFile.setProcessPolicyId(attachFileDTO.getIdPolicy());
        if (Objects.nonNull(attachFileDTO.getIdQuest())) attachFile.setPolicyQuestionaryId(attachFileDTO.getIdQuest());
        if (Objects.nonNull(attachFileDTO.getIdQuestion())) attachFile.setUserAnswerId(attachFileDTO.getIdQuestion());
        if( Objects.nonNull(attachFileDTO.getIdAction())) attachFile.setActionId(attachFileDTO.getIdAction());
        attachFile.setAttachmentName(attachFileDTO.getNameFile());
        attachFile.setAttachmentType(attachFileDTO.getTypeFile());
        attachFile.setAttachmentByte(attachFileDTO.getDataFile());

        if (verifyImage(attachFileDTO.getTypeFile().trim()) && attachFileDTO.getIdQuestion() != null && attachFileDTO.getIdQuest() != null) {
            try {
                attachFile.setAttachmentPreviewByte(resizeImage(attachFile.getAttachmentByte(), 30, 30));
            } catch (Exception e) {
                throw new EntityNotFoundException("No se pudo convertir la Imagen: " + attachFileDTO.getNameFile() + ",Error:" + e);
            }
        } else {
            attachFile.setAttachmentPreviewByte(null);
        }
        attachFile.setUploadDate(attachFileDTO.getDateUploadFile());
        return attachmentRepository.save(attachFile);
    }


    public Optional<Attachment> getFile(UUID attachmentId) throws FileNotFoundException {
        Optional<Attachment> foundFile = attachmentRepository.findById(attachmentId);
        if (foundFile.isPresent()) {
            return foundFile;
        }
        throw new FileNotFoundException();
    }

    public Optional<AttachmentDTO> getPhotoPreview(UUID attachmentId) throws FileNotFoundException {
        Optional<AttachmentDTO> foundFile = attachmentRepository.findByIdImagePreview(attachmentId);
        if (foundFile.isPresent()) {
            return foundFile;
        }
        throw new FileNotFoundException("No pudo recuperar la imagen con id" + attachmentId);

    }

    @Transactional(readOnly = true)
    public List<AttachmentDTO> getInfoFilesByProcessId(UUID processId) {
        try {
            return attachmentRepository.findByProcessId(processId);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar informacion de archivo por idProcess: " + e.getLocalizedMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<AttachmentDTO> getInfoFilesByProcessPolicyId(UUID processPolicyId) {
        try {
            return attachmentRepository.findByProcessPolicyId(processPolicyId);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar informacion de archivo por idPolicy: " + e.getLocalizedMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<AttachmentDTO> getInfoFilesByPolicyQuestionaryId(UUID policyQuestionaryId) {
        try {
            return attachmentRepository.findByPolicyQuestionaryId(policyQuestionaryId);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar informacion de archivo por idPolicy: " + e.getLocalizedMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<AttachmentDTO> getInfoFilesByUserAnswerId(UUID userAnswerId) {
        try {
            List<AttachmentDTO> attachmentDTOS= attachmentRepository.findByUserAnswerId(userAnswerId);
            for(AttachmentDTO attachmentDTO : attachmentDTOS) {
                switch (attachmentDTO.getTypeFile()) {
                    case "png":
                    case "jpeg":
                    case "jpg":
                        Optional<AttachmentDTO> imageFound = getPhotoPreview(attachmentDTO.getIdFile());
                        imageFound.ifPresent(dto -> {
                            try {
                                if(imageFound.get().getDataFile()!=null){
                                    attachmentDTO.setDataFile(resizeImage(imageFound.get().getDataFile(), 30, 30));
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
            return attachmentDTOS;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar informacion de archivo por userAnswerId: " + e.getLocalizedMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<AttachmentDTO> getInfoFilesByActionId(UUID actionId) {
        try {
            return attachmentRepository.findByActionId(actionId);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar informacion de archivo por actionId: " + e.getLocalizedMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<AttachmentDTO> getInfoFilesByPolicyQuestionaryAndUserAnswerId(UUID policyQuestionaryId, UUID userAnswerId) {
        try {
            return attachmentRepository.findByPolicyQuestionaryIdUserAnswerId(policyQuestionaryId, userAnswerId);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar informacion de archivo por policyQuestionaryId and userAnswerId: " + e.getLocalizedMessage());
        }
    }

    public Optional<AttachmentDTO> DeleteFileById(UUID idFile) {
        try {
            Optional<AttachmentDTO> fileFound = attachmentRepository.findInfoByIdFile(idFile);
            if (fileFound.isPresent()) {
                attachmentRepository.deleteById(idFile);
                return fileFound;
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al borrar la cuenta: " + e.getLocalizedMessage());
        }
    }


    public static byte[] resizeImage(byte[] originalImage, int width, int height) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(originalImage);
        BufferedImage originalBufferedImage = ImageIO.read(inputStream);
        if (originalBufferedImage == null) {
            throw new RuntimeException("Error al redimensionar imagen");
        }
        BufferedImage resizedBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedBufferedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(originalBufferedImage, 0, 0, width, height, null);
        graphics2D.dispose();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedBufferedImage, "jpeg", outputStream); // Cambia "jpeg" al formato que necesites
        return outputStream.toByteArray();
    }

    boolean verifyImage(String typeFile) {
        switch (typeFile) {
            case "png":
            case "jpeg":
            case "jpg":
                return true;
            default:
                return false;
        }
    }


}
