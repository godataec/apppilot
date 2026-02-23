package com.bancointernacional.plataformaBI.services.report.impl;

import com.bancointernacional.plataformaBI.models.DTO.AnswerDTO;
import com.bancointernacional.plataformaBI.models.DTO.QuestionDTO;
import com.bancointernacional.plataformaBI.models.report.cyber.CyberRiskComplementaryInfoForm;
import com.bancointernacional.plataformaBI.services.report.CyberRiskComplementaryService;
import com.bancointernacional.plataformaBI.services.serviceInterface.AnswerService;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionService;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class CyberRiskComplementaryServiceImpl implements CyberRiskComplementaryService {

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;


    @Override
    public CyberRiskComplementaryInfoForm createSampleForm() {
        CyberRiskComplementaryInfoForm cyberRiskData = new CyberRiskComplementaryInfoForm();
        cyberRiskData.setCyberriskcompletes1p1r1("250,000");
        cyberRiskData.setCyberriskcompletes1p1r2("175,000");
        cyberRiskData.setCyberriskcompletes1p1r3("50,000");

        cyberRiskData.setCyberriskcompletes1p1r4("30,000");
        cyberRiskData.setCyberriskcompletes1p1r5("505,000");
        cyberRiskData.setCyberriskcompletes1p2(
                "www.bancointernacional.com;" +
                        "app.bancointernacional.com;" +
                        "online.bancointernacional.com;" +
                        "bi.com.ec;" +
                        "bancointernacional.ec"
        );

        cyberRiskData.setCyberriskcompletes1p3(
                "Sí, confirmamos que el sistema informático del Banco Internacional está completamente " +
                        "segregado e independiente de los sistemas de los otros bancos del Grupo IF. " +
                        "Contamos con infraestructura dedicada, firewalls independientes y políticas " +
                        "de acceso específicas que garantizan esta separación."
        );

        cyberRiskData.setCyberriskcompletes1p4(
                "Confirmamos que todas las Entidades por asegurarse son 100% privadas y no están " +
                        "controladas por ningún Gobierno o Estado. El capital accionario es completamente " +
                        "privado y la gestión administrativa es independiente de cualquier entidad gubernamental."
        );
        cyberRiskData.setElaboradoPor("Juan Pérez Rodríguez");
        cyberRiskData.setFuente("Departamento de Seguridad Informática y Gestión de Riesgos");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        cyberRiskData.setFechaElaboracion(dateFormat.format(new Date()));

        return cyberRiskData;
    }

    @Override
    public CyberRiskComplementaryInfoForm fullFillQuestionerData(String idQuest) {
        // Get questions and answers as before
        List<QuestionDTO> procesosQuest = questionService.getQuestionByCharacters(idQuest, 1, 999);
        List<Integer> answersIds = procesosQuest.stream().map(QuestionDTO::getIdQuestion).collect(Collectors.toList());

        ArrayList<AnswerDTO> answersFound = new ArrayList<AnswerDTO>();
        for(int i = 0; i < answersIds.size(); i++) {
            List<AnswerDTO> listOfAnswer = answerService.findByProcessAndQuestion(
                    UUID.fromString(idQuest), Long.valueOf(answersIds.get(i)));
            answersFound.addAll(listOfAnswer);
        }

        CyberRiskComplementaryInfoForm cyberRiskComplementaryInfoForm = new CyberRiskComplementaryInfoForm();

        Map<String, BiConsumer<CyberRiskComplementaryInfoForm, String>> setterMap = createSetterMap();
        for (AnswerDTO answer : answersFound) {
            if (answer == null || answer.getDocumentId() == null) {
                continue;
            }

            String documentId = answer.getDocumentId();
            String answerContent = "";
            if(UUIDValidator.isValidUUID(documentId)){
                AnswerDTO temp = answerService.findQuestionByAnswer(UUID.fromString(documentId));
                answerContent  = temp.getAnswerText();
                if(answerContent.isEmpty() && !answer.getAnswerText().isEmpty()){
                    answerContent = answer.getAnswerText();
                }
                documentId = temp.getDocumentId();
            }else{
                answerContent  = answer.getAnswerText();
            }


            BiConsumer<CyberRiskComplementaryInfoForm, String> setter = setterMap.get(documentId);
            if (setter != null) {
                setter.accept(cyberRiskComplementaryInfoForm, answerContent);
            } else {
                System.out.println("Warning: No setter found for documentId: " + documentId);
            }
        }

        return cyberRiskComplementaryInfoForm;
    }

    private Map<String, BiConsumer<CyberRiskComplementaryInfoForm, String>> createSetterMap() {
        Map<String, BiConsumer<CyberRiskComplementaryInfoForm, String>> map = new HashMap<>();

        map.put("Cyberriskcompletes1p1r1", CyberRiskComplementaryInfoForm::setCyberriskcompletes1p1r1);
        map.put("Cyberriskcompletes1p1r2", CyberRiskComplementaryInfoForm::setCyberriskcompletes1p1r2);
        map.put("Cyberriskcompletes1p1r3", CyberRiskComplementaryInfoForm::setCyberriskcompletes1p1r3);
        map.put("Cyberriskcompletes1p1r4", CyberRiskComplementaryInfoForm::setCyberriskcompletes1p1r4);
        map.put("Cyberriskcompletes1p1r5", CyberRiskComplementaryInfoForm::setCyberriskcompletes1p1r5);
        map.put("Cyberriskcompletes1p2", CyberRiskComplementaryInfoForm::setCyberriskcompletes1p2);
        map.put("Cyberriskcompletes1p3", CyberRiskComplementaryInfoForm::setCyberriskcompletes1p3);
        map.put("Cyberriskcompletes1p4", CyberRiskComplementaryInfoForm::setCyberriskcompletes1p4);
        map.put("ElaboradoPor", CyberRiskComplementaryInfoForm::setElaboradoPor);
        map.put("Fuente", CyberRiskComplementaryInfoForm::setFuente);
        map.put("FechaElaboracion", CyberRiskComplementaryInfoForm::setFechaElaboracion);

        return map;
    }

}
