package com.bancointernacional.plataformaBI.services.report.impl;

import com.bancointernacional.plataformaBI.models.DTO.AnswerDTO;
import com.bancointernacional.plataformaBI.models.DTO.QuestionDTO;
import com.bancointernacional.plataformaBI.models.report.bbb.CrimeForm;
import com.bancointernacional.plataformaBI.models.report.bbb.PIForm;
import com.bancointernacional.plataformaBI.services.report.PiQuestionerService;
import com.bancointernacional.plataformaBI.services.serviceInterface.AnswerService;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionService;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class PIQuestionerServiceImpl implements PiQuestionerService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;


    @Override
    public PIForm fullFillQuestionerData(String idQuest) {
        List<QuestionDTO> procesosQuest = questionService.getQuestionByCharacters(idQuest, 1, 999);
        List<Integer> answersIds = procesosQuest.stream().map(QuestionDTO::getIdQuestion).collect(Collectors.toList());

        ArrayList<AnswerDTO> answersFound = new ArrayList<AnswerDTO>();
        for(int i = 0; i < answersIds.size(); i++) {
            List<AnswerDTO> listOfAnswer = answerService.findByProcessAndQuestion(
                    UUID.fromString(idQuest), Long.valueOf(answersIds.get(i)));
            answersFound.addAll(listOfAnswer);
        }

        PIForm piForm = new PIForm();

        Map<String, BiConsumer<PIForm, String>> setterMap = createSetterMap();
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


            BiConsumer<PIForm, String> setter = setterMap.get(documentId);
            if (setter != null) {
                setter.accept(piForm, answerContent);
            } else {
                System.out.println("Warning: No setter found for documentId: " + documentId);
            }
        }

        return piForm;
    }


    private Map<String, BiConsumer<PIForm, String>> createSetterMap() {
        Map<String, BiConsumer<PIForm, String>> map = new HashMap<>();

        map.put("companyName", PIForm::setCompanyName);
        map.put("companyEstablishmentDate", PIForm::setCompanyEstablishmentDate);
        map.put("mainCompanyActivity", PIForm::setMainCompanyActivity);
        map.put("stockExchanges", PIForm::setStockExchanges);
        map.put("directorsInwardTurnover", PIForm::setDirectorsInwardTurnover);
        map.put("directorsOutwardTurnover", PIForm::setDirectorsOutwardTurnover);
        map.put("otherEmployeesInwardTurnover", PIForm::setOtherEmployeesInwardTurnover);
        map.put("otherEmployeesOutwardTurnover", PIForm::setOtherEmployeesOutwardTurnover);
        map.put("totalSalaries", PIForm::setTotalSalaries);
        map.put("currentYearCommercialLoans", PIForm::setCurrentYearCommercialLoans);
        map.put("previousYearCommercialLoans", PIForm::setPreviousYearCommercialLoans);
        map.put("currentYearInterbancaLoans", PIForm::setCurrentYearInterbancaLoans);
        map.put("previousYearInterbancaLoans", PIForm::setPreviousYearInterbancaLoans);
        map.put("currentYearPersonalLoans", PIForm::setCurrentYearPersonalLoans);
        map.put("previousYearPersonalLoans", PIForm::setPreviousYearPersonalLoans);
        map.put("currentYearTradeFinancing", PIForm::setCurrentYearTradeFinancing);
        map.put("previousYearTradeFinancing", PIForm::setPreviousYearTradeFinancing);
        map.put("currentYearForeignExchange", PIForm::setCurrentYearForeignExchange);
        map.put("previousYearForeignExchange", PIForm::setPreviousYearForeignExchange);
        map.put("currentYearCommodityMarket", PIForm::setCurrentYearCommodityMarket);
        map.put("previousYearCommodityMarket", PIForm::setPreviousYearCommodityMarket);
        map.put("currentYearSecuritiesBroker", PIForm::setCurrentYearSecuritiesBroker);
        map.put("previousYearSecuritiesBroker", PIForm::setPreviousYearSecuritiesBroker);
        map.put("currentYearCustodian", PIForm::setCurrentYearCustodian);
        map.put("previousYearCustodian", PIForm::setPreviousYearCustodian);
        map.put("currentYearAdvisor", PIForm::setCurrentYearAdvisor);
        map.put("previousYearAdvisor", PIForm::setPreviousYearAdvisor);
        map.put("currentYearMergers", PIForm::setCurrentYearMergers);
        map.put("previousYearMergers", PIForm::setPreviousYearMergers);
        map.put("currentYearSharePlacing", PIForm::setCurrentYearSharePlacing);
        map.put("previousYearSharePlacing", PIForm::setPreviousYearSharePlacing);
        map.put("currentVentueCapital", PIForm::setCurrentVentueCapital);
        map.put("previousVentueCapital", PIForm::setPreviousVentueCapital);
        map.put("otherActivities", PIForm::setOtherActivities);
        map.put("currentYearTrustee", PIForm::setCurrentYearTrustee);
        map.put("previousYearTrustee", PIForm::setPreviousYearTrustee);
        map.put("currentYearOverseasAdvisory", PIForm::setCurrentYearOverseasAdvisory);
        map.put("previousYearOverseasAdvisory", PIForm::setPreviousYearOverseasAdvisory);
        map.put("currentYearOtherActivities", PIForm::setCurrentYearOtherActivities);
        map.put("previousYearOtherActivities", PIForm::setPreviousYearOtherActivities);
        map.put("regulatoryAuthorities", PIForm::setRegulatoryAuthorities);
        map.put("externalAuditorName", PIForm::setExternalAuditorName);
        map.put("externalAuditorAddress", PIForm::setExternalAuditorAddress);
        map.put("auditFrequency", PIForm::setAuditFrequency);
        map.put("auditRecommendationsDetails", PIForm::setAuditRecommendationsDetails);
        map.put("auditorChangeReasons", PIForm::setAuditorChangeReasons);
        map.put("internalAuditFrequency", PIForm::setInternalAuditFrequency);
        map.put("legalNameAddress", PIForm::setLegalNameAddress);
        map.put("internalLegalDeptDetails", PIForm::setInternalLegalDeptDetails);
        map.put("internalLegalIndividuals", PIForm::setInternalLegalIndividuals);
        map.put("deparmentResponsibilities", PIForm::setDeparmentResponsibilities);
        map.put("thirdPartyServicesDetails", PIForm::setThirdPartyServicesDetails);
        map.put("claimsDetails", PIForm::setClaimsDetails);
        map.put("previousInsurer", PIForm::setPreviousInsurer);
        map.put("policyEndDate", PIForm::setPolicyEndDate);
        map.put("policyAssuredSum", PIForm::setPolicyAssuredSum);
        map.put("requestedAssuredSum", PIForm::setRequestedAssuredSum);
        map.put("signerPosition", PIForm::setSignerPosition);
        map.put("signatureDate", PIForm::setSignatureDate);

        return map;
    }




    @Override
    public PIForm createSampleForm() {
        PIForm data = new PIForm();

        // Información básica de la compañía
        data.setCompanyName("Banco Financiero Internacional S.A.");
        data.setCompanyEstablishmentDate("15/03/1995");
        data.setMainCompanyActivity("Servicios bancarios y financieros para empresas y particulares, incluyendo banca corporativa, banca personal, gestión de activos y servicios de inversión.");

        // Subsidiarias
        List<PIForm.Subsidiary> subsidiaries = new ArrayList<>();
        PIForm.Subsidiary sub1 = new PIForm.Subsidiary();
        sub1.setName("BFI Inversiones S.A.");
        sub1.setLocation("Ciudad de México");
        sub1.setDateEstablished("10/06/2000");
        sub1.setServices("Gestión de inversiones y fondos mutuos");
        subsidiaries.add(sub1);

        PIForm.Subsidiary sub2 = new PIForm.Subsidiary();
        sub2.setName("BFI Seguros S.A.");
        sub2.setLocation("Monterrey");
        sub2.setDateEstablished("22/09/2005");
        sub2.setServices("Servicios de seguros y reaseguros");
        subsidiaries.add(sub2);

        PIForm.Subsidiary sub3 = new PIForm.Subsidiary();
        sub3.setName("BFI Capital S.A.");
        sub3.setLocation("Guadalajara");
        sub3.setDateEstablished("05/11/2010");
        sub3.setServices("Banca de inversión y servicios de capital privado");
        subsidiaries.add(sub3);

        data.setSubsidiaries(subsidiaries);

        // Tipo de compañía
        data.setPrivate(false);
        data.setPublic(true);
        data.setStockExchanges("Bolsa Mexicana de Valores (BMV), Bolsa de Nueva York (NYSE)");

        // Accionistas
        List<PIForm.Shareholder> shareholders = new ArrayList<>();
        PIForm.Shareholder sh1 = new PIForm.Shareholder();
        sh1.setName("Grupo Financiero Internacional");
        sh1.setPercentage("35%");
        shareholders.add(sh1);

        PIForm.Shareholder sh2 = new PIForm.Shareholder();
        sh2.setName("Inversiones Globales S.A.");
        sh2.setPercentage("15%");
        shareholders.add(sh2);

        PIForm.Shareholder sh3 = new PIForm.Shareholder();
        sh3.setName("Fondo de Pensiones Nacional");
        sh3.setPercentage("8%");
        shareholders.add(sh3);

        PIForm.Shareholder sh4 = new PIForm.Shareholder();
        sh4.setName("Corporación Latinoamericana de Inversiones");
        sh4.setPercentage("7%");
        shareholders.add(sh4);

        PIForm.Shareholder sh5 = new PIForm.Shareholder();
        sh5.setName("Accionistas minoritarios");
        sh5.setPercentage("35%");
        shareholders.add(sh5);

        data.setShareholders(shareholders);

        // Adquisiciones
        data.setAcquisitionYes(true);
        data.setAcquisitionNo(false);

        // Información de empleados
        data.setCurrentYearTotalEmployees(1250);
        data.setPreviousYearTotalEmployees(1180);
        data.setCurrentYearTrainingStaff(45);
        data.setPreviousYearTrainingStaff(40);
        data.setCurrentYearComplianceStaff(35);
        data.setPreviousYearComplianceStaff(30);
        data.setCurrentYearDirectors(12);
        data.setPreviousYearDirectors(12);
        data.setCurrentYearSalesStaff(350);
        data.setPreviousYearSalesStaff(320);
        data.setCurrentYearSupportStaff(808);
        data.setPreviousYearSupportStaff(778);

        // Directores
        List<PIForm.Director> directors = new ArrayList<>();
        PIForm.Director dir1 = new PIForm.Director();
        dir1.setName("Carlos Rodríguez Vázquez");
        dir1.setServiceLength("15 años");
        dir1.setPosition("Director General (CEO)");
        dir1.setShares("2%");
        directors.add(dir1);

        PIForm.Director dir2 = new PIForm.Director();
        dir2.setName("María González Fuentes");
        dir2.setServiceLength("8 años");
        dir2.setPosition("Directora Financiera (CFO)");
        dir2.setShares("1.5%");
        directors.add(dir2);

        PIForm.Director dir3 = new PIForm.Director();
        dir3.setName("Javier López Mendoza");
        dir3.setServiceLength("10 años");
        dir3.setPosition("Director de Operaciones (COO)");
        dir3.setShares("1.2%");
        directors.add(dir3);

        PIForm.Director dir4 = new PIForm.Director();
        dir4.setName("Ana Martínez Herrera");
        dir4.setServiceLength("6 años");
        dir4.setPosition("Directora de Tecnología (CTO)");
        dir4.setShares("0.8%");
        directors.add(dir4);

        PIForm.Director dir5 = new PIForm.Director();
        dir5.setName("Roberto Sánchez Ortiz");
        dir5.setServiceLength("12 años");
        dir5.setPosition("Director de Riesgos (CRO)");
        dir5.setShares("1.0%");
        directors.add(dir5);

        data.setDirectors(directors);

        // Rotación de personal
        data.setDirectorsInwardTurnover("8%");
        data.setDirectorsOutwardTurnover("5%");
        data.setOtherEmployeesInwardTurnover("12%");
        data.setOtherEmployeesOutwardTurnover("10%");
        data.setTotalSalaries("$125,000,000 MXN");

        // Actividades y servicios - Año actual
        data.setCurrentYearCommercialLoans("35%");
        data.setCurrentYearInterbancaLoans("10%");
        data.setCurrentYearPersonalLoans("20%");
        data.setCurrentYearTradeFinancing("8%");
        data.setCurrentYearForeignExchange("5%");
        data.setCurrentYearCommodityMarket("2%");
        data.setCurrentYearSecuritiesBroker("7%");
        data.setCurrentYearCustodian("3%");
        data.setCurrentYearAdvisor("5%");
        data.setCurrentYearMergers("2%");
        data.setCurrentYearSharePlacing("1%");
        data.setCurrentVentueCapital("1%");
        data.setCurrentYearTrustee("0.5%");
        data.setCurrentYearOverseasAdvisory("0.3%");
        data.setCurrentYearOtherActivities("0.2%");

        // Actividades y servicios - Año anterior
        data.setPreviousYearCommercialLoans("33%");
        data.setPreviousYearInterbancaLoans("12%");
        data.setPreviousYearPersonalLoans("18%");
        data.setPreviousYearTradeFinancing("7%");
        data.setPreviousYearForeignExchange("6%");
        data.setPreviousYearCommodityMarket("3%");
        data.setPreviousYearSecuritiesBroker("8%");
        data.setPreviousYearCustodian("4%");
        data.setPreviousYearAdvisor("4%");
        data.setPreviousYearMergers("2%");
        data.setPreviousYearSharePlacing("1.5%");
        data.setPreviousVentueCapital("1%");
        data.setPreviousYearTrustee("0.3%");
        data.setPreviousYearOverseasAdvisory("0.2%");
        data.setPreviousYearOtherActivities("0%");

        data.setOtherActivities("Servicios de consultoría financiera para PyMEs y asesoría en planificación patrimonial");

        // Nuevos servicios
        data.setNewServicesYes(true);
        data.setNewServicesNo(false);

        // Servicios futuros
        data.setFutureServicesYes(true);
        data.setFutureServicesNo(false);

        // Autoridades reguladoras
        data.setRegulatoryAuthorities("Comisión Nacional Bancaria y de Valores (CNBV), Banco de México (Banxico), Comisión Nacional de Seguros y Fianzas (CNSF)");

        // Asesor en fusiones y adquisiciones
        data.setMergerAdvisorYes(true);
        data.setMergerAdvisorNo(false);

        // Fusiones y adquisiciones
        List<PIForm.Merger> mergers = new ArrayList<>();
        PIForm.Merger merger1 = new PIForm.Merger();
        merger1.setOfferer("Grupo Industrial Mexicano S.A.");
        merger1.setInterestedParty("Manufacturas del Norte S.A.");
        merger1.setOutcome("Completada");
        merger1.setOfferValue("$450,000,000 MXN");
        mergers.add(merger1);

        PIForm.Merger merger2 = new PIForm.Merger();
        merger2.setOfferer("Tecnologías Avanzadas S.A.");
        merger2.setInterestedParty("Soluciones Digitales S.A.");
        merger2.setOutcome("En proceso");
        merger2.setOfferValue("$280,000,000 MXN");
        mergers.add(merger2);

        PIForm.Merger merger3 = new PIForm.Merger();
        merger3.setOfferer("Corporación Alimentaria S.A.");
        merger3.setInterestedParty("Distribuidora de Alimentos S.A.");
        merger3.setOutcome("Cancelada");
        merger3.setOfferValue("$320,000,000 MXN");
        mergers.add(merger3);

        data.setMergers(mergers);

        // Asesor en colocación de acciones
        data.setSharePlacingAdvisorYes(true);
        data.setSharePlacingAdvisorNo(false);

        // Actividades fiduciarias
        data.setTrustActivitiesYes(true);
        data.setTrustActivitiesNo(false);

        // Transferencia electrónica de fondos
        data.setElectronicTransferYes(true);
        data.setElectronicTransferNo(false);

        // Acceso por Internet
        data.setInternetAccessYes(true);
        data.setInternetAccessNo(false);

        // Auditorías
        data.setExternalAuditorName("Deloitte México");
        data.setExternalAuditorAddress("Paseo de la Reforma 505, Piso 28, Col. Cuauhtémoc, 06500 Ciudad de México, CDMX");
        data.setAuditFrequency("Anual, con revisiones trimestrales");
        data.setInternalControlReviewYes(true);
        data.setInternalControlReviewNo(false);
        data.setWrittenReportsYes(true);
        data.setWrittenReportsNo(false);
        data.setAuditRecommendationsYes(true);
        data.setAuditRecommendationsNo(false);
        data.setAuditRecommendationsDetails("Se recomendó fortalecer los controles en el área de préstamos comerciales y mejorar la documentación de los procesos de aprobación de créditos. Todas las recomendaciones han sido implementadas.");
        data.setAuditorChangedYes(false);
        data.setAuditorChangedNo(true);
        data.setAuditorChangeReasons("");
        data.setInternalAuditDeptYes(true);
        data.setInternalAuditDeptNo(false);
        data.setInternalAuditFrequency("Trimestral para áreas críticas, anual para todas las áreas");
        data.setEthicsCodeYes(true);
        data.setEthicsCodeNo(false);
        data.setEthicsCodeAcknowledgementYes(true);
        data.setEthicsCodeAcknowledgementNo(false);
        data.setConflictDeclarationYes(true);
        data.setConflictDeclarationNo(false);

        // Departamento legal
        data.setLegalNameAddress("Bufete Jurídico Financiero S.C., Av. Insurgentes Sur 1235, Piso 10, Col. Del Valle, 03100 Ciudad de México, CDMX");
        data.setLegalOpinionsYes(true);
        data.setLegalOpinionsNo(false);
        data.setInternalLegalDeptYes(true);
        data.setInternalLegalDeptNo(false);
        data.setInternalLegalDeptDetails("El departamento legal interno se encarga de todos los asuntos legales corporativos, regulatorios y contractuales del banco y sus subsidiarias.");
        data.setInternalLegalIndividuals("12 abogados titulados con especialización en derecho bancario y financiero");
        data.setDeparmentResponsibilities("Asesoría legal en operaciones bancarias, revisión de contratos, cumplimiento regulatorio, litigios, protección de datos personales y gobierno corporativo");
        data.setThirdPartyServicesYes(true);
        data.setThirdPartyServicesNo(false);
        data.setThirdPartyServicesDetails("Ofrece asesoría legal básica a clientes corporativos en temas relacionados con regulación bancaria y financiera");
        data.setLegalReviewYes(true);
        data.setLegalReviewNo(false);

        // Reclamaciones/Siniestralidad
        data.setPendingLitigationYes(true);
        data.setPendingLitigationNo(false);
        data.setPotentialClaimsYes(false);
        data.setPotentialClaimsNo(true);
        data.setRegulatoryCriticismYes(false);
        data.setRegulatoryCriticismNo(true);
        data.setClaimsDetails("Existe un litigio pendiente por un reclamo de un cliente corporativo relacionado con asesoría en inversiones por un monto de $5,000,000 MXN. El caso está en proceso y nuestros asesores legales consideran que hay altas probabilidades de resolución favorable para el banco.");

        // Programa de seguro anterior o actual
        data.setPreviousInsuranceYes(true);
        data.setPreviousInsuranceNo(false);
        data.setPreviousInsurer("AXA Seguros S.A. de C.V.");
        data.setPolicyEndDate("31/12/2023");
        data.setPolicyAssuredSum("$50,000,000 MXN");
        data.setRequestedAssuredSum("$75,000,000 MXN");

        // Declaración
        data.setSignerPosition("Director General");
        data.setSignatureDate("Ciudad de México, 15 de abril de 2023");

        return data;
    }

}
