INSERT INTO QUESTIONNAIRE (IDQUESTIONNAIRE, IDINSURANCE, NAMEQUESTIONNAIRE, DESCRIPTION, CREATEDAT, STATUS, SOURCEFILE,
                           TOTALQUESTION)
VALUES ('6', '2', 'CUESTIONARIO CYBER RISK DATOS PERSON INTER.', 'CUESTIONARIO CYBER RISK DATOS PERSONALES', '',
        'TRUE', 'CyberRisks-DatosPersonalesPInter.xlsx', 8);


INSERT INTO QUESTION (IDQUESTION, IDQUESTIONNAIRE, TYPEQUESTION, DESCRIPTIONQUESTION, QUESTIONTEXT, QUESTIONJSON,
                      PARENTID, DOCUMENTID)
VALUES (6001, '6', 'NORMAL', '1', 'RAZÓN SOCIAL DEL ASEGURADO:', '{"S1P1":{"Id":"S1P1","text":"","code":"cyberRiskA"}}',
        '', ''),
       (6002, '6', 'NORMAL', '2', '¿CUÁL ES EL NÚMERO TOTAL DE REGISTROS MANEJADO POR EL ASEGURADO?',
        '{"S1P2":{"Id":"S1P2","text":"","code":"cyberRiskB"}}', '', ''),
       (6003, '6', 'SUB', 'a', 'Por Región: ',
        '{"S1P2R1":{"Id":"S1P2R1","text":"Europa","code":"cyberRiskC"},"S1P2R2":{"Id":"S1P2R2","text":"EU / Canadá","code":"cyberRiskD"},"S1P2R3":{"Id":"S1P2R3","text":"Resto del Mundo","code":"cyberRiskE"}}',
        '6002', ''),
       (6004, '6', 'DES', '3', 'CATEGORÍAS DE DATOS PERSONALES RECOLECTADOS/PROCESADOS', '', '', ''),
       (6005, '6', 'OPTIONAL-NORMAL', 'a', 'Información comercial y marketing',
        '{"S1P3R1SI":{"Id":"S1P3R1SI","text":"SI","code":"cyberRiskF"},"S1P3R1NO":{"Id":"S1P3R1NO","text":"NO","code":"cyberRiskG"},"S1P3R1NUM":{"Id":"S1P3R1NUM","text":"Número de registros","code":"cyberRiskH"}}',
        '6004', ''),
       (6006, '6', 'OPTIONAL-NORMAL', 'b', 'Información financiera o de Tarjetas de Crédito',
        '{"S1P3R2SI":{"Id":"S1P3R2SI","text":"SI","code":"cyberRiskI"},"S1P3R2NO":{"Id":"S1P3R2NO","text":"NO","code":"cyberRiskJ"},"S1P3R2NUM":{"Id":"S1P3R2NUM","text":"Número de registros","code":"cyberRiskK"}}',
        '6004', ''),
       (6007, '6', 'OPTIONAL-NORMAL', 'c', 'Información de salud',
        '{"S1P3R3SI":{"Id":"S1P3R3SI","text":"SI","code":"cyberRiskL"},"S1P3R3NO":{"Id":"S1P3R3NO","text":"NO","code":"cyberRiskM"},"S1P3R3NUM":{"Id":"S1P3R3NUM","text":"Número de registros","code":"cyberRiskN"}}',
        '6004', ''),
       (6008, '6', 'SUB', 'd', 'Otros, por favor especificar:', '{"S1P4":{"Id":"S1P4","text":"","code":"cyberRiskO"}}',
        '6004', ''),
       (6009, '6', 'MULTIPLE', '4',
        'Se precisa el número de registros, basados en los registros totales de operaciones de crédito nuevas y stock',
        '{
        "S1P4R5": {
          "Id": "S1P4R5",
          "text": "TOTAL",
          "code": "cyberRiskT"
        },
        "S1P4R4": {
          "Id": "S1P4R4",
          "text": "Persona Jurídica - Créditos",
          "code": "cyberRiskS"
        },
        "S1P4R3": {
          "Id": "S1P4R3",
          "text": "Personas Naturales - Información de Salud",
          "code": "cyberRiskR"
        },
        "S1P4R2": {
          "Id": "S1P4R2",
          "text": "Personas Naturales - Tarjeta de Crédito",
          "code": "cyberRiskQ"
        },
        "S1P4R1": {
          "Id": "S1P4R1",
          "text": "Personas Naturales - Créditos",
          "code": "cyberRiskP"
        }
      }',
        '', '');


INSERT INTO OPTIONS (idQUESTION, OPTIONTEXT, CREATEDAT)
VALUES ('6005', '##si-no', ''),
       ('6006', '##si-no', ''),
       ('6007', '##si-no', '');