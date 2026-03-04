# Especificaciones Técnicas — SPS-BI (Sistema de Gestión de Pólizas y Siniestros)

> Plataforma de gestión de pólizas de seguros y siniestros para Banco Internacional.
> **Stack:** Python 3.11+ · FastAPI · React 18+ · SQL Server · OIDC (OpenID Connect) / JWT · Docker

---

## Tabla de Contenidos

1. [Visión General del Sistema](#1-visión-general-del-sistema)
2. [Jerarquía de Entidades](#2-jerarquía-de-entidades)
3. [Flujos de Negocio](#3-flujos-de-negocio)
4. [Flujo de Autenticación y Autorización](#4-flujo-de-autenticación-y-autorización)
5. [Catálogo de Endpoints de la API](#5-catálogo-de-endpoints-de-la-api)
6. [Tareas Programadas](#6-tareas-programadas)
7. [Aspectos Transversales](#7-aspectos-transversales)
8. [Resumen de Configuración](#8-resumen-de-configuración)
9. [Enums y Modelo de Estados](#9-enums-y-modelo-de-estados)

---

## 1. Visión General del Sistema

### 1.1 Diagrama de Arquitectura

```
┌──────────────────┐   OIDC / OAuth 2.0  ┌───────────────────────┐      JWT Bearer       ┌──────────────────┐
│   Azure AD /     │◄────────────────────►│   FastAPI Backend     │◄──────────────────────│  React Frontend  │
│   Entra ID (IdP) │                      │   (Uvicorn + Docker)  │                       │  (Vite + Nginx)  │
└──────────────────┘                      └──────────┬────────────┘                       └──────────────────┘
                                                     │                                           :5173 (dev)
                                          ┌──────────▼────────────┐                              :80 (prod)
                                          │      SQL Server       │
                                          │     (bipolizas)       │
                                          └──────────┬────────────┘
                                                     │
                                          ┌──────────▼────────────┐
                                          │    Celery Workers     │
                                          │  (Redis como broker)  │
                                          └───────────────────────┘
```

### 1.2 Stack Tecnológico Actualizado

| Componente | Tecnología Anterior (Legacy) | Tecnología Nueva |
|---|---|---|
| **Lenguaje Backend** | Java 8 | **Python 3.11+** |
| **Framework Backend** | Spring Boot 2.7.18 | **FastAPI 0.110+** |
| **ORM** | Spring Data JPA + Hibernate | **SQLAlchemy 2.0 + Alembic** (migraciones) |
| **Autenticación (IdP)** | SAML 2.0 via Azure AD | **OIDC via Azure AD / Entra ID** (authlib / python-jose) |
| **Autenticación (API)** | JWT (HS256, JJWT) | **JWT (HS256, python-jose / PyJWT)** |
| **Mapeo de Datos** | MapStruct + Lombok | **Pydantic v2** (serialización/validación) |
| **Generación de PDF** | Thymeleaf + openhtmltopdf | **Jinja2 + WeasyPrint** (o xhtml2pdf) |
| **Documentación API** | springdoc-openapi (Swagger) | **FastAPI OpenAPI nativo** (Swagger UI + ReDoc) |
| **Logging** | Log4j2 | **Loguru** (o logging estándar con structlog) |
| **Email** | Spring Mail (SMTP) | **fastapi-mail** (aiosmtplib) |
| **Servidor** | JBoss EAP / WildFly (WAR/EAR) | **Uvicorn + Docker** (contenedores) |
| **Tareas Asíncronas** | @Async (Spring) | **Celery + Redis** (o APScheduler para cron) |
| **Frontend Framework** | Angular | **React 18+ (Vite)** |
| **CSS** | Angular Material | **Tailwind CSS** |
| **Estado/Datos Frontend** | RxJS + HttpClient | **TanStack Query (React Query)** |
| **Pruebas Backend** | JUnit | **pytest + httpx** |
| **Pruebas Frontend** | Karma/Jasmine | **Vitest + React Testing Library** |

### 1.3 Despliegue con Docker

El empaquetado WAR/EAR sobre JBoss se reemplaza por contenedores Docker:

```yaml
# docker-compose.yml (estructura)
services:
  backend:
    build: ./backend
    image: sps-bi-api:latest
    ports:
      - "8000:8000"
    environment:
      - DATABASE_URL=mssql+pyodbc://...
      - JWT_SECRET=...
      - REDIS_URL=redis://redis:6379/0
    depends_on:
      - db
      - redis

  frontend:
    build: ./frontend
    image: sps-bi-web:latest
    ports:
      - "80:80"
    # Nginx sirviendo build estático de React

  db:
    image: mcr.microsoft.com/mssql/server:2022-latest
    ports:
      - "1433:1433"

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

  celery-worker:
    build: ./backend
    command: celery -A app.celery_app worker --loglevel=info
    depends_on:
      - redis
      - db

  celery-beat:
    build: ./backend
    command: celery -A app.celery_app beat --loglevel=info
    depends_on:
      - redis
```

### 1.4 Estructura del Proyecto Backend (FastAPI)

```
backend/
├── app/
│   ├── main.py                    # Punto de entrada FastAPI
│   ├── core/
│   │   ├── config.py              # Settings (Pydantic BaseSettings)
│   │   ├── security.py            # JWT, OIDC, permisos
│   │   ├── database.py            # Engine SQLAlchemy, SessionLocal
│   │   └── exceptions.py          # Excepciones globales + handlers
│   ├── models/                    # Modelos SQLAlchemy (tablas)
│   │   ├── base.py                # BaseModel (UUID pk, soft-delete, timestamps)
│   │   ├── process.py
│   │   ├── process_policy.py
│   │   ├── policy_questionary.py
│   │   ├── user_question.py
│   │   ├── user_answer.py
│   │   ├── attachment.py
│   │   ├── audit_history.py
│   │   ├── user.py
│   │   └── sinister.py
│   ├── schemas/                   # Pydantic schemas (request/response)
│   ├── api/                       # Routers (endpoints)
│   │   ├── v1/
│   │   │   ├── auth.py
│   │   │   ├── users.py
│   │   │   ├── process.py
│   │   │   ├── process_policy.py
│   │   │   ├── policy_questionary.py
│   │   │   ├── user_questions.py
│   │   │   ├── user_answers.py
│   │   │   ├── attachments.py
│   │   │   ├── reports.py
│   │   │   ├── audit.py
│   │   │   ├── email.py
│   │   │   ├── sinisters.py
│   │   │   ├── providers.py
│   │   │   └── settings.py
│   │   └── deps.py                # Dependencias inyectables (get_db, get_current_user)
│   ├── services/                  # Lógica de negocio
│   ├── tasks/                     # Tareas Celery
│   │   ├── celery_app.py
│   │   ├── notifications.py
│   │   └── user_questions.py
│   └── templates/                 # Plantillas Jinja2 para PDFs
│       ├── BBB/
│       ├── CYBER/
│       └── DO/
├── alembic/                       # Migraciones de BD
│   ├── versions/
│   └── env.py
├── tests/
├── alembic.ini
├── Dockerfile
├── pyproject.toml
└── requirements.txt
```

### 1.5 Estructura del Proyecto Frontend (React)

```
frontend/
├── src/
│   ├── main.tsx
│   ├── App.tsx
│   ├── routes/                    # React Router v6
│   ├── components/
│   │   ├── ui/                    # Componentes base (Button, Modal, Table...)
│   │   ├── layout/                # Sidebar, Header, Footer
│   │   └── shared/                # Componentes reutilizables
│   ├── features/
│   │   ├── auth/                  # Login, OIDC callback, logout
│   │   ├── processes/             # CRUD de procesos
│   │   ├── policies/              # Gestión de pólizas
│   │   ├── questionnaires/        # Cuestionarios y respuestas
│   │   ├── sinisters/             # Módulo de siniestros
│   │   ├── reports/               # Generación y descarga de reportes
│   │   ├── users/                 # Gestión de usuarios
│   │   └── audit/                 # Log de auditoría
│   ├── hooks/                     # Custom hooks
│   ├── services/                  # API client (axios/fetch + TanStack Query)
│   ├── stores/                    # Estado global (Zustand o Context)
│   ├── types/                     # TypeScript interfaces
│   └── utils/
├── public/
├── index.html
├── tailwind.config.ts
├── vite.config.ts
├── tsconfig.json
├── Dockerfile
└── package.json
```

---

## 2. Jerarquía de Entidades

Todas las entidades usan **UUID como llave primaria** y **borrado lógico** (`is_deleted: bool`). Los cambios de estado se propagan en cascada descendente a través de toda la jerarquía.

### 2.1 Modelo Base (SQLAlchemy)

```python
# app/models/base.py
import uuid
from datetime import datetime
from sqlalchemy import Column, Boolean, DateTime
from sqlalchemy.dialects.mssql import UNIQUEIDENTIFIER
from sqlalchemy.orm import DeclarativeBase

class Base(DeclarativeBase):
    pass

class BaseModel(Base):
    __abstract__ = True

    id = Column(UNIQUEIDENTIFIER, primary_key=True, default=uuid.uuid4)
    is_deleted = Column(Boolean, default=False, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow, nullable=False)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)
    created_by = Column(UNIQUEIDENTIFIER, nullable=True)
    updated_by = Column(UNIQUEIDENTIFIER, nullable=True)
```

### 2.2 Jerarquía en Cascada — Módulo de Pólizas

```
Process (período de revisión de seguros)
│   Campos: name, subsidiary_id, start_date, end_date, status, percentage
│   Tabla: processes
│
└── ProcessPolicy (enlace Proceso ↔ Plantilla de Póliza)
    │   Campos: process_id (FK), policy_id (FK), status, percentage, start_date, end_date
    │   Tabla: process_policies
    │
    └── PolicyQuestionary (asigna cuestionario a un proceso-póliza)
        │   Campos: process_policy_id (FK), questionary_id (FK), status, start_date, end_date
        │   Tabla: policy_questionnaires
        │
        └── UserQuestion (asigna una pregunta a un usuario específico)
            │   Campos: policy_questionary_id (FK), question_id (FK), user_assigned, status
            │   Tabla: user_questions
            │
            └── UserAnswer (respuesta del usuario a una pregunta)
                │   Campos: user_question_id (FK), answer, answer_type, user_assigned, created_by
                │   Tabla: user_answers
                │
                └── Attachment (archivos adjuntos — soportados en todos los niveles padre)
                    Campos: data_file (VARBINARY), preview (VARBINARY), name, asociaciones a entidades padre
                    Tabla: attachments
```

### 2.3 Entidades — Módulo de Siniestros

```
Sinister (siniestro registrado)
│   Campos: sinister_code (POXXXX / SOXXXX), type (PO / SO), status, description,
│           amount, subsidiary_id, reported_by, provider_id
│   Tabla: sinisters
│
├── SinisterAttachment (archivos adjuntos del siniestro)
│   Campos: sinister_id (FK), attachment_id (FK)
│   Tabla: sinister_attachments
│
├── SinisterComment (comentarios del proveedor o administrador)
│   Campos: sinister_id (FK), user_id (FK), comment, created_at
│   Tabla: sinister_comments
│
└── SinisterStatusHistory (historial de cambios de estado)
    Campos: sinister_id (FK), previous_status, new_status, changed_by, changed_at
    Tabla: sinister_status_history

Provider (proveedor de servicios de siniestros)
│   Campos: name, ruc, email, phone, address, is_active
│   Tabla: providers
│
└── ProviderContact (contactos múltiples del proveedor)
    Campos: provider_id (FK), name, email, phone, role
    Tabla: provider_contacts
```

### 2.4 Diagrama Entidad-Relación (simplificado)

```
┌──────────┐    1:N    ┌────────────────┐    1:N    ┌─────────────────────┐
│ Process  │──────────►│ ProcessPolicy  │──────────►│ PolicyQuestionary   │
│          │           │                │           │                     │
│ UUID pk  │           │ process_id FK  │           │ process_policy_id FK│
│ status   │           │ policy_id FK   │           │ questionary_id FK   │
│ % avance │           │ status         │           │ status              │
└──────────┘           └────────────────┘           └─────────┬───────────┘
                                                              │ 1:N
                                                    ┌─────────▼───────────┐
                                                    │   UserQuestion      │
                                                    │                     │
                                                    │ policy_quest_id FK  │
                                                    │ question_id FK      │
                                                    │ user_assigned       │
                                                    └─────────┬───────────┘
                                                              │ 1:N
                                                    ┌─────────▼───────────┐
                                                    │   UserAnswer        │
                                                    │                     │
                                                    │ user_question_id FK │
                                                    │ answer_type         │
                                                    └─────────────────────┘

┌──────────┐    1:N    ┌─────────────────┐
│ Provider │──────────►│ ProviderContact │
│          │           │                 │
│ UUID pk  │           │ provider_id FK  │
└────┬─────┘           └─────────────────┘
     │ 1:N
┌────▼─────┐
│ Sinister │ ◄── sinister_code: POXXXX / SOXXXX
│          │     status: OPEN → PENDING → SENT → CLOSED → PAID
│ UUID pk  │
└──────────┘
```

---

## 3. Flujos de Negocio

### 3.1 Ciclo de Vida del Proceso (Gestión de Períodos de Seguros)

**Propósito:** Crear y gestionar períodos acotados en el tiempo para la revisión de seguros.

```
                    ┌──────────┐
       crear ───────►  ACTIVE  │◄──────── reopen (ActionService)
                    └────┬─────┘
                         │ todas las pólizas resueltas
                         ▼
                    ┌──────────┐
                    │ RESOLVED │
                    └────┬─────┘
                         │ cierre manual
                         ▼
                    ┌──────────┐
                    │  CLOSED  │
                    └──────────┘
```

**Flujo detallado:**

1. **Crear Proceso** → `POST /api/v1/processes` → `ProcessService.create()`
   - Establece subsidiaria por defecto, normaliza fechas (`start_date` → 00:00:00, `end_date` → 23:59:59)

2. **Asignar Pólizas** → `POST /api/v1/process-policies` (bulk) → `ProcessPolicyService.bulk_create()`
   - Valida existencia de Proceso y Póliza antes de enlazar

3. **Asignar Cuestionarios** → `POST /api/v1/policy-questionnaires/assign` → `PolicyQuestionaryService.assign()`
   - Previene asignación duplicada; crea registros `UserQuestion` **asincrónicamente** vía tarea Celery

4. **Usuarios Responden** → `POST /api/v1/user-answers` o `/api/v1/user-answers/batch`
   - Prevención de duplicados (por `user_question` + `user_assigned`); modo batch omite validación individual

5. **Seguimiento de Avance** → Porcentajes auto-calculados:
   - Process % = (pólizas RESOLVED + CLOSED) / total × 100
   - ProcessPolicy % = (cuestionarios RESOLVED + CLOSED) / total × 100
   - PolicyQuestionary % = preguntas resueltas / total × 100

6. **Cerrar / Reabrir** → `POST /api/v1/process-actions` → `ActionService.create()`
   - **Cascada completa:** el cambio de estado se propaga Process → ProcessPolicy → PolicyQuestionary → UserQuestion → UserAnswer

7. **Borrado lógico** → `DELETE /api/v1/processes/{id}` → cascada de soft-delete a todos los ProcessPolicy hijos

### 3.2 Ciclo de Vida de Siniestros

```
┌──────────┐     asignar        ┌──────────┐     enviar form.     ┌──────────────────┐
│  ABIERTO │────proveedor──────►│ PENDIENTE│────liquidación──────►│ ENVIADO FORM.    │
│  (OPEN)  │                    │(PENDING) │                      │ LIQUIDACIÓN(SENT)│
└──────────┘                    └──────────┘                      └────────┬─────────┘
                                                                           │
                                                                    cierre │
                                                                           ▼
                                                                  ┌──────────┐     pago      ┌────────┐
                                                                  │ CERRADO  │──────────────►│ PAGADO │
                                                                  │ (CLOSED) │               │ (PAID) │
                                                                  └──────────┘               └────────┘
```

**Flujo:**

1. **Registro de Siniestro** → `POST /api/v1/sinisters` → genera código automático (`POXXXX` para Pérdida Operativa, `SOXXXX` para Robo/Daño)
2. **Asignación a Proveedor** → `PUT /api/v1/sinisters/{id}/assign` → notificación por correo al proveedor
3. **Actualización por Proveedor** → `PUT /api/v1/sinisters/{id}/status` → el proveedor actualiza estado y agrega comentarios
4. **Cierre y Pago** → solo Administrador/Super Administrador pueden cerrar y marcar como pagado
5. **Validación de Monto** → el Super Administrador configura el monto umbral para considerar una PO como siniestro

### 3.3 Generación de PDFs (Reportes)

`GET /api/v1/reports/{type}/{policy_questionary_id}` → servicio específico por tipo → `PDFGeneratorService`

```
┌──────────┐     1. Request       ┌──────────────┐     2. Query       ┌───────────┐
│  React   │─────────────────────►│  FastAPI      │───────────────────►│ SQL Server│
│ Frontend │                      │  Router       │                    │           │
└──────────┘                      └──────┬───────┘                    └───────────┘
      ▲                                  │
      │    5. PDF (bytes)                │ 3. Datos cargados
      │    Content-Type:                 ▼
      │    application/pdf        ┌──────────────┐     4. HTML→PDF
      └───────────────────────────│ PDFGenerator  │
                                  │ Service       │
                                  │               │
                                  │ Jinja2 render │
                                  │ WeasyPrint    │
                                  └───────────────┘
```

**Proceso:**

1. El servicio carga las respuestas del cuestionario desde la BD para el `policy_questionary_id`
2. Popula un contexto Jinja2 con los datos
3. Renderiza la plantilla HTML desde `templates/{BBB|CYBER|DO}/`
4. Convierte HTML → PDF vía **WeasyPrint** (reemplazo de openhtmltopdf/Thymeleaf)
5. Retorna PDF como `application/pdf` byte stream

**Tipos de reporte disponibles (11):**

| Sufijo del Endpoint | Plantilla | Tipo de Seguro |
|---|---|---|
| `/appendix/{id}` | `BBB/AppendixForm.html` | BBB |
| `/covid/{id}` | `BBB/CovidForm.html` | BBB |
| `/crime/{id}` | `BBB/CrimeForm.html` | BBB |
| `/pi/{id}` | `BBB/PIForm.html` | BBB |
| `/cyberlock/{id}` | `CYBER/CyberLocktonProposalForm.html` | Cyber |
| `/cyberrisk/{id}` | `CYBER/CyberRiskForm.html` | Cyber |
| `/cyberriskcom/{id}` | `CYBER/CyberRiskComplementaryInfoForm.html` | Cyber |
| `/cyberriskp/{id}` | `CYBER/CyberRiskPersonalDataForm.html` | Cyber |
| `/ransomware/{id}` | `CYBER/RansomwareForm.html` | Cyber |
| `/willis/{id}` | `DO/WillisForm.html` | D&O |
| `/{id}` | `DO/WillisForm.html` | Genérico (default) |

### 3.4 Flujo de Archivos Adjuntos

`POST /api/v1/attachments` (multipart) → `AttachmentService.store()`

```
┌──────────────┐  multipart/form-data   ┌──────────────┐   VARBINARY    ┌───────────┐
│   React      │───────────────────────►│   FastAPI     │──────────────►│ SQL Server│
│  (dropzone)  │                        │  UploadFile   │               │ (binario) │
└──────────────┘                        └──────┬───────┘               └───────────┘
                                               │
                                     ┌─────────▼─────────┐
                                     │ Si es imagen       │
                                     │ (PNG/JPEG/JPG):    │
                                     │ generar thumbnail  │
                                     │ 30×30 con Pillow   │
                                     └───────────────────┘
```

- Sube archivo binario y lo asocia con cualquier combinación de: Process, ProcessPolicy, PolicyQuestionary, UserAnswer, Action, Sinister
- Imágenes (PNG/JPEG/JPG) obtienen un thumbnail de 30×30 almacenado junto al archivo
- Descarga vía `GET /api/v1/attachments/download/{id}` retorna bytes crudos
- **Nota:** Los attachments usan **hard delete** (excepción a la convención de soft-delete del proyecto)

### 3.5 Flujo de Notificaciones por Email

Dos mecanismos:

1. **Manual:** `POST /api/v1/email/send` → `EmailService.send()` — envía a un usuario específico por ID
2. **Programado:** Tareas Celery Beat → `NotificationTaskService` — alertas automatizadas de expiración/vencimiento (ver [Sección 6](#6-tareas-programadas))

---

## 4. Flujo de Autenticación y Autorización

### 4.1 Flujo OIDC (OpenID Connect) + JWT

El sistema utiliza **OpenID Connect (OIDC)** con **Azure AD / Entra ID** como proveedor de identidad (IdP). OIDC opera sobre OAuth 2.0 y proporciona un flujo de autenticación basado en tokens (ID Token + Access Token) en lugar de assertions XML (SAML).

**Flujo Authorization Code con PKCE:**

```
┌──────────┐   1. Click "Iniciar Sesión"   ┌───────────────┐   2. Authorization Request    ┌──────────────┐
│  React   │───────────────────────────────►│   FastAPI      │───(redirect)──────────────────►│  Azure AD    │
│ Frontend │                                │  (authlib)     │   + code_challenge (PKCE)     │  (Entra ID)  │
└──────────┘                                └───────────────┘                                └──────────────┘
     ▲                                             │                                               │
     │                                             │                                               │
     │  7. Bearer JWT en todas                     │   3. Usuario se autentica                     │
     │     las peticiones API                      │      en Azure AD (login UI)                   │
     │                                             │                                               │
     │  6. Redirect a /auth/callback               │   4. Redirect con authorization_code          │
     │     con JWT de la app                       │      a /api/v1/auth/callback                  │
     │                                             ◄──────────────────────────────────────────────┘
     │                                             │
     │                                             │   5. Token Exchange:
     │                                             │      POST /oauth2/v2.0/token
     │                                             │      → code + code_verifier
     │                                             │      ← id_token + access_token + refresh_token
     │                                             │
     └─────────────────────────────────────────────┘
                                                   │
                           POST /api/v1/auth/token ──► Genera JWT interno (HS256, expiración 24h)
                                                       Claims: email, name, roles, sub (user_id)
```

**Diferencias clave respecto a SAML:**

| Aspecto | SAML 2.0 (anterior) | OIDC (actual) |
|---|---|---|
| Formato de token | XML Assertions | JSON Web Tokens (JWT) |
| Protocolo base | SAML 2.0 | OAuth 2.0 + OpenID Connect |
| Flujo | SP-Initiated SSO (POST/Redirect Binding) | Authorization Code Flow + PKCE |
| Metadatos | XML Metadata | `.well-known/openid-configuration` |
| Logout | SLO (Single Logout) XML | OIDC RP-Initiated Logout (redirect) |
| Librería Python | python3-saml / pysaml2 | **authlib** / python-jose |

### 4.2 Cadenas de Seguridad (Middleware FastAPI)

| Middleware | Orden | Rutas | Método de Auth |
|---|---|---|---|
| **OIDC** | 1 | `/api/v1/auth/login`, `/api/v1/auth/callback`, `/api/v1/auth/logout` | OIDC (Azure AD / Entra ID) |
| **JWT** | 2 | `/api/v1/**` | Bearer JWT (HS256) |
| **Público** | — | `/docs`, `/redoc`, `/openapi.json`, `/health` | Sin autenticación |

**Implementación en FastAPI:**

```python
# app/core/security.py
from fastapi import Depends, HTTPException, status
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials
from jose import jwt, JWTError

bearer_scheme = HTTPBearer()

async def get_current_user(
    credentials: HTTPAuthorizationCredentials = Depends(bearer_scheme),
    db: AsyncSession = Depends(get_db)
) -> User:
    token = credentials.credentials
    try:
        payload = jwt.decode(token, settings.JWT_SECRET, algorithms=["HS256"])
        user_id = payload.get("sub")
    except JWTError:
        raise HTTPException(status_code=401, detail="Token inválido")
    user = await db.get(User, user_id)
    if not user or user.is_deleted:
        raise HTTPException(status_code=401, detail="Usuario no encontrado")
    return user

def require_roles(*roles: str):
    """Decorador/dependencia para verificar roles."""
    async def role_checker(current_user: User = Depends(get_current_user)):
        if current_user.role not in roles:
            raise HTTPException(status_code=403, detail="Permisos insuficientes")
        return current_user
    return role_checker
```

**Implementación del flujo OIDC:**

```python
# app/core/oidc.py
from authlib.integrations.starlette_client import OAuth

oauth = OAuth()
oauth.register(
    name="azure",
    client_id=settings.OIDC_CLIENT_ID,
    client_secret=settings.OIDC_CLIENT_SECRET,
    server_metadata_url=f"https://login.microsoftonline.com/{settings.AZURE_AD_TENANT_ID}/v2.0/.well-known/openid-configuration",
    client_kwargs={
        "scope": "openid email profile",
        "code_challenge_method": "S256",  # PKCE
    },
)

# app/api/v1/auth.py
from app.core.oidc import oauth

@router.get("/login")
async def login(request: Request):
    """Inicia el flujo OIDC redirigiendo al IdP de Azure AD."""
    redirect_uri = settings.OIDC_REDIRECT_URI  # /api/v1/auth/callback
    return await oauth.azure.authorize_redirect(request, redirect_uri)

@router.get("/callback")
async def auth_callback(request: Request, db: AsyncSession = Depends(get_db)):
    """Callback OIDC: intercambia code por tokens, crea/actualiza usuario, emite JWT interno."""
    token = await oauth.azure.authorize_access_token(request)
    id_token = token.get("id_token")
    userinfo = token.get("userinfo") or await oauth.azure.userinfo(token=token)

    # Extraer claims del ID Token
    email = userinfo["email"]
    name = userinfo.get("name", "")
    roles = userinfo.get("roles", [])  # App roles configurados en Azure AD

    # Upsert usuario en BD
    user = await user_service.verify_and_save(db, email=email, name=name, roles=roles)

    # Generar JWT interno de la aplicación
    app_jwt = create_access_token(data={
        "sub": str(user.id),
        "email": user.email,
        "name": name,
        "roles": [user.role],
    })

    # Redirigir al frontend con el JWT
    return RedirectResponse(
        url=f"{settings.FRONTEND_URL}/auth/callback?token={app_jwt}"
    )

@router.post("/logout")
async def logout(request: Request):
    """Inicia OIDC RP-Initiated Logout."""
    logout_url = (
        f"https://login.microsoftonline.com/{settings.AZURE_AD_TENANT_ID}"
        f"/oauth2/v2.0/logout"
        f"?post_logout_redirect_uri={settings.OIDC_POST_LOGOUT_REDIRECT_URI}"
    )
    return {"logout_url": logout_url}
```

### 4.3 Reglas de Autorización Basadas en Rutas

| Patrón de Ruta | Roles Permitidos |
|---|---|
| `/docs`, `/redoc`, `/openapi.json`, `/health` | Público (sin auth) |
| `/api/v1/auth/**` | Público (sin auth) |
| `/api/v1/users/**` | SuperAdmin, Admin |
| `/api/v1/settings/sync-azure-ad-users` | SuperAdmin, Admin |
| `/api/v1/register-user/**` | RegisterUser |
| `/api/v1/sinisters/**` (lectura) | SuperAdmin, Admin, RegisterUser, ProviderUser |
| `/api/v1/sinisters/**` (escritura) | SuperAdmin, Admin, SinisterRegisterUser |
| Resto de `/api/v1/**` | SuperAdmin, Admin, RegisterUser |

### 4.4 Flujo de Logout OIDC (RP-Initiated)

A diferencia del SLO de SAML (basado en XML), OIDC utiliza un simple redirect al endpoint de logout del IdP:

```
Frontend                    Backend                     Azure AD / Entra ID
   │                          │                            │
   │─ POST /api/v1/auth/logout ►│                         │
   │                          │── retorna {logout_url} ──► │
   │◄── {logout_url} ─────────┘                           │
   │                                                       │
   │─── redirect a logout_url ───────────────────────────►│
   │    (https://login.microsoftonline.com/.../logout)    │
   │                                                       │
   │    Azure AD cierra sesión del IdP                    │
   │                                                       │
   │◄── redirect a post_logout_redirect_uri ──────────────┘
   │    (frontend /login)                                 │
   │                                                       │
   │── limpia JWT local (localStorage) ──►                 │
```

**Ventajas del logout OIDC vs SAML SLO:**
- No requiere intercambio de XML LogoutRequest/LogoutResponse
- No requiere binding DEFLATE + Base64
- Simple redirect con `post_logout_redirect_uri`
- Soporta `id_token_hint` para logout silencioso

### 4.5 Sincronización de Usuarios desde Azure AD (Microsoft Graph API)

`POST /api/v1/settings/sync-azure-ad-users` → `AzureADSyncService.synchronize()`

1. Adquiere token OAuth2 vía **client credentials flow** (scope `https://graph.microsoft.com/.default`) — independiente del flujo OIDC de usuario
2. Lee asignaciones de roles de la app → grupos → miembros de grupo vía Microsoft Graph API
3. Mapea roles de Azure AD → roles de la plataforma:
   - `SUPERADMIN` → Super Administrador
   - `ADMIN` → Administrador
   - `USER` → Usuario de Registro
4. Para cada usuario: upsert vía `UserService.verify_and_save()` (crear si nuevo, actualizar si cambió el nombre, omitir si sin cambios)

> **Nota:** La sincronización usa el mismo `AZURE_AD_CLIENT_ID` y `AZURE_AD_CLIENT_SECRET` que el flujo OIDC, pero con grant_type `client_credentials` en lugar de `authorization_code`.

### 4.6 Cifrado de Datos de Usuario

| Campo | Algoritmo |
|---|---|
| `user_name` | AES-256 (PBKDF2WithHmacSHA256) |
| `user_last_name` | AES-256 (PBKDF2WithHmacSHA256) |

- **Fuente de clave:** variable de entorno `ENCRYPTION_KEY` (passphrase base64)
- **Salt:** `"PlataformaBI_Salt"`, 65536 iteraciones
- Cifrado transparente al escribir, descifrado al leer en `UserService`
- Implementado con `cryptography` (Fernet o AES-CBC)

---

## 5. Catálogo de Endpoints de la API

> Todos los endpoints están bajo el prefijo `/api/v1/`. La documentación interactiva se genera automáticamente en `/docs` (Swagger UI) y `/redoc`.

### 5.1 Autenticación y Seguridad (12 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/auth/login` | Inicia flujo OIDC, redirige al IdP de Azure AD / Entra ID |
| `GET` | `/auth/callback` | Callback OIDC: intercambia authorization_code por tokens, emite JWT interno |
| `POST` | `/auth/token` | Generar JWT interno desde sesión OIDC autenticada |
| `POST` | `/auth/refresh` | Refrescar JWT interno usando refresh_token de OIDC |
| `GET` | `/auth/validate` | Validar token JWT actual |
| `GET` | `/auth/me` | Obtener info del usuario actual (email, nombre, roles) |
| `GET` | `/auth/status` | Estado de autenticación |
| `POST` | `/auth/logout` | Iniciar logout, retorna URL de logout OIDC (RP-Initiated) |
| `GET` | `/auth/test` | Probar autenticación JWT (dev) |
| `GET` | `/auth/admin-test` | Probar acceso SuperAdmin (dev) |
| `GET` | `/auth/manager-test` | Probar acceso Admin/SuperAdmin (dev) |
| `GET` | `/auth/profile` | Perfil completo desde claims JWT (dev) |

### 5.2 Configuración y Usuarios (5 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/settings/synchronize-users` | Sincronizar usuarios desde lista del frontend |
| `GET` | `/settings/color` | Obtener paletas de color activas (`?subsidiary_id=` opcional) |
| `POST` | `/settings/sync-azure-ad-users` | Sincronizar usuarios desde Azure AD Graph API |
| `GET` | `/users` | Obtener todos los usuarios activos (retorna `{users, total}`) |
| `GET` | `/users/{open_user_id}` | Obtener usuario por OpenID |

### 5.3 Gestión de Procesos (6 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/processes` | Crear nuevo proceso |
| `PUT` | `/processes/{process_id}` | Actualizar proceso |
| `GET` | `/processes/filter` | Filtrar procesos (`?subsidiary, character, page, size, date_begin, date_end, year`) |
| `DELETE` | `/processes/{process_id}` | Soft-delete proceso (cascada a hijos) |
| `GET` | `/processes` | Obtener todos los procesos activos |
| `GET` | `/processes/{process_id}/validate-closure` | Validar si el proceso puede cerrarse |

### 5.4 Acciones de Proceso (3 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/process-actions` | Crear acción (REOPEN/CLOSED — cascada de estado) |
| `GET` | `/process-actions/process/{process_id}` | Obtener acciones de un proceso |
| `GET` | `/process-actions/{action_id}` | Obtener acción específica |

### 5.5 Asignación Proceso-Póliza (5 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/process-policies` | Asignación masiva de pólizas a un proceso |
| `GET` | `/process-policies/filter` | Filtrar asignaciones con paginación |
| `DELETE` | `/process-policies/{id}` | Soft-delete asignación |
| `PUT` | `/process-policies` | Actualización masiva |
| `PUT` | `/process-policies/{id}` | Actualizar una asignación |

### 5.6 Asignación de Cuestionarios (11 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/policy-questionnaires/assign-single` | Asignar un cuestionario a un proceso-póliza |
| `POST` | `/policy-questionnaires/assign` | Asignación masiva (éxito parcial permitido) |
| `GET` | `/policy-questionnaires/by-process-policy/{id}` | Cuestionarios de un proceso-póliza |
| `GET` | `/policy-questionnaires/detailed/by-process-policy/{id}` | Info detallada del cuestionario |
| `DELETE` | `/policy-questionnaires/{id}` | Remover asignación de cuestionario |
| `PUT` | `/policy-questionnaires/{id}/status` | Actualizar estado del cuestionario |
| `PUT` | `/policy-questionnaires/update` | Actualización completa de asignación |
| `GET` | `/policy-questionnaires/{id}/user-questions/stats` | Estadísticas de UserQuestion |
| `DELETE` | `/policy-questionnaires/soft-delete/{id}` | Soft-delete alternativo |
| `GET` | `/policy-questionnaires/filter` | Filtrar con paginación |
| `GET` | `/policy-questionnaires/user/{user_uuid}` | Asignaciones por usuario |

### 5.7 Preguntas de Usuario (6 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `PUT` | `/user-questions/assignment` | Actualizar usuario asignado a una pregunta |
| `PUT` | `/user-questions/status` | Actualizar estado de la pregunta |
| `GET` | `/user-questions/{id}` | Obtener por ID |
| `GET` | `/user-questions/policy-questionary/{id}` | Todas las de un policy-questionary |
| `DELETE` | `/user-questions/{id}` | Soft-delete |
| `GET` | `/user-questions/filter` | Filtrar/paginar |

### 5.8 Respuestas de Usuario (8 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/user-answers` | Enviar una respuesta |
| `POST` | `/user-answers/batch` | Enviar múltiples respuestas |
| `PUT` | `/user-answers` | Actualizar una respuesta |
| `GET` | `/user-answers/{id}` | Obtener por ID |
| `GET` | `/user-answers/user-question/{id}` | Respuestas de una user-question |
| `GET` | `/user-answers/policy-questionary/{id}` | Respuestas de un policy-questionary |
| `GET` | `/user-answers/user/{user_assigned}` | Respuestas por usuario |
| `DELETE` | `/user-answers/{id}` | Soft-delete |

### 5.9 Archivos Adjuntos (11 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/attachments` | Subir archivo (multipart, con asociaciones padre opcionales) |
| `GET` | `/attachments/process/{id}` | Archivos por proceso |
| `GET` | `/attachments/process-policy/{id}` | Archivos por proceso-póliza |
| `GET` | `/attachments/policy-questionary/{id}` | Archivos por policy-questionary |
| `GET` | `/attachments/questionary-and-answer/{pq_id}/{answer_id}` | Archivos por cuestionario + respuesta |
| `GET` | `/attachments/user-answer/{id}` | Archivos por respuesta de usuario |
| `GET` | `/attachments/process-action/{id}` | Archivos por acción |
| `GET` | `/attachments/question-images` | Previsualizaciones de imágenes |
| `GET` | `/attachments/download/{id}` | Descargar archivo |
| `DELETE` | `/attachments/{id}` | **Hard delete** de archivo |

### 5.10 Datos de Catálogo / Plantillas (7 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/policies` | Obtener todos los tipos de póliza |
| `GET` | `/policies/unassigned` | Pólizas aún no asignadas |
| `GET` | `/questionnaires/available` | Cuestionarios disponibles para una póliza |
| `GET` | `/questionnaires/by-policy/{id}` | Cuestionarios de una póliza |
| `GET` | `/questionnaires` | Todos los cuestionarios activos |
| `GET` | `/question-options/question/{id}` | Opciones de una pregunta |
| `POST` | `/question-options/questions/batch` | Opciones para múltiples preguntas |

### 5.11 Auditoría (6 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/audit/entity/{entity_name}` | Historial por tipo de entidad |
| `GET` | `/audit/entity-id/{entity_id}` | Historial por ID de entidad |
| `GET` | `/audit/action/{action}` | Historial por acción (CREATE/UPDATE/DELETE) |
| `GET` | `/audit/date-range` | Historial dentro de rango de fechas |
| `GET` | `/audit/entity/{name}/id/{id}` | Historial por nombre de entidad + ID |
| `POST` | `/audit/log/{action}` | Entrada manual de auditoría |

### 5.12 Reportes — Generación de PDF (11 endpoints)

| Método | Ruta | Plantilla |
|---|---|---|
| `GET` | `/reports/appendix/{id}` | BBB/AppendixForm |
| `GET` | `/reports/covid/{id}` | BBB/CovidForm |
| `GET` | `/reports/crime/{id}` | BBB/CrimeForm |
| `GET` | `/reports/pi/{id}` | BBB/PIForm |
| `GET` | `/reports/cyberlock/{id}` | CYBER/CyberLocktonProposal |
| `GET` | `/reports/cyberrisk/{id}` | CYBER/CyberRisk |
| `GET` | `/reports/cyberriskcom/{id}` | CYBER/CyberRiskComplementary |
| `GET` | `/reports/cyberriskp/{id}` | CYBER/CyberRiskPersonalData |
| `GET` | `/reports/ransomware/{id}` | CYBER/Ransomware |
| `GET` | `/reports/willis/{id}` | DO/Willis |
| `GET` | `/reports/{id}` | DO/Willis (default) |

### 5.13 Email (2 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/email/test` | Enviar email de prueba |
| `POST` | `/email/send` | Enviar email a usuario por ID |

### 5.14 Siniestros (10 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/sinisters` | Crear siniestro (genera código POXXXX/SOXXXX automático) |
| `GET` | `/sinisters` | Listar siniestros con filtros y paginación |
| `GET` | `/sinisters/{id}` | Detalle de un siniestro |
| `PUT` | `/sinisters/{id}` | Actualizar siniestro |
| `PUT` | `/sinisters/{id}/assign` | Asignar proveedor al siniestro |
| `PUT` | `/sinisters/{id}/status` | Cambiar estado del siniestro |
| `POST` | `/sinisters/{id}/comments` | Agregar comentario |
| `GET` | `/sinisters/{id}/comments` | Obtener comentarios |
| `GET` | `/sinisters/reports/filter` | Reporte filtrado (año, mes, tipo, proveedor) |
| `GET` | `/sinisters/reports/export` | Exportar reporte (PDF/Excel/CSV) |

### 5.15 Proveedores (6 endpoints)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/providers` | Crear proveedor |
| `GET` | `/providers` | Listar proveedores activos |
| `GET` | `/providers/{id}` | Detalle de proveedor con contactos |
| `PUT` | `/providers/{id}` | Actualizar proveedor |
| `DELETE` | `/providers/{id}` | Soft-delete proveedor |
| `POST` | `/providers/{id}/contacts` | Agregar contacto al proveedor |

### Resumen

| Dominio | Endpoints |
|---|---|
| Autenticación y Seguridad | 12 |
| Configuración y Usuarios | 5 |
| Gestión de Procesos | 6 |
| Acciones de Proceso | 3 |
| Proceso-Póliza | 5 |
| Cuestionarios de Póliza | 11 |
| Preguntas de Usuario | 6 |
| Respuestas de Usuario | 8 |
| Archivos Adjuntos | 11 |
| Catálogo / Plantillas | 7 |
| Auditoría | 6 |
| Reportes (PDF) | 11 |
| Email | 2 |
| Siniestros | 10 |
| Proveedores | 6 |
| **Total (nueva arquitectura)** | **~109** |

---

## 6. Tareas Programadas

### 6.1 Infraestructura

Las tareas programadas se implementan con **Celery Beat** (reemplazando `@Scheduled` de Spring). Redis actúa como broker de mensajes.

```python
# app/tasks/celery_app.py
from celery import Celery
from celery.schedules import crontab

celery_app = Celery("sps_bi", broker="redis://redis:6379/0")

celery_app.conf.beat_schedule = {
    "process-expiration-warning": {
        "task": "app.tasks.notifications.process_expiration_warning",
        "schedule": crontab(hour=8, minute=0),       # 8:00 AM diario
    },
    "expired-process-notification": {
        "task": "app.tasks.notifications.expired_process_notification",
        "schedule": crontab(hour=9, minute=0),       # 9:00 AM diario
    },
    "policy-expiration-warning": {
        "task": "app.tasks.notifications.policy_expiration_warning",
        "schedule": crontab(hour=10, minute=0),      # 10:00 AM diario
    },
    "overdue-policy-notification": {
        "task": "app.tasks.notifications.overdue_policy_notification",
        "schedule": crontab(hour=11, minute=0),      # 11:00 AM diario
    },
}
```

### 6.2 Detalle de Tareas

| Tarea | Cron Default | Lógica |
|---|---|---|
| **Alerta de Expiración de Proceso** | `8:00 AM` diario | Email cuando procesos activos están a **7, 3 o 1** día(s) de su `end_date` |
| **Notificación de Proceso Expirado** | `9:00 AM` diario | Email para procesos expirados hace **1–5 días** (recordatorios diarios) |
| **Alerta de Expiración de Póliza** | `10:00 AM` diario | Email cuando ProcessPolicies están a **90, 60, 30, 7 o 1** día(s) de su `end_date` |
| **Notificación de Póliza Vencida** | `11:00 AM` diario | Email para ProcessPolicies pasadas de `end_date` aún en estado OPEN |

**Configuración:**

- Cada tarea es activable/desactivable individualmente vía variables de entorno
- Toggle global: `SCHEDULER_GLOBAL_ENABLED`
- Destinatarios: lista separada por comas en `NOTIFICATION_EMAILS`
- Idioma: Cuerpos de email en español, graduados por severidad según días restantes

---

## 7. Aspectos Transversales

### 7.1 Auditoría (Audit Trail)

**Automática** — implementada con middleware FastAPI (reemplazando AOP de Spring):

```python
# app/core/audit_middleware.py
from starlette.middleware.base import BaseHTTPMiddleware

class AuditMiddleware(BaseHTTPMiddleware):
    """Intercepta respuestas exitosas de mutación y registra en audit_history."""

    AUDITABLE_METHODS = {"POST", "PUT", "PATCH", "DELETE"}

    async def dispatch(self, request, call_next):
        response = await call_next(request)
        if (
            request.method in self.AUDITABLE_METHODS
            and 200 <= response.status_code < 300
        ):
            # Registra asincrónicamente para no bloquear el hilo principal
            background_tasks.add_task(
                audit_service.log,
                entity_name=extract_entity(request.url.path),
                action=map_method_to_action(request.method),
                request_data=await get_request_body(request),
                user_id=request.state.user.id if hasattr(request.state, "user") else None,
            )
        return response
```

**Alternativa con decorador por servicio:**

```python
# app/services/audit.py
def auditable(func):
    """Decorador que registra automáticamente operaciones CREATE/UPDATE/DELETE."""
    @wraps(func)
    async def wrapper(*args, **kwargs):
        result = await func(*args, **kwargs)
        action = "CREATE" if "create" in func.__name__ else \
                 "UPDATE" if "update" in func.__name__ else "DELETE"
        await audit_service.log_async(
            entity_name=func.__qualname__.split(".")[0].replace("Service", ""),
            entity_id=getattr(result, "id", None),
            action=action,
            request_data=str(kwargs),
        )
        return result
    return wrapper
```

- **Almacenamiento:** tabla `audit_history` (entity_name, entity_id, action, request_data JSON, timestamp, user_id)
- **Asíncrono:** las escrituras de auditoría se ejecutan en background para no bloquear transacciones principales
- **Manual:** `POST /api/v1/audit/log/{action}` — para entradas explícitas desde el frontend

### 7.2 Manejo Global de Excepciones

Respuestas de error estandarizadas para que el frontend de React las procese fácilmente:

```python
# app/core/exceptions.py
from fastapi import Request
from fastapi.responses import JSONResponse

class AppException(Exception):
    def __init__(self, status_code: int, message: str, detail: str = None):
        self.status_code = status_code
        self.message = message
        self.detail = detail

async def app_exception_handler(request: Request, exc: AppException):
    return JSONResponse(
        status_code=exc.status_code,
        content={
            "status": exc.status_code,
            "message": exc.message,
            "detail": exc.detail,
            "path": str(request.url),
            "timestamp": datetime.utcnow().isoformat(),
        }
    )

async def generic_exception_handler(request: Request, exc: Exception):
    return JSONResponse(
        status_code=500,
        content={
            "status": 500,
            "message": "Error interno del servidor",
            "detail": str(exc) if settings.DEBUG else None,
            "path": str(request.url),
            "timestamp": datetime.utcnow().isoformat(),
        }
    )

# Registro en main.py
app.add_exception_handler(AppException, app_exception_handler)
app.add_exception_handler(Exception, generic_exception_handler)
```

**Formato estándar de error:**

```json
{
  "status": 404,
  "message": "Proceso no encontrado",
  "detail": "No existe un proceso con ID a1b2c3d4-...",
  "path": "/api/v1/processes/a1b2c3d4-...",
  "timestamp": "2024-06-05T14:30:00Z"
}
```

| Código HTTP | Uso |
|---|---|
| `400` | Datos de entrada inválidos (validación Pydantic) |
| `401` | Token JWT ausente, expirado o inválido |
| `403` | Rol insuficiente para la operación |
| `404` | Entidad no encontrada o eliminada (soft-delete) |
| `409` | Conflicto (duplicado, estado inconsistente) |
| `422` | Error de validación de FastAPI (automático) |
| `500` | Error interno no controlado |

### 7.3 Procesamiento Asíncrono

| Pool / Worker | Configuración | Uso |
|---|---|---|
| **Celery Workers** | concurrency=4, prefetch=2 | Tareas programadas (notificaciones), creación masiva de UserQuestions |
| **FastAPI BackgroundTasks** | Nativo de Starlette | Escrituras de auditoría, envío de emails individuales, generación de thumbnails |
| **Uvicorn Workers** | workers=4 (producción) | Procesamiento de requests HTTP concurrentes |

### 7.4 CORS

```python
# app/main.py
from fastapi.middleware.cors import CORSMiddleware

app.add_middleware(
    CORSMiddleware,
    allow_origins=settings.CORS_ALLOWED_ORIGINS,  # ["http://localhost:5173", ...]
    allow_credentials=True,
    allow_methods=["GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"],
    allow_headers=["*"],
)
```

### 7.5 Documentación de API

- **Swagger UI** en `/docs` — interactivo, con autenticación JWT Bearer integrada
- **ReDoc** en `/redoc` — documentación de solo lectura, más legible
- **OpenAPI 3.1 spec** en `/openapi.json`
- Generación automática de schemas desde modelos Pydantic

### 7.6 Validación Inteligente (Datos Históricos)

- Al ingresar datos en cuestionarios, el backend compara automáticamente contra las respuestas del período anterior
- Si existe una discrepancia superior al umbral configurado, el endpoint retorna un campo `warnings[]` con las alertas visuales
- El frontend resalta en color los campos con discrepancia
- Al alcanzar 100% de completitud, el sistema cierra la edición al usuario automáticamente

---

## 8. Resumen de Configuración

### 8.1 Variables de Entorno

| Variable | Propósito |
|---|---|
| `DATABASE_URL` | Conexión SQL Server (`mssql+pyodbc://user:pass@host/bipolizas?driver=ODBC+Driver+18+for+SQL+Server`) |
| `JWT_SECRET` | Clave de firma HS256 para tokens JWT |
| `JWT_EXPIRATION_HOURS` | Horas de validez del token (default: 24) |
| `ENCRYPTION_KEY` | Clave AES para cifrado de PII de usuarios |
| `CORS_ALLOWED_ORIGINS` | Orígenes CORS separados por coma |
| `OIDC_CLIENT_ID` | Client ID de la app registrada en Azure AD / Entra ID |
| `OIDC_CLIENT_SECRET` | Client Secret de la app registrada en Azure AD / Entra ID |
| `OIDC_REDIRECT_URI` | URL de callback OIDC (default: `http://localhost:8000/api/v1/auth/callback`) |
| `OIDC_POST_LOGOUT_REDIRECT_URI` | URL de redirección post-logout (default: `http://localhost:5173/login`) |
| `AZURE_AD_TENANT_ID` | Tenant ID de Azure AD |
| `AZURE_AD_CLIENT_ID` | Client ID para Graph API |
| `AZURE_AD_CLIENT_SECRET` | Client Secret para Graph API |
| `FRONTEND_URL` | URL base del frontend (default: `http://localhost:5173`) |
| `SMTP_HOST` | Servidor SMTP |
| `SMTP_PORT` | Puerto SMTP (default: 587) |
| `SMTP_USER` | Usuario SMTP |
| `SMTP_PASSWORD` | Contraseña SMTP |
| `EMAIL_SENDING_ENABLED` | Toggle de funcionalidad de email |
| `NOTIFICATION_EMAILS` | Destinatarios de notificaciones automáticas |
| `SCHEDULER_GLOBAL_ENABLED` | Toggle master de tareas programadas |
| `REDIS_URL` | URL del broker Redis para Celery |
| `DEBUG` | Modo debug (default: false) |
| `SINISTER_AMOUNT_THRESHOLD` | Monto umbral para considerar una PO como siniestro |

### 8.2 Módulos de Configuración (Python)

| Módulo | Propósito |
|---|---|
| `app/core/config.py` | `Settings(BaseSettings)` — carga todas las variables de entorno con validación Pydantic |
| `app/core/database.py` | Engine SQLAlchemy async, `SessionLocal`, dependency `get_db()` |
| `app/core/security.py` | JWT encode/decode, dependencias de auth, verificación de roles |
| `app/core/oidc.py` | Configuración OIDC (authlib OAuth client), handlers de login/callback/logout |
| `app/core/exceptions.py` | Exception handlers globales |
| `app/core/audit_middleware.py` | Middleware de auditoría automática |
| `app/tasks/celery_app.py` | Configuración Celery + Beat schedule |
| `alembic/env.py` | Configuración de migraciones Alembic |

---

## 9. Enums y Modelo de Estados

### 9.1 Ciclo de Vida de Estado de Entidades (`AppStatus`)

```
ACTIVE  ──►  RESOLVED  ──►  CLOSED
   ▲                            │
   └────────── REOPEN ──────────┘
                            REJECTED (excepcional)
```

### 9.2 Estados de Siniestros (`SinisterStatus`)

```
OPEN  ──►  PENDING  ──►  SENT  ──►  CLOSED  ──►  PAID
```

### 9.3 Tabla de Enums

| Enum | Valores | Usado por |
|---|---|---|
| `AppStatus` | `ACTIVE`, `RESOLVED`, `CLOSED`, `REJECTED` | Process, ProcessPolicy, PolicyQuestionary, UserQuestion, UserAnswer |
| `PolicyStatus` | `ACTIVE`, `RESOLVED`, `CLOSED`, `REJECTED`, `EXPIRED` | Extendido para flujos específicos de póliza |
| `SinisterStatus` | `OPEN`, `PENDING`, `SENT`, `CLOSED`, `PAID` | Sinister |
| `SinisterType` | `PO` (Pérdida Operativa), `SO` (Robo/Daño a activos) | Sinister |
| `ActionTypes` | `REOPEN`, `CLOSED` | Registros de acciones de proceso |
| `AnswerTypes` | `NORMAL`, `DES`, `SUB`, `OPTIONAL`, `SELECTOR`, `OPTIONALNORMAL`, `MULTIPLE`, `SUBOPTIONAL`, `SELECTOREXCEL`, `SELECTORNORMAL`, `TABLE` | Clasificación del formato de respuesta |
| `AuditAction` | `CREATE`, `UPDATE`, `DELETE` | Tipos de acción de auditoría |
| `UserRole` | `SUPER_ADMIN`, `ADMIN`, `REGISTER_USER`, `VIEW_USER`, `SINISTER_REGISTER_USER`, `PROVIDER_USER` | Roles de seguridad (mapeados desde Azure AD) |

### 9.4 Implementación en Python

```python
# app/models/enums.py
from enum import Enum

class AppStatus(str, Enum):
    ACTIVE = "ACTIVE"
    RESOLVED = "RESOLVED"
    CLOSED = "CLOSED"
    REJECTED = "REJECTED"

class SinisterStatus(str, Enum):
    OPEN = "OPEN"
    PENDING = "PENDING"
    SENT = "SENT"
    CLOSED = "CLOSED"
    PAID = "PAID"

class SinisterType(str, Enum):
    PO = "PO"  # Pérdida Operativa
    SO = "SO"  # Robo/Daño a activos

class ActionTypes(str, Enum):
    REOPEN = "REOPEN"
    CLOSED = "CLOSED"

class AuditAction(str, Enum):
    CREATE = "CREATE"
    UPDATE = "UPDATE"
    DELETE = "DELETE"

class UserRole(str, Enum):
    SUPER_ADMIN = "SUPER_ADMIN"
    ADMIN = "ADMIN"
    REGISTER_USER = "REGISTER_USER"
    VIEW_USER = "VIEW_USER"
    SINISTER_REGISTER_USER = "SINISTER_REGISTER_USER"
    PROVIDER_USER = "PROVIDER_USER"
```

---

## Apéndice: Migración desde Stack Legacy

| Concepto Java/Spring | Equivalente Python/FastAPI |
|---|---|
| `@RestController` | `APIRouter` de FastAPI |
| `@Service` | Clase de servicio inyectada como dependencia |
| `@Repository` (Spring Data JPA) | SQLAlchemy `AsyncSession` con queries directas |
| `@Entity` + `@Table` | Modelo SQLAlchemy (`Base` declarativa) |
| `@Aspect` + `@AfterReturning` (AOP) | Middleware Starlette o decorador Python |
| `@Async` + `TaskExecutor` | `BackgroundTasks` de FastAPI o tarea Celery |
| `@Scheduled` (cron) | Celery Beat schedule |
| `MapStruct` DTO mapping | Schemas Pydantic con `model_validate()` |
| `Lombok` (`@Data`, `@Builder`) | Dataclasses Python o Pydantic `BaseModel` |
| `application.properties` | Pydantic `BaseSettings` + `.env` file |
| `GlobalExceptionHandler` (`@RestControllerAdvice`) | `app.add_exception_handler()` |
| `SpringSecurityConfig` (filter chains) | Middleware FastAPI + `Depends()` |
| SAML 2.0 (python3-saml / pysaml2) | **OIDC** (authlib + python-jose) — Authorization Code Flow + PKCE |
| `Thymeleaf` + `openhtmltopdf` | `Jinja2` + `WeasyPrint` |
| `WAR/EAR` en JBoss EAP | Contenedor Docker con Uvicorn |
| `Log4j2` | `loguru` o `structlog` |
| `Spring Mail` | `fastapi-mail` (aiosmtplib) |
| Hibernate migrations (auto-ddl) | Alembic (migraciones explícitas y versionadas) |
