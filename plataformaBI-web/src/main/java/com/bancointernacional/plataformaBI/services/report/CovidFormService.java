package com.bancointernacional.plataformaBI.services.report;

import com.bancointernacional.plataformaBI.models.report.bbb.CovidForm;

public interface CovidFormService {

    CovidForm createSampleForm();

    CovidForm fullFillQuestionerData(String questID);

}
