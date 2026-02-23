INSERT INTO questionary (questionary_id,policy_id, questionary_name, description, created_at, questionary_status, source_file, total_number) VALUES ('4','1','ANEXO 3 CUESTIONARIO PI','ANEXO 3 CUESTIONARIO PI','','TRUE','ANEXO3-FORMULARIOPI.docx', 27);

INSERT INTO question (question_id,questionary_id,question_type, description ,question_text ,question_json ,parent_id ,document_id) VALUES
(4000,'4','DES','','Pregunta 12 del Cuestionario de PI. (Question 12 of PI Proposal Form) ','','',''),
(4001,'4','OPTIONAL-NORMAL','12','¿Provee facilidades de acceso por Internet? / Do you provide an Internet Facility? / Si la respuesta es SÍ, favor dar detalles de / (If yes, please give details of):','{"S1P12":{"Id":"S1P12","text":"","code":"Anexo3pis1p12"}}','',''),

(4002,'4','DES','a','Servicios ofrecidos (Services)','','4001',''),
(4003,'4','OPTIONAL','i','¿Únicamente información de productos? (Product information only?)','{"S1P12A1":{"Id":"S1P12A1","text":"","code":"Anexo3pis1p12a1"}}','4002',''),
(4004,'4','OPTIONAL','ii','¿Balance de las cuentas? (Account balance?)','{"S1P12A2":{"Id":"S1P12A2","text":"","code":"Anexo3pis1p12a2"}}','4002',''),
(4005,'4','OPTIONAL','iii','¿Transferencias de cuenta a cuentas previamente autorizadas? (Pre-authorized account to account transfers?) ','{"S1P12A3":{"Id":"S1P12A3","text":"","code":"Anexo3pis1p12a3"}}','4002',''),
(4006,'4','OPTIONAL','iv','¿Aplicaciones para préstamos? (Loan applications?)','{"S1P12A4":{"Id":"S1P12A4","text":"","code":"Anexo3pis1p12a4"}}','4002',''),
(4007,'4','OPTIONAL','vi','¿Aplicaciones interactivas para hipotecas? (Interactive mortgage applications?)','{"S1P12A6":{"Id":"S1P12A6","text":"","code":"Anexo3pis1p12a6"}}','4002',''),
(4008,'4','OPTIONAL','vii','¿Manejo de cuentas empresariales o de negocios? (Business/Company account management?)','{"S1P12A7":{"Id":"S1P12A7","text":"","code":"Anexo3pis1p12a7"}}','4002',''),
(4010,'4','OPTIONAL','viii','¿Productos de Seguros? (Insurance Products?)','{"S1P12A8":{"Id":"S1P12A8","text":"","code":"Anexo3pis1p12a8"}}','4002',''),
(4011,'4','OPTIONAL','ix','¿Transacciones de acciones - en línea? (On-line securities dealing?)','{"S1P12A9":{"Id":"S1P12A9","text":"","code":"Anexo3pis1p12a9"}}','4002',''),
(4012,'4','NORMAL','x','Otros. Favor especificar. (Other, please specify) ','{"S1P12A10":{"Id":"S1P12A10","text":"","code":"Anexo3pis1p12a10a"}}','4002',''),
(4020, '4', 'SELECTOR-NORMAL', 'b','Por favor seleccione el método que se utiliza para verificar la identidad de los usuarios que realizan transacciones vía Internet / (Please select the method used to verify the identity of the users transacting via the Internet):',
'{"S1P12B1": { "Id": "S1P12B1", "text": "", "code": "Anexo3pis1p12b" }}', '4001', ''),
(4021,'4','SELECTOR-NORMAL','c','Por favor seleccione el método que se utiliza para proteger la integridad de cada transacción / (Please select the method used to protect the integrity of each transaction):','{"S1P12C":{"Id":"S1P12C","text":"","code":"Anexo3pis1p12c"}}','4001',''),
(4022,'4','OPTIONAL','d','¿Se utiliza un dispositivo de seguimiento para las facilidades en Internet? / Do you utilize a tracking device in relation to the Internet facilities?','{"S1P12D":{"Id":"S1P12D","text":"","code":"Anexo3pis1p12d"}}','4001',''),
(4023,'4','OPTIONAL','e','¿Se tienen condiciones formales para la utilización de su servicio de Internet en las cuales se especifican las obligaciones y responsabilidades de los usuarios? / Do you have formal terms and conditions for the use of your Internet service which outlines the obligations and responsibilities of the users?','{"S1P12E":{"Id":"S1P12E","text":"","code":"Anexo3pis1p12e"}}','4001',''),
(4024,'4','OPTIONAL','f','¿Se tienen procedimientos para monitorear a quién se prestan los servicios teniendo en cuenta los asuntos relacionados con las jurisdicciones de otros países? / Does the Proposer have procedures in place to monitor to whom their services are provided, taking into account any jurisdictional or cross border issues?','{"S1P12F":{"Id":"S1P12F","text":"","code":"Anexo3pis1p12f"}}','4001',''),
(4025,'4','OPTIONAL','g','¿Utiliza algún programa para la prevención de virus? / Do you use any anti-virus software?','{"S1P12G":{"Id":"S1P12G","text":"","code":"Anexo3pis1p12g"}}','4001',''),
(4026,'4','OPTIONAL','','Si la respuesta es SÍ, ¿se actualiza este regularmente? / If yes, is it upgraded on a regular basis?','{"S1P12G2":{"Id":"S1P12G2","text":"","code":"Anexo3pis1p12g2"}}','4025','');


INSERT INTO question_option (question_id,option_text,created_at) VALUES
('4001','##yes-no',''),
('4003','yes-no',''),
('4004','yes-no',''),
('4005','yes-no',''),
('4006','yes-no',''),
('4007','yes-no',''),
('4008','yes-no',''),
('4010','yes-no',''),
('4011','yes-no',''),
('4020','(i)Contraseña Estática (Static password)-(ii)Contraseña de una entrada (one time password)-(iii)Clave pública/privada cifrada (public/ private key encryption)-(iv)Firma digital (digital signatures)-##(v) Otra,favor especificar (Other, please specify)',''),
('4021','(i)Codificación de 128 bits (128 bit encryption)-(ii)Autenticación de mensajes (Message authentication)-(iii)Confirmación de Recepción (Receipt confirmation)-##(iv)Otra, favor especificar (Other, please specify)',''),
('4022','yes-no',''),
('4023','yes-no',''),
('4024','yes-no',''),
('4025','yes-no',''),
('4026','yes-no','');