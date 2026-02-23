package com.bancointernacional.plataformaBI.services.report;

import com.bancointernacional.plataformaBI.models.report.DO.WillisForm;

public interface WillisFormService {

    WillisForm createSampleForm();

    WillisForm fullFillQuestionerData(String reportId);
}
