INSERT INTO questionary (questionary_id, policy_id, questionary_name, description, created_at, questionary_status,
                         source_file, total_number)
VALUES ('6', '2', 'CUESTIONARIO CYBER RISK DATOS PERSONALES.', 'CUESTIONARIO CYBER RISK DATOS PERSONALES', '',
        'TRUE', 'CyberRisks-DatosPersonalesPInter.xlsx', 9);


INSERT INTO question (question_id, questionary_id, question_type, description, question_text, question_json, parent_id,
                      document_id)
VALUES (6001, '6', 'NORMAL', '1', 'RAZÓN SOCIAL DEL ASEGURADO:', '{"S1P1":{"Id":"S1P1","text":"","code":"cyberRiskA"}}',
        '', ''),
       (6002, '6', 'NORMAL', '2', '¿CUÁL ES EL NÚMERO TOTAL DE REGISTROS MANEJADO POR EL ASEGURADO?',
        '{"S1P2":{"Id":"S1P2","text":"","code":"cyberRiskB"}}', '', ''),
       (6003, '6', 'MULTIPLE', 'a', 'Por Región: ',
        '{"S1P2R1":{"Id":"S1P2R1","text":"Europa","code":"cyberRiskC"},"S1P2R2":{"Id":"S1P2R2","text":"EU / Canadá","code":"cyberRiskD"},"S1P2R3":{"Id":"S1P2R3","text":"Resto del Mundo","code":"cyberRiskE"}}',
        '6002', ''),
       (6004, '6', 'DES', '3', 'CATEGORÍAS DE DATOS PERSONALES RECOLECTADOS/PROCESADOS', '', '', ''),
       (6005, '6', 'OPTIONAL-NORMAL', 'a', 'Información comercial y marketing',
        '{"S1P3R1SI":{"Id":"S1P3R1SI","text":"Número de registros","code":"cyberRiskF"},"S1P3R1NO":{"Id":"S1P3R1NO","text":"","code":"cyberRiskG"}}',
        '6004', ''),
       (6006, '6', 'OPTIONAL-NORMAL', 'b', 'Información financiera o de Tarjetas de Crédito',
        '{"S1P3R2SI":{"Id":"S1P3R2SI","text":"Número de registros","code":"cyberRiskI"},"S1P3R2NO":{"Id":"S1P3R2NO","text":"NO","code":"cyberRiskJ"}}',
        '6004', ''),
       (6007, '6', 'OPTIONAL-NORMAL', 'c', 'Información de salud',
        '{"S1P3R3SI":{"Id":"S1P3R3SI","text":"Número de registros","code":"cyberRiskL"},"S1P3R3NO":{"Id":"S1P3R3NO","text":"NO","code":"cyberRiskM"}}',
        '6004', ''),
       (6008, '6', 'NORMAL', 'd', 'Otros, por favor especificar:', '{"S1P4":{"Id":"S1P4","text":"","code":"cyberRiskO"}}',
        '6004', ''),
       (6009, '6', 'MULTIPLE', '4',
        'Se precisa el número de registros, basados en los registros totales de operaciones de crédito nuevas y stock',
        '{
		  "S1P4R1": {
			"Id": "S1P4R1",
			"text": "Personas Naturales - Créditos",
			"code": "cyberRiskP"
		  },
		  "S1P4R2": {
			"Id": "S1P4R2",
			"text": "Personas Naturales - Tarjeta de Crédito",
			"code": "cyberRiskQ"
		  },
		  "S1P4R3": {
			"Id": "S1P4R3",
			"text": "Personas Naturales - Información de Salud",
			"code": "cyberRiskR"
		  },
		  "S1P4R4": {
			"Id": "S1P4R4",
			"text": "Persona Jurídica - Créditos",
			"code": "cyberRiskS"
		  },
		  "S1P4R5": {
			"Id": "S1P4R5",
			"text": "TOTAL",
			"code": "cyberRiskT"
		  }
		}',
        '', '');


INSERT INTO question_option (question_id, option_text, created_at)
VALUES ('6005', '##si-no', ''),
       ('6006', '##si-no', ''),
       ('6007', '##si-no', '');