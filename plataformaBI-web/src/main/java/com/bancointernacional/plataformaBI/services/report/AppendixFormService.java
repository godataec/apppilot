package com.bancointernacional.plataformaBI.services.report;

import com.bancointernacional.plataformaBI.models.report.bbb.AppendixForm;
import com.bancointernacional.plataformaBI.models.report.bbb.CovidForm;

import javax.naming.Context;

public interface AppendixFormService {


    AppendixForm createSampleForm();

    AppendixForm fullFillQuestionerData(String questID);

}
