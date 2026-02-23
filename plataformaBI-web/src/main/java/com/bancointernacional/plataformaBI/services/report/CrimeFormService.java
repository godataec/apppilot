package com.bancointernacional.plataformaBI.services.report;

import com.bancointernacional.plataformaBI.models.report.bbb.CrimeForm;

public interface CrimeFormService {

    CrimeForm createSampleForm();

    CrimeForm fullFillQuestionerData(String reportId);
}
