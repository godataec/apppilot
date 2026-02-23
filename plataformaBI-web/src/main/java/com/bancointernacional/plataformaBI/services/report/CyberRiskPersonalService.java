package com.bancointernacional.plataformaBI.services.report;

import com.bancointernacional.plataformaBI.models.report.cyber.CyberRiskPersonalDataForm;

public interface CyberRiskPersonalService {

    CyberRiskPersonalDataForm createSampleForm();

    CyberRiskPersonalDataForm fullFillQuestionerData(String reportId);
}
