package com.bancointernacional.plataformaBI.service.report.impl;

import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.model.report.table.TableView;
import com.bancointernacional.plataformaBI.domain.report.bbb.AppendixForm;
import com.bancointernacional.plataformaBI.domain.report.cyber.CyberRiskForm;
import com.bancointernacional.plataformaBI.repository.period.UserAnswerRepository;
import com.bancointernacional.plataformaBI.service.report.CyberRiskService;
import com.bancointernacional.plataformaBI.util.TableBuilder;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static com.bancointernacional.plataformaBI.enums.AnswerTypes.TABLE;

@Service
public class CyberRiskServiceImpl implements CyberRiskService {

    private static final Random random = new Random();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Override
    public CyberRiskForm fullFillQuestionerData(String policyQuestionaryId) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByPolicyQuestionaryId(policyQuestionaryId);
        CyberRiskForm cyberRiskForm = new CyberRiskForm();
        Map<String, BiConsumer<CyberRiskForm, String>> setterMap = createSetterMap();
        List<UserAnswer> tableAnswers = new ArrayList<>();
        for (UserAnswer answer : userAnswers) {
            if (answer == null || answer.getDocumentId() == null) {
                continue;
            }
            String documentId = answer.getDocumentId();
            String answerContent = "";
            if (!answer.getAnswerType().equals(TABLE.getWord())) {
                if (UUIDValidator.isValidUUID(documentId)) {
                    Optional<UserAnswer> optionalUserAnswer = userAnswerRepository
                            .findById(UUID.fromString(documentId));
                    if (optionalUserAnswer.isPresent()) {
                        UserAnswer temp = optionalUserAnswer.get();
                        answerContent = temp.getAnswerText();
                        if (answerContent.isEmpty() && !answer.getAnswerText().isEmpty()) {
                            answerContent = answer.getAnswerText();
                        }
                        documentId = temp.getDocumentId();
                    }
                } else {
                    answerContent = answer.getAnswerText();
                }

                BiConsumer<CyberRiskForm, String> setter = setterMap.get(documentId);
                if (setter != null) {
                    setter.accept(cyberRiskForm, answerContent);
                } else {
                    System.out.println("Warning: No setter found for documentId: " + documentId);
                }
            } else {
                tableAnswers.add(answer);
            }
        }
        Map<String, TableView> tables = TableBuilder.buildTables(tableAnswers);
        cyberRiskForm.setTables(tables);
        return cyberRiskForm;
    }

    private Map<String, BiConsumer<CyberRiskForm, String>> createSetterMap() {
        Map<String, BiConsumer<CyberRiskForm, String>> map = new HashMap<>();

        // 1. INFORMACIÓN GENERAL
        map.put("Cyberrisks1p1a", CyberRiskForm::setCyberrisks1p1a);
        map.put("Cyberrisks1p1b", CyberRiskForm::setCyberrisks1p1b);
        map.put("Cyberrisks1p1c", CyberRiskForm::setCyberrisks1p1c);
        map.put("Cyberrisks1p1d", CyberRiskForm::setCyberrisks1p1d);
        map.put("Cyberrisks1p1e", CyberRiskForm::setCyberrisks1p1e);
        map.put("Cyberrisks1p1f", CyberRiskForm::setCyberrisks1p1f);
        map.put("Cyberrisks1p1g", CyberRiskForm::setCyberrisks1p1g);
        map.put("Cyberrisks1p1h", (form, val) -> form.setCyberrisks1p1h("##yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p1hnormal", CyberRiskForm::setCyberrisks1p1hnormal);
        map.put("Cyberrisks1p1j", CyberRiskForm::setCyberrisks1p1j);
        map.put("Cyberrisks1p2b", CyberRiskForm::setCyberrisks1p2b);
        map.put("Cyberrisks1p2c", CyberRiskForm::setCyberrisks1p2c);
        map.put("Cyberrisks1p2d", CyberRiskForm::setCyberrisks1p2d);
        map.put("Cyberrisks1p2e1", CyberRiskForm::setCyberrisks1p2e1);
        map.put("Cyberrisks1p2e2", CyberRiskForm::setCyberrisks1p2e2);
        map.put("Cyberrisks1p2e3", CyberRiskForm::setCyberrisks1p2e3);

        // 3. CONTINUIDAD DE NEGOCIO
        map.put("Cyberrisks1p3a1", CyberRiskForm::setCyberrisks1p3a1);
        map.put("Cyberrisks1p3a1normal", CyberRiskForm::setCyberrisks1p3a1normal);
        map.put("Cyberrisks1p3a2", CyberRiskForm::setCyberrisks1p3a2);
        map.put("Cyberrisks1p3a3", CyberRiskForm::setCyberrisks1p3a3);
        map.put("Cyberrisks1p3a4", CyberRiskForm::setCyberrisks1p3a4);
        map.put("Cyberrisks1p3a5", CyberRiskForm::setCyberrisks1p3a5);
        map.put("Cyberrisks1p3a6", CyberRiskForm::setCyberrisks1p3a6);
        map.put("Cyberrisks1p3br1", CyberRiskForm::setCyberrisks1p3br1);
        map.put("Cyberrisks1p3br1normal", CyberRiskForm::setCyberrisks1p3br1normal);
        map.put("Cyberrisks1p3br2", CyberRiskForm::setCyberrisks1p3br2);
        map.put("Cyberrisks1p3br3", CyberRiskForm::setCyberrisks1p3br3);
        map.put("Cyberrisks1p3br4", CyberRiskForm::setCyberrisks1p3br4);
        map.put("Cyberrisks1p3br5", CyberRiskForm::setCyberrisks1p3br5);
        map.put("Cyberrisks1p3br6", CyberRiskForm::setCyberrisks1p3br6);
        map.put("Cyberrisks1p3c", (form, val) -> form.setCyberrisks1p3c("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p3d", CyberRiskForm::setCyberrisks1p3d);
        map.put("Cyberrisks1p3e", CyberRiskForm::setCyberrisks1p3e);
        map.put("Cyberrisks1p3f", CyberRiskForm::setCyberrisks1p3f);
        map.put("Cyberrisks1p3g", CyberRiskForm::setCyberrisks1p3g);
        map.put("Cyberrisks1p3h", (form, val) -> form.setCyberrisks1p3h("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p3i", (form, val) -> form.setCyberrisks1p3i("yes".equalsIgnoreCase(val)));

        // 4. SEGURIDAD DE LA RED
        map.put("Cyberrisks1p4a1", (form, val) -> form.setCyberrisks1p4a1("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p4a1normal", CyberRiskForm::setCyberrisks1p4a1normal);
        map.put("Cyberrisks1p4b", (form, val) -> form.setCyberrisks1p4b("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p4c", (form, val) -> form.setCyberrisks1p4c("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p4d", (form, val) -> form.setCyberrisks1p4d("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p4d1", CyberRiskForm::setCyberrisks1p4d1);
        map.put("Cyberrisks1p4de", (form, val) -> form.setCyberrisks1p4de("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p4df", (form, val) -> form.setCyberrisks1p4df("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p4dg", (form, val) -> form.setCyberrisks1p4dg("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p4dh", CyberRiskForm::setCyberrisks1p4dh);
        map.put("Cyberrisks1p4di", CyberRiskForm::setCyberrisks1p4di);
        map.put("Cyberrisks1p4dj", (form, val) -> form.setCyberrisks1p4dj("yes".equalsIgnoreCase(val)));

        // 5. INFORMACIÓN Y GESTIÓN DE DATOS
        map.put("Cyberrisks1p5a", (form, val) -> form.setCyberrisks1p5a("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p5b", (form, val) -> form.setCyberrisks1p5b("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p5c", (form, val) -> form.setCyberrisks1p5c("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p5d", (form, val) -> form.setCyberrisks1p5d("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p5e", (form, val) -> form.setCyberrisks1p5e("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p5f", (form, val) -> form.setCyberrisks1p5f("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p5g", (form, val) -> form.setCyberrisks1p5g("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p5h", (form, val) -> form.setCyberrisks1p5h("yes".equalsIgnoreCase(val)));

        // 6. ACTIVIDAD DE OUTSOURCING
        map.put("Cyberrisks1p7a", (form, val) -> form.setCyberrisks1p7a("##yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p7anormal", CyberRiskForm::setCyberrisks1p7anormal);
        map.put("Cyberrisks1p7b", (form, val) -> form.setCyberrisks1p7b("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p7c", (form, val) -> form.setCyberrisks1p7c("##yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p7cnormal", CyberRiskForm::setCyberrisks1p7cnormal);
        map.put("Cyberrisks1p7d", (form, val) -> form.setCyberrisks1p7d("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p7e", (form, val) -> form.setCyberrisks1p7e("##yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p7enormal", CyberRiskForm::setCyberrisks1p7enormal);
        map.put("Cyberrisks1p7f", (form, val) -> form.setCyberrisks1p7f("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p7g", (form, val) -> form.setCyberrisks1p7g("yes".equalsIgnoreCase(val)));

        // 7. SINIESTROS Y CIRCUNSTANCIAS
        map.put("Cyberrisks1p8a1", (form, val) -> form.setCyberrisks1p8a1("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p8a2", (form, val) -> form.setCyberrisks1p8a2("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p8a3", (form, val) -> form.setCyberrisks1p8a3("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p8a4", (form, val) -> form.setCyberrisks1p8a4("yes".equalsIgnoreCase(val)));
        map.put("Cyberrisks1p8a5", CyberRiskForm::setCyberrisks1p8a5);
        map.put("Cyberrisks1p8a6", (form, val) -> form.setCyberrisks1p8a6("yes".equalsIgnoreCase(val)));

        // FOOTER
        map.put("NombreApellidos", CyberRiskForm::setNombreApellidos);
        map.put("Cargo", CyberRiskForm::setCargo);
        map.put("Fecha", CyberRiskForm::setFecha);
        map.put("Firma", CyberRiskForm::setFirma);

        return map;
    }
}