-- Inserción en la tabla QUESTIONNAIRE
INSERT INTO questionary (questionary_id, policy_id, questionary_name, description, created_at, questionary_status,
                         source_file, total_number)
VALUES (7, 2, 'ECUADOR CUESTIONARIO RANSOMWARE SUPPLEMENTAL',
        'ECUADOR CUESTIONARIO RANSOMWARE SUPPLEMENTAL', '', 'TRUE', 'CuestionarioRansomwareSupplemental.xlsx', 51);

-- Inserción en la tabla QUESTION
INSERT INTO question (question_id, questionary_id, question_type, description, question_text, question_json, parent_id,
                      document_id)
VALUES (7001, 7, 'NORMAL', '', 'Nombre Completo del Solicitante', '', NULL, 'applicantName'),

       (7002, 7, 'SELECTOR', '1',
        'Con respecto a los esfuerzos del Solicitante para mitigar la suplantación de identidad ("Phishing"), seleccione todas las que correspondan',
        '{"S1P1OP1":{"Id":"S1P1OP1","text":"El Solicitante proporciona capacitación de concientización de seguridad a los empleados al menos una vez al año.","code":"qOneOpOne"},
          "S1P1OP2":{"Id":"S1P1OP2","text":"El Solicitante utiliza ataques de phishing simulados para probar la conciencia de seguridad cibernética de los empleados al menos una vez al año.","code":"qOneOpTwo"},
          "S1P1OP3":{"Id":"S1P1OP3","text":"Cuando el Solicitante está realizando ataques de phishing simulados, la tasa de éxito fue inferior al 15% en la última prueba.","code":"qOneOpThree"},
          "S1P1OP4":{"Id":"S1P1OP4","text":"El Solicitante etiqueta o marca los correos electrónicos de fuera de la organización.","code":"qOneOpFour"},
          "S1P1OP5":{"Id":"S1P1OP5","text":"El Solicitante tiene un proceso para reportar correos electrónicos sospechosos a un equipo de seguridad interno para que los investigue.","code":"qOneOpFive"},
          "S1P1OP6":{"Id":"S1P1OP6","text":"Ninguna de las anteriores.","code":"qOneOpSix"}}', NULL, ''),

       (7003, 7, 'NORMAL', 'a', 'Comentarios adicionales sobre los esfuerzos de la compañía para mitigar el phishing',
        '{"S1P1OP7":{"Id":"S1P1OP7","text":"","code":"qOneOpSeven"}}', 7002, ''),

       (7004, 7, 'OPTIONAL', '2',
        '¿El Solicitante tiene un proceso documentado para responder a las campañas de phishing (ya sea que estén dirigidas específicamente al solicitante o no)?',
        '{"S1P2OP1":{"Id":"S1P2OP1","text":"","code":"qTwoOpOne"}}', NULL, ''),

       (7005, 7, 'NORMAL', 'a', 'Si la respuesta es "Sí", describa los pasos principales para responder:',
        '{"S1P2OP3":{"Id":"S1P2OP3","text":"","code":"qTwoOpThree"}}', 7004, ''),

       (7006, 7, 'SELECTOR', '3',
        'Con respecto a los esfuerzos del Solicitante para bloquear sitios web y/o correo electrónico potencialmente maliciosos, seleccione todo lo que corresponda:',
        '{"S1P3OP1":{"Id":"S1P3OP1","text":"El Solicitante utiliza una solución de filtrado de correo electrónico que bloquea los archivos adjuntos maliciosos conocidos y los tipos de archivos sospechosos, incluidos los ejecutables.","code":"qThreeOpOne"},
          "S1P3OP2":{"Id":"S1P3OP2","text":"El Solicitante utiliza una solución de filtrado de correo electrónico que bloquea los mensajes sospechosos en función de su contenido o los atributos del remitente.","code":"qThreeOpTwo"},
          "S1P3OP3":{"Id":"S1P3OP3","text":"El Solicitante utiliza una solución de filtrado web que evita que los empleados visiten páginas web sospechosas o maliciosas.","code":"qThreeOpThree"},
          "S1P3OP4":{"Id":"S1P3OP4","text":"El Solicitante bloquea dominios no categorizados y recién registrados mediante servidores proxy web o filtros DNS.","code":"qThreeOpFour"},
          "S1P3OP5":{"Id":"S1P3OP5","text":"El Solicitante utiliza una solución de filtrado web que bloquea las descargas sospechosas o maliciosas conocidas, incluidos los ejecutables.","code":"qThreeOpFive"},
          "S1P3OP6":{"Id":"S1P3OP6","text":"La solución de filtrado de correo electrónico del Solicitante tiene la capacidad de ejecutar archivos adjuntos sospechosos en una zona de pruebas o entorno aislado.","code":"qThreeOpSix"},
          "S1P3OP7":{"Id":"S1P3OP7","text":"Las capacidades de filtrado web del Solicitante son efectivas en todos los activos corporativos, incluso si el activo corporativo no está en una red corporativa.","code":"qThreeOpSeven"},
          "S1P3OP8":{"Id":"S1P3OP8","text":"Ninguna de las anteriores.","code":"qThreeOpEight"}}', NULL, ''),

       (7007, 7, 'NORMAL', 'a',
        'Comentarios adicionales sobre los esfuerzos para bloquear sitios web y/o correo electrónico maliciosos:',
        '{"S1P3OP9":{"Id":"S1P3OP9","text":"","code":"qThreeOpNine"}}', 7006, ''),

       (7008, 7, 'OPTIONAL', '4',
        'Con respecto a la autenticación para los empleados que acceden de forma remota a la red corporativa y cualquier servicio basado en la nube donde puedan residir datos confidenciales (incluido el acceso al VPN, correo electrónico y CRM basados en la nube; juntos "acceso remoto a los recursos corporativos"), seleccione la descripción que mejor refleje la postura del Solicitante:',
        '{"S1P4OP1":{"Id":"S1P4OP1","text":"","code":"qFourOpOne"}}', NULL, ''),

       (7009, 7, 'NORMAL', 'a', 'Comentarios adicional sobre autenticación para empleados:',
        '{"S1P4OP5":{"Id":"S1P4OP5","text":"","code":"qFourOpFive"}}', 7008, ''),

       (7010, 7, 'OPTIONAL', '5',
        'Con respecto a la autenticación para contratistas y proveedores independientes que acceden remotamente a la red corporativa y cualquier servicio basado en la nube donde los datos confidenciales pueden residir (incluido el acceso VPN y el correo electrónico y CRM basados en la nube; juntos "acceso remoto a los recursos corporativos"), seleccione la descripción que mejor refleje la postura del solicitante:',
        '{"S1P5OP1":{"Id":"S1P5OP1","text":"","code":"qFiveOpOne"}}', NULL, ''),

       (7011, 7, 'NORMAL', 'a',
        'Comentarios adicional sobre autenticación para contratistas y proveedores independientes:',
        '{"S1P5OP5":{"Id":"S1P5OP5","text":"","code":"qFiveOpFive"}}', 7010, ''),

       (7012, 7, 'OPTIONAL', '6',
        '¿La implementación de autenticación multifactor del solicitante también cumple los criterios de que el compromiso de un solo dispositivo sólo comprometerá un único autenticador?',
        '{"S1P6OP1":{"Id":"S1P6OP1","text":"","code":"qSixOpOne"}}', NULL, ''),

       (7013, 7, 'NORMAL', 'a', 'Comentarios adicional sobre implementación de autenticación multifactor:',
        '{"S1P6OP4":{"Id":"S1P6OP4","text":"","code":"qSixOpFour"}}', 7012, ''),

       (7014, 7, 'SELECTOR', '7',
        'Con respecto a la seguridad en los endpoints de estaciones de trabajo (computadoras y laptops), seleccione todas las que correspondan:',
        '{"S1P7OP1":{"Id":"S1P7OP1","text":"La política del Solicitante es que todas las estaciones de trabajo tienen antivirus con capacidades heurísticas.","code":"qSevenOpOne"},
          "S1P7OP2":{"Id":"S1P7OP2","text":"El Solicitante utiliza herramientas de seguridad de endpoints con capacidades de detección del comportamiento y mitigación de vulnerabilidades.","code":"qSevenOpTwo"},
          "S1P7OP3":{"Id":"S1P7OP3","text":"El Solicitante tiene un grupo interno que supervisa la salida de las herramientas de seguridad en los endpoints e investiga cualquier anomalía.","code":"qSevenOpThree"},
          "S1P7OP4":{"Id":"S1P7OP4","text":"Ninguna de las anteriores.","code":"qSevenOpFour"}}', NULL, ''),

       (7015, 7, 'NORMAL', 'a', 'Comentario adicional sobre las capacidades de seguridad de los endpoints:',
        '{"S1P7OP5":{"Id":"S1P7OP5","text":"","code":"qSevenOpFive"}}', 7014, ''),

       (7016, 7, 'OPTIONAL', '8',
        'Con respecto a la supervisión de las herramientas de salida de seguridad, seleccione la descripción que mejor refleje las capacidades del solicitante:',
        '{"S1P8OP1":{"Id":"S1P8OP1","text":"","code":"qEightOpOne"}}', NULL, ''),

       (7017, 7, 'NORMAL', 'a', 'Comentarios adicionales sobre la supervisión de la seguridad:',
        '{"S1P8OP5":{"Id":"S1P8OP5","text":"","code":"qEightOpFive"}}', 7016, ''),

       (7018, 7, 'OPTIONAL', '9',
        '¿Cuál es el tiempo medio del solicitante para evaluar y contener incidentes de seguridad de estaciones de trabajo?',
        '{"S1P9OP1":{"Id":"S1P9OP1","text":"","code":"qNineOpOne"}}', NULL, ''),

       (7019, 7, 'NORMAL', 'a', 'Comentario adicional sobre el tiempo promedio para corregir:',
        '{"S1P9OP6":{"Id":"S1P9OP6","text":"","code":"qNineOpSix"}}', 7018, ''),

       (7020, 7, 'OPTIONAL', '10',
        'Con respecto a los controles de acceso para la estación de trabajo de cada usuario, seleccione la descripción que mejor refleje la postura del Solicitante:',
        '{"S1P10OP1":{"Id":"S1P10OP1","text":"","code":"qTenOpOne"}}', NULL, ''),

       (7021, 7, 'NORMAL', 'a', 'Comentarios adicionales sobre los controles de acceso para estaciones de trabajo:',
        '{"S1P10OP5":{"Id":"S1P10OP5","text":"","code":"qTenOpFive"}}', 7020, ''),

       (7022, 7, 'SELECTOR', '11',
        'Con respecto a la protección de credenciales privilegiadas, seleccione todas las que correspondan con la postura del Solicitante:',
        '{"S1P11OP1":{"Id":"S1P11OP1","text":"Los administradores del sistema del solicitante tienen una credencial única y privilegiada para las tareas administrativas.","code":"qElevenOpOne"},
          "S1P11OP2":{"Id":"S1P11OP2","text":"Las cuentas con privilegios (incluidos los administradores de dominio) requieren autenticación multifactor.","code":"qElevenOpTwo"},
          "S1P11OP3":{"Id":"S1P11OP3","text":"Las cuentas privilegiadas se guardan en un lugar seguro con contraseña que requieren que el usuario \"revise\" la credencial.","code":"qElevenOpThree"},
          "S1P11OP4":{"Id":"S1P11OP4","text":"Hay un registro de todo el uso de la cuenta con privilegios durante al menos los últimos treinta días.","code":"qElevenOpFour"},
          "S1P11OP5":{"Id":"S1P11OP5","text":"Las estaciones de trabajo de acceso con privilegios se utilizan para la administración de sistemas críticos.","code":"qElevenOpFive"},
          "S1P11OP6":{"Id":"S1P11OP6","text":"Ninguna de las anteriores.","code":"qElevenOpSix"}}', NULL, ''),

       (7023, 7, 'NORMAL', 'a', 'Comentarios adicionales sobre la protección de credenciales con privilegios:',
        '{"S1P11OP7":{"Id":"S1P11OP7","text":"","code":"qElevenOpSeven"}}', 7022, ''),

       (7024, 7, 'DES', '12',
        'Indique el uso de Microsoft Active Directory por parte del solicitante (en todos los dominios o "forests"):',
        '', NULL, ''),

       (7025, 7, 'NORMAL', 'a', 'El Solicitante no utiliza Microsoft Active Directory',
        '{"S1P12OP1":{"Id":"S1P12OP1","text":"","code":"qTwelveOpOne"}}', 7024, ''),

       (7026, 7, 'NORMAL', 'b',
        'Número de cuentas de usuario en el grupo Administradores de dominio (incluya cuentas de servicio , si las hay, en este total):',
        '{"S1P12OP2":{"Id":"S1P12OP2","text":"","code":"qTwelveOpTwo"}}', 7024, ''),

       (7027, 7, 'NORMAL', 'c', 'Número de cuentas de servicio en el grupo Administradores de dominio:',
        '{"S1P12OP3":{"Id":"S1P12OP3","text":"","code":"qTwelveOpThree"}}', 7024, ''),

       (7028, 7, 'NORMAL', 'd', 'Comentario adicional sobre el número de administradores de dominio:',
        '{"S1P12OP4":{"Id":"S1P12OP4","text":"","code":"qTwelveOpFour"}}', 7024, ''),

       (7029, 7, 'DES', '13',
        '¿Cuántos usuarios tienen cuentas privilegiadas persistentes para endpoints (servidores y estaciones de trabajo)?',
        '', NULL, ''),

       (7030, 7, 'NORMAL', 'a', 'Introduzca un número entero:',
        '{"S1P13OP1":{"Id":"S1P13OP1","text":"","code":"qThirteenOpOne"}}', 7029, ''),

       (7031, 7, 'NORMAL', 'b', 'Comentario adicional sobre el número de cuentas con privilegios:',
        '{"S1P13OP2":{"Id":"S1P13OP2","text":"","code":"qThirteenOpTwo"}}', 7029, ''),

       (7032, 7, 'SELECTOR', '14',
        'Con respecto a la seguridad de los sistemas externos, seleccione todo lo que corresponda a la postura del Solicitante:',
        '{"S1P14OP1":{"Id":"S1P14OP1","text":"El Solicitante realiza una prueba de penetración al menos una vez al año para evaluar la seguridad de sus sistemas externos.","code":"qFourteenOpOne"},
          "S1P14OP2":{"Id":"S1P14OP2","text":"El Solicitante tiene un firewall de aplicaciones web (\"Web Application Firewall\" - WAF) frente a todas las aplicaciones externas y está en modo de bloqueo.","code":"qFourteenOpTwo"},
          "S1P14OP3":{"Id":"S1P14OP3","text":"El Solicitante utiliza un servicio externo para monitorear su superficie de ataque (sistemas externos/orientados a Internet).","code":"qFourteenOpThree"},
          "S1P14OP4":{"Id":"S1P14OP4","text":"Ninguna de las anteriores.","code":"qFourteenOpFour"}}', NULL, ''),

       (7033, 7, 'OPTIONAL', '15',
        '¿Cuál es el tiempo objetivo del Solicitante para implementar parches "críticos" – de alta prioridad, (según lo determinen los estándares del solicitante para cuándo deben implementarse los parches)?',
        '{"S1P15OP1":{"Id":"S1P15OP1","text":"","code":"qFifteenOpOne"}}', NULL, ''),

       (7034, 7, 'NORMAL', 'a', 'Comentario adicional sobre los tiempos de destino para la aplicación de parches:',
        '{"S1P15OP6":{"Id":"S1P15OP6","text":"","code":"qFifteenOpSix"}}', 7033, ''),

       (7035, 7, 'OPTIONAL', '16',
        '¿Cuál es el % de cumplimiento de los propios estándares del Solicitante al año para la implementación de parches críticos?',
        '{"S1P16OP1":{"Id":"S1P16OP1","text":"","code":"qSixteenOpOne"}}', NULL, ''),

       (7036, 7, 'NORMAL', 'a', 'Comentarios adicionales sobre el cumplimiento de los parches:',
        '{"S1P16OP6":{"Id":"S1P16OP6","text":"","code":"qSixteenOpSix"}}', 7035, ''),

       (7037, 7, 'SELECTOR', '17',
        'Con respecto a las capacidades de supervisión de la red del Solicitante, seleccione todas las que correspondan:',
        '{"S1P17OP1":{"Id":"S1P17OP1","text":"El Solicitante utiliza una herramienta de monitoreo de eventos e información de seguridad (SIEM) para correlacionar la salida de múltiples herramientas de seguridad.","code":"qSeventeenOpOne"},
          "S1P17OP2":{"Id":"S1P17OP2","text":"El Solicitante monitorea el tráfico de la red en busca de transferencias de datos anómalas y potencialmente sospechosas.","code":"qESeventeenOpTwo"},
          "S1P17OP3":{"Id":"S1P17OP3","text":"El Solicitante supervisa los problemas de rendimiento y capacidad de almacenamiento.","code":"qSeventeenOpThree"},
          "S1P17OP4":{"Id":"S1P17OP4","text":"El Solicitante tiene herramientas para monitorear la pérdida de datos (DLP) y están en modo de bloqueo.","code":"qSeventeenOpFour"},
          "S1P17OP5":{"Id":"S1P17OP5","text":"Ninguna de las anteriores.","code":"qSeventeenOpFive"}}', NULL, ''),

       (7038, 7, 'NORMAL', 'a', 'Comentarios adicionales sobre la supervisión de la red:',
        '{"S1P17OP6":{"Id":"S1P17OP6","text":"","code":"qSeventeenOpSix"}}', 7037, ''),

       (7039, 7, 'OPTIONAL', '18',
        'Con respecto a limitar el movimiento lateral, seleccione todo lo que se aplique a la postura del Solicitante:',
        '{"S1P18OP1":{"Id":"S1P18OP1","text":"","code":"qEightteenOpOne"}}', NULL, ''),

       (7040, 7, 'NORMAL', 'a', 'Comentario adicional sobre la segmentación:',
        '{"S1P18OP6":{"Id":"S1P18OP6","text":"","code":"qEightteenOpSix"}}', 7039, ''),

       (7041, 7, 'OPTIONAL-NORMAL', '19',
        'Introduzca la fecha del último ejercicio ransomware del Solicitante seleccionando la opción de Fecha, si no se ha llevado a cabo ninguno seleccione la opción correspondiente',
        '{"S1P19OP1":{"Id":"S1P19OP1","text":"Fecha","code":"qNineteenOpOne"},
          "S1P19OP2":{"Id":"S1P19OP2","text":"No se ha realizado ningún ejercicio ransomware","code":"qNineteenOpTwo"}}',
        NULL, ''),

       (7042, 7, 'OPTIONAL', '20',
        '¿Tiene el solicitante un plan documentado para responder al ransomware de un proveedor/proveedor o cliente tercero?',
        '{"S1P20OP1":{"Id":"S1P20OP1","text":"","code":"qTwentyOpOne"}}', NULL, ''),

       (7043, 7, 'NORMAL', 'a', 'En caso afirmativo, indique los pasos principales:',
        '{"S1P20OP3":{"Id":"S1P20OP3","text":"","code":"qTwentyOpThree"}}', 7042, ''),

       (7044, 7, 'SELECTOR', '21',
        'Con respecto a la verificación de la eficacia de los controles de seguridad, seleccione todo lo que se aplica al Solicitante:',
        '{"S1P21OP1":{"Id":"S1P21OP1","text":"El solicitante utiliza el software de simulación de infracciones y ataques (BAS) para verificar la eficacia de los controles de seguridad.","code":"qTwentyoneOpOne"},
          "S1P21OP2":{"Id":"S1P21OP2","text":"El Solicitante tiene un \"red team\" interno que prueba los controles de seguridad y la respuesta de los mismos.","code":"qTwentyoneOpTwo"},
          "S1P21OP3":{"Id":"S1P21OP3","text":"El Solicitante ha contratado a una parte externa para simular actores de amenazas y probar controles de seguridad en el último año.","code":"qTwentyoneOpThree"},
          "S1P21OP4":{"Id":"S1P21OP4","text":"Ninguna de las anteriores.","code":"qTwentyoneOpFour"}}', NULL, ''),

       (7045, 7, 'NORMAL', 'a', 'Comentario adicional sobre la verificación de los controles:',
        '{"S1P21OP5":{"Id":"S1P21OP5","text":"","code":"qTwentyoneOpFive"}}', 7044, ''),

       (7046, 7, 'SELECTOR', '22',
        'Con respecto a las capacidades de recuperación ante desastres, seleccione todas las que se aplican al Solicitante:',
        '{"S1P22OP1":{"Id":"S1P22OP1","text":"Existe un proceso para crear copias de seguridad, pero es indocumentado y/o ad hoc.","code":"qTwentytwoOpOne"},
          "S1P22OP2":{"Id":"S1P22OP2","text":"El Solicitante tiene una política de recuperación ante desastres documentada, que incluye estándares para copias de seguridad basadas en la criticidad de la información.","code":"qTwentytwoOpTwo"},
          "S1P22OP3":{"Id":"S1P22OP3","text":"Al menos dos veces al año, el Solicitante pone a prueba su capacidad para restaurar diferentes sistemas y datos críticos de manera oportuna a partir de sus copias de seguridad.","code":"qTwentytwoOpThree"},
          "S1P22OP4":{"Id":"S1P22OP4","text":"Ninguna de las anteriores.","code":"qTwentytwoOpFour"}}', NULL, ''),

       (7047, 7, 'OPTIONAL', '23',
        '¿Cuál es el tiempo de recuperación objetivo (RTO) del Solicitante para los sistemas críticos?',
        '{"S1P23OP1":{"Id":"S1P23OP1","text":"","code":"qTwentyThreeOpOne"}}', NULL, ''),

       (7048, 7, 'SELECTOR', '24',
        'Con respecto a las capacidades de las copias de seguridad, seleccione todas las que se aplican al Solicitante:',
        '{"S1P24OP1":{"Id":"S1P24OP1","text":"La estrategia de copia de seguridad del Solicitante incluye copias de seguridad sin conexión (se pueden almacenar en el sitio).","code":"qTwentyfourOpOne"},
          "S1P24OP2":{"Id":"S1P24OP2","text":"La estrategia de copia de seguridad del solicitante incluye copias de seguridad sin conexión almacenadas fuera del sitio.","code":"qTwentyfourOpTwo"},
          "S1P24OP3":{"Id":"S1P24OP3","text":"Solo se puede acceder a las copias de seguridad del solicitante a través de un mecanismo de autenticación fuera de nuestro Active Directory corporativo.","code":"qTwentyfourOpThree"}}',
        NULL, ''),

       (7049, 7, 'NORMAL', 'a', 'Comentarios adicionales sobre las capacidades de copia de seguridad:',
        '{"S1P24OP4":{"Id":"S1P24OP4","text":"","code":"qTwentyfourOpFour"}}', 7048, ''),

       (7050, 7, 'OPTIONAL', '25',
        '¿El Solicitante cuenta con alguna política que todos los dispositivos portátiles utilizan cifrado de disco completo?',
        '{"S1P25OP1":{"Id":"S1P25OP1","text":"","code":"qTwentyfiveOpOne"}}', NULL, ''),

       (7051, 7, 'NORMAL', 'a', 'Comentarios Adicionales:',
        '{"S1P25OP3":{"Id":"S1P25OP3","text":"","code":"qTwentyfiveOpThree"}}', 7050, '');

-- Inserción en la tabla OPTIONS
INSERT INTO question_option (question_id, option_text, created_at)
VALUES (7002, ' El Solicitante proporciona capacitación y concienciación sobre la seguridad cibernética a los empleados al menos una vez al año.-El Solicitante utiliza ataques de phishing simulados para probar la conciencia de seguridad cibernética de los empleados al menos una vez al año.-Cuando el Solicitante está realizando ataques de phishing simulados, la tasa de éxito fue inferior al 15% en la última prueba (menos del 15% de los empleados dieron click al phishing exitosamente).-El Solicitante "etiqueta" o marca los correos electrónicos de fuera de la organización.-El Solicitante tiene un proceso para reportar correos electrónicos sospechosos a un equipo de seguridad interno para que los investigue.-Ninguna de las anteriores', NULL),
       (7004, 'si-no', NULL),
       (7006,
        'El Solicitante utiliza una solución de filtrado de correo electrónico que bloquea los archivos adjuntos maliciosos conocidos y los tipos de archivos sospechosos, incluidos los ejecutables.-El Solicitante utiliza una solución de filtrado de correo electrónico que bloquea los mensajes sospechosos en función de su contenido o los atributos del remitente.-El Solicitante utiliza una solución de filtrado web que evita que los empleados visiten páginas web sospechosas o maliciosas.-El Solicitante bloquea dominios no categorizados y recién registrados mediante servidores proxy web o filtros DNS.-El Solicitante utiliza una solución de filtrado web que bloquea las descargas sospechosas o maliciosas conocidas, incluidos los ejecutables.-La solución de filtrado de correo electrónico del Solicitante tiene la capacidad de ejecutar archivos adjuntos sospechosos en una zona de pruebas o entorno aislado-Las capacidades de filtrado web del Solicitante son efectivas en todos los activos corporativos, incluso si el activo corporativo no está en una red corporativa (por ejemplo, los activos están configurados para utilizar filtros web basados en la nube o requieren una conexión VPN para navegar por Internet).-Ninguna de las anteriores',
        NULL),
       (7008,
        'El acceso remoto a los recursos corporativos requiere un nombre de usuario y una contraseña válidas (autenticación de factor único).-La autenticación multifactor está implementada para algunos tipos de acceso remoto a los recursos corporativos, pero no para todos.-La política exige la autenticación multifactor para todos los accesos remotos a los recursos corporativos, todas las excepciones a la política están documentadas.-El Solicitante no proporciona acceso remoto a los empleados.',
        NULL),
       (7010,
        'El acceso remoto a los recursos corporativos requiere un nombre de usuario y una contraseña válidas (autenticación de factor único).-La autenticación multifactor está implementada para algunos tipos de acceso remoto a los recursos corporativos, pero no para todos.-La política exige la autenticación multifactor para todos los accesos remotos a los recursos corporativos, todas las excepciones a la política están documentadas.-El Solicitante no proporciona acceso remoto a los contratistas y proveedores independientes',
        NULL),
       (7012,
        'No aplicable (el Solicitante no utiliza la autenticación multifactor)-No, La implementación multifactor del Solicitante no cumple los criterios anteriores.-Sí, la implementación multifactor del solicitante cumple con los criterios anteriores.',
        NULL),
       (7014,
        'La política del Solicitante es que todas las estaciones de trabajo tienen antivirus con capacidades heurísticas.-El Solicitante utiliza herramientas de seguridad de endpoints con capacidades de detección del comportamiento y mitigación de vulnerabilidades.-El Solicitante tiene un grupo interno que supervisa la salida de las herramientas de seguridad en los endpoints e investiga cualquier anomalía.-Ninguna de las anteriores',
        NULL),
       (7016,
        'El Solicitante no tiene personal dedicado a supervisar las operaciones de seguridad (un "Security Operations Center").-El Solicitante tiene un centro de operaciones de seguridad ("Security Operations Center"), pero no es 24/7 (puede ser interno o externo).-El Solicitante tiene una supervisión 24/7 de las operaciones de seguridad mediante un tercero (como un proveedor de servicios de seguridad).-El Solicitante tiene monitoreo 24/7 de las operaciones de seguridad internamente.',
        NULL),
       (7018,
        'El Solicitante no rastrea esta métrica/No sabe-<30 minutos-30 minutos a 2 horas-2 a 8 horas->8 horas',
        NULL),
       (7020,
        'Ningún empleado está en el grupo de administradores ni tiene acceso de administrador local a sus estaciones de trabajo.-La política del Solicitante es que los empleados de forma predeterminada no están en el grupo de administradores y no tienen acceso de administrador local, todas las excepciones se documentan.-Algunos de los empleados del Solicitante están en el grupo de administradores o son administradores locales.-No sabe',
        NULL),
       (7022,
        'Los administradores del sistema del solicitante tienen una credencial única y privilegiada para las tareas administrativas (independientemente de sus credenciales de usuario de acceso diario, email, etc.).-Las cuentas con privilegios (incluidos los administradores de dominio) requieren autenticación multifactor.-Las cuentas privilegiadas se guardan en un lugar seguro con contraseña que requieren que el usuario "revise" la credencial (que luego se rota).-Hay un registro de todo el uso de la cuenta con privilegios durante al menos los últimos treinta días.-Las estaciones de trabajo de acceso con privilegios (estaciones de trabajo que no tienen acceso a Internet o correo electrónico) se utilizan para la administración de sistemas críticos (incluidos los servidores de autenticación/ Controladores de dominio).-Ninguna de las anteriores',
        NULL),
       (7032,
        'El Solicitante realiza una prueba de penetración al menos una vez al año para evaluar la seguridad de sus sistemas externos.-El Solicitante tiene un firewall de aplicaciones web ("Web Application Firewall" WAF) frente a todas las aplicaciones externas y está en modo de bloqueo.-El Solicitante utiliza un servicio externo para monitorear su superficie de ataque (sistemas externos/orientados a Internet).-Ninguna de las anteriores',
        NULL),
       (7033,
        'No hay ninguna directiva definida para cuando se deben implementar revisiones.-Dentro de 24 horas.-24 a 72 horas.-3 a 7 días.-> 7 días.',
        NULL),
       (7035, 'Applicant does not track this metric/Do not know->95%-90 a 95%-80 a 90%-<80%', NULL),
       (7037,
        'El Solicitante utiliza una herramienta de monitoreo de eventos e información de seguridad (SIEM) para correlacionar la salida de múltiples herramientas de seguridad.-El Solicitante monitorea el tráfico de la red en busca de transferencias de datos anómalas y potencialmente sospechosas.-El Solicitante supervisa los problemas de rendimiento y capacidad de almacenamiento (como un uso elevado de memoria o procesador, o falta de espacio libre en el disco).-El Solicitante tiene herramientas para monitorear la pérdida de datos (DLP) y están en modo de bloqueo.-Ninguna de las anteriores',
        NULL),
       (7039,'El Solicitante ha segmentado la red por geografía (por ejemplo, se deniega el tráfico entre oficinas en diferentes ubicaciones a menos que sea necesario para apoyar un requisito empresarial específico).-El Solicitante ha segmentado la red por función empresarial (por ejemplo, se prohibe el tráfico entre activos que soportan diferentes funciones (Recursos Humanos y Finanzas, por ejemplo) a menos que sea necesario para apoyar un requisito empresarial específico).-El Solicitante ha implementado reglas de firewall de host que impiden el uso de RDP para iniciar sesión en estaciones de trabajo.-El Solicitante ha configurado todas las cuentas de servicio para denegar los inicios de sesión interactivos.-Ninguna de las anteriores',
        NULL),
       (7041, '##Registrar Fecha-No se ha realizado ningún ejercicio ransomware', NULL),
       (7042, '##si-no', NULL),
       (7043,
        'El solicitante utiliza el software de simulación de infracciones y ataques (BAS) para verificar la eficacia de los controles de seguridad.-El Solicitante tiene un "red team" interno que prueba los controles de seguridad y la respuesta de los mismos.-El Solicitante ha contratado a una parte externa para simular actores de amenazas y probar controles de seguridad en el último año.-Ninguna de las anteriores',
        NULL),
        (7044, 
        'El solicitante utiliza el software de simulación de infracciones y ataques (BAS) para verificar la eficacia de los controles de seguridad.-El Solicitante tiene un "red team" interno que prueba los controles de seguridad y la respuesta de los mismos.-El Solicitante ha contratado a una parte externa para simular actores de amenazas y probar controles de seguridad en el último año.-Ninguna de las anteriores',
        NULL),
       (7046,
        'Existe un proceso para crear copias de seguridad, pero es indocumentado y/o ad hoC-El Solicitante tiene una política de recuperación ante desastres documentada, que incluye estándares para copias de seguridad basadas en la criticidad de la información.-Al menos dos veces al año, el Solicitante pone a prueba su capacidad para restaurar diferentes sistemas y datos críticos de manera oportuna a partir de sus copias de seguridad.-Ninguna de las anteriores',
        NULL),
       (7047, 'El Solicitante no tiene un RTO/No sabe-< 4 horas-4 a 24 horas.-1 to 2 días.-2 a 7 días.', NULL),
       (7048,
        'La estrategia de copia de seguridad del Solicitante incluye copias de seguridad sin conexión (se pueden almacenar en el sitio)-La estrategia de copia de seguridad del solicitante incluye copias de seguridad sin conexión almacenadas fuera del sitio-Solo se puede acceder a las copias de seguridad del solicitante a través de un mecanismo de autenticación fuera de nuestro Active Directory corporativo.',
        NULL),
       (7050, 'si-no', NULL);