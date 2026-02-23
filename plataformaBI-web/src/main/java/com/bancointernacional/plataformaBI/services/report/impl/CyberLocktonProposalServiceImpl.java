package com.bancointernacional.plataformaBI.services.report.impl;

import com.bancointernacional.plataformaBI.models.DTO.AnswerDTO;
import com.bancointernacional.plataformaBI.models.DTO.QuestionDTO;
import com.bancointernacional.plataformaBI.models.report.cyber.CyberLocktonProposalForm;
import com.bancointernacional.plataformaBI.services.report.CyberLockTonProporsalService;
import com.bancointernacional.plataformaBI.services.serviceInterface.AnswerService;
import com.bancointernacional.plataformaBI.services.serviceInterface.QuestionService;
import com.bancointernacional.plataformaBI.util.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class CyberLocktonProposalServiceImpl implements CyberLockTonProporsalService {

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;


    @Override
    public CyberLocktonProposalForm createSampleForm() {
        return null;
    }

    @Override
    public CyberLocktonProposalForm fullFillQuestionerData(String idQuest) {
        List<QuestionDTO> procesosQuest = questionService.getQuestionByCharacters(idQuest, 1, 999);
        List<Integer> answersIds = procesosQuest.stream().map(QuestionDTO::getIdQuestion).collect(Collectors.toList());

        ArrayList<AnswerDTO> answersFound = new ArrayList<AnswerDTO>();
        for(int i = 0; i < answersIds.size(); i++) {
            List<AnswerDTO> listOfAnswer = answerService.findByProcessAndQuestion(
                    UUID.fromString(idQuest), Long.valueOf(answersIds.get(i)));
            answersFound.addAll(listOfAnswer);
        }

        CyberLocktonProposalForm cyberLocktonProposalForm = new CyberLocktonProposalForm();

        Map<String, BiConsumer<CyberLocktonProposalForm, String>> setterMap = createSetterMap();
        for (AnswerDTO answer : answersFound) {
            if (answer == null || answer.getDocumentId() == null) {
                continue;
            }

            String documentId = answer.getDocumentId();
            String answerContent = "";
            if(UUIDValidator.isValidUUID(documentId)){
                AnswerDTO temp = answerService.findQuestionByAnswer(UUID.fromString(documentId));
                answerContent  = temp.getAnswerText();
                if(answerContent.isEmpty() && !answer.getAnswerText().isEmpty()){
                    answerContent = answer.getAnswerText();
                }
                documentId = temp.getDocumentId();
            }else{
                answerContent  = answer.getAnswerText();
            }


            BiConsumer<CyberLocktonProposalForm, String> setter = setterMap.get(documentId);
            if (setter != null) {
                setter.accept(cyberLocktonProposalForm, answerContent);
            } else {
                System.out.println("Warning: No setter found for documentId: " + documentId);
            }
        }

        return cyberLocktonProposalForm;
    }

    private Map<String, BiConsumer<CyberLocktonProposalForm, String>> createSetterMap() {
        Map<String, BiConsumer<CyberLocktonProposalForm, String>> map = new HashMap<>();

        map.put("Clocktonproposals1p1a", CyberLocktonProposalForm::setClocktonproposals1p1a);
        map.put("Clocktonproposals1p1b", CyberLocktonProposalForm::setClocktonproposals1p1b);
        map.put("Clocktonproposals1p1c", CyberLocktonProposalForm::setClocktonproposals1p1c);
        map.put("Clocktonproposals1p1d", CyberLocktonProposalForm::setClocktonproposals1p1d);
        map.put("Clocktonproposals1p1e", CyberLocktonProposalForm::setClocktonproposals1p1e);
        map.put("Clocktonproposals1p1f", CyberLocktonProposalForm::setClocktonproposals1p1f);
        map.put("Clocktonproposals1p1h", CyberLocktonProposalForm::setClocktonproposals1p1h);
        map.put("Clocktonproposals1p1i", CyberLocktonProposalForm::setClocktonproposals1p1i);
        map.put("Clocktonproposals1p1j", CyberLocktonProposalForm::setClocktonproposals1p1j);
        map.put("Clocktonproposals1p1k", CyberLocktonProposalForm::setClocktonproposals1p1k);
        map.put("Clocktonproposals1p1m", CyberLocktonProposalForm::setClocktonproposals1p1m);
        map.put("Clocktonproposals1p1n", CyberLocktonProposalForm::setClocktonproposals1p1n);
        map.put("Clocktonproposals1p1o", CyberLocktonProposalForm::setClocktonproposals1p1o);
        map.put("Clocktonproposals1p1ar1", CyberLocktonProposalForm::setClocktonproposals1p1ar1);
        map.put("Clocktonproposals1p1q", CyberLocktonProposalForm::setClocktonproposals1p1q);
        map.put("Clocktonproposals1p1r", CyberLocktonProposalForm::setClocktonproposals1p1r);

        map.put("Clocktonproposals2p1ar1", CyberLocktonProposalForm::setClocktonproposals2p1ar1);
        map.put("Clocktonproposals2p1br1", CyberLocktonProposalForm::setClocktonproposals2p1br1);
        map.put("Clocktonproposals2p1cr1", CyberLocktonProposalForm::setClocktonproposals2p1cr1);
        map.put("Clocktonproposals2p1ar2", CyberLocktonProposalForm::setClocktonproposals2p1ar2);
        map.put("Clocktonproposals2p1br2", CyberLocktonProposalForm::setClocktonproposals2p1br2);
        map.put("Clocktonproposals2p1cr2", CyberLocktonProposalForm::setClocktonproposals2p1cr2);
        map.put("Clocktonproposals2p1ar3", CyberLocktonProposalForm::setClocktonproposals2p1ar3);
        map.put("Clocktonproposals2p1br3", CyberLocktonProposalForm::setClocktonproposals2p1br3);
        map.put("Clocktonproposals2p1cr3", CyberLocktonProposalForm::setClocktonproposals2p1cr3);

        map.put("Clocktonproposals2p2a", CyberLocktonProposalForm::setClocktonproposals2p2a);
        map.put("Clocktonproposals2p2a1", CyberLocktonProposalForm::setClocktonproposals2p2a1);
        map.put("Clocktonproposals2p2b", CyberLocktonProposalForm::setClocktonproposals2p2b);
        map.put("Clocktonproposals2p2c", CyberLocktonProposalForm::setClocktonproposals2p2c);
        map.put("Clocktonproposals2p2dr1", CyberLocktonProposalForm::setClocktonproposals2p2dr1);
        map.put("Clocktonproposals2p2dr2", CyberLocktonProposalForm::setClocktonproposals2p2dr2);
        map.put("Clocktonproposals2p2dr3", CyberLocktonProposalForm::setClocktonproposals2p2dr3);
        map.put("Clocktonproposals2p2e", CyberLocktonProposalForm::setClocktonproposals2p2e);
        map.put("Clocktonproposals2p2f", CyberLocktonProposalForm::setClocktonproposals2p2f);
        map.put("Clocktonproposals2p2g", CyberLocktonProposalForm::setClocktonproposals2p2g);
        map.put("Clocktonproposals2p2h", CyberLocktonProposalForm::setClocktonproposals2p2h);

        map.put("Clocktonproposals2p2i", CyberLocktonProposalForm::setClocktonproposals2p2i);
        map.put("Clocktonproposals2p2j", CyberLocktonProposalForm::setClocktonproposals2p2j);
        map.put("Clocktonproposals2p2k", CyberLocktonProposalForm::setClocktonproposals2p2k);
        map.put("Clocktonproposals2p2l", CyberLocktonProposalForm::setClocktonproposals2p2l);
        map.put("Clocktonproposals2p2s1", CyberLocktonProposalForm::setClocktonproposals2p2s1);

        map.put("Clocktonproposals2p6a", CyberLocktonProposalForm::setClocktonproposals2p6a);
        map.put("Clocktonproposals2p6b", CyberLocktonProposalForm::setClocktonproposals2p6b);
        map.put("Clocktonproposals2p6b1", CyberLocktonProposalForm::setClocktonproposals2p6b1);
        map.put("Clocktonproposals2p6b2", CyberLocktonProposalForm::setClocktonproposals2p6b2);
        map.put("Clocktonproposals2p6c", CyberLocktonProposalForm::setClocktonproposals2p6c);
        map.put("Clocktonproposals2p6d", CyberLocktonProposalForm::setClocktonproposals2p6d);
        map.put("Clocktonproposals2p6d2", CyberLocktonProposalForm::setClocktonproposals2p6d2);
        map.put("Clocktonproposals2p6d3", CyberLocktonProposalForm::setClocktonproposals2p6d3);
        map.put("Clocktonproposals2p6e", CyberLocktonProposalForm::setClocktonproposals2p6e);
        map.put("Clocktonproposals2p7a", CyberLocktonProposalForm::setClocktonproposals2p7a);
        map.put("Clocktonproposals2p7a1", CyberLocktonProposalForm::setClocktonproposals2p7a1);

        map.put("Clocktonproposals2p8", CyberLocktonProposalForm::setClocktonproposals2p8);
        map.put("Clocktonproposals2p8a1", CyberLocktonProposalForm::setClocktonproposals2p8a1);
        map.put("Clocktonproposals2p8a1_other", CyberLocktonProposalForm::setClocktonproposals2p8a1Other);
        map.put("Clocktonproposals2p8b", CyberLocktonProposalForm::setClocktonproposals2p8b);

        map.put("Clocktonproposals2p9", CyberLocktonProposalForm::setClocktonproposals2p9);
        map.put("Clocktonproposals2p9a", CyberLocktonProposalForm::setClocktonproposals2p9a);
        map.put("Clocktonproposals2p10", CyberLocktonProposalForm::setClocktonproposals2p10);
        map.put("Clocktonproposals2p10a", CyberLocktonProposalForm::setClocktonproposals2p10a);
        map.put("Clocktonproposals2p11", CyberLocktonProposalForm::setClocktonproposals2p11);
        map.put("Clocktonproposals2p12", CyberLocktonProposalForm::setClocktonproposals2p12);
        map.put("Clocktonproposals2p13", CyberLocktonProposalForm::setClocktonproposals2p13);
        map.put("Clocktonproposals2p13a", CyberLocktonProposalForm::setClocktonproposals2p13a);
        map.put("Clocktonproposals2p13b1", CyberLocktonProposalForm::setClocktonproposals2p13b1);
        map.put("Clocktonproposals2p13b1_other", CyberLocktonProposalForm::setClocktonproposals2p13b1Other);
        map.put("Clocktonproposals2p14", CyberLocktonProposalForm::setClocktonproposals2p14);
        map.put("Clocktonproposals2p14a", CyberLocktonProposalForm::setClocktonproposals2p14a);
        map.put("Clocktonproposals2p14a_other", CyberLocktonProposalForm::setClocktonproposals2p14aOther);
        map.put("Clocktonproposals2p15", CyberLocktonProposalForm::setClocktonproposals2p15);
        map.put("Clocktonproposals2p15a1", CyberLocktonProposalForm::setClocktonproposals2p15a1);
        map.put("Clocktonproposals2p15a1_other", CyberLocktonProposalForm::setClocktonproposals2p15a1Other);
        map.put("Clocktonproposals2p16", CyberLocktonProposalForm::setClocktonproposals2p16);
        map.put("Clocktonproposals2p17", CyberLocktonProposalForm::setClocktonproposals2p17);
        map.put("Clocktonproposals2p18", CyberLocktonProposalForm::setClocktonproposals2p18);

        map.put("Clocktonproposals2p19r1", CyberLocktonProposalForm::setClocktonproposals2p19r1);
        map.put("Clocktonproposals2p19r2", CyberLocktonProposalForm::setClocktonproposals2p19r2);
        map.put("Clocktonproposals2p19r3", CyberLocktonProposalForm::setClocktonproposals2p19r3);
        map.put("Clocktonproposals2p20r1", CyberLocktonProposalForm::setClocktonproposals2p20r1);
        map.put("Clocktonproposals2p20r2", CyberLocktonProposalForm::setClocktonproposals2p20r2);
        map.put("Clocktonproposals2p20Ar1", CyberLocktonProposalForm::setClocktonproposals2p20Ar1);

        map.put("Clocktonproposals3p1a", CyberLocktonProposalForm::setClocktonproposals3p1a);
        map.put("Clocktonproposals3p1a_records", CyberLocktonProposalForm::setClocktonproposals3p1aRecords);
        map.put("Clocktonproposals3p1b", CyberLocktonProposalForm::setClocktonproposals3p1b);
        map.put("Clocktonproposals3p1b_records", CyberLocktonProposalForm::setClocktonproposals3p1bRecords);
        map.put("Clocktonproposals3p1c", CyberLocktonProposalForm::setClocktonproposals3p1c);
        map.put("Clocktonproposals3p1c_records", CyberLocktonProposalForm::setClocktonproposals3p1cRecords);
        map.put("Clocktonproposals3p1d", CyberLocktonProposalForm::setClocktonproposals3p1d);
        map.put("Clocktonproposals3p1d_records", CyberLocktonProposalForm::setClocktonproposals3p1dRecords);
        map.put("Clocktonproposals3p1e", CyberLocktonProposalForm::setClocktonproposals3p1e);
        map.put("Clocktonproposals3p1e_records", CyberLocktonProposalForm::setClocktonproposals3p1eRecords);
        map.put("Clocktonproposals3p1f", CyberLocktonProposalForm::setClocktonproposals3p1f);
        map.put("Clocktonproposals3p1f_records", CyberLocktonProposalForm::setClocktonproposals3p1fRecords);
        map.put("Clocktonproposals3p1f_explain", CyberLocktonProposalForm::setClocktonproposals3p1fExplain);
        map.put("Clocktonproposals3p2", CyberLocktonProposalForm::setClocktonproposals3p2);
        map.put("Clocktonproposals3p3", CyberLocktonProposalForm::setClocktonproposals3p3);
        map.put("Clocktonproposals3p4", CyberLocktonProposalForm::setClocktonproposals3p4);
        map.put("Clocktonproposals3p4_volume", CyberLocktonProposalForm::setClocktonproposals3p4Volume);
        map.put("Clocktonproposals3p5", CyberLocktonProposalForm::setClocktonproposals3p5);

        map.put("Clocktonproposals4p1", CyberLocktonProposalForm::setClocktonproposals4p1);
        map.put("Clocktonproposals4p2", CyberLocktonProposalForm::setClocktonproposals4p2);
        map.put("Clocktonproposals4p3", CyberLocktonProposalForm::setClocktonproposals4p3);
        map.put("Clocktonproposals4p4", CyberLocktonProposalForm::setClocktonproposals4p4);
        map.put("Clocktonproposals4p5", CyberLocktonProposalForm::setClocktonproposals4p5);
        map.put("Clocktonproposals4p6", CyberLocktonProposalForm::setClocktonproposals4p6);
        map.put("Clocktonproposals4p7", CyberLocktonProposalForm::setClocktonproposals4p7);
        map.put("Clocktonproposals4p8", CyberLocktonProposalForm::setClocktonproposals4p8);
        map.put("Clocktonproposals4p9", CyberLocktonProposalForm::setClocktonproposals4p9);

        map.put("Clocktonproposals5p1a", CyberLocktonProposalForm::setClocktonproposals5p1a);
        map.put("Clocktonproposals5p1b", CyberLocktonProposalForm::setClocktonproposals5p1b);
        map.put("Clocktonproposals5p1b1", CyberLocktonProposalForm::setClocktonproposals5p1b1);
        map.put("Clocktonproposals5p1b2", CyberLocktonProposalForm::setClocktonproposals5p1b2);
        map.put("Clocktonproposals5p1b3", CyberLocktonProposalForm::setClocktonproposals5p1b3);
        map.put("Clocktonproposals5p1b4", CyberLocktonProposalForm::setClocktonproposals5p1b4);
        map.put("Clocktonproposals5p1c", CyberLocktonProposalForm::setClocktonproposals5p1c);
        map.put("Clocktonproposals5p1d", CyberLocktonProposalForm::setClocktonproposals5p1d);
        map.put("Clocktonproposals5p1e", CyberLocktonProposalForm::setClocktonproposals5p1e);
        map.put("Clocktonproposals5p1f", CyberLocktonProposalForm::setClocktonproposals5p1f);

        map.put("Clocktonproposals5p2a", CyberLocktonProposalForm::setClocktonproposals5p2a);
        map.put("Clocktonproposals5p2b", CyberLocktonProposalForm::setClocktonproposals5p2b);
        map.put("Clocktonproposals5p2c", CyberLocktonProposalForm::setClocktonproposals5p2c);
        map.put("Clocktonproposals5p2d", CyberLocktonProposalForm::setClocktonproposals5p2d);
        map.put("Clocktonproposals5p2e", CyberLocktonProposalForm::setClocktonproposals5p2e);
        map.put("Clocktonproposals5p3", CyberLocktonProposalForm::setClocktonproposals5p3);
        map.put("Clocktonproposals5p4", CyberLocktonProposalForm::setClocktonproposals5p4);
        map.put("Clocktonproposals5p5", CyberLocktonProposalForm::setClocktonproposals5p5);

        map.put("Clocktonproposals6p1", CyberLocktonProposalForm::setClocktonproposals6p1);
        map.put("Clocktonproposals6p1a", CyberLocktonProposalForm::setClocktonproposals6p1a);
        map.put("Clocktonproposals6p1b", CyberLocktonProposalForm::setClocktonproposals6p1b);
        map.put("Clocktonproposals6p2", CyberLocktonProposalForm::setClocktonproposals6p2);
        map.put("Clocktonproposals6p3", CyberLocktonProposalForm::setClocktonproposals6p3);
        map.put("Clocktonproposals6p4", CyberLocktonProposalForm::setClocktonproposals6p4);
        map.put("Clocktonproposals6p5", CyberLocktonProposalForm::setClocktonproposals6p5);
        map.put("Clocktonproposals6p6", CyberLocktonProposalForm::setClocktonproposals6p6);

        map.put("Clocktonproposals7p1", CyberLocktonProposalForm::setClocktonproposals7p1);
        map.put("Clocktonproposals7p2", CyberLocktonProposalForm::setClocktonproposals7p2);
        map.put("Clocktonproposals7p2a", CyberLocktonProposalForm::setClocktonproposals7p2a);
        map.put("Clocktonproposals7p3", CyberLocktonProposalForm::setClocktonproposals7p3);
        map.put("Clocktonproposals7p4", CyberLocktonProposalForm::setClocktonproposals7p4);
        map.put("Clocktonproposals7p5", CyberLocktonProposalForm::setClocktonproposals7p5);
        map.put("Clocktonproposals7p6", CyberLocktonProposalForm::setClocktonproposals7p6);

        map.put("Clocktonproposals8p1", CyberLocktonProposalForm::setClocktonproposals8p1);
        map.put("Clocktonproposals8p2", CyberLocktonProposalForm::setClocktonproposals8p2);
        map.put("Clocktonproposals8p3", CyberLocktonProposalForm::setClocktonproposals8p3);
        map.put("Clocktonproposals8p4", CyberLocktonProposalForm::setClocktonproposals8p4);
        map.put("Clocktonproposals8p5", CyberLocktonProposalForm::setClocktonproposals8p5);

        map.put("Clocktonproposals9p1", CyberLocktonProposalForm::setClocktonproposals9p1);
        map.put("Clocktonproposals9p1a", CyberLocktonProposalForm::setClocktonproposals9p1a);
        map.put("Clocktonproposals9p2", CyberLocktonProposalForm::setClocktonproposals9p2);
        map.put("Clocktonproposals9p2a", CyberLocktonProposalForm::setClocktonproposals9p2a);
        map.put("Clocktonproposals9p3", CyberLocktonProposalForm::setClocktonproposals9p3);
        map.put("Clocktonproposals9p4", CyberLocktonProposalForm::setClocktonproposals9p4);
        map.put("Clocktonproposals9p5", CyberLocktonProposalForm::setClocktonproposals9p5);
        map.put("Clocktonproposals9p5a", CyberLocktonProposalForm::setClocktonproposals9p5a);
        map.put("Clocktonproposals9p6", CyberLocktonProposalForm::setClocktonproposals9p6);

        map.put("Clocktonproposals10p1", CyberLocktonProposalForm::setClocktonproposals10p1);
        map.put("Clocktonproposals10p1a", CyberLocktonProposalForm::setClocktonproposals10p1a);
        map.put("Clocktonproposals10p2", CyberLocktonProposalForm::setClocktonproposals10p2);
        map.put("Clocktonproposals10p3", CyberLocktonProposalForm::setClocktonproposals10p3);
        map.put("Clocktonproposals10p3a", CyberLocktonProposalForm::setClocktonproposals10p3a);
        map.put("Clocktonproposals10p4", CyberLocktonProposalForm::setClocktonproposals10p4);
        map.put("Clocktonproposals10p5", CyberLocktonProposalForm::setClocktonproposals10p5);

        map.put("Clocktonproposals11p1", CyberLocktonProposalForm::setClocktonproposals11p1);
        map.put("Clocktonproposals11p1a", CyberLocktonProposalForm::setClocktonproposals11p1a);
        map.put("Clocktonproposals11p2", CyberLocktonProposalForm::setClocktonproposals11p2);
        map.put("Clocktonproposals11p2a", CyberLocktonProposalForm::setClocktonproposals11p2a);
        map.put("Clocktonproposals11p3", CyberLocktonProposalForm::setClocktonproposals11p3);
        map.put("Clocktonproposals11p3a", CyberLocktonProposalForm::setClocktonproposals11p3a);
        map.put("Clocktonproposals11p4", CyberLocktonProposalForm::setClocktonproposals11p4);

        map.put("Clocktonproposals12p1", CyberLocktonProposalForm::setClocktonproposals12p1);
        map.put("Clocktonproposals12p2", CyberLocktonProposalForm::setClocktonproposals12p2);
        map.put("Clocktonproposals12p3", CyberLocktonProposalForm::setClocktonproposals12p3);
        map.put("Clocktonproposals12p4", CyberLocktonProposalForm::setClocktonproposals12p4);
        map.put("Clocktonproposals12p5", CyberLocktonProposalForm::setClocktonproposals12p5);

        map.put("Clocktonproposals13p1", CyberLocktonProposalForm::setClocktonproposals13p1);
        map.put("Clocktonproposals13p1a", CyberLocktonProposalForm::setClocktonproposals13p1a);
        map.put("Clocktonproposals13p2", CyberLocktonProposalForm::setClocktonproposals13p2);
        map.put("Clocktonproposals13p2a", CyberLocktonProposalForm::setClocktonproposals13p2a);
        map.put("Clocktonproposals13p3", CyberLocktonProposalForm::setClocktonproposals13p3);
        map.put("Clocktonproposals13p3a", CyberLocktonProposalForm::setClocktonproposals13p3a);
        map.put("Clocktonproposals13p4", CyberLocktonProposalForm::setClocktonproposals13p4);

        map.put("Clocktonproposals14p1", CyberLocktonProposalForm::setClocktonproposals14p1);
        map.put("Clocktonproposals14p1a", CyberLocktonProposalForm::setClocktonproposals14p1a);
        map.put("Clocktonproposals14p2", CyberLocktonProposalForm::setClocktonproposals14p2);
        map.put("Clocktonproposals14p3", CyberLocktonProposalForm::setClocktonproposals14p3);
        map.put("Clocktonproposals14p4", CyberLocktonProposalForm::setClocktonproposals14p4);
        map.put("Clocktonproposals14p5", CyberLocktonProposalForm::setClocktonproposals14p5);

        map.put("Clocktonproposals15p1", CyberLocktonProposalForm::setClocktonproposals15p1);
        map.put("Clocktonproposals15p2", CyberLocktonProposalForm::setClocktonproposals15p2);
        map.put("Clocktonproposals15p3", CyberLocktonProposalForm::setClocktonproposals15p3);
        map.put("Clocktonproposals15p4", CyberLocktonProposalForm::setClocktonproposals15p4);
        map.put("Clocktonproposals15p5", CyberLocktonProposalForm::setClocktonproposals15p5);

        map.put("Clocktonproposals16p1", CyberLocktonProposalForm::setClocktonproposals16p1);
        map.put("Clocktonproposals16p1a", CyberLocktonProposalForm::setClocktonproposals16p1a);
        map.put("Clocktonproposals16p2", CyberLocktonProposalForm::setClocktonproposals16p2);
        map.put("Clocktonproposals16p2a", CyberLocktonProposalForm::setClocktonproposals16p2a);
        map.put("Clocktonproposals16p3", CyberLocktonProposalForm::setClocktonproposals16p3);
        map.put("Clocktonproposals16p3a", CyberLocktonProposalForm::setClocktonproposals16p3a);

        map.put("Clocktonproposals17p1", CyberLocktonProposalForm::setClocktonproposals17p1);
        map.put("Clocktonproposals17p2", CyberLocktonProposalForm::setClocktonproposals17p2);
        map.put("Clocktonproposals17p3", CyberLocktonProposalForm::setClocktonproposals17p3);
        map.put("Clocktonproposals17p4", CyberLocktonProposalForm::setClocktonproposals17p4);

        map.put("Clocktonproposals18p1", CyberLocktonProposalForm::setClocktonproposals18p1);
        map.put("Clocktonproposals18p2", CyberLocktonProposalForm::setClocktonproposals18p2);
        map.put("Clocktonproposals18p3", CyberLocktonProposalForm::setClocktonproposals18p3);
        map.put("Clocktonproposals18p4", CyberLocktonProposalForm::setClocktonproposals18p4);

        // Audit fields
        map.put("CreatedBy", CyberLocktonProposalForm::setCreatedBy);
        map.put("UpdatedBy", CyberLocktonProposalForm::setUpdatedBy);

        return map;
    }
}
