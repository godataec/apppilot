package com.bancointernacional.plataformaBI.service.report;


import com.bancointernacional.plataformaBI.domain.report.DO.WillisForm;

public interface WillisFormService {

    WillisForm createSampleForm();

    WillisForm fullFillQuestionerData(String reportId);
}
