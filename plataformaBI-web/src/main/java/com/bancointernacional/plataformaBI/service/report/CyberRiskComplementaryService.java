package com.bancointernacional.plataformaBI.service.report;


import com.bancointernacional.plataformaBI.domain.report.cyber.CyberRiskComplementaryInfoForm;

public interface CyberRiskComplementaryService {

    public CyberRiskComplementaryInfoForm createSampleForm();

    public CyberRiskComplementaryInfoForm fullFillQuestionerData(String idQuest);
}
