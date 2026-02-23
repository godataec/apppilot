package com.bancointernacional.plataformaBI.services.report;

import com.bancointernacional.plataformaBI.models.report.cyber.CyberLocktonProposalForm;

public interface CyberLockTonProporsalService {

    CyberLocktonProposalForm createSampleForm();

    CyberLocktonProposalForm fullFillQuestionerData(String reportId);
}
