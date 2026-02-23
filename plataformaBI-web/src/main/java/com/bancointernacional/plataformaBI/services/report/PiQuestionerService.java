package com.bancointernacional.plataformaBI.services.report;

import com.bancointernacional.plataformaBI.models.report.bbb.PIForm;

public interface PiQuestionerService {

    PIForm createSampleForm();

    PIForm fullFillQuestionerData(String reportId);
}
