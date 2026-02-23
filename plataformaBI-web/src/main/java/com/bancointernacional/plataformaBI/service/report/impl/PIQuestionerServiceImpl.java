package com.bancointernacional.plataformaBI.service.report.impl;

import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.model.report.table.TableView;
import com.bancointernacional.plataformaBI.domain.report.bbb.PIForm;
import com.bancointernacional.plataformaBI.repository.period.UserAnswerRepository;
import com.bancointernacional.plataformaBI.service.report.PiQuestionerService;
import com.bancointernacional.plataformaBI.util.TableBuilder;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;

import static com.bancointernacional.plataformaBI.enums.AnswerTypes.TABLE;

@Service
public class PIQuestionerServiceImpl implements PiQuestionerService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Override
    public PIForm fullFillQuestionerData(String policyQuestionaryId) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByPolicyQuestionaryId(policyQuestionaryId);
        PIForm piForm = new PIForm();
        Map<String, BiConsumer<PIForm, String>> setterMap = createSetterMap();
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
                BiConsumer<PIForm, String> setter = setterMap.get(documentId);
                if (setter != null) {
                    setter.accept(piForm, answerContent);
                } else {
                    System.out.println("Warning: No setter found for documentId: " + documentId);
                }
            } else {
                tableAnswers.add(answer);
            }
        }
        Map<String, TableView> tables = TableBuilder.buildTables(tableAnswers);
        piForm.setTables(tables);
        return piForm;
    }

    private Map<String, BiConsumer<PIForm, String>> createSetterMap() {
        Map<String, BiConsumer<PIForm, String>> map = new HashMap<>();

        map.put("companyName", PIForm::setCompanyName);
        map.put("companyEstablishmentDate", PIForm::setCompanyEstablishmentDate);
        map.put("mainCompanyActivity", PIForm::setMainCompanyActivity);
        map.put("privateCompany", (form, val) -> form.setPrivateCompany(
                val != null && (val.equalsIgnoreCase("yes") ||
                        val.equalsIgnoreCase("Private") ||
                        val.toLowerCase().contains("private"))));
        map.put("privateCompanynormal", PIForm::setPrivateCompanynormal);
        map.put("stockExchanges", PIForm::setStockExchanges);
        map.put("acquisitionYes", (form, val) -> form.setAcquisitionYes("yes".equalsIgnoreCase(val)));
        map.put("currentYearLoans", PIForm::setCurrentYearLoans);
        map.put("previousYearLoans", PIForm::setPreviousYearLoans);
        map.put("currentYearTotalEmployees", PIForm::setCurrentYearTotalEmployees);
        map.put("previousYearTotalEmployees", PIForm::setPreviousYearTotalEmployees);
        map.put("currentYearTrainingStaff", PIForm::setCurrentYearTrainingStaff);
        map.put("previousYearTrainingStaff", PIForm::setPreviousYearTrainingStaff);
        map.put("currentYearComplianceStaff", PIForm::setCurrentYearComplianceStaff);
        map.put("previousYearComplianceStaff", PIForm::setPreviousYearComplianceStaff);
        map.put("currentYearDirectors", PIForm::setCurrentYearDirectors);
        map.put("previousYearDirectors", PIForm::setPreviousYearDirectors);
        map.put("currentYearSalesStaff", PIForm::setCurrentYearSalesStaff);
        map.put("previousYearSalesStaff", PIForm::setPreviousYearSalesStaff);
        map.put("currentYearSupportStaff", PIForm::setCurrentYearSupportStaff);
        map.put("previousYearSupportStaff", PIForm::setPreviousYearSupportStaff);
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
        map.put("currentVentureCapital", PIForm::setCurrentVentureCapital);
        map.put("previousVentureCapital", PIForm::setPreviousVentureCapital);
        map.put("otherActivities", PIForm::setOtherActivities);
        map.put("currentYearTrustee", PIForm::setCurrentYearTrustee);
        map.put("previousYearTrustee", PIForm::setPreviousYearTrustee);
        map.put("currentYearOverseasAdvisory", PIForm::setCurrentYearOverseasAdvisory);
        map.put("previousYearOverseasAdvisory", PIForm::setPreviousYearOverseasAdvisory);
        map.put("currentYearOtherActivities", PIForm::setCurrentYearOtherActivities);
        map.put("previousYearOtherActivities", PIForm::setPreviousYearOtherActivities);
        map.put("newServicesYes", (form, val) -> form.setNewServicesYes("yes".equalsIgnoreCase(val)));
        map.put("futureServicesYes", (form, val) -> form.setFutureServicesYes("yes".equalsIgnoreCase(val)));
        map.put("regulatoryAuthorities", PIForm::setRegulatoryAuthorities);
        map.put("mergerAdvisorYes", (form, val) -> form.setMergerAdvisorYes("yes".equalsIgnoreCase(val)));
        map.put("sharePlacingAdvisorYes", (form, val) -> form.setSharePlacingAdvisorYes("yes".equalsIgnoreCase(val)));
        map.put("trustActivitiesYes", (form, val) -> form.setTrustActivitiesYes("yes".equalsIgnoreCase(val)));
        map.put("electronicTransferYes", (form, val) -> form.setElectronicTransferYes("yes".equalsIgnoreCase(val)));
        map.put("internetAccessYes", (form, val) -> form.setInternetAccessYes("yes".equalsIgnoreCase(val)));
        map.put("externalAuditorName", PIForm::setExternalAuditorName);
        map.put("externalAuditorAddress", PIForm::setExternalAuditorAddress);
        map.put("auditFrequency", PIForm::setAuditFrequency);
        map.put("internalControlReviewYes",(form, val) -> form.setInternalControlReviewYes("yes".equalsIgnoreCase(val)));
        map.put("writtenReportsYes", (form, val) -> form.setWrittenReportsYes("yes".equalsIgnoreCase(val)));
        map.put("auditRecommendationsYes", (form, val) -> form.setAuditRecommendationsYes("##yes".equalsIgnoreCase(val)));
        map.put("auditRecommendationsYesnormal", PIForm::setAuditRecommendationsYesnormal);
        map.put("auditorChangedYes", (form, val) -> form.setAuditorChangedYes("##yes".equalsIgnoreCase(val)));
        map.put("auditorChangedYesnormal", PIForm::setAuditorChangedYesnormal);
        map.put("internalAuditDeptYes", (form, val) -> form.setInternalAuditDeptYes("yes".equalsIgnoreCase(val)));
        map.put("internalAuditFrequency", PIForm::setInternalAuditFrequency);
        map.put("ethicsCodeYes", (form, val) -> form.setEthicsCodeYes("yes".equalsIgnoreCase(val)));
        map.put("ethicsCodeAcknowledgementYes",(form, val) -> form.setEthicsCodeAcknowledgementYes("yes".equalsIgnoreCase(val)));
        map.put("conflictDeclarationYes", (form, val) -> form.setConflictDeclarationYes("yes".equalsIgnoreCase(val)));

        map.put("legalNameAddress", PIForm::setLegalNameAddress);
        map.put("legalOpinionsYes", (form, val) -> form.setLegalOpinionsYes("yes".equalsIgnoreCase(val)));
        map.put("internalLegalDeptYes", (form, val) -> form.setInternalLegalDeptYes("yes".equalsIgnoreCase(val)));
        map.put("internalLegalDeptnormal", PIForm::setInternalLegalDeptnormal);
        map.put("internalLegalIndividuals", PIForm::setInternalLegalIndividuals);
        map.put("departmentResponsibilities", PIForm::setDepartmentResponsibilities);
        map.put("thirdPartyServicesYes", (form, val) -> form.setThirdPartyServicesYes("##yes".equalsIgnoreCase(val)));
        map.put("thirdPartyServicesYesnormal", PIForm::setThirdPartyServicesYesnormal);
        map.put("legalReviewYes", (form, val) -> form.setLegalReviewYes("yes".equalsIgnoreCase(val)));
        map.put("pendingLitigationYes", (form, val) -> form.setPendingLitigationYes("yes".equalsIgnoreCase(val)));
        map.put("potentialClaimsYes", (form, val) -> form.setPotentialClaimsYes("yes".equalsIgnoreCase(val)));
        map.put("regulatoryCriticismYes", (form, val) -> form.setRegulatoryCriticismYes("yes".equalsIgnoreCase(val)));
        map.put("claimDetails", PIForm::setClaimDetails);
        map.put("previousInsuranceYes", (form, val) -> form.setPreviousInsuranceYes("yes".equalsIgnoreCase(val)));

        map.put("previousInsurer", PIForm::setPreviousInsurer);
        map.put("policyEndDate", PIForm::setPolicyEndDate);
        map.put("policyAssuredSum", PIForm::setPolicyAssuredSum);
        map.put("requestedAssuredSum", PIForm::setRequestedAssuredSum);
        map.put("signerPosition", PIForm::setSignerPosition);
        map.put("signatureDate", PIForm::setSignatureDate);

        return map;
    }

}
