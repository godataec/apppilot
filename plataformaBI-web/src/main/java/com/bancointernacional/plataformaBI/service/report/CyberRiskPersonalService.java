package com.bancointernacional.plataformaBI.service.report;


import com.bancointernacional.plataformaBI.domain.report.cyber.CyberRiskPersonalDataForm;

public interface CyberRiskPersonalService {

    CyberRiskPersonalDataForm createSampleForm();

    CyberRiskPersonalDataForm fullFillQuestionerData(String reportId);
}
