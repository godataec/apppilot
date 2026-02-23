package com.bancointernacional.plataformaBI.service.report;


import com.bancointernacional.plataformaBI.domain.report.cyber.RansomwareForm;

public interface RansomwareFormService {

    RansomwareForm createSampleForm();

    RansomwareForm fullFillQuestionerData(String reportId);

}
