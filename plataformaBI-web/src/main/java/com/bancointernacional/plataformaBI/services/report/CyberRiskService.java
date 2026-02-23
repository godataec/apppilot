package com.bancointernacional.plataformaBI.services.report;

import com.bancointernacional.plataformaBI.models.report.bbb.CovidForm;
import com.bancointernacional.plataformaBI.models.report.cyber.CyberRiskForm;

public interface CyberRiskService {

    public CyberRiskForm createSampleForm();

    CyberRiskForm fullFillQuestionerData(String questID);

}
