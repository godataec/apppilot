package com.bancointernacional.plataformaBI.services.report.impl;

import com.bancointernacional.plataformaBI.models.DTO.AnswerDTO;
import com.bancointernacional.plataformaBI.models.DTO.QuestionDTO;
import com.bancointernacional.plataformaBI.models.report.bbb.CrimeForm;
import com.bancointernacional.plataformaBI.models.report.cyber.CyberRiskPersonalDataForm;
import com.bancointernacional.plataformaBI.services.report.CyberRiskPersonalService;
import com.bancointernacional.plataformaBI.services.serviceInterface.AnswerService;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionService;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class CyberRiskPersonalServiceImpl implements CyberRiskPersonalService {


    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Override
    public CyberRiskPersonalDataForm fullFillQuestionerData(String idQuest) {
        List<QuestionDTO> procesosQuest = questionService.getQuestionByCharacters(idQuest, 1, 999);
        List<Integer> answersIds = procesosQuest.stream().map(QuestionDTO::getIdQuestion).collect(Collectors.toList());

        ArrayList<AnswerDTO> answersFound = new ArrayList<AnswerDTO>();
        for(int i = 0; i < answersIds.size(); i++) {
            List<AnswerDTO> listOfAnswer = answerService.findByProcessAndQuestion(
                    UUID.fromString(idQuest), Long.valueOf(answersIds.get(i)));
            answersFound.addAll(listOfAnswer);
        }

        CyberRiskPersonalDataForm cyberRiskPersonalDataForm = new CyberRiskPersonalDataForm();

        Map<String, BiConsumer<CyberRiskPersonalDataForm, String>> setterMap = createSetterMap();
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


            BiConsumer<CyberRiskPersonalDataForm, String> setter = setterMap.get(documentId);
            if (setter != null) {
                setter.accept(cyberRiskPersonalDataForm, answerContent);
            } else {
                System.out.println("Warning: No setter found for documentId: " + documentId);
            }
        }

        return cyberRiskPersonalDataForm;
    }

    private Map<String, BiConsumer<CyberRiskPersonalDataForm, String>> createSetterMap() {
        Map<String, BiConsumer<CyberRiskPersonalDataForm, String>> map = new HashMap<>();

        map.put("cyberRiskA", CyberRiskPersonalDataForm::setCyberRiskA);
        map.put("cyberRiskB", CyberRiskPersonalDataForm::setCyberRiskB);
        map.put("cyberRiskC", CyberRiskPersonalDataForm::setCyberRiskC);
        map.put("cyberRiskD", CyberRiskPersonalDataForm::setCyberRiskD);
        map.put("cyberRiskE", CyberRiskPersonalDataForm::setCyberRiskE);
        map.put("cyberRiskF", CyberRiskPersonalDataForm::setCyberRiskF);
        map.put("cyberRiskG", CyberRiskPersonalDataForm::setCyberRiskG);
        map.put("cyberRiskH", CyberRiskPersonalDataForm::setCyberRiskH);
        map.put("cyberRiskI", CyberRiskPersonalDataForm::setCyberRiskI);
        map.put("cyberRiskJ", CyberRiskPersonalDataForm::setCyberRiskJ);
        map.put("cyberRiskK", CyberRiskPersonalDataForm::setCyberRiskK);
        map.put("cyberRiskL", CyberRiskPersonalDataForm::setCyberRiskL);
        map.put("cyberRiskM", CyberRiskPersonalDataForm::setCyberRiskM);
        map.put("cyberRiskN", CyberRiskPersonalDataForm::setCyberRiskN);
        map.put("cyberRiskO", CyberRiskPersonalDataForm::setCyberRiskO);
        map.put("cyberRiskP", CyberRiskPersonalDataForm::setCyberRiskP);
        map.put("cyberRiskQ", CyberRiskPersonalDataForm::setCyberRiskQ);
        map.put("cyberRiskR", CyberRiskPersonalDataForm::setCyberRiskR);
        map.put("cyberRiskS", CyberRiskPersonalDataForm::setCyberRiskS);
        map.put("cyberRiskT", CyberRiskPersonalDataForm::setCyberRiskT);

        return map;
    }

    @Override
    public CyberRiskPersonalDataForm createSampleForm() {
        CyberRiskPersonalDataForm formulario = new CyberRiskPersonalDataForm();

        // Información general
        formulario.setCyberRiskA("Banco Internacional S.A.");
        formulario.setCyberRiskB("1250000");

        // Registros por región
        formulario.setCyberRiskC("150000");
        formulario.setCyberRiskD("350000");
        formulario.setCyberRiskE("750000");

        // Información comercial y marketing
        formulario.setCyberRiskF("X");
        formulario.setCyberRiskG("");
        formulario.setCyberRiskH("850000");

        // Información financiera o de Tarjetas de Crédito
        formulario.setCyberRiskI("X");
        formulario.setCyberRiskJ("");
        formulario.setCyberRiskK("950000");

        // Información de salud
        formulario.setCyberRiskL("");
        formulario.setCyberRiskM("X");
        formulario.setCyberRiskN("0");

        // Otros
        formulario.setCyberRiskO("Información de contacto y datos demográficos");

        // Personas Naturales
        formulario.setCyberRiskP("450000");
        formulario.setCyberRiskQ("350000");
        formulario.setCyberRiskR("0");

        // Persona Jurídica
        formulario.setCyberRiskS("450000");

        // Total
        formulario.setCyberRiskT("1250000");

        return formulario;
    }



}
