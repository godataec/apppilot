/*==============================================================
   Inserción en la tabla QUESTIONNAIRE
==============================================================*/
INSERT INTO questionary (questionary_id, policy_id, questionary_name, description, created_at, questionary_status,
                         source_file, total_number)
VALUES (5, 2, 'CUESTIONARIO CYBER RISK', 'CUESTIONARIO CYBER RISK', '', 'TRUE', 'CUESTIONARIOCYBERRISK.docx', 65);


/*==============================================================
   Inserción en la tabla QUESTION
==============================================================*/

INSERT INTO question (question_id, questionary_id, question_type, description, question_text, question_json, parent_id,
                      document_id)
VALUES (5000, 5, 'DES', '1', 'INFORMACIÓN GENERAL / GENERAL INFORMATION', '', NULL, ''),
       (5001, 5, 'SUB', 'a', 'Empresa Solicitante / Company or trading name:',
        '{"S1P1A":{"Id":"S1P1A","text":"","code":"Cyberrisks1p1a"}}', '5000', ''),
       (5002, 5, 'SUB', 'b', 'CIF / Tax ID Code:', '{"S1P1B":{"Id":"S1P1B","text":"","code":"Cyberrisks1p1b"}}', '5000',
        ''),
       (5003, 5, 'SUB', 'c', 'Domicilio social / Address:', '{"S1P1c":{"Id":"S1P1c","text":"","code":"Cyberrisks1p1c"}}',
        '5000', ''),
       (5004, 5, 'SUB', 'd', 'E-mail / E-mail :', '{"S1P1d":{"Id":"S1P1d","text":"","code":"Cyberrisks1p1d"}}', '5000',
        ''),
       (5005, 5, 'SUB', 'e', 'Página web / Web page:', '{"S1P1E":{"Id":"S1P1E","text":"","code":"Cyberrisks1p1e"}}',
        '5000', ''),
       (5006, 5, 'SUB', 'f', 'Fecha de creación de la empresa / Data business established:',
        '{"S1P1F":{"Id":"S1P1F","text":"","code":"Cyberrisks1p1f"}}', '5000', ''),
       (5007, 5, 'SUB', 'g', 'Número de empleados / Number of employees:',
        '{"S1P1G":{"Id":"S1P1G","text":"","code":"Cyberrisks1p1g"}}', '5000', ''),
       (5008, 5, 'OPTIONAL-NORMAL', 'h',
        '¿Ha estado involucrado en alguna fusión o adquisición en los últimos 3 años? / Have you been involved in any mergers and acquisitions within the last 3 years?',
        '{"S1P1H":{"Id":"S1P1H","text":"En caso afirmativo, por favor facilite detalles completos, si es necesario utilice una hoja adicional. / If Yes, please provide full details, on a separate sheet if necessary","code":"Cyberrisks1p1h"}}',
        '5000', ''),
       (5010, 5, 'SUB', 'i',
        'Descripción de su actividad principal y otros posibles segmentos de negocio incluyendo a sus filiales. Por favor, anexe un organigrama con la estructura corporativa / Description of its main activity and other possible business segments including its subsidiaries. Please attach an organizational chart with the corporate structure.',
        '{"S1P1J":{"Id":"S1P1J","text":"","code":"Cyberrisks1p1j"}}', '5000', '');

/* Bloque 2: INFORMACIÓN FINANCIERA
*/
INSERT INTO question (question_id, questionary_id, question_type, description, question_text, question_json, parent_id,
                      document_id)
VALUES (5011, 5, 'DES', '2', 'INFORMACIÓN FINANCIERA / FINANCIAL INFORMATION', '', NULL, ''),
       (5012, 5, 'TABLE', 'a', 'Por favor, complete los siguientes campos:',
        '',
        '5011', 'Cyberrisks1p2a'),
       (5013, 5, 'SUB', 'b',
        'Porcentaje de los ingresos brutos anuales u operaciones a través de la página Web / Percentage of gross annual revenue accounted for by sales or operations through your website:',
        '{"S1P2B":{"Id":"S1P2B","text":"","code":"Cyberrisks1p2b"}}', 5011, ''),
       (5014, 5, 'SUB', 'c',
        'Porcentaje anual de transacciones pagadas con tarjeta de crédito o débito / Percentage of annual transactions paid for by debit/credit card %:',
        '{"S1P2C":{"Id":"S1P2C","text":"","code":"Cyberrisks1p2c"}}', '5011', ''),
       (5015, 5, 'SUB', 'd', 'Valor medio de las transacciones / Average transaction value:',
        '{"S1P2D":{"Id":"S1P2D","text":"","code":"Cyberrisks1p2d"}}', '5011', ''),
       (5016, 5, 'DES', 'e',
        'Porcentaje de los ingresos brutos generados la pasada anualidad provenientes de / Percentage of last year''s gross annual revenue generated from:',
        '', '5011', ''),
       (5017, 5, 'SUB', 'i',
        'Clientes en EE.UU/Canadá sujetos a la ley Americana o canadiense / US/Canadian clients subject to US/Canadian Law:',
        '{"S1P2E1":{"Id":"S1P2E1","text":"","code":"Cyberrisks1p2e1"}}', '5016', ''),
       (5018, 5, 'SUB', 'ii',
        'País de donde emana el mayor volumen de negocio / Business emanating from the Insured''s main domicile Jurisdiction where most business derives from (%):',
        '{"S1P2E2":{"Id":"S1P2E2","text":"","code":"Cyberrisks1p2e2"}}', '5016', ''),
       (5019, 5, 'SUB', 'iii', 'Clientes en el resto del mundo / Clients anywhere else in the world:',
        '{"S1P2E3":{"Id":"S1P2E3","text":"","code":"Cyberrisks1p2e3"}}', '5016', '');

/* Bloque 3: CONTINUIDAD DE NEGOCIO
*/
INSERT INTO question (question_id, questionary_id, question_type, description, question_text, question_json, parent_id,
                      document_id)
VALUES (5020, 5, 'DES', '3',
        'CONTINUIDAD DE NEGOCIO FRENTE A BRECHAS DE SEGURIDAD O PRIVACIDAD / Business continuity against security gaps or computer privacy',
        '', NULL, ''),
       (5021, 5, 'OPTIONAL-NORMAL', 'a',
        'Después de cuánto tiempo sin acceso al Sistema / Seguridad / Plataforma tecnológica por parte de sus empleados se ocasionaría un impacto significativo en su negocio / Indicate time after which the inability for staff to access your internal computer network and systems would have a significant impact on your business:',
        '{"S1P3AR1":{"Id":"S1P3AR1","text":"","code":"Cyberrisks1p3a1"}}', '5020', ''),
       (5023, 5, 'OPTIONAL-NORMAL', 'b',
        'Indique cuánto tiempo sin que sus clientes tengan acceso a su página web supondría un impacto significativo para su negocio / Indicate time after which the inability for customers to access your website would have a significant impact on your business:',
        '{"S1P3BR1":{"Id":"S1P3BR1","text":"","code":"Cyberrisks1p3br1"}}', '5020', ''),
       (5025, 5, 'SUB-OPTIONAL', 'c',
        '¿Dispone de un plan de actuación en caso de descubrir una brecha de seguridad o privacidad de datos? / Do you have a BCP or a DRP?',
        '{"S1P3C":{"Id":"S1P3C","text":"","code":"Cyberrisks1p3c"}}', '5020', ''),
       (5027, 5, 'SUB', 'd', '¿Cada cuánto tiempo son revisados estos planes? / How often are these plans tested?',
        '{"S1P3D":{"Id":"S1P3D","text":"","code":"Cyberrisks1p3d"}}', '5020', ''),
       (5028, 5, 'SUB-OPTIONAL', 'e',
        '¿Ha llevado a cabo alguna auditoría de sus sistemas tecnológicos recientemente? / Have you recently conducted an audit of your IT systems?',
        '{"S1P3E":{"Id":"S1P3E","text":"","code":"Cyberrisks1p3e"}}', '5020', ''),
       (5029, 5, 'SUB', 'f',
        '¿Indique la fecha en la cual se llevó a cabo su última prueba de intromisión externa en sus sistemas? / When was your last external penetration test carried out?; How often do you conduct these tests?',
        '{"S1P3F":{"Id":"S1P3F","text":"","code":"Cyberrisks1p3f"}}', '5020', ''),
       (5030, 5, 'SUB-OPTIONAL', 'g',
        '¿Les ha surgido alguna preocupación sobre cualquier aspecto de la red en la que se aconsejó una subsanación inmediata? / Was any serious concern raised with any aspect of the network where immediate correction was advised?',
        '{"S1P3G":{"Id":"S1P3G","text":"","code":"Cyberrisks1p3g"}}', '5020', ''),
       (5031, 5, 'SUB-OPTIONAL', 'h',
        'En caso de que la pregunta anterior sea afirmativa, ¿han sido las recomendaciones llevadas a cabo? / If "Yes" to the above, were the recommendations carried out?',
        '{"S1P3H":{"Id":"S1P3H","text":"","code":"Cyberrisks1p3h"}}', '5030', '');

/* Bloque 4: SEGURIDAD DE LA RED
*/
INSERT INTO question (question_id, questionary_id, question_type, description, question_text, question_json, parent_id,
                      document_id)
VALUES (5033, 5, 'DES', '4', 'SEGURIDAD DE LA RED / Network Security', '', NULL, ''),
       (5034, 5, 'OPTIONAL-NORMAL', 'a',
        '¿Tiene un responsable o supervisor de seguridad o un responsable de protección de datos o un supervisor de información encargado de la protección y seguridad de los datos y cumplimiento interno de la legislación en materia de protección de datos? / Do you employ a Chief Privacy Officer, or Chief Information Office?',
        '{"S1P4A1":{"Id":"S1P4A1","text":"If no, who is responsible for data protection and privacy issues?","code":"Cyberrisks1p4a1"}}',
        '5033', ''),
       (5037, 5, 'SUB-OPTIONAL', 'b',
        '¿Incluyen en sus políticas de seguridad y protección de datos un curso de formación a todos los empleados / Does your IT employee policy include mandatory training for all employees?',
        '{"S1P4B":{"Id":"S1P4B","text":"","code":"Cyberrisks1p4b"}}', '5033', ''),
       (5038, 5, 'OPTIONAL', 'c',
        '¿A todos los puestos de trabajo analizados, se le asigna un único usuario de acceso y clave que se modifica periódicamente? / Are all assigned specified rights, privileges and unique user ID and passwords, assessed periodically?',
        '{"S1P4C":{"Id":"S1P4C","text":"","code":"Cyberrisks1p4c"}}', '5033', ''),
       (5039, 5, 'SUB-OPTIONAL', 'd',
        '¿Tiene procedimientos estrictos de renovación de acceso a usuarios cuyo contrato de trabajo haya finalizado? ¿Hay acceso al histórico de Información utilizada por dicho empleado? / Do you have strict user revocation procedures on user accounts and inventoried recovery of all information assets following employment termination? ',
        '{"S1P4D":{"Id":"S1P4D","text":"","code":"Cyberrisks1p4d"}}', '5033', ''),
       (5041, 5, 'SUB-OPTIONAL', 'e',
        '¿Tiene software antivirus actualizado en todos sus ordenadores, servidores y redes de acuerdo con las recomendaciones de sus proveedores de software? / Do you have antivirus software on all computer devices, servers and networks which are updated in accordance with the software providers’ recommendations?',
        '{"S1P4E":{"Id":"S1P4DE","text":"","code":"Cyberrisks1p4de"}}', '5033', ''),
       (5042, 5, 'SUB-OPTIONAL', 'f', '¿Utilizan Firewall? / Do you have a Firewall?',
        '{"S1P4F":{"Id":"S1P4DF","text":"","code":"Cyberrisks1p4df"}}', '5033', ''),
       (5043, 5, 'SUB-OPTIONAL', 'g',
        '¿Tiene usted procedimientos de control de acceso y encriptado de disco duro para evitar la exposición no autorizada de datos en todos los ordenadores portátiles, PDAs, smartphones (p.ej. BlackBerry) y PC''s en la oficina u ordenadores personales? / Do you have access control procedures and hard drive encryption on all portable media and mobile devises?',
        '{"S1P4G":{"Id":"S1P4DG","text":"","code":"Cyberrisks1p4dg"}}', '5033', ''),
       (5044, 5, 'SUB-OPTIONAL', 'h',
        '¿La información sensible y confidencial que se transmite dentro y desde su organización está encriptada? / Is all sensitive and confidential information that is transmitted within and from   your organization encrypted? ',
        '{"S1P4H":{"Id":"S1P4DH","text":"","code":"Cyberrisks1p4dh"}}', '5033', ''),
       (5045, 5, 'SUB-OPTIONAL', 'i',
        '¿Se encuentra encriptado la información sensible y confidencial almacenada en sus bases de datos, servidores y archivos? / Is all sensitive and confidential information stored on your databases, servers and data files encrypted?',
        '{"S1P4I":{"Id":"S1P4DI","text":"","code":"Cyberrisks1p4di"}}', '5033', ''),
       (5046, 5, 'SUB-OPTIONAL', 'j',
        '¿Se encripta la información contenida en dispositivos móviles (portátiles, drivers, dispositivos USB, etc)? / IS all mobile devices content information encrypted (laptop, drivers, USB Devices, etc.)? ',
        '{"S1P4J":{"Id":"S1P4DJ","text":"","code":"Cyberrisks1p4dj"}}', '5033', '');


/* Bloque 5: INFORMACIÓN Y GESTIÓN DE DATOS
*/
INSERT INTO question (question_id, questionary_id, question_type, description, question_text, question_json, parent_id,
                      document_id)
VALUES (5047, 5, 'DES', '6', 'INFORMACIÓN Y GESTIÓN DE DATOS / Information and data management', '', NULL, ''),
       (5048, 5, 'SUB-OPTIONAL', 'a',
        '¿Su programa de clasificación de información incluye una clasificación estándar de datos (por ejemplo "públicos", "solo para uso interno", "confidencial")? / Does your information asset programme include a data classification standard (E.g. public, internal use only, confidential…)?',
        '{"S1P6A":{"Id":"S1P6A","text":"","code":"Cyberrisks1p5a"}}', '5047', ''),
       (5049, 5, 'SUB-OPTIONAL', 'b',
        '¿Los sistemas críticos cuentan con medidas adicionales de segmentación, autentificación segura? / Do critical systems have additional segmentation, secure authentication?',
        '{"S1P6B":{"Id":"S1P6B","text":"","code":"Cyberrisks1p5b"}}', '5047', ''),
       (5050, 5, 'SUB-OPTIONAL', 'c',
        '¿Contiene su página web la política de privacidad habiendo sido revisada esta por un abogado? / Do you post a privacy policy on your website which has been reviewed by a qualified lawyer?',
        '{"S1P6C":{"Id":"S1P6C","text":"","code":"Cyberrisks1p5c"}}', '5047', ''),
       (5051, 5, 'SUB-OPTIONAL', 'd',
        '¿El solicitante trata, almacena o cede datos especialmente protegidos como datos personales, tarjetas de crédito o numerosas cuentas bancarias? / Does the applicant treat, store or transfer specially protected data such as personal data, credit cards or numerous bank accounts?',
        '{"S1P6D":{"Id":"S1P6D","text":"","code":"Cyberrisks1p5d"}}', '5047', ''),
       (5052, 5, 'SUB-OPTIONAL', 'e',
        '¿Dispone de un inventario sobre información que indique los propietarios y las fuentes de dichos datos? / Do you have an information asset inventory that lists the owners and sources of all data? ',
        '{"S1P6E":{"Id":"S1P6E","text":"","code":"Cyberrisks1p5e"}}', '5047', ''),
       (5053, 5, 'SUB-OPTIONAL', 'f',
        '¿Tiene procedimientos en vigor para eliminar información y para controlar el período de tiempo que debe mantenerse la información en sus archivos? / Do you have procedures in force to monitor the period for which customer data is held, and have processes for deleting this information at the end of that period?',
        '{"S1P6F":{"Id":"S1P6F","text":"","code":"Cyberrisks1p5f"}}', '5047', ''),
       (5054, 5, 'SUB-OPTIONAL', 'g',
        '¿La información almacenada en formato físico (papel, discos, CD''S, etc.) se desecha o recicla con procedimientos confidenciales y seguros conocidos por toda la organización? / Is all information held in physical form (paper, disks, CDs etc.) disposed of or recycled by confidential and secure methods which are recognized throughout the organization?',
        '{"S1P6G":{"Id":"S1P6G","text":"","code":"Cyberrisks1p5g"}}', '5047', ''),
       (5055, 5, 'SUB-OPTIONAL', 'h',
        '¿Mantiene un registro de incidencias (amenazas a sistemas de seguridad) y fallos en la red? / Do you keep an incident log of all system security breaches and network failures?',
        '{"S1P6H":{"Id":"S1P6H","text":"","code":"Cyberrisks1p5h"}}', '5047', '');


/* Bloque 6: ACTIVIDAD DE OUTSOURCING
*/
INSERT INTO question (question_id, questionary_id, question_type, description, question_text, question_json, parent_id,
                      document_id)
VALUES (5056, 5, 'DES', '7', 'ACTIVIDAD DE OUTSOURCING', '', NULL, ''),
       (5057, 5, 'OPTIONAL-NORMAL', 'a',
        '¿Externaliza la gestión o alguna de sus operaciones tecnológicas? / Do you outsource the management or any part of your IT operations?',
        '{"S1P7A":{"Id":"S1P7A","text":"En caso afirmativo, por favor facilite detalles, si fuera necesario, usar una hoja adicional, incluyendo qué tipo de subcontratación, a quién y detalles acerca del proceso para elegir el subcontratista y copia del contrato estándar que utilicen para ello / If Yes, please provide brief details below, or on a separate sheet if necessary, including what is outsource","code":"Cyberrisks1p7a"}}',
        '5056', ''),
       (5059, 5, 'SUB-OPTIONAL', 'b',
        'El solicitante ¿exige al subcontratista indemnizaciones por daños y perjuicios derivados de las responsabilidades que pudiera ser atribuible a dicho subcontratista? / Does the applicant require compensation from the subcontractor for damages arising from the responsibilities that could be attributable to that subcontractor?',
        '{"S1P7B":{"Id":"S1P7B","text":" ","code":"Cyberrisks1p7b"}}', '5056', ''),
       (5060, 5, 'OPTIONAL-NORMAL', 'c',
        '¿Subcontrata el solicitante actividades de proceso o recogida de datos? / Does the applicant subcontract the processing or data collection activities?',
        '{"S1P7C":{"Id":"S1P7C","text":"En caso afirmativo, por favor detalle las funciones de proceso o recogida de datos que son externalizadas. / If yes, please detail the process functions or data collection that are outsourced","code":"Cyberrisks1p7c"}}',
        '5056', ''),
       (5062, 5, 'SUB-OPTIONAL', 'd',
        '¿El solicitante, requiere al subcontratista que dé cumplimiento con los términos de la política de protección de datos del solicitante? / Does the applicant require the subcontractor to comply with the terms of the data protection policy of the applicant?',
        '{"S1P7D":{"Id":"S1P7D","text":"  ","code":"Cyberrisks1p7d"}}', '5056', ''),
       (5063, 5, 'OPTIONAL-NORMAL', 'e',
        '¿Dispone la sociedad de servicios en nube o cloud computing? / Does the company have services in cloud or cloud computing? ',
        '{"S1P7E":{"Id":"S1P7E","text":"En caso afirmativo, detallar política de seguridad en cloud para seguridad de los datos personales y corporativos / If yes, detail cloud security policy for personal and corporate data security","code":"Cyberrisks1p7e"}}',
        '5056', ''),
       (5064, 5, 'SUB-OPTIONAL', 'f',
        '¿Realiza revisiones periódicas a sus proveedores de servicios y socios para comprobar que cumplen con sus requerimientos para la protección de la información sensible que manejan? / Do you conduct regular reviews of your third party service providers and partners to ensure that they meet your requirements for protecting sensitive information in their care?',
        '{"S1P7F":{"Id":"S1P7F","text":"  ","code":"Cyberrisks1p7f"}}', '5056', ''),
       (5065, 5, 'SUB-OPTIONAL', 'g',
        '¿Se auditan periódicamente las funciones de subcontratistas/prestadores del servicio para asegurar que cumplen con las políticas de seguridad del Solicitante? / Do you enforce provisions for non-compliance by contractors and others?',
        '{"S1P7G":{"Id":"S1P7G","text":"  ","code":"Cyberrisks1p7g"}}', '5056', '');


/* Bloque 7: SINIESTROS Y CIRCUNSTANCIAS
*/
INSERT INTO question (question_id, questionary_id, question_type, description, question_text, question_json, parent_id,
                      document_id)
VALUES (5066, 5, 'DES', '8', 'SINIESTROS Y CIRCUNSTANCIAS / Claims and Circumstances', '', NULL, ''),
       (5067, 5, 'SUB-OPTIONAL', '1',
        '¿Ha sido objeto de investigaciones o ha recibido reclamaciones por protección de datos por partes de la Agencia Española de Protección de Datos u otra autoridad similar? / Have you been investigated or have you received data protection claims by parts of the Spanish Data Protection Agency or other similar authority?',
        '{"S1P8A1":{"Id":"S1P8A1","text":"","code":"Cyberrisks1p8a1"}}', '5066', ''),
       (5068, 5, 'SUB-OPTIONAL', '2',
        '¿Ha sufrido alguna caída de la red o interrupción no programada en los últimos 24 meses? / Have you sustained any unscheduled network outage or interruption within the past 24 months?',
        '{"S1P8A2":{"Id":"S1P8A2","text":"","code":"Cyberrisks1p8a2"}}', '5066', ''),
       (5069, 5, 'SUB-OPTIONAL', '3',
        '¿Ha sufrido alguna vez un acceso no autorizado al sistema, alteraciones, virus o un ataque de código malicioso, pérdida de datos, incidente de hacking, robo de datos o un incidente similar? / Have you sustained a material or significant system intrusion, tampering, virus or malicious code attack, loss of data, hacking incident, data theft or similar incident or situation?',
        '{"S1P8A3":{"Id":"S1P8A3","text":"","code":"Cyberrisks1p8a3"}}', '5066', ''),
       (5070, 5, 'SUB-OPTIONAL', '4',
        '¿Tiene conocimiento de hechos o circunstancias que puedan dar lugar a una reclamación contra usted y/o una reclamación bajo esta póliza de seguros? / Are you or they aware of any circumstances or incidents that have resulted in any claim against you and/or a claim against any insurance policy that provides the type of coverage being requested in this application?',
        '{"S1P8A4":{"Id":"S1P8A4","text":"","code":"Cyberrisks1p8a4"}}', '5066', ''),
       (5071, 5, 'SUB', '5',
        'En caso de haber contestado afirmativamente cualquiera de las preguntas formuladas en dicho apartado, por favor facilite detalles en una hoja aparte si fuera necesario. / If ‘Yes’ to any questions within this section, please provide full details, on a separate sheet if necessary.',
        '{"S1P8A5":{"Id":"S1P8A5","text":"Detalles","code":"Cyberrisks1p8a5"}}', '5070', ''),
       (5072, 5, 'SUB-OPTIONAL', '6',
        '¿Le han declinado cobertura de seguro o su asegurador actual le ha impuesto alguna condición? / Have you ever been refused insurance or had any special terms or conditions imposed by any insurer?',
        '{"S1P8A6":{"Id":"S1P8A6","text":"","code":"Cyberrisks1p8a6"}}', '5066', '');


/*==============================================================
   Inserción en la tabla OPTIONS
==============================================================*/
INSERT INTO question_option (question_id, option_text, created_at)
VALUES (5008, '##yes-no', NULL),
       (5012,
        'Ingresos brutos anuales Gross annual revenue Dic 2024-Facturación último cierre anual Last year Invoicing-Estimación año en curso Current year estimate',
        ''),
       (5021,
        'Inmediatamente / Immediately-Después de 6 horas / After 6 hrs-Después de 12 horas / After 12 hrs-Después de 24 horas / After 24 hrs-Después de 48 horas / After 48 hrs-Nunca / Never-##Otro/Other',
        NULL),
       (5023,
        'Inmediatamente / Immediately-Después de 6 horas / After 6 hrs-Después de 12 horas / After 12 hrs-Después de 24 horas / After 24 hrs-Después de 48 horas / After 48 hrs-Nunca / Never-##Otro/Other',
        NULL),
       (5025, 'yes-no', NULL),
       (5028, 'yes-no', NULL),
       (5030, 'yes-no', NULL),
       (5031, 'yes-no', NULL),
       (5033, 'yes-no', NULL),
       (5034, 'yes-##no', NULL),
       (5037, 'yes-no', NULL),
       (5038, 'yes-no', NULL),
       (5039, 'yes-no', NULL),
       (5041, 'yes-no', NULL),
       (5042, 'yes-no', NULL),
       (5043, 'yes-no', NULL),
       (5044, 'yes-no-parcialmente', NULL),
       (5045, 'yes-no-parcialmente', NULL),
       (5046, 'yes-no', NULL),
       (5048, 'yes-no', NULL),
       (5049, 'yes-no', NULL),
       (5050, 'yes-no', NULL),
       (5051, 'yes-no', NULL),
       (5052, 'yes-no', NULL),
       (5053, 'yes-no', NULL),
       (5054, 'yes-no', NULL),
       (5055, 'yes-no', NULL),

       (5057, '##yes-no', NULL),

       (5059, 'yes-no', NULL),
       (5060, '##yes-no', NULL),

       (5062, 'yes-no', NULL),
       (5063, '##yes-no', NULL),
       (5064, 'yes-no', NULL),
       (5065, 'yes-no', NULL),
       (5067, 'yes-no', NULL),
       (5068, 'yes-no', NULL),
       (5069, 'yes-no', NULL),
       (5070, 'yes-no', NULL),
       (5072, 'yes-no', NULL);