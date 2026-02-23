package com.bancointernacional.plataformaBI.service.report.impl;

import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.model.report.table.TableView;
import com.bancointernacional.plataformaBI.domain.report.bbb.AppendixForm;
import com.bancointernacional.plataformaBI.repository.period.UserAnswerRepository;
import com.bancointernacional.plataformaBI.service.report.AppendixFormService;
import com.bancointernacional.plataformaBI.util.TableBuilder;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;

import static com.bancointernacional.plataformaBI.enums.AnswerTypes.TABLE;

@Service
public class AppendixServiceImpl implements AppendixFormService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Override
    public AppendixForm fullFillQuestionerData(String policyQuestionaryId) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByPolicyQuestionaryId(policyQuestionaryId);
        AppendixForm appendixForm = new AppendixForm();
        Map<String, BiConsumer<AppendixForm, String>> setterMap = createSetterMap();
        List<UserAnswer> tableAnswers = new ArrayList<>();
        for (UserAnswer answer : userAnswers) {
            if (answer == null || answer.getDocumentId() == null) {
                continue;
            }
            String documentId = answer.getDocumentId();
            String answerContent = "";
            if(!answer.getAnswerType().equals(TABLE.getWord())){
                if(UUIDValidator.isValidUUID(documentId)){
                    Optional<UserAnswer> optionalUserAnswer = userAnswerRepository.findById(UUID.fromString(documentId));
                    if(optionalUserAnswer.isPresent()){
                        UserAnswer temp = optionalUserAnswer.get();
                        answerContent = temp.getAnswerText();
                        if(answerContent.isEmpty() && !answer.getAnswerText().isEmpty()){
                            answerContent = answer.getAnswerText();
                        }
                        documentId = temp.getDocumentId();
                    }
                }else{
                    answerContent = answer.getAnswerText();
                }
                BiConsumer<AppendixForm, String> setter = setterMap.get(documentId);
                if (setter != null) {
                    setter.accept(appendixForm, answerContent);
                } else {
                    System.out.println("Warning: No setter found for documentId: " + documentId);
                }
            }else {
                tableAnswers.add(answer);
            }
        }
        Map<String, TableView> tables = TableBuilder.buildTables(tableAnswers);
        appendixForm.setTables(tables);
        return appendixForm;
    }

    private void assignTablesToFields(AppendixForm appendixForm, Map<String, TableView> tables, Map<String, String> tableDocumentIds) {
        for (Map.Entry<String, String> entry : tableDocumentIds.entrySet()) {
            String documentId = entry.getKey();
            String tableKey = entry.getValue();
            TableView table = tables.get(tableKey);
            if (table != null) {
                System.out.println("Table found for documentId: " + documentId + " with tableKey: " + tableKey);
            }
        }
    }

    private Map<String, BiConsumer<AppendixForm, String>> createSetterMap() {
        Map<String, BiConsumer<AppendixForm, String>> map = new HashMap<>();
        map.put("Anexo3pis1p12", AppendixForm::setAnexo3pis1p12);
        map.put("Anexo3pis1p12normal", AppendixForm::setAnexo3pis1p12normal);
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
        map.put("Anexo3pis1p12a10g", AppendixForm::setAnexo3pis1p12a10g);
        map.put("Anexo3pis1p12b", AppendixForm::setAnexo3pis1p12b);
        map.put("Anexo3pis1p12bnormal", AppendixForm::setAnexo3pis1p12bnormal);
        map.put("Anexo3pis1p12c", AppendixForm::setAnexo3pis1p12c);
        map.put("Anexo3pis1p12cnormal", AppendixForm::setAnexo3pis1p12cnormal);
        map.put("Anexo3pis1p12c", AppendixForm::setAnexo3pis1p12c);
        map.put("Anexo3pis1p12d", AppendixForm::setAnexo3pis1p12d);
        map.put("Anexo3pis1p12e", AppendixForm::setAnexo3pis1p12e);
        map.put("Anexo3pis1p12f", AppendixForm::setAnexo3pis1p12f);
        map.put("Anexo3pis1p12g", AppendixForm::setAnexo3pis1p12g);
        map.put("Anexo3pis1p12g2", AppendixForm::setAnexo3pis1p12g2);
        return map;
    }

}
