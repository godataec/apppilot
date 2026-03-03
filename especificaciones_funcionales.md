# Especificación de Requisitos de Software (ERS)
## Sistema de Gestión de Pólizas y Siniestros Banco Internacional (SPS-BI)

[cite_start]**Fecha:** 2024/06/05 [cite: 11] (Actualizado a Stack Moderno)
[cite_start]**Autor:** Juan Carlos Montiel [cite: 14]

---

## 1. Introducción

### 1.1. Propósito
[cite_start]El presente documento tiene como propósito definir las especificaciones funcionales y no funcionales para el desarrollo de un sistema de información web[cite: 39]. [cite_start]Este sistema permitirá automatizar y optimizar la gestión de los procesos administrativos y operativos relacionados con pólizas y siniestros[cite: 42]. [cite_start]Éste será utilizado por personal del Banco Internacional y bancos filiales[cite: 40].

### 1.2. Arquitectura y Stack Tecnológico Propuesto
[cite_start]El Sistema de Gestión de Pólizas y Siniestros (SPS-BI) será un producto diseñado para operar en entornos web de forma rápida y eficiente[cite: 58]. 
* **Frontend:** React 18+ (Vite, Tailwind CSS, TanStack Query).
* **Backend:** FastAPI (Python 3.11+) para operaciones asíncronas de alto rendimiento.
* [cite_start]**Base de Datos:** SQL Server con SQLAlchemy (ORM) y Alembic para migraciones[cite: 72].
* [cite_start]**Autenticación:** Integración con Active Directory (SAML 2.0) y JWT para el manejo de sesiones[cite: 59].

---

## 2. Perfiles de Usuario

El sistema soportará los siguientes perfiles con control de acceso basado en roles (RBAC):

* [cite_start]**Super Administrador:** Gestión completa de usuarios, configuración de parámetros del sistema (ej. montos de pérdida operativa), reapertura de cuestionarios cerrados y supervisión de logs de auditoría[cite: 63].
* [cite_start]**Administrador:** Administración de cuestionarios, siniestros, generación de reportes y gestión de notificaciones[cite: 64].
* [cite_start]**Usuario de Registro (Pólizas):** Ingreso de información en cuestionarios, adjuntar archivos y validación de datos económicos comparados con períodos anteriores[cite: 65].
* [cite_start]**Usuario de Visualización (Pólizas):** Revisión y descarga de cuestionarios y reportes sin capacidad de edición[cite: 66].
* [cite_start]**Usuario de Registro (Siniestros):** Registro de siniestros por pérdida operativa, robo o daño a activos, y adjuntar archivos pertinentes[cite: 67].
* [cite_start]**Usuario Proveedor (Siniestros):** Visualización de siniestros asignados, actualización de estados y adición de comentarios[cite: 68].

---

## 3. Requisitos Funcionales: Módulo de Pólizas

### RF1: Autenticación de Usuarios
* [cite_start]El sistema verificará las credenciales contra el Active Directory del Banco Internacional[cite: 114].
* [cite_start]Si las credenciales no coinciden, se utilizará un inicio de sesión local gestionado por el Super Administrador[cite: 116].
* [cite_start]Se implementará autenticación multifactor (MFA/TOTP) para añadir una capa adicional de seguridad[cite: 118].

### RF2: Gestión de Usuarios
* [cite_start]El Super Administrador podrá crear, editar, visualizar y desactivar (soft-delete) usuarios[cite: 130, 150].
* [cite_start]El sistema enviará correos automáticos para el establecimiento o reinicio de contraseñas[cite: 141, 146].

### RF3 y RF4: Administración de Pólizas y Cuestionarios
* [cite_start]Los administradores podrán asignar periodos de vigencia (fecha inicio/fin) a los cuestionarios[cite: 170].
* [cite_start]Se permitirá asignar uno o más usuarios responsables a cada cuestionario[cite: 175].
* [cite_start]El sistema mostrará el avance global de cada cuestionario mediante un indicador visual[cite: 180, 182].
* [cite_start]Solo el Super Administrador podrá reabrir cuestionarios cerrados[cite: 183].
* [cite_start]Los usuarios podrán habilitar o deshabilitar pólizas[cite: 217].

### RF6: Registro de Información (Validación Inteligente)
* [cite_start]Los usuarios de registro ingresarán datos en formularios interactivos[cite: 277].
* [cite_start]El sistema validará que la información concuerde con el formulario del año anterior; caso contrario, emitirá una alerta visual resaltando las discrepancias[cite: 280, 398].
* [cite_start]El sistema cerrará la edición al usuario cuando alcance el 100% de completitud[cite: 390].

### RF8: Generación Multi Riesgos (Procesamiento Excel)
* [cite_start]Los usuarios podrán subir un archivo Excel para generar el documento de la póliza multi riesgos[cite: 318].
* [cite_start]El sistema validará el formato y agrupará categorías en Macro Categorías procesando el archivo de forma asíncrona[cite: 321, 322].

### RF10: Reportes de Pólizas
* [cite_start]Generación de reportes con filtros por Año, Periodo, DSLA y Costos[cite: 367, 369, 370, 371, 372].
* [cite_start]Descarga en formatos estándar como PDF y Excel[cite: 379].

---

## 4. Requisitos Funcionales: Módulo de Siniestros

### RF1 y RF2: Gestión de Siniestros y Pérdidas Operativas
* [cite_start]Creación, edición y visualización de siniestros por Pérdida Operativa (PO) y Robo/Daño a activos (SO)[cite: 410, 436, 592].
* [cite_start]Generación automática de IDs con el formato POXXXX y SOXXXX[cite: 593].
* [cite_start]Manejo de estados: Abierto, Pendiente, Enviado Formulario de Liquidación, Cerrado y Pagado[cite: 591].

### RF3 y RF4: Gestión de Proveedores
* [cite_start]Registro de proveedores y múltiples contactos asociados[cite: 460, 480].
* [cite_start]Los usuarios proveedores podrán actualizar el estado de sus siniestros asignados y agregar comentarios[cite: 494, 498].

### RF5: Administración y Notificaciones
* [cite_start]El administrador puede asignar siniestros a proveedores y adjuntar archivos[cite: 519, 522].
* [cite_start]El Super Administrador configurará el monto umbral para considerar una pérdida operativa como siniestro[cite: 528].
* [cite_start]Notificación automática vía correo electrónico en caso de reapertura o envío a proveedor[cite: 590].

### RF6 y RF7: Reportes de Siniestros
* [cite_start]Generación de reportes detallados aplicando filtros por Año, Mes, Tipo de Activo y Proveedor[cite: 536, 560].
* [cite_start]Descarga de reportes en formato PDF, Excel o CSV[cite: 553, 576].

---

## 5. Requisitos No Funcionales (Optimizados)

### 5.1. Rendimiento y Disponibilidad
* La API RESTful (FastAPI) responderá a consultas CRUD estándar en un tiempo no mayor a 500ms. [cite_start]Las solicitudes complejas se responderán en menos de 5 segundos[cite: 385, 584].
* [cite_start]El sistema será desplegado en contenedores para manejar aumentos en la carga de trabajo sin degradación del rendimiento[cite: 403, 595].

### 5.2. Seguridad y Auditoría
* Integración con Active Directory y validación mediante tokens JWT.
* [cite_start]El sistema manejará logs de seguridad para registrar todas las acciones (creación, edición, eliminación) de cada usuario[cite: 77, 601].
* [cite_start]Se registrará automáticamente la fecha y hora de modificación de cada registro[cite: 588].
* [cite_start]El sistema exigirá el registro de un motivo de justificación cada vez que un usuario descargue información[cite: 392, 607].

### 5.3. Interfaz de Usuario
* [cite_start]Diseño intuitivo, responsive y fácil de usar, desarrollado en React[cite: 79].
* [cite_start]Implementación de mensajes de validación en tiempo real para guiar al usuario[cite: 98].

### 5.4. Integridad de Datos
* [cite_start]El sistema calculará automáticamente los valores económicos dependiendo de la sucursal[cite: 395].
* [cite_start]Se permitirá recalcular y generar documentos actualizados si se reabre el periodo de la póliza[cite: 396].