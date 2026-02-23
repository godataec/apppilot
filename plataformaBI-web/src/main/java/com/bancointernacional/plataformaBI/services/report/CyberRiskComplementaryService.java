package com.bancointernacional.plataformaBI.services.report;

import com.bancointernacional.plataformaBI.models.report.cyber.CyberRiskComplementaryInfoForm;

public interface CyberRiskComplementaryService {

    public CyberRiskComplementaryInfoForm createSampleForm();

    public CyberRiskComplementaryInfoForm fullFillQuestionerData(String idQuest);
}
