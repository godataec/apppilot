package com.bancointernacional.plataformaBI.service.report.impl;

import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.report.bbb.AppendixForm;
import com.bancointernacional.plataformaBI.domain.report.cyber.CyberRiskPersonalDataForm;
import com.bancointernacional.plataformaBI.repository.period.UserAnswerRepository;
import com.bancointernacional.plataformaBI.service.report.CyberRiskPersonalService;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class CyberRiskPersonalServiceImpl implements CyberRiskPersonalService {


    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Override
    public CyberRiskPersonalDataForm fullFillQuestionerData(String policyQuestionaryId) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByPolicyQuestionaryId(policyQuestionaryId);
        CyberRiskPersonalDataForm cyberRiskPersonalDataForm = new CyberRiskPersonalDataForm();
        Map<String, BiConsumer<CyberRiskPersonalDataForm, String>> setterMap = createSetterMap();
        for (UserAnswer answer : userAnswers) {
            if (answer == null || answer.getDocumentId() == null) {
                continue;
            }
            String documentId = answer.getDocumentId();
            String answerContent = "";
            if(UUIDValidator.isValidUUID(documentId)){
                Optional<UserAnswer> optionalUserAnswer = userAnswerRepository.findById(UUID.fromString(documentId));
                if(optionalUserAnswer.isPresent()){
                    UserAnswer temp = optionalUserAnswer.get();
                    answerContent  = temp.getAnswerText();
                    if(answerContent.isEmpty() && !answer.getAnswerText().isEmpty()){
                        answerContent = answer.getAnswerText();
                    }
                    documentId = temp.getDocumentId();
                }
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
        map.put("cyberRiskFnormal", CyberRiskPersonalDataForm::setCyberRiskFnormal);
        map.put("cyberRiskI", CyberRiskPersonalDataForm::setCyberRiskI);
        map.put("cyberRiskInormal", CyberRiskPersonalDataForm::setCyberRiskInormal);
        map.put("cyberRiskK", CyberRiskPersonalDataForm::setCyberRiskK);
        map.put("cyberRiskL", CyberRiskPersonalDataForm::setCyberRiskL);
        map.put("cyberRiskLnormal", CyberRiskPersonalDataForm::setCyberRiskLnormal);
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
