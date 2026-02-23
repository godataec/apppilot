package com.bancointernacional.plataformaBI.service.report;


import com.bancointernacional.plataformaBI.domain.report.bbb.CovidForm;

public interface CovidFormService {

    CovidForm createSampleForm();

    CovidForm fullFillQuestionerData(String questID);

}
