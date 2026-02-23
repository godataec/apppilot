INSERT INTO QUESTIONNAIRE 
  (IDQUESTIONNAIRE, IDINSURANCE, NAMEQUESTIONNAIRE, DESCRIPTION, CREATEDAT, STATUS, SOURCEFILE, TOTALQUESTION)
VALUES 
  ('8', '2', 'INFORMACIÓN COMPLETA SOLICITUD CYBER RISK', 'INFORMACIÓN COMPLETA SOLICITUD CYBER RISK', '', 'TRUE', 'INFORMACIONCOMPLEMENTARIACYBERRISK.docx',4);

INSERT INTO QUESTION 
  (IDQUESTION, IDQUESTIONNAIRE, TYPEQUESTION, DESCRIPTIONQUESTION, QUESTIONTEXT, QUESTIONJSON, PARENTID, DOCUMENTID) 
VALUES 
  (8001, '8', 'TABLE', '1', 'Favor proporcionar el número de datos PII almacenados (Información Personal, Información de Salud e Información Financiera).', 
  '{"S1P1R1":{"Id":"S1P1R1","text":"","code":"Cyberriskcompletes1p1r1"}, "S1P1R2":{"Id":"S1P1R2","text":"","code":"Cyberriskcompletes1p1r2"}, "S1P1R3":{"Id":"S1P1R3","text":"","code":"Cyberriskcompletes1p1r3"},"S1P2R4":{"Id":"S1P2R4","text":"","code":"Cyberriskcompletes1p1r4"},"S1P2R45":{"Id":"S1P2R5","text":"","code":"Cyberriskcompletes1p1r5"}}',
  '', ''),
  
  (8002, '8', 'NORMAL', '2', 
    'Favor proporcionar todos los nombres de dominios aplicables al Banco Internacional que van a estar cubiertos bajo esta póliza. Tener en cuenta que por “Nombres de Dominios” nos referimos a todos los nombres de dominio de Internet registrados que se utilizan para comercializar el negocio, proporcionar información sobre el negocio u operar el negocio. Si se utilizan varios dominios de países, notifique todos los dominios de nivel superior relevantes para esa ubicación, por ejemplo, co.uk o co.fr, etc.)', 
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
INSERT INTO OPTIONS (IDQUESTION, OPTIONTEXT, CREATEDAT) VALUES 
  (8001, '(Personas Naturales) Créditos-(Personas Naturales) Tarjeta de Crédito-(Personas Naturales) Información de Salud-(Personas Jurídicas) Créditos-TOTAL', NULL);