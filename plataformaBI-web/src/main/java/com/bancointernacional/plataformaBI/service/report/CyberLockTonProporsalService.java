package com.bancointernacional.plataformaBI.service.report;


import com.bancointernacional.plataformaBI.domain.report.cyber.CyberLocktonProposalForm;

public interface CyberLockTonProporsalService {

    CyberLocktonProposalForm createSampleForm();

    CyberLocktonProposalForm fullFillQuestionerData(String reportId);
}
