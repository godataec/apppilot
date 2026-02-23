package com.bancointernacional.plataformaBI.service.report;


import com.bancointernacional.plataformaBI.domain.report.bbb.PIForm;

public interface PiQuestionerService {

    PIForm fullFillQuestionerData(String reportId);
}
