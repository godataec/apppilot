package com.bancointernacional.plataformaBI.service.report.impl;

import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.model.report.table.TableView;
import com.bancointernacional.plataformaBI.domain.report.bbb.CrimeForm;
import com.bancointernacional.plataformaBI.repository.period.UserAnswerRepository;
import com.bancointernacional.plataformaBI.service.report.CrimeFormService;
import com.bancointernacional.plataformaBI.util.TableBuilder;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;

import static com.bancointernacional.plataformaBI.enums.AnswerTypes.TABLE;

@Service
public class CrimeFormServiceImpl implements CrimeFormService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Override
    public CrimeForm fullFillQuestionerData(String policyQuestionaryId) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByPolicyQuestionaryId(policyQuestionaryId);
        CrimeForm crimeForm = new CrimeForm();
        Map<String, BiConsumer<CrimeForm, String>> setterMap = createSetterMap();
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
                BiConsumer<CrimeForm, String> setter = setterMap.get(documentId);
                if (setter != null) {
                    setter.accept(crimeForm, answerContent);
                } else {
                    System.out.println("Warning: No setter found for documentId: " + documentId);
                }
            } else {
                tableAnswers.add(answer);
            }
        }
        Map<String, TableView> tables = TableBuilder.buildTables(tableAnswers);
        crimeForm.setTables(tables);
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
        map.put("crimes2p2b1r1", CrimeForm::setCrimes2p2b1r1);
        map.put("crimes2p2b1r2", CrimeForm::setCrimes2p2b1r2);
        map.put("crimes2p2b1r3", CrimeForm::setCrimes2p2b1r3);
        map.put("crimes2p2b1r4", CrimeForm::setCrimes2p2b1r4);
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
        map.put("crimes2p2a", (form, val) -> form.setCrimes2p2a("yes".equalsIgnoreCase(val)));
        map.put("crimes2p2b", (form, val) -> form.setCrimes2p2b("yes".equalsIgnoreCase(val)));
        map.put("crimes2p2c", (form, val) -> form.setCrimes2p2c("yes".equalsIgnoreCase(val)));
        map.put("crimes2p2d", (form, val) -> form.setCrimes2p2d("yes".equalsIgnoreCase(val)));
        map.put("crimes2p2f", CrimeForm::setCrimes2p2f);
        map.put("crimes2p2g", CrimeForm::setCrimes2p2g);
        map.put("crimes2p3a", (form, val) -> form.setCrimes2p3a("yes".equalsIgnoreCase(val)));
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
        map.put("crimes5p2a", (form, val) -> form.setCrimes5p2a("##yes".equalsIgnoreCase(val)));
        map.put("crimes5p2anormal", CrimeForm::setCrimes5p2anormal);
        map.put("crimes5p2b", CrimeForm::setCrimes5p2b);
        map.put("crimes5p3a", (form, val) -> form.setCrimes5p3a("##yes".equalsIgnoreCase(val)));
        map.put("crimes5p3anormal", CrimeForm::setCrimes5p3anormal);
        map.put("crimes5p3b", CrimeForm::setCrimes5p3b);
        map.put("crimes5p4a", (form, val) -> form.setCrimes5p4a("##yes".equalsIgnoreCase(val)));
        map.put("crimes5p4anormal", CrimeForm::setCrimes5p4anormal);
        map.put("crimes5p4b", CrimeForm::setCrimes5p4b);
        map.put("crimes6p1a", (form, val) -> form.setCrimes6p1a("yes".equalsIgnoreCase(val)));
        map.put("crimes6p1b", (form, val) -> form.setCrimes6p1b("yes".equalsIgnoreCase(val)));
        map.put("crimes6p1c", (form, val) -> form.setCrimes6p1c("yes".equalsIgnoreCase(val)));
        map.put("crimes6p2", CrimeForm::setCrimes6p2);
        map.put("crimes6p3a", (form, val) -> form.setCrimes6p3a("yes".equalsIgnoreCase(val)));
        map.put("crimes6p3b", (form, val) -> form.setCrimes6p3b("yes".equalsIgnoreCase(val)));
        map.put("crimes6p3c", (form, val) -> form.setCrimes6p3c("yes".equalsIgnoreCase(val)));

        map.put("crimes6p4a", (form, val) -> form.setCrimes6p4a("yes".equalsIgnoreCase(val)));
        map.put("crimes6p4a1", (form, val) -> form.setCrimes6p4a1("yes".equalsIgnoreCase(val)));
        map.put("crimes6p4a2", (form, val) -> form.setCrimes6p4a2("yes".equalsIgnoreCase(val)));
        map.put("crimes6p4a3", (form, val) -> form.setCrimes6p4a3("yes".equalsIgnoreCase(val)));

        map.put("crimes6p4b1", (form, val) -> form.setCrimes6p4b1("yes".equalsIgnoreCase(val)));
        map.put("crimes6p4b2", (form, val) -> form.setCrimes6p4b2("yes".equalsIgnoreCase(val)));
        map.put("crimes6p4b3", (form, val) -> form.setCrimes6p4b3("yes".equalsIgnoreCase(val)));
        map.put("crimes6p4b4", (form, val) -> form.setCrimes6p4b4("yes".equalsIgnoreCase(val)));

        map.put("crimes6p5", (form, val) -> form.setCrimes6p5("##yes".equalsIgnoreCase(val)));
        map.put("crimes6p5normal", CrimeForm::setCrimes6p5normal);
        map.put("crimes6p5a", (form, val) -> form.setCrimes6p5a("yes".equalsIgnoreCase(val)));

        map.put("crimes6p5b", CrimeForm::setCrimes6p5b);
        map.put("crimes6p5c", CrimeForm::setCrimes6p5c);

        map.put("crimes6p5d", (form, val) -> form.setCrimes6p5d("yes".equalsIgnoreCase(val)));
        map.put("crimes6p5e", (form, val) -> form.setCrimes6p5e("yes".equalsIgnoreCase(val)));
        map.put("crimes6p5f", (form, val) -> form.setCrimes6p5f("yes".equalsIgnoreCase(val)));

        map.put("crimes6p6a", (form, val) -> form.setCrimes6p6a("##yes".equalsIgnoreCase(val)));
        map.put("crimes6p6anormal", CrimeForm::setCrimes6p6anormal);

        map.put("crimes6p6b", CrimeForm::setCrimes6p6b);
        map.put("crimes6p6c1", (form, val) -> form.setCrimes6p6c1("##no".equalsIgnoreCase(val)));
        map.put("crimes6p6c1normal", CrimeForm::setCrimes6p6c1normal);
        map.put("crimes6p6c2", CrimeForm::setCrimes6p6c2);
        map.put("crimes6p6d", (form, val) -> form.setCrimes6p6d("yes".equalsIgnoreCase(val)));
        map.put("crimes6p6e1", (form, val) -> form.setCrimes6p6e1("yes".equalsIgnoreCase(val)));
        map.put("crimes6p6e2", (form, val) -> form.setCrimes6p6e2("yes".equalsIgnoreCase(val)));
        map.put("crimes6p6e3", (form, val) -> form.setCrimes6p6e3("##yes".equalsIgnoreCase(val)));

        map.put("crimes6p6e3normal", CrimeForm::setCrimes6p6e3normal);
        map.put("crimes7p1ar1", (form, val) -> form.setCrimes7p1ar1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1ar2", (form, val) -> form.setCrimes7p1ar2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1ar3", (form, val) -> form.setCrimes7p1ar3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p1b", CrimeForm::setCrimes7p1b);

        map.put("crimes7p1b1r1", (form, val) -> form.setCrimes7p1b1r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1b1r2", (form, val) -> form.setCrimes7p1b1r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1b1r3", (form, val) -> form.setCrimes7p1b1r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p1b2r1", (form, val) -> form.setCrimes7p1b2r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1b2r2", (form, val) -> form.setCrimes7p1b2r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1b2r3", (form, val) -> form.setCrimes7p1b2r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p1b3r1", (form, val) -> form.setCrimes7p1b3r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1b3r2", (form, val) -> form.setCrimes7p1b3r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1b3r3", (form, val) -> form.setCrimes7p1b3r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p1c1r1", (form, val) -> form.setCrimes7p1c1r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1c1r2", (form, val) -> form.setCrimes7p1c1r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1c1r3", (form, val) -> form.setCrimes7p1c1r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p1c2r1", (form, val) -> form.setCrimes7p1c2r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1c2r2", (form, val) -> form.setCrimes7p1c2r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1c2r3", (form, val) -> form.setCrimes7p1c2r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p1c3r1", (form, val) -> form.setCrimes7p1c3r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1c3r2", (form, val) -> form.setCrimes7p1c3r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p1c3r3", (form, val) -> form.setCrimes7p1c3r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p1att1", CrimeForm::setCrimes7p1att1);
        map.put("crimes7p1att2", CrimeForm::setCrimes7p1att2);
        map.put("crimes7p1att3", CrimeForm::setCrimes7p1att3);

        map.put("crimes7p2ar1", (form, val) -> form.setCrimes7p2ar1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p2ar2", (form, val) -> form.setCrimes7p2ar2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p2ar3", (form, val) -> form.setCrimes7p2ar3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p2br1", (form, val) -> form.setCrimes7p2br1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p2br2", (form, val) -> form.setCrimes7p2br2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p2br3", (form, val) -> form.setCrimes7p2br3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p2cr1", (form, val) -> form.setCrimes7p2cr1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p2cr2", (form, val) -> form.setCrimes7p2cr2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p2cr3", (form, val) -> form.setCrimes7p2cr3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p2dr1", (form, val) -> form.setCrimes7p2dr1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p2dr2", (form, val) -> form.setCrimes7p2dr2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p2dr3", (form, val) -> form.setCrimes7p2dr3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p2er1", (form, val) -> form.setCrimes7p2er1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p2er2", (form, val) -> form.setCrimes7p2er2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p2er3", (form, val) -> form.setCrimes7p2er3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p2att1", CrimeForm::setCrimes7p2att1);
        map.put("crimes7p2att2", CrimeForm::setCrimes7p2att2);
        map.put("crimes7p2att3", CrimeForm::setCrimes7p2att3);

        map.put("crimes7p3ar1", (form, val) -> form.setCrimes7p3ar1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p3ar2", (form, val) -> form.setCrimes7p3ar2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p3ar3", (form, val) -> form.setCrimes7p3ar3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p3br1", (form, val) -> form.setCrimes7p3br1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p3br2", (form, val) -> form.setCrimes7p3br2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p3br3", (form, val) -> form.setCrimes7p3br3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p4ar1", (form, val) -> form.setCrimes7p4ar1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p4ar2", (form, val) -> form.setCrimes7p4ar2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p4ar3", (form, val) -> form.setCrimes7p4ar3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p4b", CrimeForm::setCrimes7p4b);

        map.put("crimes7p4b1r1", (form, val) -> form.setCrimes7p4b1r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p4b1r2", (form, val) -> form.setCrimes7p4b1r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p4b1r3", (form, val) -> form.setCrimes7p4b1r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p4b2r1", CrimeForm::setCrimes7p4b2r1);
        map.put("crimes7p4b2r2", CrimeForm::setCrimes7p4b2r2);
        map.put("crimes7p4b2r3", CrimeForm::setCrimes7p4b2r3);

        map.put("crimes7p4b2r1", CrimeForm::setCrimes7p4b2r1);
        map.put("crimes7p4b2r2", CrimeForm::setCrimes7p4b2r2);
        map.put("crimes7p4b2r3", CrimeForm::setCrimes7p4b2r3);

        map.put("crimes7p5ar1", (form, val) -> form.setCrimes7p5ar1("##yes".equalsIgnoreCase(val)));
        map.put("crimes7p5ar2", (form, val) -> form.setCrimes7p5ar2("##yes".equalsIgnoreCase(val)));
        map.put("crimes7p5ar3", (form, val) -> form.setCrimes7p5ar3("##yes".equalsIgnoreCase(val)));

        map.put("crimes7p5br1", (form, val) -> form.setCrimes7p5br1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5br2", (form, val) -> form.setCrimes7p5br2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5br3", (form, val) -> form.setCrimes7p5br3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p5cr1", (form, val) -> form.setCrimes7p5cr1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5cr2", (form, val) -> form.setCrimes7p5cr2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5cr3", (form, val) -> form.setCrimes7p5cr3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p5dr1", (form, val) -> form.setCrimes7p5dr1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5dr2", (form, val) -> form.setCrimes7p5dr2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5dr3", (form, val) -> form.setCrimes7p5dr3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p5er1", (form, val) -> form.setCrimes7p5er1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5er2", (form, val) -> form.setCrimes7p5er2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5er3", (form, val) -> form.setCrimes7p5er3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p5fr1", (form, val) -> form.setCrimes7p5fr1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5fr2", (form, val) -> form.setCrimes7p5fr2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5fr3", (form, val) -> form.setCrimes7p5fr3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p5gr1", (form, val) -> form.setCrimes7p5gr1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5gr2", (form, val) -> form.setCrimes7p5gr2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p5gr3", (form, val) -> form.setCrimes7p5gr3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p6ar1", (form, val) -> form.setCrimes7p6ar1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6ar2", (form, val) -> form.setCrimes7p6ar2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6ar3", (form, val) -> form.setCrimes7p6ar3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p6b", CrimeForm::setCrimes7p6b);

        map.put("crimes7p6b1r1", (form, val) -> form.setCrimes7p6b1r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6b1r2", (form, val) -> form.setCrimes7p6b1r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6b1r3", (form, val) -> form.setCrimes7p6b1r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p6b2r1", (form, val) -> form.setCrimes7p6b2r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6b2r2", (form, val) -> form.setCrimes7p6b2r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6b2r3", (form, val) -> form.setCrimes7p6b2r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p6c", CrimeForm::setCrimes7p6c);

        map.put("crimes7p6c1r1", (form, val) -> form.setCrimes7p6c1r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6c1r2", (form, val) -> form.setCrimes7p6c1r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6c1r3", (form, val) -> form.setCrimes7p6c1r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p6c2r1", (form, val) -> form.setCrimes7p6c2r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6c2r2", (form, val) -> form.setCrimes7p6c2r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6c2r3", (form, val) -> form.setCrimes7p6c2r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p6c3r1", (form, val) -> form.setCrimes7p6c3r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6c3r2", (form, val) -> form.setCrimes7p6c3r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6c3r3", (form, val) -> form.setCrimes7p6c3r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p6d1r1", (form, val) -> form.setCrimes7p6d1r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6d1r2", (form, val) -> form.setCrimes7p6d1r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6d1r3", (form, val) -> form.setCrimes7p6d1r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p6e1r1", (form, val) -> form.setCrimes7p6e1r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6e1r2", (form, val) -> form.setCrimes7p6e1r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p6e1r3", (form, val) -> form.setCrimes7p6e1r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p7a1r1", CrimeForm::setCrimes7p7a1r1);
        map.put("crimes7p7a1r2", CrimeForm::setCrimes7p7a1r2);
        map.put("crimes7p7a1r3", CrimeForm::setCrimes7p7a1r3);
        map.put("crimes7p7a2r1", CrimeForm::setCrimes7p7a2r1);
        map.put("crimes7p7a2r2", CrimeForm::setCrimes7p7a2r2);
        map.put("crimes7p7a2r3", CrimeForm::setCrimes7p7a2r3);
        map.put("crimes7p7a3r1", CrimeForm::setCrimes7p7a3r1);
        map.put("crimes7p7a3r2", CrimeForm::setCrimes7p7a3r2);
        map.put("crimes7p7a3r3", CrimeForm::setCrimes7p7a3r3);
        map.put("crimes7p7b1r1", (form, val) -> form.setCrimes7p7b1r1("##no".equalsIgnoreCase(val)));
        map.put("crimes7p7b1r1normal", CrimeForm::setCrimes7p7b1r1normal);
        map.put("crimes7p7b1r2", (form, val) -> form.setCrimes7p7b1r2("##no".equalsIgnoreCase(val)));
        map.put("crimes7p7b1r2normal", CrimeForm::setCrimes7p7b1r2normal);
        map.put("crimes7p7b1r3", (form, val) -> form.setCrimes7p7b1r3("##no".equalsIgnoreCase(val)));
        map.put("crimes7p7b1r3normal", CrimeForm::setCrimes7p7b1r3normal);
        map.put("crimes7p7b2r1", (form, val) -> form.setCrimes7p7b2r1("##no".equalsIgnoreCase(val)));
        map.put("crimes7p7b2r1normal", CrimeForm::setCrimes7p7b2r1normal);
        map.put("crimes7p7b2r2", (form, val) -> form.setCrimes7p7b2r2("##no".equalsIgnoreCase(val)));
        map.put("crimes7p7b2r2normal", CrimeForm::setCrimes7p7b2r2normal);
        map.put("crimes7p7b2r3", (form, val) -> form.setCrimes7p7b2r3("##no".equalsIgnoreCase(val)));
        map.put("crimes7p7b2r3normal", CrimeForm::setCrimes7p7b2r3normal);

        map.put("crimes7p7cr1", (form, val) -> form.setCrimes7p7cr1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p7cr2", (form, val) -> form.setCrimes7p7cr2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p7cr3", (form, val) -> form.setCrimes7p7cr3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p8a1r1", (form, val) -> form.setCrimes7p8a1r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p8a1r2", (form, val) -> form.setCrimes7p8a1r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p8a1r3", (form, val) -> form.setCrimes7p8a1r3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p8a1sr1", (form, val) -> form.setCrimes7p8a1sr1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p8a1sr2", (form, val) -> form.setCrimes7p8a1sr2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p8a1sr3", (form, val) -> form.setCrimes7p8a1sr3("yes".equalsIgnoreCase(val)));

        map.put("crimes7p8a2r1", CrimeForm::setCrimes7p8a2r1);
        map.put("crimes7p8b1sr1", (form, val) -> form.setCrimes7p8b1sr1("##yes".equalsIgnoreCase(val)));
        map.put("crimes7p8b1sr1normal", CrimeForm::setCrimes7p8b1sr1normal);
        map.put("crimes7p8b1sr2", (form, val) -> form.setCrimes7p8b1sr2("##yes".equalsIgnoreCase(val)));
        map.put("crimes7p8b1sr2normal", CrimeForm::setCrimes7p8b1sr2normal);
        map.put("crimes7p8b1sr3", (form, val) -> form.setCrimes7p8b1sr3("##yes".equalsIgnoreCase(val)));
        map.put("crimes7p8b1sr3normal", CrimeForm::setCrimes7p8b1sr3normal);

        map.put("crimes7p8b2r1", (form, val) -> form.setCrimes7p8b2r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p8b2r2", (form, val) -> form.setCrimes7p8b2r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p8b2r3", (form, val) -> form.setCrimes7p8b2r3("yes".equalsIgnoreCase(val)));
        
        map.put("crimes7p8cr1", (form, val) -> form.setCrimes7p8cr1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p8cr2", (form, val) -> form.setCrimes7p8cr2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p8cr3", (form, val) -> form.setCrimes7p8cr3("yes".equalsIgnoreCase(val)));
        
        map.put("crimes7p8c1r1", (form, val) -> form.setCrimes7p8c1r1("yes".equalsIgnoreCase(val)));
        map.put("crimes7p8c1r2", (form, val) -> form.setCrimes7p8c1r2("yes".equalsIgnoreCase(val)));
        map.put("crimes7p8c1r3", (form, val) -> form.setCrimes7p8c1r3("yes".equalsIgnoreCase(val)));

        map.put("crimes8p1", (form, val) -> form.setCrimes8p1("yes".equalsIgnoreCase(val)));

        map.put("crimes8p2a1", (form, val) -> form.setCrimes8p2a1("yes".equalsIgnoreCase(val)));
        map.put("crimes8p2a2", (form, val) -> form.setCrimes8p2a2("yes".equalsIgnoreCase(val)));
        map.put("crimes8p2a3", (form, val) -> form.setCrimes8p2a3("yes".equalsIgnoreCase(val)));
        map.put("crimes8p2a4", (form, val) -> form.setCrimes8p2a4("yes".equalsIgnoreCase(val)));

        map.put("crimes8p2a5", (form, val) -> form.setCrimes8p2a5("##X".equalsIgnoreCase(val)));
        map.put("crimes8p2a5normal", CrimeForm::setCrimes8p2a5normal);
        map.put("crimes8p3ar1", CrimeForm::setCrimes8p3ar1);
        map.put("crimes8p3br1", CrimeForm::setCrimes8p3br1);
        map.put("crimes8p3cr1", CrimeForm::setCrimes8p3cr1);
        map.put("crimes8p3ar2", CrimeForm::setCrimes8p3ar2);
        map.put("crimes8p3br2", CrimeForm::setCrimes8p3br2);
        map.put("crimes8p3cr2", CrimeForm::setCrimes8p3cr2);

        map.put("crimes8p4", (form, val) -> form.setCrimes8p4("yes".equalsIgnoreCase(val)));
        map.put("crimes8p5", (form, val) -> form.setCrimes8p5("yes".equalsIgnoreCase(val)));
        map.put("crimes8p6", (form, val) -> form.setCrimes8p6("##yes".equalsIgnoreCase(val)));

        map.put("crimes8p6normal", CrimeForm::setCrimes8p6normal);

        map.put("crimes8p6a", (form, val) -> form.setCrimes8p6a("yes".equalsIgnoreCase(val)));
        map.put("crimes8p6b", (form, val) -> form.setCrimes8p6b("yes".equalsIgnoreCase(val)));
        map.put("crimes8p6c", (form, val) -> form.setCrimes8p6c("yes".equalsIgnoreCase(val)));

        map.put("crimes8p7", (form, val) -> form.setCrimes8p7("yes".equalsIgnoreCase(val)));
        map.put("crimes8p8", (form, val) -> form.setCrimes8p8("yes".equalsIgnoreCase(val)));
        map.put("crimes8p9", (form, val) -> form.setCrimes8p9("yes".equalsIgnoreCase(val)));
        map.put("crimes8p10", (form, val) -> form.setCrimes8p10("yes".equalsIgnoreCase(val)));
        map.put("crimes8p11", (form, val) -> form.setCrimes8p11("yes".equalsIgnoreCase(val)));
        map.put("crimes8p12", (form, val) -> form.setCrimes8p12("yes".equalsIgnoreCase(val)));
        map.put("crimes8p13", (form, val) -> form.setCrimes8p13("yes".equalsIgnoreCase(val)));
        map.put("crimes8p14", (form, val) -> form.setCrimes8p14("yes".equalsIgnoreCase(val)));

        map.put("crimes8p14a", (form, val) -> form.setCrimes8p14a("yes".equalsIgnoreCase(val)));
        map.put("crimes8p14b", (form, val) -> form.setCrimes8p14b("yes".equalsIgnoreCase(val)));
        map.put("crimes8p14c", (form, val) -> form.setCrimes8p14c("yes".equalsIgnoreCase(val)));

        map.put("crimes9p1", (form, val) -> form.setCrimes9p1("yes".equalsIgnoreCase(val)));
        map.put("crimes9p2", (form, val) -> form.setCrimes9p2("yes".equalsIgnoreCase(val)));
        map.put("crimes9p3", (form, val) -> form.setCrimes9p3("yes".equalsIgnoreCase(val)));
        map.put("crimes9p4", (form, val) -> form.setCrimes9p4("yes".equalsIgnoreCase(val)));
        map.put("crimes9p5", (form, val) -> form.setCrimes9p5("yes".equalsIgnoreCase(val)));
        map.put("crimes9p6", (form, val) -> form.setCrimes9p6("yes".equalsIgnoreCase(val)));
        map.put("crimes9p7", (form, val) -> form.setCrimes9p7("yes".equalsIgnoreCase(val)));

        map.put("printDate", CrimeForm::setPrintDate);

        return map;
    }

}
