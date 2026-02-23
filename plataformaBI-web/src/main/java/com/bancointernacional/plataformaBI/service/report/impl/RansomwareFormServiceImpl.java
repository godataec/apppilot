package com.bancointernacional.plataformaBI.service.report.impl;


import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.report.cyber.RansomwareForm;
import com.bancointernacional.plataformaBI.repository.period.UserAnswerRepository;
import com.bancointernacional.plataformaBI.service.report.RansomwareFormService;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;

@Service
public class RansomwareFormServiceImpl implements RansomwareFormService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Override
    public RansomwareForm fullFillQuestionerData(String policyQuestionaryId) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByPolicyQuestionaryId(policyQuestionaryId);
        RansomwareForm ransomwareForm = new RansomwareForm();
        Map<String, BiConsumer<RansomwareForm, String>> setterMap = createSetterMap();
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
            BiConsumer<RansomwareForm, String> setter = setterMap.get(documentId);
            if (setter != null) {
                setter.accept(ransomwareForm, answerContent);
            } else {
                System.out.println("Warning: No setter found for documentId: " + documentId);
            }
        }

        return ransomwareForm;
    }

    private Map<String, BiConsumer<RansomwareForm, String>> createSetterMap() {
        Map<String, BiConsumer<RansomwareForm, String>> map = new HashMap<>();

        map.put("applicantName", RansomwareForm::setApplicantName);
        map.put("qOneOpOne", RansomwareForm::setQOneOpOne);
        map.put("qOneOpTwo", RansomwareForm::setQOneOpTwo);
        map.put("qOneOpThree", RansomwareForm::setQOneOpThree);
        map.put("qOneOpFour", RansomwareForm::setQOneOpFour);
        map.put("qOneOpFive", RansomwareForm::setQOneOpFive);
        map.put("qOneOpSix", RansomwareForm::setQOneOpSix);
        map.put("qOneOpSeven", RansomwareForm::setQOneOpSeven);
        map.put("qTwoOpOne", RansomwareForm::setQTwoOpOne);
        map.put("qTwoOpThree", RansomwareForm::setQTwoOpThree);
        map.put("qThreeOpOne", RansomwareForm::setQThreeOpOne);
        map.put("qThreeOpTwo", RansomwareForm::setQThreeOpTwo);
        map.put("qThreeOpThree", RansomwareForm::setQThreeOpThree);
        map.put("qThreeOpFour", RansomwareForm::setQThreeOpFour);
        map.put("qThreeOpFive", RansomwareForm::setQThreeOpFive);
        map.put("qThreeOpSix", RansomwareForm::setQThreeOpSix);
        map.put("qThreeOpSeven", RansomwareForm::setQThreeOpSeven);
        map.put("qThreeOpEight", RansomwareForm::setQThreeOpEight);
        map.put("qThreeOpNine", RansomwareForm::setQThreeOpNine);
        map.put("qFourOpOne", RansomwareForm::setQFourOpOne);
        map.put("qFourOpTwo", RansomwareForm::setQFourOpTwo);
        map.put("qFourOpThree", RansomwareForm::setQFourOpThree);
        map.put("qFourOpFour", RansomwareForm::setQFourOpFour);
        map.put("qFourOpFive", RansomwareForm::setQFourOpFive);
        map.put("qFiveOpOne", RansomwareForm::setQFiveOpOne);
        map.put("qFiveOpTwo", RansomwareForm::setQFiveOpTwo);
        map.put("qFiveOpThree", RansomwareForm::setQFiveOpThree);
        map.put("qFiveOpFour", RansomwareForm::setQFiveOpFour);
        map.put("qFiveOpFive", RansomwareForm::setQFiveOpFive);
        map.put("qSixOpOne", RansomwareForm::setQSixOpOne);
        map.put("qSixOpTwo", RansomwareForm::setQSixOpTwo);
        map.put("qSixOpThree", RansomwareForm::setQSixOpThree);
        map.put("qSixOpFour", RansomwareForm::setQSixOpFour);
        map.put("qSevenOpOne", RansomwareForm::setQSevenOpOne);
        map.put("qSevenOpTwo", RansomwareForm::setQSevenOpTwo);
        map.put("qSevenOpThree", RansomwareForm::setQSevenOpThree);
        map.put("qSevenOpFour", RansomwareForm::setQSevenOpFour);
        map.put("qSevenOpFive", RansomwareForm::setQSevenOpFive);
        map.put("qEightOpOne", RansomwareForm::setQEightOpOne);
        map.put("qEightOpTwo", RansomwareForm::setQEightOpTwo);
        map.put("qEightOpThree", RansomwareForm::setQEightOpThree);
        map.put("qEightOpFour", RansomwareForm::setQEightOpFour);
        map.put("qEightOpFive", RansomwareForm::setQEightOpFive);
        map.put("qNineOpOne", RansomwareForm::setQNineOpOne);
        map.put("qNineOpTwo", RansomwareForm::setQNineOpTwo);
        map.put("qNineOpThree", RansomwareForm::setQNineOpThree);
        map.put("qNineOpFour", RansomwareForm::setQNineOpFour);
        map.put("qNineOpFive", RansomwareForm::setQNineOpFive);
        map.put("qNineOpSix", RansomwareForm::setQNineOpSix);
        map.put("qTenOpOne", RansomwareForm::setQTenOpOne);
        map.put("qTenOpTwo", RansomwareForm::setQTenOpTwo);
        map.put("qTenOpThree", RansomwareForm::setQTenOpThree);
        map.put("qTenOpFour", RansomwareForm::setQTenOpFour);
        map.put("qTenOpFive", RansomwareForm::setQTenOpFive);
        map.put("qElevenOpOne", RansomwareForm::setQElevenOpOne);
        map.put("qElevenOpTwo", RansomwareForm::setQElevenOpTwo);
        map.put("qElevenOpThree", RansomwareForm::setQElevenOpThree);
        map.put("qElevenOpFour", RansomwareForm::setQElevenOpFour);
        map.put("qElevenOpFive", RansomwareForm::setQElevenOpFive);
        map.put("qElevenOpSix", RansomwareForm::setQElevenOpSix);
        map.put("qElevenOpSeven", RansomwareForm::setQElevenOpSeven);
        map.put("qTwelveOpOne", RansomwareForm::setQTwelveOpOne);
        map.put("qTwelveOpTwo", RansomwareForm::setQTwelveOpTwo);
        map.put("qTwelveOpThree", RansomwareForm::setQTwelveOpThree);
        map.put("qTwelveOpFour", RansomwareForm::setQTwelveOpFour);
        map.put("qThirteenOpOne", RansomwareForm::setQThirteenOpOne);
        map.put("qThirteenOpTwo", RansomwareForm::setQThirteenOpTwo);
        map.put("qFourteenOpOne", RansomwareForm::setQFourteenOpOne);
        map.put("qFourteenOpTwo", RansomwareForm::setQFourteenOpTwo);
        map.put("qFourteenOpThree", RansomwareForm::setQFourteenOpThree);
        map.put("qFourteenOpFour", RansomwareForm::setQFourteenOpFour);
        map.put("qFifteenOpOne", RansomwareForm::setQFifteenOpOne);
        map.put("qFifteenOpTwo", RansomwareForm::setQFifteenOpTwo);
        map.put("qFifteenOpThree", RansomwareForm::setQFifteenOpThree);
        map.put("qFifteenOpFour", RansomwareForm::setQFifteenOpFour);
        map.put("qFifteenOpFive", RansomwareForm::setQFifteenOpFive);
        map.put("qFifteenOpSix", RansomwareForm::setQFifteenOpSix);
        map.put("qSixteenOpOne", RansomwareForm::setQSixteenOpOne);
        map.put("qSixteenOpTwo", RansomwareForm::setQSixteenOpTwo);
        map.put("qSixteenOpThree", RansomwareForm::setQSixteenOpThree);
        map.put("qSixteenOpFour", RansomwareForm::setQSixteenOpFour);
        map.put("qSixteenOpFive", RansomwareForm::setQSixteenOpFive);
        map.put("qSixteenOpSix", RansomwareForm::setQSixteenOpSix);
        map.put("qSeventeenOpOne", RansomwareForm::setQSeventeenOpOne);
        map.put("qESeventeenOpTwo", RansomwareForm::setQESeventeenOpTwo);
        map.put("qSeventeenOpThree", RansomwareForm::setQSeventeenOpThree);
        map.put("qSeventeenOpFour", RansomwareForm::setQSeventeenOpFour);
        map.put("qSeventeenOpFive", RansomwareForm::setQSeventeenOpFive);
        map.put("qSeventeenOpSix", RansomwareForm::setQSeventeenOpSix);
        map.put("qEightteenOpOne", RansomwareForm::setQEightteenOpOne);
        map.put("qEightteenOpTwo", RansomwareForm::setQEightteenOpTwo);
        map.put("qEightteenOpThree", RansomwareForm::setQEightteenOpThree);
        map.put("qEightteenOpFour", RansomwareForm::setQEightteenOpFour);
        map.put("qEightteenOpFive", RansomwareForm::setQEightteenOpFive);
        map.put("qEightteenOpSix", RansomwareForm::setQEightteenOpSix);
        map.put("qNineteenOpOne", RansomwareForm::setQNineteenOpOne);
        map.put("qNineteenOpOnenormal", RansomwareForm::setQNineteenOpOnenormal);
        map.put("qNineteenOpTwo", RansomwareForm::setQNineteenOpTwo);
        map.put("qTwentyOpOne", RansomwareForm::setQTwentyOpOne);
        map.put("qTwentyOpTwo", RansomwareForm::setQTwentyOpTwo);
        map.put("qTwentyOpThree", RansomwareForm::setQTwentyOpThree);
        map.put("qTwentyoneOpOne", RansomwareForm::setQTwentyoneOpOne);
        map.put("qTwentyoneOpTwo", RansomwareForm::setQTwentyoneOpTwo);
        map.put("qTwentyoneOpThree", RansomwareForm::setQTwentyoneOpThree);
        map.put("qTwentyoneOpFour", RansomwareForm::setQTwentyoneOpFour);
        map.put("qTwentyoneOpFive", RansomwareForm::setQTwentyoneOpFive);
        map.put("qTwentytwoOpOne", RansomwareForm::setQTwentytwoOpOne);
        map.put("qTwentytwoOpTwo", RansomwareForm::setQTwentytwoOpTwo);
        map.put("qTwentytwoOpThree", RansomwareForm::setQTwentytwoOpThree);
        map.put("qTwentytwoOpFour", RansomwareForm::setQTwentytwoOpFour);
        map.put("qTwentyThreeOpOne", RansomwareForm::setQTwentyThreeOpOne);
        map.put("qTwentyThreeOpTwo", RansomwareForm::setQTwentyThreeOpTwo);
        map.put("qTwentyThreeOpThree", RansomwareForm::setQTwentyThreeOpThree);
        map.put("qTwentyThreeOpFour", RansomwareForm::setQTwentyThreeOpFour);
        map.put("qTwentyThreeOpFive", RansomwareForm::setQTwentyThreeOpFive);
        map.put("qTwentyfourOpOne", RansomwareForm::setQTwentyfourOpOne);
        map.put("qTwentyfourOpTwo", RansomwareForm::setQTwentyfourOpTwo);
        map.put("qTwentyfourOpThree", RansomwareForm::setQTwentyfourOpThree);
        map.put("qTwentyfourOpFour", RansomwareForm::setQTwentyfourOpFour);
        map.put("qTwentyfiveOpOne", RansomwareForm::setQTwentyfiveOpOne);
        map.put("qTwentyfiveOpTwo", RansomwareForm::setQTwentyfiveOpTwo);
        map.put("qTwentyfiveOpThree", RansomwareForm::setQTwentyfiveOpThree);

        return map;
    }


    @Override
    public RansomwareForm createSampleForm() {
        RansomwareForm respuestas = new RansomwareForm();

        // Pregunta 1: Esfuerzos para mitigar phishing
        respuestas.setQOneOpOne("X");
        respuestas.setQOneOpTwo("X");
        respuestas.setQOneOpThree("X");
        respuestas.setQOneOpFour("X");
        respuestas.setQOneOpFive("X");
        respuestas.setQOneOpSix("");
        respuestas.setQOneOpSeven("Realizamos capacitaciones trimestrales y simulacros mensuales de phishing. El último simulacro tuvo una tasa de éxito del 8%.");

        // Pregunta 2: Proceso documentado para respuesta a phishing
        respuestas.setQTwoOpOne("X");
        respuestas.setQTwoOpThree("1. Identificación del correo sospechoso\n2. Reporte al equipo de seguridad\n3. Análisis forense del correo\n4. Bloqueo de remitentes maliciosos\n5. Notificación a usuarios afectados");

        // Pregunta 3: Bloqueo de sitios web/correos dañinos
        respuestas.setQThreeOpOne("X");
        respuestas.setQThreeOpTwo("X");
        respuestas.setQThreeOpThree("X");
        respuestas.setQThreeOpFour("X");
        respuestas.setQThreeOpFive("X");
        respuestas.setQThreeOpSix("X");
        respuestas.setQThreeOpSeven("X");
        respuestas.setQThreeOpEight("");
        respuestas.setQThreeOpNine("Utilizamos Proofpoint para filtrado de correo y Cisco Umbrella para filtrado web.");

        // Pregunta 4: Autenticación para empleados
        respuestas.setQFourOpOne("");
        respuestas.setQFourOpTwo("");
        respuestas.setQFourOpThree("X");
        respuestas.setQFourOpFour("");
        respuestas.setQFourOpFive("Implementamos autenticación multifactor con Microsoft Authenticator para todos los accesos remotos.");

        // Pregunta 5: Autenticación para contratistas/proveedores
        respuestas.setQFiveOpOne("");
        respuestas.setQFiveOpTwo("");
        respuestas.setQFiveOpThree("X");
        respuestas.setQFiveOpFour("");
        respuestas.setQFiveOpFive("Los proveedores acceden a través de una VPN dedicada con MFA obligatorio.");

        // Pregunta 6: Implementación de MFA
        respuestas.setQSixOpOne("");
        respuestas.setQSixOpTwo("");
        respuestas.setQSixOpThree("X");
        respuestas.setQSixOpFour("Utilizamos tokens físicos YubiKey para autenticación de administradores.");

        // Pregunta 7: Seguridad de endpoints
        respuestas.setQSevenOpOne("X");
        respuestas.setQSevenOpTwo("X");
        respuestas.setQSevenOpThree("X");
        respuestas.setQSevenOpFour("");
        respuestas.setQSevenOpFive("Utilizamos CrowdStrike Falcon para protección de endpoints con capacidades de EDR.");

        // Pregunta 8: Monitoreo de seguridad
        respuestas.setQEightOpOne("");
        respuestas.setQEightOpTwo("");
        respuestas.setQEightOpThree("X");
        respuestas.setQEightOpFour("");
        respuestas.setQEightOpFive("Contratamos servicios de SOC gestionado con AlertLogic.");

        // Pregunta 9: Tiempo de respuesta a incidentes
        respuestas.setQNineOpOne("");
        respuestas.setQNineOpTwo("");
        respuestas.setQNineOpThree("X");
        respuestas.setQNineOpFour("");
        respuestas.setQNineOpFive("");
        respuestas.setQNineOpSix("Nuestro tiempo promedio de respuesta es de 45 minutos según las métricas del último trimestre.");

        // Pregunta 10: Controles de acceso para estaciones de trabajo
        respuestas.setQTenOpOne("");
        respuestas.setQTenOpTwo("X");
        respuestas.setQTenOpThree("");
        respuestas.setQTenOpFour("");
        respuestas.setQTenOpFive("Solo el equipo de TI tiene acceso de administrador local, documentado en nuestro sistema de gestión de identidades.");

        // Pregunta 11: Protección de credenciales privilegiadas
        respuestas.setQElevenOpOne("X");
        respuestas.setQElevenOpTwo("X");
        respuestas.setQElevenOpThree("X");
        respuestas.setQElevenOpFour("X");
        respuestas.setQElevenOpFive("X");
        respuestas.setQElevenOpSix("");
        respuestas.setQElevenOpSeven("Utilizamos CyberArk para gestión de credenciales privilegiadas.");

        // Pregunta 12: Uso de Microsoft Active Directory
        respuestas.setQTwelveOpOne("");
        respuestas.setQTwelveOpTwo("5");
        respuestas.setQTwelveOpThree("2");
        respuestas.setQTwelveOpFour("Los administradores de dominio están estrictamente controlados y auditados.");

        // Pregunta 13: Cuentas privilegiadas para endpoints
        respuestas.setQThirteenOpOne("8");
        respuestas.setQThirteenOpTwo("Estas cuentas corresponden al equipo de operaciones de TI y están sujetas a revisión trimestral.");

        // Pregunta 14: Seguridad de sistemas expuestos externamente
        respuestas.setQFourteenOpOne("X");
        respuestas.setQFourteenOpTwo("X");
        respuestas.setQFourteenOpThree("X");
        respuestas.setQFourteenOpFour("");

        // Pregunta 15: Tiempo objetivo para despliegue de parches
        respuestas.setQFifteenOpOne("");
        respuestas.setQFifteenOpTwo("");
        respuestas.setQFifteenOpThree("X");
        respuestas.setQFifteenOpFour("");
        respuestas.setQFifteenOpFive("");
        respuestas.setQFifteenOpSix("Los parches críticos se despliegan en un máximo de 48 horas después de su evaluación.");

        // Pregunta 16: Cumplimiento de estándares de parches
        respuestas.setQSixteenOpOne("");
        respuestas.setQSixteenOpTwo("X");
        respuestas.setQSixteenOpThree("");
        respuestas.setQSixteenOpFour("");
        respuestas.setQSixteenOpFive("");
        respuestas.setQSixteenOpSix("Mantenemos un cumplimiento del 97% en la aplicación de parches críticos.");

        // Pregunta 17: Monitoreo de red
        respuestas.setQSeventeenOpOne("X");
        respuestas.setQESeventeenOpTwo("X");
        respuestas.setQSeventeenOpThree("X");
        respuestas.setQSeventeenOpFour("X");
        respuestas.setQSeventeenOpFive("");
        respuestas.setQSeventeenOpSix("Utilizamos Splunk como SIEM y Darktrace para detección de anomalías en la red.");

        // Pregunta 18: Limitación de movimiento lateral
        respuestas.setQEightteenOpOne("X");
        respuestas.setQEightteenOpTwo("X");
        respuestas.setQEightteenOpThree("X");
        respuestas.setQEightteenOpFour("X");
        respuestas.setQEightteenOpFive("");
        respuestas.setQSeventeenOpSix("Implementamos microsegmentación con Cisco ACI y políticas de firewall por host.");

        // Pregunta 19: Ejercicio de ransomware
        LocalDate fechaEjercicio = LocalDate.now().minusMonths(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        respuestas.setQNineteenOpOne(fechaEjercicio.format(formatter));
        respuestas.setQNineteenOpTwo("");

        // Pregunta 20: Plan de respuesta a ransomware de terceros
        respuestas.setQTwentyOpOne("");
        respuestas.setQTwentyOpTwo("X");
        respuestas.setQTwentyOpThree("1. Identificación del proveedor afectado\n2. Aislamiento de conexiones\n3. Evaluación de impacto en datos compartidos\n4. Activación de plan de continuidad\n5. Coordinación con el equipo de respuesta del proveedor");

        // Pregunta 21: Verificación de controles de seguridad
        respuestas.setQTwentyoneOpOne("X");
        respuestas.setQTwentyoneOpTwo("");
        respuestas.setQTwentyoneOpThree("X");
        respuestas.setQTwentyoneOpFour("");
        respuestas.setQTwentyoneOpFive("Realizamos pruebas de penetración anuales con una firma externa especializada.");

        // Pregunta 22: Capacidades de recuperación ante desastres
        respuestas.setQTwentytwoOpOne("");
        respuestas.setQTwentytwoOpTwo("X");
        respuestas.setQTwentytwoOpThree("X");
        respuestas.setQTwentytwoOpFour("");

        // Pregunta 23: Objetivo de tiempo de recuperación (RTO)
        respuestas.setQTwentyThreeOpOne("");
        respuestas.setQTwentyThreeOpTwo("");
        respuestas.setQTwentyThreeOpThree("X");
        respuestas.setQTwentyThreeOpFour("");
        respuestas.setQTwentyThreeOpFive("");

        // Pregunta 24: Capacidades de backup
        respuestas.setQTwentyfourOpOne("X");
        respuestas.setQTwentyfourOpTwo("X");
        respuestas.setQTwentyfourOpThree("X");
        respuestas.setQTwentyfourOpFour("Utilizamos Veeam para backups con una política 3-2-1 (3 copias, 2 medios diferentes, 1 copia fuera del sitio).");

        // Pregunta 25: Cifrado de dispositivos portátiles
        respuestas.setQTwentyfiveOpOne("X");
        respuestas.setQTwentyfiveOpTwo("");
        respuestas.setQTwentyfiveOpThree("Todos los dispositivos portátiles utilizan BitLocker con las claves almacenadas en Azure AD.");

        return respuestas;
    }


}
