# Sprint 0 — Especificación Técnica

> **Proyecto:** SPS-BI — Sistema de Gestión de Pólizas y Siniestros
> **Sprint:** 0 — Infraestructura y Configuración Base
> **Duración:** 2 semanas
> **Módulo:** DevOps / Arquitectura

---

## 1. Objetivo

Establecer el entorno de desarrollo completo, la estructura del proyecto, la configuración de infraestructura (Docker, CI/CD), la base de datos inicial y los cimientos arquitectónicos sobre los que se construirán los sprints posteriores.

---

## 2. Alcance

### 2.1 Incluido

- Estructura de monorepo (`backend/`, `frontend/`)
- Orquestación Docker con 6 servicios
- Scaffolding del backend (FastAPI) con configuración centralizada
- Scaffolding del frontend (React + Vite + TypeScript + Tailwind)
- Sistema de migraciones de base de datos (Alembic)
- Modelo base abstracto con patrones enterprise (UUID, soft-delete, auditoría)
- Pipeline CI/CD (lint, test, build)
- Manejo global de excepciones
- Health check endpoint
- CORS middleware

### 2.2 Excluido

- Autenticación y autorización (Sprint 1)
- Modelos de dominio: pólizas, siniestros, usuarios (Sprints 1-2)
- Endpoints de negocio
- Páginas del frontend más allá de la landing page

---

## 3. Arquitectura del Sistema

### 3.1 Diagrama de Servicios

```
┌─────────────┐     ┌─────────────┐     ┌──────────────────┐
│  Frontend    │────▶│  Backend    │────▶│  SQL Server 2022 │
│  React/Vite  │     │  FastAPI    │     │  (db)            │
│  :5173       │     │  :8000      │     │  :1433           │
└─────────────┘     └──────┬──────┘     └──────────────────┘
                           │
                    ┌──────┴──────┐
                    │             │
              ┌─────▼─────┐ ┌────▼──────┐
              │  Celery    │ │  Celery   │
              │  Worker    │ │  Beat     │
              └─────┬─────┘ └────┬──────┘
                    │            │
                    └──────┬─────┘
                     ┌─────▼─────┐
                     │  Redis 7  │
                     │  :6379    │
                     └───────────┘
```

### 3.2 Stack Tecnológico

| Capa | Tecnología | Versión |
|------|-----------|---------|
| Runtime backend | Python | 3.11+ |
| Framework API | FastAPI | 0.115.6 |
| Servidor ASGI | Uvicorn | 0.34.0 |
| ORM | SQLAlchemy (async) | 2.0.36 |
| Migraciones | Alembic | 1.14.1 |
| Base de datos | SQL Server | 2022 Developer |
| Driver ODBC | pyodbc + aioodbc | 5.2.0 / 0.5.0 |
| Cola de tareas | Celery | 5.4.0 |
| Message broker | Redis | 7 Alpine |
| Validación | Pydantic v2 | 2.10.4 |
| Runtime frontend | Node.js | 20 LTS |
| Framework UI | React | 18.3.1 |
| Lenguaje frontend | TypeScript | 5.6.2 |
| Build tool | Vite | 6.0.0 |
| CSS | Tailwind CSS | 3.4.16 |
| Routing | react-router-dom | 6.28.0 |
| Estado servidor | TanStack React Query | 5.62.0 |
| HTTP client | axios | 1.7.9 |
| Contenedores | Docker + Docker Compose | — |

---

## 4. Especificación por Tarea

### 4.1 Tarea 0.1 — Repositorio Git y Estructura de Monorepo

**Descripción:** Crear el repositorio con estructura estándar de monorepo.

**Estructura generada:**

```
apppolizas/
├── .docs/                  # Documentación del proyecto
├── .github/workflows/      # CI/CD pipelines
├── .gitignore              # Reglas de exclusión Git
├── .env.example            # Variables de entorno documentadas
├── docker-compose.yml      # Orquestación de servicios
├── backend/                # Código del servidor API
│   ├── Dockerfile
│   ├── requirements.txt
│   ├── pyproject.toml
│   ├── alembic.ini
│   ├── alembic/
│   ├── app/
│   └── tests/
└── frontend/               # Código de la interfaz web
    ├── Dockerfile
    ├── package.json
    ├── tsconfig.json
    ├── vite.config.ts
    ├── tailwind.config.ts
    └── src/
```

**Criterio de aceptación:** El repositorio clonado contiene ambas carpetas con sus respectivos archivos de configuración.

---

### 4.2 Tarea 0.2 — Docker Compose

**Descripción:** Configurar `docker-compose.yml` con 6 servicios interconectados.

**Servicios:**

| Servicio | Imagen | Puerto | Health Check | Dependencias |
|----------|--------|--------|-------------|-------------|
| `backend` | `sps-bi-api:latest` (build `./backend`) | 8000 | — | db (healthy), redis (healthy) |
| `frontend` | `sps-bi-web:latest` (build `./frontend`) | 5173 | — | backend |
| `db` | `mcr.microsoft.com/mssql/server:2022-latest` | 1433 | `sqlcmd SELECT 1` cada 10s | — |
| `redis` | `redis:7-alpine` | 6379 | `redis-cli ping` cada 10s | — |
| `celery-worker` | `sps-bi-api:latest` | — | — | redis, db |
| `celery-beat` | `sps-bi-api:latest` | — | — | redis |

**Volúmenes persistentes:**
- `mssql_data` — datos de SQL Server
- `redis_data` — datos de Redis

**Volúmenes de desarrollo (bind mounts):**
- `./backend:/app` — hot-reload del backend
- `./frontend:/app` — hot-reload del frontend
- `/app/node_modules` — volumen anónimo para evitar conflictos con node_modules del host

**Política de restart:** `unless-stopped` para todos los servicios.

**Dockerfiles:**

- **Backend (`backend/Dockerfile`):**
  - Base: `python:3.11-slim`
  - Instala ODBC Driver 18 para SQL Server (vía repositorio Microsoft)
  - Dependencias de compilación: `gcc`, `g++`, `unixodbc-dev`
  - Instala dependencias Python desde `requirements.txt`
  - Expone puerto 8000
  - CMD: `uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload`

- **Frontend (`frontend/Dockerfile`):**
  - Base: `node:20-alpine`
  - Instala dependencias con `npm install`
  - Expone puerto 5173
  - CMD: `npm run dev`

---

### 4.3 Tarea 0.3 — Scaffolding Backend FastAPI

**Descripción:** Crear la estructura base de la aplicación FastAPI.

#### 4.3.1 `app/main.py`

```python
# Responsabilidades:
# - Instanciar FastAPI con metadata (title, version, description)
# - Registrar CORS middleware
# - Registrar exception handlers
# - Exponer endpoint GET /health
```

**Endpoint `/health`:**
- Método: `GET`
- Response 200:
  ```json
  {
    "status": "ok",
    "database": "connected",
    "version": "1.0.0"
  }
  ```
- Response degradada (DB no disponible):
  ```json
  {
    "status": "degraded",
    "database": "disconnected",
    "version": "1.0.0"
  }
  ```
- Lógica: ejecuta `SELECT 1` contra la DB para verificar conectividad.

#### 4.3.2 `app/core/config.py`

Clase `Settings` basada en `pydantic_settings.BaseSettings`:

| Variable | Tipo | Default | Descripción |
|----------|------|---------|-------------|
| `DEBUG` | `bool` | `False` | Modo debug |
| `APP_NAME` | `str` | `"SPS-BI API"` | Nombre de la aplicación |
| `APP_VERSION` | `str` | `"1.0.0"` | Versión semántica |
| `API_V1_PREFIX` | `str` | `"/api/v1"` | Prefijo de rutas API v1 |
| `DATABASE_URL` | `str` | connection string MSSQL | URL de conexión a SQL Server |
| `JWT_SECRET` | `str` | `"change-me..."` | Secreto para firmar JWT |
| `JWT_ALGORITHM` | `str` | `"HS256"` | Algoritmo de JWT |
| `JWT_EXPIRATION_HOURS` | `int` | `24` | TTL del token en horas |
| `ENCRYPTION_KEY` | `str` | 32 bytes | Clave AES-256 para PII |
| `CORS_ALLOWED_ORIGINS` | `str` | localhost:5173,3000 | Orígenes CORS (comma-separated) |
| `OIDC_CLIENT_ID` | `str` | `""` | ID de cliente Azure AD |
| `OIDC_CLIENT_SECRET` | `str` | `""` | Secreto de cliente Azure AD |
| `OIDC_REDIRECT_URI` | `str` | `""` | URI de callback OIDC |
| `OIDC_TENANT_ID` | `str` | `""` | Tenant ID de Azure AD |
| `FRONTEND_URL` | `str` | `"http://localhost:5173"` | URL del frontend |
| `SMTP_HOST` | `str` | `""` | Host SMTP |
| `SMTP_PORT` | `int` | `587` | Puerto SMTP |
| `SMTP_USER` | `str` | `""` | Usuario SMTP |
| `SMTP_PASSWORD` | `str` | `""` | Contraseña SMTP |
| `SMTP_FROM_EMAIL` | `str` | `""` | Email remitente |
| `SMTP_NOTIFICATION_EMAILS` | `str` | `""` | Emails de notificación |
| `REDIS_URL` | `str` | `"redis://redis:6379/0"` | URL de conexión Redis |
| `SCHEDULER_GLOBAL_ENABLED` | `bool` | `False` | Habilitar tareas programadas |
| `SINISTER_AMOUNT_THRESHOLD` | `float` | `0.0` | Umbral de monto de siniestro |

**Carga de variables:** desde archivo `.env` con `env_file = ".env"`.

#### 4.3.3 `app/core/database.py`

- Conversión dinámica de URL: `mssql+pyodbc` → `mssql+aioodbc` para soporte async.
- Engine: `create_async_engine` con `poolclass=NullPool` (compatibilidad async).
- Session factory: `async_sessionmaker(AsyncSession)` con `expire_on_commit=False`.
- Función `get_db()`: generador async que yield la sesión, commit en éxito, rollback en error, close en finally.

---

### 4.4 Tarea 0.4 — Scaffolding Frontend React

**Descripción:** Configurar el proyecto frontend con Vite, TypeScript, Tailwind y React Router.

#### 4.4.1 Configuración de Vite (`vite.config.ts`)

- Plugin: `@vitejs/plugin-react`
- Alias de importación: `@` → `./src`
- Dev server: puerto 5173, host `0.0.0.0`
- Proxy: `/api` → `http://backend:8000` (para desarrollo con Docker)

#### 4.4.2 Configuración de Tailwind (`tailwind.config.ts`)

- Content paths: `index.html`, `src/**/*.{js,ts,jsx,tsx}`
- Paleta de colores personalizada `primary` (azul corporativo):
  - 50: `#eff6ff` ... 900: `#1e3a8a`

#### 4.4.3 TypeScript (`tsconfig.json`)

- Target: ES2020
- Module: ESNext
- Strict mode habilitado
- Sin variables/parámetros sin usar
- Path alias: `@/*` → `src/*`

#### 4.4.4 Punto de entrada (`src/main.tsx`)

- `React.StrictMode`
- `QueryClientProvider` con configuración:
  - `retry: 1`
  - `refetchOnWindowFocus: false`
- `BrowserRouter` de React Router

#### 4.4.5 Componente raíz (`src/App.tsx`)

- Routing básico: `<Routes>` con ruta `/` → `HomePage`
- `HomePage`: landing page con título "SPS-BI", subtítulo y pie de Banco Internacional.

#### 4.4.6 Cliente HTTP (`src/services/api.ts`)

- Instancia de axios con `baseURL` desde `VITE_API_URL` o `/api/v1`
- Interceptor de request: inyecta `Authorization: Bearer <token>` desde `localStorage`
- Interceptor de response: redirige a `/login` en respuesta `401`

#### 4.4.7 Estructura de directorios del frontend

```
src/
├── api/              # Integraciones de API (por implementar)
├── components/
│   ├── layout/       # Componentes de layout (header, sidebar, etc.)
│   ├── shared/       # Componentes compartidos
│   └── ui/           # Componentes de UI reutilizables
├── features/
│   ├── auth/         # Módulo de autenticación
│   ├── policies/     # Módulo de pólizas
│   ├── sinisters/    # Módulo de siniestros
│   ├── users/        # Módulo de usuarios
│   ├── audit/        # Módulo de auditoría
│   ├── reports/      # Módulo de reportes
│   ├── processes/    # Módulo de procesos
│   └── questionnaires/ # Módulo de cuestionarios
├── hooks/            # Custom hooks de React
├── routes/           # Definiciones de rutas
├── services/         # Servicios (api.ts)
├── stores/           # State management
├── types/            # Tipos TypeScript
└── utils/            # Utilidades
```

---

### 4.5 Tarea 0.5 — Configurar Alembic

**Descripción:** Sistema de migraciones para control de versiones del esquema de BD.

**Configuración (`alembic.ini`):**
- `script_location = alembic`
- `sqlalchemy.url` = connection string SQL Server (override en runtime desde settings)

**Environment (`alembic/env.py`):**
- Importa `Base.metadata` desde `app.models.base`
- Importa `settings` desde `app.core.config`
- Soporte async: usa `async_engine_from_config`
- Modos: offline (SQL dump) y online (ejecución directa)

**Comandos:**
```bash
alembic revision --autogenerate -m "descripción"  # Generar migración
alembic upgrade head                                # Aplicar todas las migraciones
alembic downgrade -1                                # Revertir última migración
alembic history                                     # Ver historial
```

---

### 4.6 Tarea 0.6 — BaseModel SQLAlchemy

**Descripción:** Modelo abstracto que sirve como base para todos los modelos de dominio.

**Campos:**

| Campo | Tipo SQL Server | Tipo Python | Default | Descripción |
|-------|----------------|-------------|---------|-------------|
| `id` | `UNIQUEIDENTIFIER` | `uuid.UUID` | `uuid.uuid4()` | Clave primaria UUID v4 |
| `is_deleted` | `BIT` | `bool` | `False` | Flag de eliminación lógica |
| `created_at` | `DATETIME` | `datetime` | `func.now()` | Timestamp de creación |
| `updated_at` | `DATETIME` | `datetime` | `func.now()` (onupdate) | Timestamp de última actualización |
| `created_by` | `UNIQUEIDENTIFIER` | `uuid.UUID | None` | `None` | UUID del usuario creador |
| `updated_by` | `UNIQUEIDENTIFIER` | `uuid.UUID | None` | `None` | UUID del último modificador |

**Patrones implementados:**
- **Soft delete:** Los registros nunca se eliminan físicamente; se marcan con `is_deleted=True`.
- **Auditoría básica:** `created_by` y `updated_by` rastrean quién creó/modificó el registro.
- **Timestamps automáticos:** `created_at` se genera al insertar, `updated_at` se actualiza automáticamente.
- **UUID como PK:** Evita colisiones en sistemas distribuidos y oculta el orden secuencial.

---

### 4.7 Tarea 0.7 — Pipeline CI/CD

**Descripción:** GitHub Actions con 3 jobs para validación automatizada.

**Archivo:** `.github/workflows/ci.yml`

**Triggers:**
- Push a ramas `main` y `develop`
- Pull requests a `main`

**Jobs:**

| Job | Runner | Steps |
|-----|--------|-------|
| `backend-lint-test` | `ubuntu-latest` | Setup Python 3.11 → pip install ruff + deps → `ruff check .` → `pytest tests/ -v --tb=short` |
| `frontend-lint-build` | `ubuntu-latest` | Setup Node 20 → `npm ci` → `npm run lint` → `npm run build` → `npm run test -- --run` |
| `docker-build` | `ubuntu-latest` | (Needs: backend + frontend) → `docker build ./backend` → `docker build ./frontend` |

---

### 4.8 Tarea 0.8 — `.env.example`

**Descripción:** Archivo de referencia con todas las variables de entorno organizadas por sección.

**Secciones:**
1. **Aplicación:** DEBUG, APP_NAME, APP_VERSION
2. **Base de datos:** DATABASE_URL (con connection string completo de SQL Server + ODBC 18)
3. **Seguridad:** JWT_SECRET, JWT_ALGORITHM, JWT_EXPIRATION_HOURS, ENCRYPTION_KEY
4. **CORS:** CORS_ALLOWED_ORIGINS
5. **OIDC/Azure AD:** OIDC_CLIENT_ID, OIDC_CLIENT_SECRET, OIDC_REDIRECT_URI, OIDC_TENANT_ID
6. **Frontend:** FRONTEND_URL
7. **Email/SMTP:** SMTP_HOST, SMTP_PORT, SMTP_USER, SMTP_PASSWORD, SMTP_FROM_EMAIL, SMTP_NOTIFICATION_EMAILS
8. **Redis:** REDIS_URL
9. **Scheduler:** SCHEDULER_GLOBAL_ENABLED
10. **Negocio:** SINISTER_AMOUNT_THRESHOLD
11. **SQL Server Docker:** SA_PASSWORD, ACCEPT_EULA, MSSQL_PID

---

### 4.9 Tarea 0.9 — CORS Middleware y Health Check

**CORS middleware:**
- Orígenes permitidos: parseados desde `CORS_ALLOWED_ORIGINS` (comma-separated)
- Credenciales: habilitadas (`allow_credentials=True`)
- Métodos: todos (`allow_methods=["*"]`)
- Headers: todos (`allow_headers=["*"]`)

**Health check:** ver sección 4.3.1.

---

### 4.10 Tarea 0.10 — Manejo Global de Excepciones

**Clase `AppException`:**
```python
class AppException(Exception):
    def __init__(self, status_code: int, message: str, detail: str | None = None):
        self.status_code = status_code
        self.message = message
        self.detail = detail
```

**Handlers registrados en `main.py`:**

1. **`app_exception_handler(AppException)`** — excepciones de negocio controladas:
   ```json
   {
     "status": "error",
     "message": "Recurso no encontrado",
     "detail": "No existe póliza con ID xyz",
     "path": "/api/v1/policies/xyz",
     "timestamp": "2026-03-04T10:00:00Z"
   }
   ```

2. **`generic_exception_handler(Exception)`** — errores no controlados:
   - En modo `DEBUG=True`: incluye detalle del error
   - En modo `DEBUG=False`: responde genéricamente "Internal server error" (sin filtrar información sensible)
   - Status code: `500`

---

## 5. Enumeraciones de Dominio

Definidas en `app/models/enums.py` como base para los sprints futuros:

| Enum | Valores | Uso |
|------|---------|-----|
| `AppStatus` | ACTIVE, RESOLVED, CLOSED, REJECTED | Estado general de solicitudes |
| `PolicyStatus` | ACTIVE, RESOLVED, CLOSED, REJECTED, EXPIRED | Estado de pólizas |
| `SinisterStatus` | OPEN, PENDING, SENT, CLOSED, PAID | Estado de siniestros |
| `SinisterType` | PO, SO | Tipo de siniestro |
| `ActionTypes` | REOPEN, CLOSED | Tipos de acción |
| `AnswerTypes` | NORMAL, DES, SUB, OPTIONAL, SELECTOR, OPTIONALNORMAL, MULTIPLE, SUBOPTIONAL, SELECTOREXCEL, SELECTORNORMAL, TABLE | Tipos de respuesta en cuestionarios |
| `AuditAction` | CREATE, UPDATE, DELETE | Acciones de auditoría |
| `UserRole` | SUPER_ADMIN, ADMIN, REGISTER_USER, VIEW_USER, SINISTER_REGISTER_USER, PROVIDER_USER | Roles de usuario |

---

## 6. Configuración de Celery

**Archivo:** `app/tasks/celery_app.py`

| Parámetro | Valor |
|-----------|-------|
| Broker | Redis (`redis://redis:6379/0`) |
| Result backend | Redis (`redis://redis:6379/0`) |
| Serialización | JSON (accept/result/task) |
| Timezone | `America/Guayaquil` |
| Concurrency | 4 workers |
| Prefetch multiplier | 2 |
| Beat schedule | Vacío (se pobla en Sprint 7) |

---

## 7. Dependencias Completas

### Backend (`requirements.txt`)

| Paquete | Versión | Propósito |
|---------|---------|-----------|
| fastapi | 0.115.6 | Framework API |
| uvicorn | 0.34.0 | Servidor ASGI |
| sqlalchemy | 2.0.36 | ORM |
| alembic | 1.14.1 | Migraciones |
| pydantic | 2.10.4 | Validación |
| pydantic-settings | 2.7.1 | Configuración |
| pyodbc | 5.2.0 | Driver SQL Server |
| aioodbc | 0.5.0 | Driver async SQL Server |
| python-jose[cryptography] | 3.3.0 | JWT (Sprint 1, instalado base) |
| authlib | 1.4.1 | OIDC (Sprint 1, instalado base) |
| httpx | 0.28.1 | HTTP client async |
| python-multipart | 0.0.20 | Uploads |
| celery[redis] | 5.4.0 | Cola de tareas |
| redis | 5.2.1 | Cliente Redis |
| loguru | 0.7.3 | Logging |
| jinja2 | 3.1.5 | Templates |
| pillow | 11.1.0 | Imágenes |
| pytest | 8.3.4 | Testing |
| pytest-asyncio | 0.25.0 | Testing async |

### Frontend (`package.json`)

| Paquete | Versión | Propósito |
|---------|---------|-----------|
| react | 18.3.1 | Framework UI |
| react-dom | 18.3.1 | Renderizado DOM |
| react-router-dom | 6.28.0 | Routing |
| @tanstack/react-query | 5.62.0 | Server state |
| axios | 1.7.9 | HTTP client |
| typescript | ~5.6.2 | Tipado estático |
| vite | 6.0.0 | Build tool |
| tailwindcss | 3.4.16 | CSS utility-first |
| vitest | 2.1.8 | Testing |
| @testing-library/react | 16.1.0 | Testing UI |
| eslint | 9.15.0 | Linting |

---

## 8. Decisiones Arquitectónicas

| # | Decisión | Justificación |
|---|----------|---------------|
| ADR-01 | UUID v4 como PK en lugar de auto-increment | Evita colisiones en entornos distribuidos, no revela volumen de datos |
| ADR-02 | Soft delete (`is_deleted`) en lugar de DELETE físico | Trazabilidad y recuperación de datos; requerimiento de auditoría bancaria |
| ADR-03 | `NullPool` en SQLAlchemy async | Evita problemas de connection pool con drivers async (aioodbc) |
| ADR-04 | Conversión dinámica `pyodbc` → `aioodbc` | Permite usar la misma URL de conexión para sync (Alembic) y async (app) |
| ADR-05 | Monorepo con carpetas separadas | Simplicidad para un equipo pequeño; un solo repo pero builds independientes |
| ADR-06 | Celery + Redis para tareas async | Requerimiento de notificaciones programadas y procesamiento en segundo plano |
| ADR-07 | TanStack React Query para server state | Caching, deduplicación y sincronización automática; evita Redux para datos del servidor |
| ADR-08 | Feature-based folder structure (frontend) | Escalabilidad: cada módulo autocontenido con sus componentes, hooks y tipos |
| ADR-09 | Pydantic BaseSettings para config | Validación de tipos en tiempo de carga; integración nativa con `.env` |
| ADR-10 | Vite proxy en desarrollo | Elimina problemas de CORS en desarrollo local; simula routing de producción |
