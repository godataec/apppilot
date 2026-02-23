package com.bancointernacional.plataformaBI.services.report.impl;

import com.bancointernacional.plataformaBI.models.DTO.AnswerDTO;
import com.bancointernacional.plataformaBI.models.DTO.QuestionDTO;
import com.bancointernacional.plataformaBI.models.report.bbb.AppendixForm;
import com.bancointernacional.plataformaBI.models.report.bbb.CrimeForm;
import com.bancointernacional.plataformaBI.services.report.CrimeFormService;
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
public class CrimeFormServiceImpl implements CrimeFormService {

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Override
    public CrimeForm createSampleForm() {
        CrimeForm questionnaire = new CrimeForm();

        initializeBasicInfo(questionnaire);
        initializeFinancialInfo(questionnaire);
        initializeRevenueInfo(questionnaire);
        initializeAccountInfo(questionnaire);
        initializeStaffInfo(questionnaire);
        initializeCoverageInfo(questionnaire);
        initializeRiskValues(questionnaire);
        initializeClaimInfo(questionnaire);
        initializeControlsInfo(questionnaire);
        initializeSecurityInfo(questionnaire);
        initializeComputerInfo(questionnaire);
        initializeFundManagementInfo(questionnaire);

        questionnaire.setPrintDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return questionnaire;
    }

    @Override
    public CrimeForm fullFillQuestionerData(String idQuest) {
        List<QuestionDTO> procesosQuest = questionService.getQuestionByCharacters(idQuest, 1, 999);
        List<Integer> answersIds = procesosQuest.stream().map(QuestionDTO::getIdQuestion).collect(Collectors.toList());

        ArrayList<AnswerDTO> answersFound = new ArrayList<AnswerDTO>();
        for(int i = 0; i < answersIds.size(); i++) {
            List<AnswerDTO> listOfAnswer = answerService.findByProcessAndQuestion(
                    UUID.fromString(idQuest), Long.valueOf(answersIds.get(i)));
            answersFound.addAll(listOfAnswer);
        }

        CrimeForm crimeForm = new CrimeForm();

        Map<String, BiConsumer<CrimeForm, String>> setterMap = createSetterMap();
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


            BiConsumer<CrimeForm, String> setter = setterMap.get(documentId);
            if (setter != null) {
                setter.accept(crimeForm, answerContent);
            } else {
                System.out.println("Warning: No setter found for documentId: " + documentId);
            }
        }

        return crimeForm;
    }


    private Map<String, BiConsumer<CrimeForm, String>> createSetterMap() {
        Map<String, BiConsumer<CrimeForm, String>> map = new HashMap<>();

        map.put("crime1", CrimeForm::setCrime1);
        map.put("crime2", CrimeForm::setCrime2);
        map.put("crime3", CrimeForm::setCrime3);
        map.put("crime4a", CrimeForm::setCrime4a);
        map.put("crime4b", CrimeForm::setCrime4b);
        map.put("crime4c", CrimeForm::setCrime4c);
        map.put("crime4d", CrimeForm::setCrime4d);
        map.put("crime4e", CrimeForm::setCrime4e);
        map.put("crime4f", CrimeForm::setCrime4f);
        map.put("crime5ar1", CrimeForm::setCrime5ar1);
        map.put("crime5ar2", CrimeForm::setCrime5ar2);
        map.put("crime5br1", CrimeForm::setCrime5br1);
        map.put("crime5br2", CrimeForm::setCrime5br2);
        map.put("crime5cr1", CrimeForm::setCrime5cr1);
        map.put("crime5cr2", CrimeForm::setCrime5cr2);
        map.put("crime5dr1", CrimeForm::setCrime5dr1);
        map.put("crime5dr2", CrimeForm::setCrime5dr2);
        map.put("crime5er1", CrimeForm::setCrime5er1);
        map.put("crime5er2", CrimeForm::setCrime5er2);
        map.put("crime5fr1", CrimeForm::setCrime5fr1);
        map.put("crime5fr2", CrimeForm::setCrime5fr2);
        map.put("crime5fr1a1", CrimeForm::setCrime5fr1a1);
        map.put("crime5fr2b1", CrimeForm::setCrime5fr2b1);
        map.put("crime5fr1a2", CrimeForm::setCrime5fr1a2);
        map.put("crime5fr2b2", CrimeForm::setCrime5fr2b2);
        map.put("crime5fr1a3", CrimeForm::setCrime5fr1a3);
        map.put("crime5fr2b3", CrimeForm::setCrime5fr2b3);
        map.put("crime5fr1a4", CrimeForm::setCrime5fr1a4);
        map.put("crime5fr2b4", CrimeForm::setCrime5fr2b4);
        map.put("crime5fr1a5", CrimeForm::setCrime5fr1a5);
        map.put("crime5fr2b5", CrimeForm::setCrime5fr2b5);
        map.put("crime5gr1", CrimeForm::setCrime5gr1);
        map.put("crime5gr2", CrimeForm::setCrime5gr2);
        map.put("crime5hr1", CrimeForm::setCrime5hr1);
        map.put("crime5hr2", CrimeForm::setCrime5hr2);
        map.put("crime5ir1", CrimeForm::setCrime5ir1);
        map.put("crime5ir2", CrimeForm::setCrime5ir2);
        map.put("crime6ar1", CrimeForm::setCrime6ar1);
        map.put("crime6ar2", CrimeForm::setCrime6ar2);
        map.put("crime6br1", CrimeForm::setCrime6br1);
        map.put("crime6br2", CrimeForm::setCrime6br2);
        map.put("crime6cr1", CrimeForm::setCrime6cr1);
        map.put("crime6cr2", CrimeForm::setCrime6cr2);
        map.put("crimes2p1", CrimeForm::setCrimes2p1);
        map.put("crimes2p2ar1", CrimeForm::setCrimes2p2ar1);
        map.put("crimes2p2ar2", CrimeForm::setCrimes2p2ar2);
        map.put("crimes2p2ar3", CrimeForm::setCrimes2p2ar3);
        map.put("crimes2p2ar4", CrimeForm::setCrimes2p2ar4);
        map.put("crimes2p2ar5", CrimeForm::setCrimes2p2ar5);
        map.put("crimes2p2b1r3", CrimeForm::setCrimes2p2b1r3);
        map.put("crimes2p2b1r4", CrimeForm::setCrimes2p2b1r4);
        map.put("crimes2p2b1r5", CrimeForm::setCrimes2p2b1r5);
        map.put("crimes2p2b2r1", CrimeForm::setCrimes2p2b2r1);
        map.put("crimes2p2b2r2", CrimeForm::setCrimes2p2b2r2);
        map.put("crimes2p2b2r3", CrimeForm::setCrimes2p2b2r3);
        map.put("crimes2p2b2r4", CrimeForm::setCrimes2p2b2r4);
        map.put("crimes2p2b2r5", CrimeForm::setCrimes2p2b2r5);
        map.put("crimes2p2cr1", CrimeForm::setCrimes2p2cr1);
        map.put("crimes2p2cr3", CrimeForm::setCrimes2p2cr3);
        map.put("crimes2p2cr4", CrimeForm::setCrimes2p2cr4);
        map.put("crimes2p2cr5", CrimeForm::setCrimes2p2cr5);
        map.put("crimes2p2dr1", CrimeForm::setCrimes2p2dr1);
        map.put("crimes2p2dr2", CrimeForm::setCrimes2p2dr2);
        map.put("crimes2p2dr3", CrimeForm::setCrimes2p2dr3);
        map.put("crimes2p2dr4", CrimeForm::setCrimes2p2dr4);
        map.put("crimes2p2dr5", CrimeForm::setCrimes2p2dr5);
        map.put("crimes3p1", CrimeForm::setCrimes3p1);
        map.put("crimes2p2f", CrimeForm::setCrimes2p2f);
        map.put("crimes2p2g", CrimeForm::setCrimes2p2g);
        map.put("crimes2p3b", CrimeForm::setCrimes2p3b);
        map.put("crimes4p1a", CrimeForm::setCrimes4p1a);
        map.put("crimes4p1b", CrimeForm::setCrimes4p1b);
        map.put("crimes4p1c", CrimeForm::setCrimes4p1c);
        map.put("crimes4p1d", CrimeForm::setCrimes4p1d);
        map.put("crimes4p2a", CrimeForm::setCrimes4p2a);
        map.put("crimes4p2b", CrimeForm::setCrimes4p2b);
        map.put("crimes4p2c", CrimeForm::setCrimes4p2c);
        map.put("crimes4p2d", CrimeForm::setCrimes4p2d);
        map.put("crimes4p2e", CrimeForm::setCrimes4p2e);
        map.put("crimes4p2f", CrimeForm::setCrimes4p2f);
        map.put("crimes4p2g", CrimeForm::setCrimes4p2g);
        map.put("crimes4p3ar1", CrimeForm::setCrimes4p3ar1);
        map.put("crimes4p3ar2", CrimeForm::setCrimes4p3ar2);
        map.put("crimes4p3br1", CrimeForm::setCrimes4p3br1);
        map.put("crimes4p3br2", CrimeForm::setCrimes4p3br2);
        map.put("crimes4p3cr1", CrimeForm::setCrimes4p3cr1);
        map.put("crimes4p3cr2", CrimeForm::setCrimes4p3cr2);
        map.put("crimes4p3dr1", CrimeForm::setCrimes4p3dr1);
        map.put("crimes4p3dr2", CrimeForm::setCrimes4p3dr2);
        map.put("crimes4p4", CrimeForm::setCrimes4p4);
        map.put("crimes5p1tar1", CrimeForm::setCrimes5p1tar1);
        map.put("crimes5p1tar2", CrimeForm::setCrimes5p1tar2);
        map.put("crimes5p1tar3", CrimeForm::setCrimes5p1tar3);
        map.put("crimes5p1tar4", CrimeForm::setCrimes5p1tar4);
        map.put("crimes5p2b", CrimeForm::setCrimes5p2b);
        map.put("crimes5p3b", CrimeForm::setCrimes5p3b);
        map.put("crimes5p4b", CrimeForm::setCrimes5p4b);
        map.put("crimes6p5b", CrimeForm::setCrimes6p5b);
        map.put("crimes6p5c", CrimeForm::setCrimes6p5c);
        map.put("crimes6p6b", CrimeForm::setCrimes6p6b);
        map.put("crimes6p6c2", CrimeForm::setCrimes6p6c2);
        map.put("crimes6p6f", CrimeForm::setCrimes6p6f);
        map.put("crimes7p1b", CrimeForm::setCrimes7p1b);
        map.put("crimes7p1att1", CrimeForm::setCrimes7p1att1);
        map.put("crimes7p1att2", CrimeForm::setCrimes7p1att2);
        map.put("crimes7p1att3", CrimeForm::setCrimes7p1att3);
        map.put("crimes7p2att1", CrimeForm::setCrimes7p2att1);
        map.put("crimes7p2att2", CrimeForm::setCrimes7p2att2);
        map.put("crimes7p2att3", CrimeForm::setCrimes7p2att3);
        map.put("crimes7p4b", CrimeForm::setCrimes7p4b);
        map.put("crimes7p4b2r1", CrimeForm::setCrimes7p4b2r1);
        map.put("crimes7p4b2r2", CrimeForm::setCrimes7p4b2r2);
        map.put("crimes7p4b2r3", CrimeForm::setCrimes7p4b2r3);
        map.put("crimes7p6b", CrimeForm::setCrimes7p6b);
        map.put("crimes7p6c", CrimeForm::setCrimes7p6c);
        map.put("crimes7p7a1r1", CrimeForm::setCrimes7p7a1r1);
        map.put("crimes7p7a1r2", CrimeForm::setCrimes7p7a1r2);
        map.put("crimes7p7a1r3", CrimeForm::setCrimes7p7a1r3);
        map.put("crimes7p7a2r1", CrimeForm::setCrimes7p7a2r1);
        map.put("crimes7p7a2r2", CrimeForm::setCrimes7p7a2r2);
        map.put("crimes7p7a2r3", CrimeForm::setCrimes7p7a2r3);
        map.put("crimes7p7a3r1", CrimeForm::setCrimes7p7a3r1);
        map.put("crimes7p7a3r2", CrimeForm::setCrimes7p7a3r2);
        map.put("crimes7p7a3r3", CrimeForm::setCrimes7p7a3r3);
        map.put("crimes7p7b2r1", CrimeForm::setCrimes7p7b2r1);
        map.put("crimes7p7b2r2", CrimeForm::setCrimes7p7b2r2);
        map.put("crimes7p7b2r3", CrimeForm::setCrimes7p7b2r3);
        map.put("crimes7p7att1", CrimeForm::setCrimes7p7att1);
        map.put("crimes7p7att2", CrimeForm::setCrimes7p7att2);
        map.put("crimes7p7att3", CrimeForm::setCrimes7p7att3);
        map.put("crimes7p8a2r1", CrimeForm::setCrimes7p8a2r1);
        map.put("crimes7p8b2r1", CrimeForm::setCrimes7p8b2r1);
        map.put("crimes7p8b2r2", CrimeForm::setCrimes7p8b2r2);
        map.put("crimes7p8b2r3", CrimeForm::setCrimes7p8b2r3);
        map.put("crimes8p2a5", CrimeForm::setCrimes8p2a5);
        map.put("crimes8p3ar1", CrimeForm::setCrimes8p3ar1);
        map.put("crimes8p3br1", CrimeForm::setCrimes8p3br1);
        map.put("crimes8p3cr1", CrimeForm::setCrimes8p3cr1);
        map.put("crimes8p3ar2", CrimeForm::setCrimes8p3ar2);
        map.put("crimes8p3br2", CrimeForm::setCrimes8p3br2);
        map.put("crimes8p3cr2", CrimeForm::setCrimes8p3cr2);
        map.put("crimes8p61", CrimeForm::setCrimes8p61);
        map.put("printDate", CrimeForm::setPrintDate);

        return map;
    }


    private void initializeBasicInfo(CrimeForm questionnaire) {
        questionnaire.setCrime1("Banco Internacional S.A.");
        questionnaire.setCrime2("Av. Principal 123, Ciudad Capital");
        questionnaire.setCrime3("15/06/1985");
        questionnaire.setCrime4a("USD 500,000,000");
        questionnaire.setCrime4b("USD 350,000,000");
        questionnaire.setCrime4c("USD 2,500,000,000");
        questionnaire.setCrime4d("USD 1,800,000,000");
        questionnaire.setCrime4e("USD 1,200,000,000");
        questionnaire.setCrime4f("Informe Anual 2023");
    }

    private void initializeFinancialInfo(CrimeForm questionnaire) {
        // Porcentajes de ingresos por tipo de operación
        questionnaire.setCrime5ar1("45%");
        questionnaire.setCrime5ar2("42%");
        questionnaire.setCrime5br1("10%");
        questionnaire.setCrime5br2("12%");
        questionnaire.setCrime5cr1("25%");
        questionnaire.setCrime5cr2("26%");
        questionnaire.setCrime5dr1("5%");
        questionnaire.setCrime5dr2("5%");
        questionnaire.setCrime5er1("8%");
        questionnaire.setCrime5er2("7%");
        questionnaire.setCrime5fr1("7%");
        questionnaire.setCrime5fr2("8%");

        // Subcategorías de banca de inversión
        questionnaire.setCrime5fr1a1("2%");
        questionnaire.setCrime5fr2b1("2.5%");
        questionnaire.setCrime5fr1a2("1%");
        questionnaire.setCrime5fr2b2("1.5%");
        questionnaire.setCrime5fr1a3("1%");
        questionnaire.setCrime5fr2b3("1%");
        questionnaire.setCrime5fr1a4("1.5%");
        questionnaire.setCrime5fr2b4("1.5%");
        questionnaire.setCrime5fr1a5("1.5%");
        questionnaire.setCrime5fr2b5("1.5%");

        questionnaire.setCrime5gr1("0%");
        questionnaire.setCrime5gr2("0%");
        questionnaire.setCrime5hr1("0%");
        questionnaire.setCrime5hr2("0%");
        questionnaire.setCrime5ir1("0%");
        questionnaire.setCrime5ir2("0%");
    }

    private void initializeRevenueInfo(CrimeForm questionnaire) {
        // Esta sección ya está cubierta en initializeFinancialInfo
    }

    private void initializeAccountInfo(CrimeForm questionnaire) {
        questionnaire.setCrime6ar1("125,000");
        questionnaire.setCrime6ar2("USD 750,000,000");
        questionnaire.setCrime6br1("15,000");
        questionnaire.setCrime6br2("USD 45,000,000");
        questionnaire.setCrime6cr1("250,000");
        questionnaire.setCrime6cr2("USD 1,000,000,000");
    }

    private void initializeStaffInfo(CrimeForm questionnaire) {
        questionnaire.setCrimes2p1("12");

        // Número de ubicaciones
        questionnaire.setCrimes2p2ar1("1");
        questionnaire.setCrimes2p2ar2("1");
        questionnaire.setCrimes2p2ar3("15");
        questionnaire.setCrimes2p2ar4("45");
        questionnaire.setCrimes2p2ar5("3");

        // Número de empleados - Tareas bancarias
        questionnaire.setCrimes2p2b1r3("150");
        questionnaire.setCrimes2p2b1r4("300");
        questionnaire.setCrimes2p2b1r5("75");

        // Número de empleados - Tareas no bancarias
        questionnaire.setCrimes2p2b2r1("100");
        questionnaire.setCrimes2p2b2r2("50");
        questionnaire.setCrimes2p2b2r3("75");
        questionnaire.setCrimes2p2b2r4("150");
        questionnaire.setCrimes2p2b2r5("25");

        // Información de cajeros automáticos
        questionnaire.setCrimes2p2cr1("5");
        questionnaire.setCrimes2p2cr3("30");
        questionnaire.setCrimes2p2cr4("60");
        questionnaire.setCrimes2p2cr5("10");

        // Número máximo de cajeros automáticos en una ubicación
        questionnaire.setCrimes2p2dr1("5");
        questionnaire.setCrimes2p2dr2("0");
        questionnaire.setCrimes2p2dr3("3");
        questionnaire.setCrimes2p2dr4("2");
        questionnaire.setCrimes2p2dr5("4");
    }

    private void initializeCoverageInfo(CrimeForm questionnaire) {
        questionnaire.setCrimes3p1("USD 10,000,000");

        // Pólizas de seguro existentes
        questionnaire.setCrimes2p2a(true);
        questionnaire.setCrimes2p2b(true);
        questionnaire.setCrimes2p2c(true);
        questionnaire.setCrimes2p2d(true);
        questionnaire.setCrimes2p2f("USD 5,000,000");
        questionnaire.setCrimes2p2g("Aseguradora Internacional S.A.");

        // Historial de seguros
        questionnaire.setCrimes2p3a(false);
        questionnaire.setCrimes2p3b("No aplica");
    }


    private void initializeRiskValues(CrimeForm questionnaire) {
        // Valores en las instalaciones
        questionnaire.setCrimes4p1a("USD 5,000,000");
        questionnaire.setCrimes4p1b("USD 2,000,000");
        questionnaire.setCrimes4p1c("USD 1,000,000");
        questionnaire.setCrimes4p1d("USD 10,000,000");

        // Efectivo en las instalaciones
        questionnaire.setCrimes4p2a("USD 2,000,000");
        questionnaire.setCrimes4p2b("USD 1,000,000");
        questionnaire.setCrimes4p2c("USD 500,000");
        questionnaire.setCrimes4p2d("USD 10,000");
        questionnaire.setCrimes4p2e("USD 100,000");
        questionnaire.setCrimes4p2f("USD 200,000");
        questionnaire.setCrimes4p2g("USD 50,000");

        // Valores en tránsito
        questionnaire.setCrimes4p3ar1("USD 500,000");
        questionnaire.setCrimes4p3ar2("USD 1,000,000");
        questionnaire.setCrimes4p3br1("USD 100,000");
        questionnaire.setCrimes4p3br2("USD 200,000");
        questionnaire.setCrimes4p3cr1("USD 1,000,000");
        questionnaire.setCrimes4p3cr2("USD 2,000,000");
        questionnaire.setCrimes4p3dr1("USD 50,000");
        questionnaire.setCrimes4p3dr2("USD 100,000");

        questionnaire.setCrimes4p4("Seguridad Total S.A.");
    }


    private void initializeClaimInfo(CrimeForm questionnaire) {
        // Detalles de pérdidas
        questionnaire.setCrimes5p1tar1("15/03/2023");
        questionnaire.setCrimes5p1tar2("Fraude interno");
        questionnaire.setCrimes5p1tar3("Sucursal Central");
        questionnaire.setCrimes5p1tar4("USD 75,000");

        questionnaire.setCrimes5p2a(false);
        questionnaire.setCrimes5p2b("No aplica");

        questionnaire.setCrimes5p3a(true);
        questionnaire.setCrimes5p3b("Se implementaron controles adicionales de verificación y se reforzó la capacitación del personal");

        questionnaire.setCrimes5p4a(true);
        questionnaire.setCrimes5p4b("Todas las recomendaciones han sido implementadas");
    }


    private void initializeControlsInfo(CrimeForm questionnaire) {
        questionnaire.setCrimes6p1a(true);
        questionnaire.setCrimes6p1b(true);
        questionnaire.setCrimes6p1c(true);

        questionnaire.setCrimes6p2(true);

        questionnaire.setCrimes6p3a(true);
        questionnaire.setCrimes6p3b(true);
        questionnaire.setCrimes6p3c(true);

        // Custodia conjunta
        questionnaire.setCrimes6p4a(true);
        questionnaire.setCrimes6p4a1(true);
        questionnaire.setCrimes6p4a2(true);
        questionnaire.setCrimes6p4a3(true);

        // Control dual
        questionnaire.setCrimes6p4b1(true);
        questionnaire.setCrimes6p4b2(true);
        questionnaire.setCrimes6p4b3(true);
        questionnaire.setCrimes6p4b4(true);

        // Auditoría interna
        questionnaire.setCrimes6p5(true);
        questionnaire.setCrimes6p5a(true);
        questionnaire.setCrimes6p5b("15");
        questionnaire.setCrimes6p5c("Trimestral");
        questionnaire.setCrimes6p5d(true);
        questionnaire.setCrimes6p5e(true);
        questionnaire.setCrimes6p5f(true);

        // Auditoría externa
        questionnaire.setCrimes6p6a(true);
        questionnaire.setCrimes6p6b("Deloitte &amp; Touche");
        questionnaire.setCrimes6p6c1(true);
        questionnaire.setCrimes6p6c2("No aplica");
        questionnaire.setCrimes6p6d(true);
        questionnaire.setCrimes6p6e1(true);
        questionnaire.setCrimes6p6e2(true);
        questionnaire.setCrimes6p6e3(false);
        questionnaire.setCrimes6p6f("No aplica");
    }


    private void initializeSecurityInfo(CrimeForm questionnaire) {
        // Bóvedas y cámaras acorazadas
        questionnaire.setCrimes7p1ar1(true);
        questionnaire.setCrimes7p1ar2(true);
        questionnaire.setCrimes7p1ar3(true);

        questionnaire.setCrimes7p1b("Equipadas con sistemas de seguridad de última generación");

        // Equipamiento de bóvedas - Cerradura de combinación
        questionnaire.setCrimes7p1b1r1(true);
        questionnaire.setCrimes7p1b1r2(true);
        questionnaire.setCrimes7p1b1r3(true);

        // Equipamiento de bóvedas - Cerradura de tiempo
        questionnaire.setCrimes7p1b2r1(true);
        questionnaire.setCrimes7p1b2r2(true);
        questionnaire.setCrimes7p1b2r3(true);

        // Equipamiento de bóvedas - Puerta de día con cerradura
        questionnaire.setCrimes7p1b3r1(true);
        questionnaire.setCrimes7p1b3r2(true);
        questionnaire.setCrimes7p1b3r3(true);

        // Construcción de bóvedas - Hormigón armado y acero
        questionnaire.setCrimes7p1c1r1(true);
        questionnaire.setCrimes7p1c1r2(true);
        questionnaire.setCrimes7p1c1r3(true);

        // Construcción de bóvedas - Puertas resistentes
        questionnaire.setCrimes7p1c2r1(true);
        questionnaire.setCrimes7p1c2r2(true);
        questionnaire.setCrimes7p1c2r3(true);

        // Construcción de bóvedas - Dispositivo anti-explosivo
        questionnaire.setCrimes7p1c3r1(true);
        questionnaire.setCrimes7p1c3r2(true);
        questionnaire.setCrimes7p1c3r3(true);

        // Detalles de protección alternativa
        questionnaire.setCrimes7p1att1("No aplica");
        questionnaire.setCrimes7p1att2("No aplica");
        questionnaire.setCrimes7p1att3("No aplica");

        // Cajas fuertes
        questionnaire.setCrimes7p2ar1(true);
        questionnaire.setCrimes7p2ar2(true);
        questionnaire.setCrimes7p2ar3(true);

        // Equipamiento de cajas fuertes - Cerraduras de combinación con dispositivo de rebloqueo
        questionnaire.setCrimes7p2br1(true);
        questionnaire.setCrimes7p2br2(true);
        questionnaire.setCrimes7p2br3(true);

        // Construcción de cajas fuertes - Puertas resistentes
        questionnaire.setCrimes7p2cr1(true);
        questionnaire.setCrimes7p2cr2(true);
        questionnaire.setCrimes7p2cr3(true);

        // Construcción de cajas fuertes - Dispositivo anti-explosivo
        questionnaire.setCrimes7p2dr1(true);
        questionnaire.setCrimes7p2dr2(true);
        questionnaire.setCrimes7p2dr3(true);

        // Anclaje de cajas fuertes
        questionnaire.setCrimes7p2er1(true);
        questionnaire.setCrimes7p2er2(true);
        questionnaire.setCrimes7p2er3(true);

        // Detalles de protección alternativa para cajas fuertes
        questionnaire.setCrimes7p2att1("No aplica");
        questionnaire.setCrimes7p2att2("No aplica");
        questionnaire.setCrimes7p2att3("No aplica");

        // Puertas y ventanas - Cerraduras sustanciales en puertas
        questionnaire.setCrimes7p3ar1(true);
        questionnaire.setCrimes7p3ar2(true);
        questionnaire.setCrimes7p3ar3(true);

        // Puertas y ventanas - Cerraduras sustanciales o barras en ventanas
        questionnaire.setCrimes7p3br1(true);
        questionnaire.setCrimes7p3br2(true);
        questionnaire.setCrimes7p3br3(true);

        // Alarmas - Sistemas de alarma contra robo
        questionnaire.setCrimes7p4ar1(true);
        questionnaire.setCrimes7p4ar2(true);
        questionnaire.setCrimes7p4ar3(true);

        questionnaire.setCrimes7p4b("Conectadas a central de monitoreo 24/7");

        // Conexión de alarma - Estación de policía
        questionnaire.setCrimes7p4b1r1(true);
        questionnaire.setCrimes7p4b1r2(true);
        questionnaire.setCrimes7p4b1r3(true);

        // Conexión de alarma - Otras ubicaciones
        questionnaire.setCrimes7p4b2r1("Central de monitoreo privada");
        questionnaire.setCrimes7p4b2r2("Central de monitoreo privada");
        questionnaire.setCrimes7p4b2r3("Central de monitoreo privada");

        // Posiciones de cajeros - Sistemas de alarma contra robo
        questionnaire.setCrimes7p5ar1(true);
        questionnaire.setCrimes7p5ar2(true);
        questionnaire.setCrimes7p5ar3(true);

        // Posiciones de cajeros - Botón/pedal de alarma contra robo
        questionnaire.setCrimes7p5br1(true);
        questionnaire.setCrimes7p5br2(true);
        questionnaire.setCrimes7p5br3(true);

        // Posiciones de cajeros - Vidrio anti-bandido
        questionnaire.setCrimes7p5cr1(true);
        questionnaire.setCrimes7p5cr2(true);
        questionnaire.setCrimes7p5cr3(true);

        // Posiciones de cajeros - Separación del hall bancario
        questionnaire.setCrimes7p5dr1(true);
        questionnaire.setCrimes7p5dr2(true);
        questionnaire.setCrimes7p5dr3(true);

        // Posiciones de cajeros - Remoción de exceso de efectivo
        questionnaire.setCrimes7p5er1(true);
        questionnaire.setCrimes7p5er2(true);
        questionnaire.setCrimes7p5er3(true);

        // Posiciones de cajeros - Efectivo a caja fuerte/bóveda al cierre
        questionnaire.setCrimes7p5fr1(true);
        questionnaire.setCrimes7p5fr2(true);
        questionnaire.setCrimes7p5fr3(true);

        // Posiciones de cajeros - Dinero señuelo/carnada
        questionnaire.setCrimes7p5gr1(true);
        questionnaire.setCrimes7p5gr2(true);
        questionnaire.setCrimes7p5gr3(true);

        // Guardias - Patrulla policial
        questionnaire.setCrimes7p6ar1(true);
        questionnaire.setCrimes7p6ar2(true);
        questionnaire.setCrimes7p6ar3(true);

        questionnaire.setCrimes7p6b("Guardias armados en todas las ubicaciones principales");

        // Guardias armados - Día
        questionnaire.setCrimes7p6b1r1(true);
        questionnaire.setCrimes7p6b1r2(true);
        questionnaire.setCrimes7p6b1r3(true);

        // Guardias armados - Noche
        questionnaire.setCrimes7p6b2r1(true);
        questionnaire.setCrimes7p6b2r2(true);
        questionnaire.setCrimes7p6b2r3(true);

        questionnaire.setCrimes7p6c("Proveedor de seguridad certificado");

        // Proveedor de guardias - Policía
        questionnaire.setCrimes7p6c1r1(false);
        questionnaire.setCrimes7p6c1r2(false);
        questionnaire.setCrimes7p6c1r3(false);

        // Proveedor de guardias - Agencia
        questionnaire.setCrimes7p6c2r1(true);
        questionnaire.setCrimes7p6c2r2(true);
        questionnaire.setCrimes7p6c2r3(true);

        // Proveedor de guardias - El banco mismo
        questionnaire.setCrimes7p6c3r1(false);
        questionnaire.setCrimes7p6c3r2(false);
        questionnaire.setCrimes7p6c3r3(false);

        // Guardias - Cabinas a prueba de balas
        questionnaire.setCrimes7p6d1r1(true);
        questionnaire.setCrimes7p6d1r2(true);
        questionnaire.setCrimes7p6d1r3(true);

        // Guardias - Aplicación en todas las ubicaciones
        questionnaire.setCrimes7p6e1r1(true);
        questionnaire.setCrimes7p6e1r2(true);
        questionnaire.setCrimes7p6e1r3(true);

        // Cajas de seguridad - Número de cajas
        questionnaire.setCrimes7p7a1r1("500");
        questionnaire.setCrimes7p7a1r2("200");
        questionnaire.setCrimes7p7a1r3("100");

        // Cajas de seguridad - Número alquiladas
        questionnaire.setCrimes7p7a2r1("450");
        questionnaire.setCrimes7p7a2r2("180");
        questionnaire.setCrimes7p7a2r3("90");

        // Cajas de seguridad - Número de ubicaciones con instalaciones
        questionnaire.setCrimes7p7a3r1("1");
        questionnaire.setCrimes7p7a3r2("5");
        questionnaire.setCrimes7p7a3r3("3");

        // Cajas de seguridad - Bóveda separada
        questionnaire.setCrimes7p7b1r1(true);
        questionnaire.setCrimes7p7b1r2(true);
        questionnaire.setCrimes7p7b1r3(true);

        // Cajas de seguridad - Ubicación si no está en bóveda separada
        questionnaire.setCrimes7p7b2r1("No aplica");
        questionnaire.setCrimes7p7b2r2("No aplica");
        questionnaire.setCrimes7p7b2r3("No aplica");

        // Cajas de seguridad - Supervisión de clientes
        questionnaire.setCrimes7p7b3r1(true);
        questionnaire.setCrimes7p7b3r2(true);
        questionnaire.setCrimes7p7b3r3(true);

        // Cajas de seguridad - Detalles de control si los clientes quedan sin supervisión
        questionnaire.setCrimes7p7att1("No aplica");
        questionnaire.setCrimes7p7att2("No aplica");
        questionnaire.setCrimes7p7att3("No aplica");

        // Cajas de seguridad - Control dual
        questionnaire.setCrimes7p7cr1(true);
        questionnaire.setCrimes7p7cr2(true);
        questionnaire.setCrimes7p7cr3(true);

        // Tránsito - Vehículo blindado/seguridad de terceros
        questionnaire.setCrimes7p8a1r1(true);
        questionnaire.setCrimes7p8a1r2(true);
        questionnaire.setCrimes7p8a1r3(true);

        // Tránsito - Seguro completo y responsabilidad de terceros
        questionnaire.setCrimes7p8a1sr1(true);
        questionnaire.setCrimes7p8a1sr2(true);
        questionnaire.setCrimes7p8a1sr3(true);

        // Tránsito - Arreglos alternativos
        questionnaire.setCrimes7p8a2r1("No aplica");

        // Tránsito - Mensajeros
        questionnaire.setCrimes7p8b1sr1(true);
        questionnaire.setCrimes7p8b1sr2(true);
        questionnaire.setCrimes7p8b1sr3(true);

        // Tránsito - Número de mensajeros
        questionnaire.setCrimes7p8b2r1("10");
        questionnaire.setCrimes7p8b2r2("20");
        questionnaire.setCrimes7p8b2r3("15");

        // Tránsito - Mensajeros acompañados por policía/guardias armados
        questionnaire.setCrimes7p8b3r1(true);
        questionnaire.setCrimes7p8b3r2(true);
        questionnaire.setCrimes7p8b3r3(true);

        // Tránsito - Intervalos irregulares y rutas variadas
        questionnaire.setCrimes7p8c1r1(true);
        questionnaire.setCrimes7p8c1r2(true);
        questionnaire.setCrimes7p8c1r3(true);
    }

    private void initializeComputerInfo(CrimeForm questionnaire) {
        questionnaire.setCrimes8p1(true);

        // Métodos de transferencia de fondos
        questionnaire.setCrimes8p2a1(false);
        questionnaire.setCrimes8p2a2(true);
        questionnaire.setCrimes8p2a3(false);
        questionnaire.setCrimes8p2a4(true);
        questionnaire.setCrimes8p2a5("Sistema de transferencia local");

        // Estadísticas de transferencia por cable - Cuentas bancarias
        questionnaire.setCrimes8p3ar1("5,000");
        questionnaire.setCrimes8p3br1("USD 50,000");
        questionnaire.setCrimes8p3cr1("USD 1,000,000");

        // Estadísticas de transferencia por cable - Cuentas de clientes
        questionnaire.setCrimes8p3ar2("25,000");
        questionnaire.setCrimes8p3br2("USD 10,000");
        questionnaire.setCrimes8p3cr2("USD 500,000");

        questionnaire.setCrimes8p4(true);
        questionnaire.setCrimes8p5(true);
        questionnaire.setCrimes8p6(true);
        questionnaire.setCrimes8p61("Se aplican controles adicionales para cuentas corporativas");

        // Transferencias electrónicas - Cuentas corporativas
        questionnaire.setCrimes8p6a(true);
        questionnaire.setCrimes8p6b(true);
        questionnaire.setCrimes8p6c(true);

        questionnaire.setCrimes8p7(true);
        questionnaire.setCrimes8p8(true);
        questionnaire.setCrimes8p9(true);
        questionnaire.setCrimes8p10(true);
        questionnaire.setCrimes8p11(true);
        questionnaire.setCrimes8p12(true);
        questionnaire.setCrimes8p13(true);
        questionnaire.setCrimes8p14(true);

        // Controles de contratistas independientes
        questionnaire.setCrimes8p14a(true);
        questionnaire.setCrimes8p14b(true);
        questionnaire.setCrimes8p14c(true);
    }

    private void initializeFundManagementInfo(CrimeForm questionnaire) {
        questionnaire.setCrimes9p1(true);
        questionnaire.setCrimes9p2(true);
        questionnaire.setCrimes9p3(true);
        questionnaire.setCrimes9p4(true);
        questionnaire.setCrimes9p5(true);
        questionnaire.setCrimes9p6(true);
        questionnaire.setCrimes9p7(true);
    }

}
