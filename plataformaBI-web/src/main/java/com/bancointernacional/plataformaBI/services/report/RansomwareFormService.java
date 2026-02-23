package com.bancointernacional.plataformaBI.services.report;

import com.bancointernacional.plataformaBI.models.report.cyber.RansomwareForm;

public interface RansomwareFormService {

    RansomwareForm createSampleForm();

    RansomwareForm fullFillQuestionerData(String reportId);

}
