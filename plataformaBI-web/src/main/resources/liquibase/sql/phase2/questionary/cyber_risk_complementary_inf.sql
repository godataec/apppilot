INSERT INTO questionary (questionary_id,policy_id, questionary_name, description, created_at, questionary_status, source_file, total_number)
VALUES 
  ('8', '2', 'INFORMACIÓN COMPLEMENTARIA SOLICITUD CYBER RISK', 'INFORMACIÓN COMPLEMENTARIA SOLICITUD CYBER RISK', '', 'TRUE', 'INFORMACIONCOMPLEMENTARIACYBERRISK.docx',4);

INSERT INTO question (question_id,questionary_id,question_type, description ,question_text ,question_json ,parent_id ,document_id)
VALUES 
         (8001, '8', 'MULTIPLE', '1',
        'Favor proporcionar el número de datos PII almacenados (Información Personal, Información de Salud e Información Financiera).',
        '{
		  "S1P1R1": {
			"Id": "S1P1R1",
			"text": "Personas Naturales - Créditos",
			"code": "Cyberriskcompletes1p1r1"
		  },
		  "S1P1R2": {
			"Id": "S1P1R2",
			"text": "Personas Naturales - Tarjeta de Crédito",
			"code": "Cyberriskcompletes1p1r2"
		  },
		  "S1P1R3": {
			"Id": "S1P1R3",
			"text": "Personas Naturales - Información de Salud",
			"code": "Cyberriskcompletes1p1r3"
		  },
		  "S1P1R4": {
			"Id": "S1P1R4",
			"text": "Persona Jurídica - Créditos",
			"code": "Cyberriskcompletes1p1r4"
		  },
		  "S1P1R5": {
			"Id": "S1P1R5",
			"text": "TOTAL",
			"code": "Cyberriskcompletes1p1r5"
		  }
		}',
        '', ''),
  
  (8002, '8', 'NORMAL', '2', 
    'Favor proporcionar todos los nombres de dominios aplicables al Banco Internacional que van a estar cubiertos bajo esta póliza. Tener en cuenta que por "Nombres de Dominios" nos referimos a todos los nombres de dominio de Internet registrados que se utilizan para comercializar el negocio, proporcionar información sobre el negocio u operar el negocio. Si se utilizan varios dominios de países, notifique todos los dominios de nivel superior relevantes para esa ubicación, por ejemplo, co.uk o co.fr, etc.)',
    '',
    '', 
    'Cyberriskcompletes1p2'),
  
  (8003, '8', 'NORMAL', '3', 
    'Confirmar si el sistema informático del Banco Internacional está segregado y es independiente a los sistemas de los otros bancos del Grupo IF', 
    '', 
    '', 
    'Cyberriskcompletes1p3'),
  
  (8004, '8', 'NORMAL', '4', 
    'Confirmar que todas las Entidades por asegurarse son 100% privadas y no controladas por ningún Gobierno o Estado.',
    '',
    '', 
    'Cyberriskcompletes1p4');
