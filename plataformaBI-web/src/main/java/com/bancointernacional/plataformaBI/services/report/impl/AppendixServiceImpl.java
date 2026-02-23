package com.bancointernacional.plataformaBI.services.report.impl;

import com.bancointernacional.plataformaBI.models.DTO.AnswerDTO;
import com.bancointernacional.plataformaBI.models.DTO.QuestionDTO;
import com.bancointernacional.plataformaBI.models.report.bbb.AppendixForm;
import com.bancointernacional.plataformaBI.models.report.bbb.CovidForm;
import com.bancointernacional.plataformaBI.services.report.AppendixFormService;
import com.bancointernacional.plataformaBI.services.serviceInterface.AnswerService;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionService;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class AppendixServiceImpl implements AppendixFormService {

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;


    @Override
    public AppendixForm createSampleForm() {

        AppendixForm dataFormMock = new AppendixForm();

        dataFormMock.setAnexo3pis1p12("true");
        dataFormMock.setAnexo3pis2p12r("Proporcionamos servicios de banca por internet a nuestros clientes.");

        dataFormMock.setAnexo3pis1p12a1("SI / YES");
        dataFormMock.setAnexo3pis1p12a2("SI / YES");
        dataFormMock.setAnexo3pis1p12a3("SI / YES");
        dataFormMock.setAnexo3pis1p12a4("NO");
        dataFormMock.setAnexo3pis1p12a4("NO");
        dataFormMock.setAnexo3pis1p12a6("NO");
        dataFormMock.setAnexo3pis1p12a7("SI / YES");
        dataFormMock.setAnexo3pis1p12a8("NO");
        dataFormMock.setAnexo3pis1p12a9("NO");
        dataFormMock.setAnexo3pis1p12a10a("SI / YES");


        dataFormMock.setAnexo3pis1p12b("Autenticación de dos factores con nombre de usuario/contraseña y OTP (contraseña de un solo uso).");
        dataFormMock.setAnexo3pis1p12c("Encriptación SSL/TLS y firma de transacciones.");
        dataFormMock.setAnexo3pis1p12d("Sí, utilizamos cookies de seguimiento y monitoreo de sesiones.");
        dataFormMock.setAnexo3pis1p12e("Sí, contamos con términos y condiciones formales que los usuarios deben aceptar.");
        dataFormMock.setAnexo3pis1p12f("Sí, contamos con procedimientos KYC (Conozca a su Cliente) y monitoreo de geolocalización.");

        dataFormMock.setAnexo3pis1p12g("Sí, utilizamos soluciones antivirus de nivel empresarial.");
        dataFormMock.setAnexo3pis1p12g2("Sí, las actualizaciones se aplican automáticamente a diario.");
        return dataFormMock;
    }

    @Override
    public AppendixForm fullFillQuestionerData(String idQuest) {
        List<QuestionDTO> procesosQuest = questionService.getQuestionByCharacters(idQuest, 1, 999);
        List<Integer> answersIds = procesosQuest.stream().map(QuestionDTO::getIdQuestion).collect(Collectors.toList());

        ArrayList<AnswerDTO> answersFound = new ArrayList<AnswerDTO>();
        for(int i = 0; i < answersIds.size(); i++) {
            List<AnswerDTO> listOfAnswer = answerService.findByProcessAndQuestion(
                    UUID.fromString(idQuest), Long.valueOf(answersIds.get(i)));
            answersFound.addAll(listOfAnswer);
        }

        AppendixForm appendixForm = new AppendixForm();

        Map<String, BiConsumer<AppendixForm, String>> setterMap = createSetterMap();
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

            BiConsumer<AppendixForm, String> setter = setterMap.get(documentId);
            if (setter != null) {
                setter.accept(appendixForm, answerContent);
            } else {
                System.out.println("Warning: No setter found for documentId: " + documentId);
            }
        }

        return appendixForm;
    }

    private Map<String, BiConsumer<AppendixForm, String>> createSetterMap() {
        Map<String, BiConsumer<AppendixForm, String>> map = new HashMap<>();

        map.put("Anexo3pis1p12", AppendixForm::setAnexo3pis1p12);
        map.put("Anexo3pis2p12r", AppendixForm::setAnexo3pis2p12r);

        map.put("Anexo3pis1p12a1", AppendixForm::setAnexo3pis1p12a1);
        map.put("Anexo3pis1p12a2", AppendixForm::setAnexo3pis1p12a2);
        map.put("Anexo3pis1p12a3", AppendixForm::setAnexo3pis1p12a3);
        map.put("Anexo3pis1p12a4", AppendixForm::setAnexo3pis1p12a4);
        map.put("Anexo3pis1p12a6", AppendixForm::setAnexo3pis1p12a6);
        map.put("Anexo3pis1p12a7", AppendixForm::setAnexo3pis1p12a7);
        map.put("Anexo3pis1p12a8", AppendixForm::setAnexo3pis1p12a8);
        map.put("Anexo3pis1p12a9", AppendixForm::setAnexo3pis1p12a9);

        map.put("Anexo3pis1p12a10a", AppendixForm::setAnexo3pis1p12a10a);
        map.put("Anexo3pis1p12a10b", AppendixForm::setAnexo3pis1p12a10b);
        map.put("Anexo3pis1p12a10c", AppendixForm::setAnexo3pis1p12a10c);
        map.put("Anexo3pis1p12a10d", AppendixForm::setAnexo3pis1p12a10d);
        map.put("Anexo3pis1p12a10e", AppendixForm::setAnexo3pis1p12a10e);
        map.put("Anexo3pis1p12a10f", AppendixForm::setAnexo3pis1p12a10f);
        map.put("Anexo3pis1p12a10g", AppendixForm::setAnexo3pis1p12a10g);

        map.put("Anexo3pis1p12b", AppendixForm::setAnexo3pis1p12b);
        map.put("Anexo3pis1p12c", AppendixForm::setAnexo3pis1p12c);
        map.put("Anexo3pis1p12d", AppendixForm::setAnexo3pis1p12d);
        map.put("Anexo3pis1p12e", AppendixForm::setAnexo3pis1p12e);
        map.put("Anexo3pis1p12f", AppendixForm::setAnexo3pis1p12f);
        map.put("Anexo3pis1p12g", AppendixForm::setAnexo3pis1p12g);
        map.put("Anexo3pis1p12g2", AppendixForm::setAnexo3pis1p12g2);

        return map;
    }


}
