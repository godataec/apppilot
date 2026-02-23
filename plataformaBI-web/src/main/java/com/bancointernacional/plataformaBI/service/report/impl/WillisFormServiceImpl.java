package com.bancointernacional.plataformaBI.service.report.impl;


import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.model.report.table.TableView;
import com.bancointernacional.plataformaBI.domain.report.DO.WillisForm;
import com.bancointernacional.plataformaBI.repository.period.UserAnswerRepository;
import com.bancointernacional.plataformaBI.service.report.WillisFormService;
import com.bancointernacional.plataformaBI.util.TableBuilder;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;

import static com.bancointernacional.plataformaBI.enums.AnswerTypes.TABLE;

@Service
public class WillisFormServiceImpl implements WillisFormService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Override
    public WillisForm fullFillQuestionerData(String policyQuestionaryId) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByPolicyQuestionaryId(policyQuestionaryId);
        WillisForm willisForm = new WillisForm();
        Map<String, BiConsumer<WillisForm, String>> setterMap = createSetterMap();
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
                        answerContent  = temp.getAnswerText();
                        if(answerContent.isEmpty() && !answer.getAnswerText().isEmpty()){
                            answerContent = answer.getAnswerText();
                        }
                        documentId = temp.getDocumentId();
                    }
                }else{
                    answerContent  = answer.getAnswerText();
                }
                BiConsumer<WillisForm, String> setter = setterMap.get(documentId);
                if (setter != null) {
                    setter.accept(willisForm, answerContent);
                } else {
                    System.out.println("Warning: No setter found for documentId: " + documentId);
                }
            }else {
                tableAnswers.add(answer);
            }
        }
        Map<String, TableView> tables = TableBuilder.buildTables(tableAnswers);
        willisForm.setTables(tables);
        return willisForm;
    }

    private Map<String, BiConsumer<WillisForm, String>> createSetterMap() {
        Map<String, BiConsumer<WillisForm, String>> map = new HashMap<>();

        // String fields
        map.put("willisDOpONEa", WillisForm::setWillisDOpONEa);
        map.put("willisDOpONEb", WillisForm::setWillisDOpONEb);
        map.put("willisDOpONEc", WillisForm::setWillisDOpONEc);
        map.put("willisDOpONEd", WillisForm::setWillisDOpONEd);
        map.put("willisDOpONEe", WillisForm::setWillisDOpONEe);
        map.put("willisDOpTWOaPublic", WillisForm::setWillisDOpTWOaPublic);
        map.put("willisDOpTWOaPrivate", WillisForm::setWillisDOpTWOaPrivate);
        map.put("willisDOpTHREEDname", WillisForm::setWillisDOpTHREEDname);
        map.put("willisDOpTHREEFstockExchanges", WillisForm::setWillisDOpTHREEFstockExchanges);
        map.put("willisDOpTHREEG", WillisForm::setWillisDOpTHREEG);
        map.put("willisDOpTHREEdetails", WillisForm::setWillisDOpTHREEdetails);
        map.put("willisDOpFOURa", WillisForm::setWillisDOpFOURa);
        map.put("willisDOpFOURb", WillisForm::setWillisDOpFOURb);
        map.put("willisDOpFOURcname", WillisForm::setWillisDOpFOURcname);
        map.put("willisDOpFIVEa", WillisForm::setWillisDOpFIVEa);
        map.put("willisDOpFIVEb", WillisForm::setWillisDOpFIVEb);
        map.put("willisDOpFIVEc", WillisForm::setWillisDOpFIVEc);
        map.put("willisDOpFIVEcIF", WillisForm::setWillisDOpFIVEcIF);
        map.put("willisDOpFIVEd", WillisForm::setWillisDOpFIVEd);
        map.put("willisDOpFIVEe", WillisForm::setWillisDOpFIVEe);
        map.put("willisDOpFIVEeif", WillisForm::setWillisDOpFIVEeif);
        map.put("willisDOpSIX", WillisForm::setWillisDOpSIX);
        map.put("willisDOpSIXa", WillisForm::setWillisDOpSIXa);
        map.put("willisDOpSIXb", WillisForm::setWillisDOpSIXb);
        map.put("willisDOpSIXc", WillisForm::setWillisDOpSIXc);
        map.put("willisDOpSeven", WillisForm::setWillisDOpSeven);
        map.put("willisDOpTHREEFYes", WillisForm::setWillisDOpTHREEFYes);
        map.put("willisDOpTHREEFYesnormal", WillisForm::setWillisDOpTHREEFYesnormal);

        // Double fields
        map.put("willisDOpONEeHome", (w, v) -> w.setWillisDOpONEeHome(parseDouble(v)));
        map.put("willisDOpONEeEurope", (w, v) -> w.setWillisDOpONEeEurope(parseDouble(v)));
        map.put("willisDOpONEeUSA", (w, v) -> w.setWillisDOpONEeUSA(parseDouble(v)));
        map.put("willisDOpONEeWorld", (w, v) -> w.setWillisDOpONEeWorld(parseDouble(v)));
        map.put("willisDOpTWOc", (w, v) -> w.setWillisDOpTWOc(parseDouble(v)));
        map.put("willisDOpTHREEDpercent", (w, v) -> w.setWillisDOpTHREEDpercent(parseDouble(v)));
        map.put("willisDOpFOURcpercent", (w, v) -> w.setWillisDOpFOURcpercent(parseDouble(v)));

        // Integer fields
        map.put("willisDOpTWOb", (w, v) -> w.setWillisDOpTWOb(parseInteger(v)));

        // Boolean fields
        map.put("willisDOpTHREEEYes", (w, v) -> w.setWillisDOpTHREEEYes(parseBoolean(v)));
        map.put("willisDOpTHREEENo", (w, v) -> w.setWillisDOpTHREEENo(parseBoolean(v)));
        map.put("willisDOpTHREEFNo", (w, v) -> w.setWillisDOpTHREEFNo(parseBoolean(v)));
        map.put("willisDOpTHREEHYes", (w, v) -> w.setWillisDOpTHREEHYes(parseBoolean(v)));
        map.put("willisDOpTHREEHNo", (w, v) -> w.setWillisDOpTHREEHNo(parseBoolean(v)));
        map.put("willisDOpTHREEaYes", (w, v) -> w.setWillisDOpTHREEaYes(parseBoolean(v)));
        map.put("willisDOpTHREEaNo", (w, v) -> w.setWillisDOpTHREEaNo(parseBoolean(v)));
        map.put("willisDOpTHREEbYes", (w, v) -> w.setWillisDOpTHREEbYes(parseBoolean(v)));
        map.put("willisDOpTHREEbNo", (w, v) -> w.setWillisDOpTHREEbNo(parseBoolean(v)));
        map.put("willisDOpTHREEcYes", (w, v) -> w.setWillisDOpTHREEcYes(parseBoolean(v)));
        map.put("willisDOpTHREEcNo", (w, v) -> w.setWillisDOpTHREEcNo(parseBoolean(v)));
        map.put("willisDOpFOURYes", (w, v) -> w.setWillisDOpFOURYes(parseBoolean(v)));
        map.put("willisDOpFOURNo", (w, v) -> w.setWillisDOpFOURNo(parseBoolean(v)));

        return map;
    }

    // Helpers for type conversion
    private Double parseDouble(String s) {
        try {
            return s != null && !s.isEmpty() ? Double.valueOf(s) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInteger(String s) {
        try {
            return s != null && !s.isEmpty() ? Integer.valueOf(s) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean parseBoolean(String s) {
        if(s != null){
            s = s.equalsIgnoreCase("YES") ? "true" : "false";
            return !s.isEmpty() ? Boolean.valueOf(s) : false;
        }else
            return false;
    }

    @Override
    public WillisForm createSampleForm() {
        WillisForm formulario = new WillisForm();

        formulario.setWillisDOpONEa("Tecnología Innovadora S.A.");
        formulario.setWillisDOpONEb("Av. Principal 123, Ciudad Empresarial, CP 12345");
        formulario.setWillisDOpONEc("Desarrollo de software y soluciones tecnológicas para empresas");
        formulario.setWillisDOpONEd("15/03/2010");
        formulario.setWillisDOpONEe("Desglose de ventas por región");
        formulario.setWillisDOpONEeHome(65.0);
        formulario.setWillisDOpONEeEurope(20.0);
        formulario.setWillisDOpONEeUSA(10.0);
        formulario.setWillisDOpONEeWorld(5.0);

        formulario.setWillisDOpTWOaPublic("No");
        formulario.setWillisDOpTWOaPrivate("Sí");
        formulario.setWillisDOpTWOb(12);
        formulario.setWillisDOpTWOc(45.0);


        formulario.setWillisDOpTHREEDname("Inversiones Globales S.A.");
        formulario.setWillisDOpTHREEDpercent(25.0);


        formulario.setWillisDOpTHREEEYes(true);
        formulario.setWillisDOpTHREEENo(false);
        formulario.setWillisDOpTHREEFYes("false");
        formulario.setWillisDOpTHREEFNo(true);
        formulario.setWillisDOpTHREEFstockExchanges("N/A");
        formulario.setWillisDOpTHREEG("1,000,000");
        formulario.setWillisDOpTHREEHYes(false);
        formulario.setWillisDOpTHREEHNo(true);
        formulario.setWillisDOpTHREEdetails("En 2022 se realizó una emisión privada de acciones para capitalizar la empresa con un aumento de capital de $5,000,000.");


        formulario.setWillisDOpTHREEaYes(true);
        formulario.setWillisDOpTHREEaNo(false);
        formulario.setWillisDOpTHREEbYes(false);
        formulario.setWillisDOpTHREEbNo(true);
        formulario.setWillisDOpTHREEcYes(false);
        formulario.setWillisDOpTHREEcNo(true);


        formulario.setWillisDOpFOURYes(true);
        formulario.setWillisDOpFOURNo(false);
        formulario.setWillisDOpFOURa("$2,500,000");
        formulario.setWillisDOpFOURb("15%");
        formulario.setWillisDOpFOURcname("Tech Solutions Inc.");
        formulario.setWillisDOpFOURcpercent(100.0);


        formulario.setWillisDOpFIVEa("No");
        formulario.setWillisDOpFIVEb("No");
        formulario.setWillisDOpFIVEc("No");
        formulario.setWillisDOpFIVEcIF("N/A");
        formulario.setWillisDOpFIVEd("No");
        formulario.setWillisDOpFIVEe("No");
        formulario.setWillisDOpFIVEeif("N/A");

        formulario.setWillisDOpSIX("Sí");
        formulario.setWillisDOpSIXa("Seguros Confianza S.A.");
        formulario.setWillisDOpSIXb("31/12/2023");
        formulario.setWillisDOpSIXc("$5,000,000");


        formulario.setWillisDOpSeven("$10,000,000");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            formulario.setPrintDate(sdf.parse("15/04/2024"));
        } catch (ParseException e) {
            formulario.setPrintDate(new Date());
        }

        return formulario;
    }

}
