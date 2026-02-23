package com.bancointernacional.plataformaBI.services.report.impl;

import com.bancointernacional.plataformaBI.models.DTO.AnswerDTO;
import com.bancointernacional.plataformaBI.models.DTO.QuestionDTO;
import com.bancointernacional.plataformaBI.models.report.cyber.CyberRiskForm;
import com.bancointernacional.plataformaBI.services.report.CyberRiskService;
import com.bancointernacional.plataformaBI.services.serviceInterface.AnswerService;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionService;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class CyberRiskServiceImpl implements CyberRiskService {

    private static final Random random = new Random();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Override
    public CyberRiskForm createSampleForm() {
        CyberRiskForm form = new CyberRiskForm();

        // 1. INFORMACIÓN GENERAL
        form.setCyberrisks1p1a("Empresa Tecnológica Internacional S.A.");
        form.setCyberrisks1p1B("B12345678");
        form.setCyberrisks1p1c("Calle Innovación 123, 28001 Madrid");
        form.setCyberrisks1p1d("contacto@empresatecnologica.com");
        form.setCyberrisks1p1e("www.empresatecnologica.com");
        form.setCyberrisks1p1f(LocalDate.now().minusYears(10).format(dateFormatter));
        form.setCyberrisks1p1g("250");
        form.setCyberrisks1p1h("Response");
        form.setCyberrisks1p1j("Desarrollo de software y soluciones tecnológicas para el sector financiero. " +
                "Especialistas en seguridad informática y sistemas de gestión de datos.");

        // 2. INFORMACIÓN FINANCIERA
        form.setCyberrisks1p2ar1("15.000.000 €");
        form.setCyberrisks1p2ar2("14.500.000 €");
        form.setCyberrisks1p2ar3("16.200.000 €");
        form.setCyberrisks1p2b("85% España, 15% Internacional");
        form.setCyberrisks1p2c("35%");
        form.setCyberrisks1p2d("2.500 €");
        form.setCyberrisks1p2e1("5%");
        form.setCyberrisks1p2e2("España");
        form.setCyberrisks1p2e3("10%");

        // 3. CONTINUIDAD DE NEGOCIO
        form.setCyberrisks1p3a1("Impacto mínimo");
        form.setCyberrisks1p3a2("Impacto moderado");
        form.setCyberrisks1p3a3("Impacto significativo");
        form.setCyberrisks1p3a4("Impacto severo");
        form.setCyberrisks1p3a5("Impacto crítico");
        form.setCyberrisks1p3a6("N/A");

        form.setCyberrisks1p3br1("Sin afectación");
        form.setCyberrisks1p3br2("Afectación leve");
        form.setCyberrisks1p3br3("Afectación moderada");
        form.setCyberrisks1p3br4("Afectación importante");
        form.setCyberrisks1p3br5("Afectación crítica");
        form.setCyberrisks1p3br6("N/A");

        form.setCyberrisks1p3c(true);
        form.setCyberrisks1p3d("Anualmente");
        form.setCyberrisks1p3e(true);
        form.setCyberrisks1p3f(LocalDate.now().minusMonths(3).format(dateFormatter));
        form.setCyberrisks1p3g(false);
        form.setCyberrisks1p3h(true);

        // 4. SEGURIDAD DE LA RED
        form.setCyberrisks1p4a1(true);
        form.setCyberrisks1p4a2("Director de Seguridad de la Información (CISO)");
        form.setCyberrisks1p4b(true);
        form.setCyberrisks1p4c(true);
        form.setCyberrisks1p4d(true);
        form.setCyberrisks1p4d1("Solo para personal autorizado con necesidad justificada");
        form.setCyberrisks1p4de(true);
        form.setCyberrisks1p4df(true);
        form.setCyberrisks1p4dg(true);
        form.setCyberrisks1p4dh(true);
        form.setCyberrisks1p4di(true);
        form.setCyberrisks1p4dj(true);

        // 5. INFORMACIÓN Y GESTIÓN DE DATOS
        form.setCyberrisks1p5a(true);
        form.setCyberrisks1p5b(true);
        form.setCyberrisks1p5c(true);
        form.setCyberrisks1p5d(true);
        form.setCyberrisks1p5e(true);
        form.setCyberrisks1p5f(true);
        form.setCyberrisks1p5g(true);
        form.setCyberrisks1p5h(true);

        // 6. ACTIVIDAD DE OUTSOURCING
        form.setCyberrisks1p7a(true);
        form.setCyberrisks1p7aDetails("Servicios de hosting y mantenimiento de infraestructura cloud");
        form.setCyberrisks1p7b(true);
        form.setCyberrisks1p7c(true);
        form.setCyberrisks1p7d(true);
        form.setCyberrisks1p7e(true);
        form.setCyberrisks1p7eDetails("Política de seguridad en cloud basada en ISO 27001 y NIST");
        form.setCyberrisks1p7f(true);
        form.setCyberrisks1p7g(true);

        // 7. SINIESTROS Y CIRCUNSTANCIAS
        form.setCyberrisks1p8a1(false);
        form.setCyberrisks1p8a2(true);
        form.setCyberrisks1p8a3(false);
        form.setCyberrisks1p8a4(false);
        form.setCyberrisks1p8a5("Experimentamos una caída de red planificada durante una actualización " +
                "de sistemas que se extendió 2 horas más de lo previsto. No hubo pérdida de datos ni " +
                "accesos no autorizados.");
        form.setCyberrisks1p8a6(false);

        // FOOTER
        form.setNombreApellidos("Carlos Rodríguez Martínez");
        form.setCargo("Director de Tecnología");
        form.setFecha(LocalDate.now().format(dateFormatter));
        form.setFirma("CRodriguez");

        return form;
    }

    @Override
    public CyberRiskForm fullFillQuestionerData(String idQuest) {
        // Get questions and answers as before
        List<QuestionDTO> procesosQuest = questionService.getQuestionByCharacters(idQuest, 1, 999);
        List<Integer> answersIds = procesosQuest.stream().map(QuestionDTO::getIdQuestion).collect(Collectors.toList());

        ArrayList<AnswerDTO> answersFound = new ArrayList<AnswerDTO>();
        for(int i = 0; i < answersIds.size(); i++) {
            List<AnswerDTO> listOfAnswer = answerService.findByProcessAndQuestion(
                    UUID.fromString(idQuest), Long.valueOf(answersIds.get(i)));
            answersFound.addAll(listOfAnswer);
        }

        CyberRiskForm cyberRiskForm = new CyberRiskForm();

        Map<String, BiConsumer<CyberRiskForm, String>> setterMap = createSetterMap();
        for (AnswerDTO answer : answersFound) {
            if (answer == null || answer.getDocumentId() == null) {
                continue;
            }

            String documentId = answer.getDocumentId();
            String answerContent = "";
            if(UUIDValidator.isValidUUID(documentId)){
                AnswerDTO temp = answerService.findQuestionByAnswer(UUID.fromString(documentId));
                answerContent  = temp.getAnswerText();
                documentId = temp.getDocumentId();
            }else{
                answerContent  = answer.getAnswerText();
            }


            BiConsumer<CyberRiskForm, String> setter = setterMap.get(documentId);
            if (setter != null) {
                setter.accept(cyberRiskForm, answerContent);
            } else {
                System.out.println("Warning: No setter found for documentId: " + documentId);
            }
        }

        return cyberRiskForm;
    }

    private Map<String, BiConsumer<CyberRiskForm, String>> createSetterMap() {
        Map<String, BiConsumer<CyberRiskForm, String>> map = new HashMap<>();

        // 1. INFORMACIÓN GENERAL
        map.put("Cyberrisks1p1a", CyberRiskForm::setCyberrisks1p1a);
        map.put("Cyberrisks1p1B", CyberRiskForm::setCyberrisks1p1B);
        map.put("Cyberrisks1p1c", CyberRiskForm::setCyberrisks1p1c);
        map.put("Cyberrisks1p1d", CyberRiskForm::setCyberrisks1p1d);
        map.put("Cyberrisks1p1e", CyberRiskForm::setCyberrisks1p1e);
        map.put("Cyberrisks1p1f", CyberRiskForm::setCyberrisks1p1f);
        map.put("Cyberrisks1p1g", CyberRiskForm::setCyberrisks1p1g);
        map.put("Cyberrisks1p1j", CyberRiskForm::setCyberrisks1p1j);
        map.put("Cyberrisks1p1h", CyberRiskForm::setCyberrisks1p1h);

        // 2. INFORMACIÓN FINANCIERA
        map.put("Cyberrisks1p2ar1", CyberRiskForm::setCyberrisks1p2ar1);
        map.put("Cyberrisks1p2ar2", CyberRiskForm::setCyberrisks1p2ar2);
        map.put("Cyberrisks1p2ar3", CyberRiskForm::setCyberrisks1p2ar3);
        map.put("Cyberrisks1p2b", CyberRiskForm::setCyberrisks1p2b);
        map.put("Cyberrisks1p2c", CyberRiskForm::setCyberrisks1p2c);
        map.put("Cyberrisks1p2d", CyberRiskForm::setCyberrisks1p2d);
        map.put("Cyberrisks1p2e1", CyberRiskForm::setCyberrisks1p2e1);
        map.put("Cyberrisks1p2e2", CyberRiskForm::setCyberrisks1p2e2);
        map.put("Cyberrisks1p2e3", CyberRiskForm::setCyberrisks1p2e3);

        // 3. CONTINUIDAD DE NEGOCIO
        map.put("Cyberrisks1p3a1", CyberRiskForm::setCyberrisks1p3a1);
        map.put("Cyberrisks1p3a2", CyberRiskForm::setCyberrisks1p3a2);
        map.put("Cyberrisks1p3a3", CyberRiskForm::setCyberrisks1p3a3);
        map.put("Cyberrisks1p3a4", CyberRiskForm::setCyberrisks1p3a4);
        map.put("Cyberrisks1p3a5", CyberRiskForm::setCyberrisks1p3a5);
        map.put("Cyberrisks1p3a6", CyberRiskForm::setCyberrisks1p3a6);
        map.put("Cyberrisks1p3br1", CyberRiskForm::setCyberrisks1p3br1);
        map.put("Cyberrisks1p3br2", CyberRiskForm::setCyberrisks1p3br2);
        map.put("Cyberrisks1p3br3", CyberRiskForm::setCyberrisks1p3br3);
        map.put("Cyberrisks1p3br4", CyberRiskForm::setCyberrisks1p3br4);
        map.put("Cyberrisks1p3br5", CyberRiskForm::setCyberrisks1p3br5);
        map.put("Cyberrisks1p3br6", CyberRiskForm::setCyberrisks1p3br6);
        map.put("Cyberrisks1p3c", (form, value) -> form.setCyberrisks1p3c(Boolean.valueOf(value)));
        map.put("Cyberrisks1p3d", CyberRiskForm::setCyberrisks1p3d);
        map.put("Cyberrisks1p3e", (form, value) -> form.setCyberrisks1p3e(Boolean.valueOf(value)));
        map.put("Cyberrisks1p3f", CyberRiskForm::setCyberrisks1p3f);
        map.put("Cyberrisks1p3g", (form, value) -> form.setCyberrisks1p3g(Boolean.valueOf(value)));
        map.put("Cyberrisks1p3h", (form, value) -> form.setCyberrisks1p3h(Boolean.valueOf(value)));

        // 4. SEGURIDAD DE LA RED
        map.put("Cyberrisks1p4a1", (form, value) -> form.setCyberrisks1p4a1(Boolean.valueOf(value)));
        map.put("Cyberrisks1p4a2", CyberRiskForm::setCyberrisks1p4a2);
        map.put("Cyberrisks1p4b", (form, value) -> form.setCyberrisks1p4b(Boolean.valueOf(value)));
        map.put("Cyberrisks1p4c", (form, value) -> form.setCyberrisks1p4c(Boolean.valueOf(value)));
        map.put("Cyberrisks1p4d", (form, value) -> form.setCyberrisks1p4d(Boolean.valueOf(value)));
        map.put("Cyberrisks1p4d1", CyberRiskForm::setCyberrisks1p4d1);
        map.put("Cyberrisks1p4de", (form, value) -> form.setCyberrisks1p4de(Boolean.valueOf(value)));
        map.put("Cyberrisks1p4df", (form, value) -> form.setCyberrisks1p4df(Boolean.valueOf(value)));
        map.put("Cyberrisks1p4dg", (form, value) -> form.setCyberrisks1p4dg(Boolean.valueOf(value)));
        map.put("Cyberrisks1p4dh", (form, value) -> form.setCyberrisks1p4dh(Boolean.valueOf(value)));
        map.put("Cyberrisks1p4di", (form, value) -> form.setCyberrisks1p4di(Boolean.valueOf(value)));
        map.put("Cyberrisks1p4dj", (form, value) -> form.setCyberrisks1p4dj(Boolean.valueOf(value)));

        // 5. INFORMACIÓN Y GESTIÓN DE DATOS
        map.put("Cyberrisks1p5a", (form, value) -> form.setCyberrisks1p5a(Boolean.valueOf(value)));
        map.put("Cyberrisks1p5b", (form, value) -> form.setCyberrisks1p5b(Boolean.valueOf(value)));
        map.put("Cyberrisks1p5c", (form, value) -> form.setCyberrisks1p5c(Boolean.valueOf(value)));
        map.put("Cyberrisks1p5d", (form, value) -> form.setCyberrisks1p5d(Boolean.valueOf(value)));
        map.put("Cyberrisks1p5e", (form, value) -> form.setCyberrisks1p5e(Boolean.valueOf(value)));
        map.put("Cyberrisks1p5f", (form, value) -> form.setCyberrisks1p5f(Boolean.valueOf(value)));
        map.put("Cyberrisks1p5g", (form, value) -> form.setCyberrisks1p5g(Boolean.valueOf(value)));
        map.put("Cyberrisks1p5h", (form, value) -> form.setCyberrisks1p5h(Boolean.valueOf(value)));

        // 6. ACTIVIDAD DE OUTSOURCING
        map.put("Cyberrisks1p7a", (form, value) -> form.setCyberrisks1p7a(Boolean.valueOf(value)));
        map.put("Cyberrisks1p7aDetails", CyberRiskForm::setCyberrisks1p7aDetails);
        map.put("Cyberrisks1p7b", (form, value) -> form.setCyberrisks1p7b(Boolean.valueOf(value)));
        map.put("Cyberrisks1p7c", (form, value) -> form.setCyberrisks1p7c(Boolean.valueOf(value)));
        map.put("Cyberrisks1p7d", (form, value) -> form.setCyberrisks1p7d(Boolean.valueOf(value)));
        map.put("Cyberrisks1p7e", (form, value) -> form.setCyberrisks1p7e(Boolean.valueOf(value)));
        map.put("Cyberrisks1p7eDetails", CyberRiskForm::setCyberrisks1p7eDetails);
        map.put("Cyberrisks1p7f", (form, value) -> form.setCyberrisks1p7f(Boolean.valueOf(value)));
        map.put("Cyberrisks1p7g", (form, value) -> form.setCyberrisks1p7g(Boolean.valueOf(value)));

        // 7. SINIESTROS Y CIRCUNSTANCIAS
        map.put("Cyberrisks1p8a1", (form, value) -> form.setCyberrisks1p8a1(Boolean.valueOf(value)));
        map.put("Cyberrisks1p8a2", (form, value) -> form.setCyberrisks1p8a2(Boolean.valueOf(value)));
        map.put("Cyberrisks1p8a3", (form, value) -> form.setCyberrisks1p8a3(Boolean.valueOf(value)));
        map.put("Cyberrisks1p8a4", (form, value) -> form.setCyberrisks1p8a4(Boolean.valueOf(value)));
        map.put("Cyberrisks1p8a5", CyberRiskForm::setCyberrisks1p8a5);
        map.put("Cyberrisks1p8a6", (form, value) -> form.setCyberrisks1p8a6(Boolean.valueOf(value)));

        // FOOTER
        map.put("NombreApellidos", CyberRiskForm::setNombreApellidos);
        map.put("Cargo", CyberRiskForm::setCargo);
        map.put("Fecha", CyberRiskForm::setFecha);
        map.put("Firma", CyberRiskForm::setFirma);

        return map;
    }
}