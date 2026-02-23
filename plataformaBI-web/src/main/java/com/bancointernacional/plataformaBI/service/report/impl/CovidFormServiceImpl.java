package com.bancointernacional.plataformaBI.service.report.impl;

import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.report.bbb.AppendixForm;
import com.bancointernacional.plataformaBI.domain.report.bbb.CovidForm;
import com.bancointernacional.plataformaBI.repository.period.UserAnswerRepository;
import com.bancointernacional.plataformaBI.service.report.CovidFormService;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class CovidFormServiceImpl implements CovidFormService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Override
    public CovidForm fullFillQuestionerData(String policyQuestionaryId) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByPolicyQuestionaryId(policyQuestionaryId);
        CovidForm covidForm = new CovidForm();
        Map<String, BiConsumer<CovidForm, String>> setterMap = createSetterMap();
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
            BiConsumer<CovidForm, String> setter = setterMap.get(documentId);
            if (setter != null) {
                setter.accept(covidForm, answerContent);
            } else {
                System.out.println("Warning: No setter found for documentId: " + documentId);
            }
        }
        return covidForm;
    }

    private Map<String, BiConsumer<CovidForm, String>> createSetterMap() {
        Map<String, BiConsumer<CovidForm, String>> map = new HashMap<>();
        map.put("Covid1", CovidForm::setCovid1);
        map.put("Covid2", CovidForm::setCovid2);
        map.put("Covid3", CovidForm::setCovid3);
        map.put("Covid4a", CovidForm::setCovid4a);
        map.put("Covid4b", CovidForm::setCovid4b);
        map.put("Covid4c", CovidForm::setCovid4c);
        map.put("Covid4d", CovidForm::setCovid4d);
        map.put("Covid5", CovidForm::setCovid5);
        map.put("Covid6", CovidForm::setCovid6);
        map.put("Covid7", CovidForm::setCovid7);
        map.put("Covid8", CovidForm::setCovid8);
        map.put("Covid9", CovidForm::setCovid9);
        map.put("Covid10", CovidForm::setCovid10);
        map.put("Covid11", CovidForm::setCovid11);
        map.put("Covid12", CovidForm::setCovid12);
        map.put("Covid13", CovidForm::setCovid13);
        map.put("Covid14", CovidForm::setCovid14);
        map.put("Covid15", CovidForm::setCovid15);
        map.put("Covid16", CovidForm::setCovid16);
        map.put("Covid17", CovidForm::setCovid17);
        map.put("Covid18", CovidForm::setCovid18);
        map.put("Covid19", CovidForm::setCovid19);
        map.put("Covid20", CovidForm::setCovid20);
        map.put("Covid21", CovidForm::setCovid21);
        map.put("Covid22", CovidForm::setCovid22);
        map.put("Covid23", CovidForm::setCovid23);
        map.put("Covid24", CovidForm::setCovid24);
        map.put("Covid25", CovidForm::setCovid25);
        map.put("Covid26", CovidForm::setCovid26);

        return map;
    }


    @Override
    public CovidForm createSampleForm() {
        CovidForm questionnaire = new CovidForm();

        questionnaire.setCovid1("La compañía ha implementado un plan de contingencia integral que incluye trabajo remoto, protocolos de higiene y distanciamiento social, y comunicación constante con los empleados. Se ha contratado a consultores especializados en salud ocupacional para asesorar en la implementación de estos protocolos.");
        questionnaire.setCovid2("Sí, la compañía ha identificado varias fuentes de disrupciones, incluyendo: restricciones de movilidad que afectan a nuestros empleados, interrupciones en la cadena de suministro, y cambios en los patrones de demanda de nuestros clientes. Estas disrupciones han sido documentadas y se están monitoreando continuamente.");
        questionnaire.setCovid3("El equipo de Gestión de Riesgos Corporativos ha finalizado su análisis inicial. Las conclusiones principales indican una exposición moderada al virus, con impactos potenciales en la continuidad operativa y en los resultados financieros a corto plazo. Se ha establecido un comité de crisis que se reúne semanalmente para actualizar este análisis.");

        questionnaire.setCovid4a("Cuando se confirma que un colaborador ha contraído COVID-19, se activa inmediatamente el protocolo de aislamiento. Se notifica a las personas que tuvieron contacto cercano con el colaborador afectado, se realiza una desinfección profunda del área de trabajo, y se proporciona seguimiento médico al colaborador. Además, se implementa trabajo remoto para el equipo afectado durante 14 días.");
        questionnaire.setCovid4b("Sí, hemos realizado estudios detallados sobre el impacto en nuestros principales clientes. Estos estudios indican que aproximadamente un 30% de nuestra cartera de clientes experimentará una reducción significativa en su demanda, mientras que un 15% podría aumentar sus requerimientos. Hemos ajustado nuestras proyecciones de ventas y estamos desarrollando estrategias específicas para cada segmento de clientes.");
        questionnaire.setCovid4c("El Plan de Continuidad de Negocio está funcionando según lo esperado en términos generales. Hemos experimentado algunos problemas menores con los sistemas de VPN debido al aumento repentino de usuarios remotos, pero estos han sido resueltos. Las operaciones continúan funcionando al 85% de capacidad, con expectativas de alcanzar el 95% en las próximas semanas a medida que se optimizan los procesos remotos.");
        questionnaire.setCovid4d("Sí, nuestra compañía tiene contratada una póliza de Seguros de Incendio y Líneas Aliadas que incluye cobertura de Lucro Cesante. Sin embargo, esta póliza tiene exclusiones específicas relacionadas con pandemias, por lo que estamos evaluando opciones adicionales de cobertura.");

        questionnaire.setCovid5("Sí, hemos experimentado retrasos en la recepción de materias primas importadas y dificultades logísticas para la distribución de nuestros productos. Aproximadamente el 40% de nuestros servicios requieren visitas presenciales a clientes, lo cual ha sido parcialmente reemplazado por asistencia remota cuando es posible.");
        questionnaire.setCovid6("El análisis de nuestros niveles de existencias indica que tenemos suficiente inventario para mantener las operaciones durante aproximadamente 3 meses bajo la demanda actual. Hemos identificado proveedores alternativos para los insumos críticos y estamos aumentando selectivamente nuestros niveles de inventario para componentes esenciales.");
        questionnaire.setCovid7("Anticipamos una reducción del 20-25% en nuestros ingresos para el año fiscal actual en comparación con el año anterior. Ya hemos registrado una disminución del 15% en los ingresos del primer trimestre. Esta información ha sido comunicada a los accionistas a través de nuestro informe trimestral y en una reunión extraordinaria realizada virtualmente.");
        questionnaire.setCovid8("Basado en nuestras proyecciones financieras actualizadas, no esperamos tener dificultades significativas para honrar nuestros compromisos de deuda en los próximos 12 a 18 meses. Hemos implementado medidas de control de costos y hemos asegurado líneas de crédito adicionales como precaución.");
        questionnaire.setCovid9("Hemos negociado con nuestros principales acreedores cláusulas de flexibilidad que incluyen: posibilidad de diferir hasta dos pagos de capital sin penalización, ajuste temporal de los covenants financieros relacionados con el ratio de cobertura de intereses, y opciones de extensión de plazos para créditos que vencen en los próximos 12 meses.");
        questionnaire.setCovid10("Se esperan algunas deficiencias temporales en componentes específicos importados, pero hemos implementado un sistema de priorización para asegurar que los productos y servicios críticos no se vean afectados. No anticipamos deficiencias significativas en nuestros productos principales.");
        questionnaire.setCovid11("Sí, hemos realizado pruebas de presión financiera considerando tres escenarios: optimista, moderado y pesimista. Las conclusiones indican que incluso en el escenario pesimista (reducción de ingresos del 35% durante 18 meses), la compañía mantendría suficiente liquidez para continuar operaciones, aunque requeriría implementar medidas adicionales de reducción de costos y posiblemente renegociar algunos términos de deuda.");
        questionnaire.setCovid12("Esperamos un impacto moderado en nuestro flujo de caja durante los próximos 6 meses, con una reducción aproximada del 25% en comparación con nuestras proyecciones pre-COVID. Sin embargo, nuestras reservas de efectivo y líneas de crédito disponibles son suficientes para mantener la liquidez necesaria para las operaciones.");
        questionnaire.setCovid13("Sí, esperamos un impacto material adverso en nuestros resultados operacionales para el año fiscal actual, con una reducción estimada del 20-25% en ingresos y una disminución del 30-35% en el EBITDA en comparación con el año anterior. Sin embargo, no anticipamos un impacto material adverso en nuestra condición financiera a largo plazo.");
        questionnaire.setCovid14("Sí, hemos realizado pruebas de presión relacionadas con la pérdida de beneficios. Estas pruebas indican que podríamos absorber pérdidas de hasta el 40% de nuestros ingresos anuales sin exceder nuestras líneas de crédito, siempre que implementemos las medidas de control de costos ya identificadas.");
        questionnaire.setCovid15("Proyectamos que nuestros ingresos disminuirán aproximadamente un 30% entre marzo y junio de 2020, seguido por una recuperación gradual en el segundo semestre. Para el año completo, estimamos que los ingresos serán aproximadamente un 20% inferiores en comparación con el mismo período del año pasado.");
        questionnaire.setCovid16("El COVID-19 ha impactado significativamente nuestras proyecciones financieras. Hemos revisado nuestras proyecciones de ingresos a la baja en un 20-25% para el año fiscal actual, y hemos ajustado nuestras expectativas de margen EBITDA del 18% original al 14-15% debido a los costos adicionales relacionados con la pandemia y la menor absorción de costos fijos.");
        questionnaire.setCovid17("Hemos emitido dos comunicados públicos informando sobre los cambios en nuestras proyecciones: uno el 15 de marzo de 2020 con una evaluación preliminar, y otro el 30 de abril de 2020 con proyecciones actualizadas basadas en datos más completos. Estos comunicados fueron publicados en nuestra página web corporativa y enviados a la Superintendencia del Mercado de Valores. Se pueden proporcionar copias de estos comunicados si se requieren.");
        questionnaire.setCovid18("Nuestra compañía considera que está cumpliendo con los lineamientos de divulgación de información impuestos por la SMV. Hemos contratado a asesores legales especializados en mercado de valores para asegurar que nuestras comunicaciones y reportes cumplan con todos los requisitos regulatorios durante este período excepcional.");
        questionnaire.setCovid19("Sí, la compañía ha solicitado a la SMV una prórroga de 45 días para la presentación de los estados financieros auditados del primer trimestre, amparándonos en las disposiciones excepcionales emitidas por el regulador debido a la emergencia sanitaria.");
        questionnaire.setCovid20("Sí, hemos contratado los servicios de una firma de relaciones públicas especializada en comunicación corporativa y financiera para asesorarnos en la redacción de comunicados públicos relacionados con el impacto del COVID-19 en nuestras operaciones y resultados financieros.");

        questionnaire.setCovid21("Sí, hemos revisado y actualizado completamente nuestros procedimientos de salud y seguridad ocupacional. Las actualizaciones incluyen nuevos protocolos de limpieza y desinfección, implementación de controles de temperatura, reorganización de espacios de trabajo para garantizar el distanciamiento social, y capacitación específica sobre prevención de COVID-19 para todos los colaboradores.");
        questionnaire.setCovid22("Aproximadamente el 70% de nuestros colaboradores trabaja actualmente desde casa. Para aquellos que deben asistir presencialmente, hemos proporcionado equipos de protección personal que incluyen mascarillas, guantes y desinfectantes. Hemos implementado estrictamente la política de distanciamiento social de 2 metros entre personas, y hemos reorganizado los espacios de trabajo para cumplir con este requisito. La compañía solo requiere asistencia presencial para funciones esenciales que no pueden realizarse remotamente, y se han implementado horarios escalonados para minimizar la concentración de personal.");
        questionnaire.setCovid23("Sí, la compañía ha desarrollado un plan de contingencia escalonado que incluye diferentes niveles de reducción de operaciones según la evolución de la situación. Este plan contempla la posibilidad de reducción temporal de jornada laboral, suspensión temporal de contratos para ciertas posiciones no esenciales, cierre parcial de algunas instalaciones, y en el escenario más extremo, una paralización temporal de operaciones no críticas. Estas medidas se implementarían progresivamente y solo si las condiciones económicas lo hacen necesario.");

        questionnaire.setCovid24("Sí, la compañía estaba relativamente bien preparada para el trabajo remoto, ya que aproximadamente un 30% de nuestros colaboradores ya tenía esta modalidad ocasionalmente. Hemos ampliado nuestra infraestructura de VPN, implementado autenticación de dos factores para todos los accesos remotos, y proporcionado equipos adicionales cuando ha sido necesario. Los controles de seguridad de la información han sido reforzados y se realizan auditorías periódicas para verificar su efectividad.");
        questionnaire.setCovid25("Sí, nuestra compañía cuenta con un Seguro de Riesgos Cibernéticos que cubre incidentes de seguridad, violaciones de datos, extorsión cibernética y responsabilidad por privacidad. Hemos revisado la póliza para confirmar que la cobertura es adecuada en el contexto actual de trabajo remoto generalizado.");
        questionnaire.setCovid26("La compañía ha implementado un plan integral para combatir el incremento en la ciber-delincuencia que incluye: actualización de todas las políticas de seguridad para el trabajo remoto, implementación de VPN con cifrado avanzado, monitoreo continuo de actividades sospechosas, capacitación específica sobre seguridad para todos los colaboradores que trabajan remotamente, restricción de acceso a información sensible, y auditorías periódicas de seguridad. Además, hemos contratado servicios especializados de monitoreo de amenazas cibernéticas 24/7.");

        return questionnaire;
    }


}