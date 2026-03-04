# Plan de Implementación por Sprints — SPS-BI

> Sistema de Gestión de Pólizas y Siniestros — Banco Internacional
> **Metodología:** Scrum (sprints de 2 semanas)
> **Stack:** Python 3.11+ · FastAPI · React 18+ · SQL Server · OIDC (OpenID Connect) / JWT · Docker

---

## Resumen Ejecutivo

| Sprint | Nombre | Duración | Módulo Principal |
|--------|--------|----------|------------------|
| 0 | Infraestructura y Configuración Base | 2 semanas | DevOps / Arquitectura |
| 1 | Autenticación y Gestión de Usuarios | 2 semanas | Seguridad |
| 2 | Modelo de Datos y CRUD de Procesos | 2 semanas | Pólizas — Core |
| 3 | Proceso-Póliza y Cuestionarios | 2 semanas | Pólizas — Asignación |
| 4 | Preguntas, Respuestas y Validación Inteligente | 2 semanas | Pólizas — Registro |
| 5 | Archivos Adjuntos y Generación de PDFs | 2 semanas | Pólizas — Documentos |
| 6 | Módulo de Siniestros | 2 semanas | Siniestros |
| 7 | Proveedores, Notificaciones y Tareas Programadas | 2 semanas | Siniestros — Complemento |
| 8 | Reportes, Auditoría y Frontend Avanzado | 2 semanas | Transversal |
| 9 | Integración, QA y Despliegue | 2 semanas | Estabilización |

**Duración total estimada:** 20 semanas (5 meses)

---

## Sprint 0 — Infraestructura y Configuración Base

**Objetivo:** Establecer el entorno de desarrollo, CI/CD, estructura del proyecto y base de datos inicial.

### Tareas

| # | Tarea | Responsable |
|---|-------|-------------|
| 0.1 | Crear repositorio Git con estructura de monorepo (`backend/`, `frontend/`) | DevOps |
| 0.2 | Configurar `docker-compose.yml` con servicios: backend, frontend, db (SQL Server), redis, celery-worker, celery-beat | DevOps |
| 0.3 | Scaffolding backend FastAPI: `main.py`, `core/config.py` (BaseSettings), `core/database.py` (SQLAlchemy async engine) | Backend |
| 0.4 | Scaffolding frontend React: Vite + TypeScript + Tailwind CSS + React Router v6 | Frontend |
| 0.5 | Configurar Alembic para migraciones de BD | Backend |
| 0.6 | Implementar `BaseModel` SQLAlchemy (UUID pk, soft-delete, timestamps, created_by/updated_by) | Backend |
| 0.7 | Configurar pipeline CI/CD (linting, tests, build Docker) | DevOps |
| 0.8 | Crear archivo `.env.example` con todas las variables de entorno documentadas | DevOps |
| 0.9 | Configurar CORS middleware y health check endpoint (`/health`) | Backend |
| 0.10 | Configurar manejo global de excepciones (`AppException`, handlers) | Backend |

### Pruebas — Sprint 0

| ID | Prueba | Tipo | Criterio de Aceptación |
|----|--------|------|------------------------|
| T0.1 | `docker-compose up` levanta todos los servicios sin errores | Infraestructura | Todos los contenedores en estado `healthy` |
| T0.2 | `GET /health` retorna `200 OK` con status del sistema | Integración | Response: `{"status": "ok", "database": "connected"}` |
| T0.3 | `GET /docs` muestra Swagger UI correctamente | Humo | Página Swagger carga sin errores 404 |
| T0.4 | Alembic genera y aplica migración inicial | Integración | `alembic upgrade head` ejecuta sin errores, tablas creadas en SQL Server |
| T0.5 | Frontend en `http://localhost:5173` carga la app React | Humo | Página principal renderiza sin errores de consola |
| T0.6 | Pipeline CI ejecuta linting + tests + build | CI/CD | Pipeline completa en verde |
| T0.7 | `BaseModel` crea registro con UUID, timestamps y soft-delete | Unitaria | `pytest test_base_model.py` — UUID generado, `created_at` no nulo, `is_deleted=False` |

```bash
# Comandos de verificación Sprint 0
docker-compose up -d && docker-compose ps          # T0.1
curl http://localhost:8000/health                    # T0.2
curl http://localhost:8000/docs                      # T0.3
docker exec backend alembic upgrade head             # T0.4
curl http://localhost:5173                            # T0.5
pytest tests/ -v                                     # T0.7
```

---

## Sprint 1 — Autenticación y Gestión de Usuarios

**Objetivo:** Implementar flujo completo de autenticación OIDC (OpenID Connect) + JWT, gestión de usuarios y RBAC.

### Tareas

| # | Tarea | Responsable |
|---|-------|-------------|
| 1.1 | Implementar `core/security.py`: JWT encode/decode (HS256), `get_current_user`, `require_roles` | Backend |
| 1.2 | Implementar `core/oidc.py`: configuración authlib OAuth client, flujo Authorization Code + PKCE | Backend |
| 1.3 | Crear modelo `User` (SQLAlchemy): email, name, last_name (cifrados AES-256), role, open_user_id, is_active | Backend |
| 1.4 | Implementar `UserService`: CRUD, cifrado/descifrado AES-256 de PII, `verify_and_save()` | Backend |
| 1.5 | Crear endpoints de auth: `GET /auth/login`, `GET /auth/callback`, `POST /auth/token`, `POST /auth/refresh`, `GET /auth/validate`, `GET /auth/me`, `POST /auth/logout` | Backend |
| 1.6 | Implementar logout OIDC (RP-Initiated): redirect al endpoint de logout de Azure AD con `post_logout_redirect_uri` | Backend |
| 1.7 | Implementar sincronización Azure AD: `POST /settings/sync-azure-ad-users` (Graph API, client_credentials flow) | Backend |
| 1.8 | Crear endpoints de usuarios: `GET /users`, `GET /users/{open_user_id}` | Backend |
| 1.9 | Frontend: página de Login, callback OIDC (`/auth/callback?token=...`), almacenamiento de JWT, AuthContext/Provider | Frontend |
| 1.10 | Frontend: componente ProtectedRoute con verificación de roles | Frontend |
| 1.11 | Frontend: página de gestión de usuarios (listado, detalle) — solo SuperAdmin/Admin | Frontend |
| 1.12 | Implementar logout frontend (limpieza de token + redirect a OIDC logout URL) | Frontend |

### Pruebas — Sprint 1

| ID | Prueba | Tipo | Criterio de Aceptación |
|----|--------|------|------------------------|
| T1.1 | Generación de JWT con claims correctos | Unitaria | Token decodificado contiene `sub`, `email`, `roles`, `exp` |
| T1.2 | JWT expirado retorna 401 | Unitaria | `decode()` lanza `ExpiredSignatureError`, endpoint retorna `401` |
| T1.3 | `get_current_user` con token válido retorna el usuario | Unitaria | Usuario retornado con email y roles correctos |
| T1.4 | `require_roles("SUPER_ADMIN")` rechaza usuario con rol `REGISTER_USER` | Unitaria | Retorna `403 Forbidden` |
| T1.5 | Cifrado AES-256 de `user_name` y descifrado idéntico | Unitaria | `decrypt(encrypt("Juan Carlos"))` == `"Juan Carlos"` |
| T1.6 | `GET /auth/callback` con authorization_code válido genera JWT | Integración | Response redirect con `?token=...`, JWT válido |
| T1.7 | `GET /auth/me` sin token retorna 401 | Integración | Response `401 Unauthorized` |
| T1.8 | `GET /auth/me` con token válido retorna perfil del usuario | Integración | Response contiene `email`, `name`, `roles` |
| T1.9 | `GET /users` con rol Admin retorna lista de usuarios | Integración | Response `200` con `{ "users": [...], "total": N }` |
| T1.10 | `GET /users` con rol RegisterUser retorna 403 | Integración | Response `403 Forbidden` |
| T1.11 | Sincronización Azure AD crea/actualiza usuarios | Integración | Usuarios creados con roles mapeados correctamente |
| T1.12 | Frontend: login redirecciona al IdP OIDC de Azure AD | E2E | Click en "Iniciar Sesión" → redirect a `login.microsoftonline.com` |
| T1.13 | Frontend: callback OIDC almacena JWT y redirecciona al dashboard | E2E | Token almacenado en localStorage, usuario en dashboard |
| T1.14 | Frontend: ruta protegida sin auth redirecciona a login | E2E | Acceso a `/dashboard` sin token → redirect a `/login` |
| T1.15 | Frontend: logout limpia token y redirecciona | E2E | Token eliminado, usuario en página de login |

```python
# tests/test_auth.py
import pytest
from httpx import AsyncClient

class TestAuth:
    async def test_oidc_callback(self, client: AsyncClient):
        """T1.6: GET /auth/callback con code válido genera JWT"""
        response = await client.get("/api/v1/auth/callback", params={"code": "mock_auth_code", "state": "mock_state"})
        assert response.status_code == 200
        assert "access_token" in response.json()

    async def test_me_without_token(self, client: AsyncClient):
        """T1.7: GET /auth/me sin token retorna 401"""
        response = await client.get("/api/v1/auth/me")
        assert response.status_code == 401

    async def test_me_with_valid_token(self, authenticated_client: AsyncClient):
        """T1.8: GET /auth/me con token retorna perfil"""
        response = await authenticated_client.get("/api/v1/auth/me")
        assert response.status_code == 200
        data = response.json()
        assert "email" in data
        assert "roles" in data

    async def test_role_restriction(self, register_user_client: AsyncClient):
        """T1.10: RegisterUser no puede acceder a /users"""
        response = await register_user_client.get("/api/v1/users")
        assert response.status_code == 403

class TestEncryption:
    def test_aes_encrypt_decrypt(self):
        """T1.5: Cifrado y descifrado AES-256 de PII"""
        from app.services.encryption import encrypt, decrypt
        original = "Juan Carlos Montiel"
        encrypted = encrypt(original)
        assert encrypted != original
        assert decrypt(encrypted) == original
```

---

## Sprint 2 — Modelo de Datos y CRUD de Procesos

**Objetivo:** Implementar los modelos del módulo de pólizas y CRUD completo de Procesos.

### Tareas

| # | Tarea | Responsable |
|---|-------|-------------|
| 2.1 | Crear modelos SQLAlchemy: `Process`, `Policy` (catálogo), `Questionary` (catálogo), `Question`, `QuestionOption` | Backend |
| 2.2 | Crear schemas Pydantic para Process (Create, Update, Response, Filter) | Backend |
| 2.3 | Implementar `ProcessService`: create, update, delete (soft), filter, validate-closure | Backend |
| 2.4 | Crear migración Alembic con todas las tablas del módulo de pólizas | Backend |
| 2.5 | Implementar endpoints de procesos: `POST`, `PUT`, `GET /filter`, `DELETE`, `GET`, `GET /validate-closure` | Backend |
| 2.6 | Implementar endpoints de catálogo: `GET /policies`, `GET /policies/unassigned`, `GET /questionnaires` | Backend |
| 2.7 | Normalización de fechas: `start_date → 00:00:00`, `end_date → 23:59:59` | Backend |
| 2.8 | Frontend: página de listado de procesos con filtros (subsidiaria, fecha, año, carácter) y paginación | Frontend |
| 2.9 | Frontend: formulario de creación/edición de proceso | Frontend |
| 2.10 | Frontend: componente de eliminación con confirmación (soft-delete) | Frontend |
| 2.11 | Implementar cálculo de porcentaje de avance del proceso | Backend |

### Pruebas — Sprint 2

| ID | Prueba | Tipo | Criterio de Aceptación |
|----|--------|------|------------------------|
| T2.1 | Crear proceso con datos válidos | Integración | `POST /processes` → `201`, proceso creado con UUID, status=ACTIVE |
| T2.2 | Crear proceso sin nombre retorna error de validación | Integración | `POST /processes` → `422`, detalle del campo faltante |
| T2.3 | Actualizar proceso existente | Integración | `PUT /processes/{id}` → `200`, campos actualizados |
| T2.4 | Soft-delete de proceso | Integración | `DELETE /processes/{id}` → `200`, `is_deleted=True` en BD |
| T2.5 | Proceso eliminado no aparece en listado | Integración | `GET /processes` no incluye procesos con `is_deleted=True` |
| T2.6 | Filtrar procesos por subsidiaria y año | Integración | `GET /processes/filter?subsidiary=X&year=2024` retorna solo procesos coincidentes |
| T2.7 | Paginación funciona correctamente | Integración | `GET /processes/filter?page=1&size=10` retorna máximo 10 items |
| T2.8 | Fechas se normalizan correctamente | Unitaria | `start_date` tiene hora `00:00:00`, `end_date` tiene hora `23:59:59` |
| T2.9 | Validación de cierre: proceso con pólizas abiertas no puede cerrarse | Integración | `GET /validate-closure/{id}` retorna `{ "can_close": false, "reason": "..." }` |
| T2.10 | Porcentaje se calcula correctamente | Unitaria | 3 de 5 pólizas resueltas → percentage = 60 |
| T2.11 | Frontend: tabla de procesos carga y muestra datos | E2E | Tabla muestra procesos con nombre, fechas, estado, porcentaje |
| T2.12 | Frontend: crear proceso desde formulario | E2E | Formulario envía datos, proceso aparece en listado |

```python
# tests/test_processes.py
class TestProcessCRUD:
    async def test_create_process(self, admin_client: AsyncClient, db_session):
        """T2.1: Crear proceso exitosamente"""
        payload = {
            "name": "Revisión 2024-Q1",
            "subsidiary_id": "uuid-subsidiaria",
            "start_date": "2024-01-01",
            "end_date": "2024-03-31"
        }
        response = await admin_client.post("/api/v1/processes", json=payload)
        assert response.status_code == 201
        data = response.json()
        assert data["status"] == "ACTIVE"
        assert data["id"] is not None

    async def test_soft_delete_hides_from_list(self, admin_client: AsyncClient, created_process):
        """T2.5: Proceso eliminado no aparece en listado"""
        await admin_client.delete(f"/api/v1/processes/{created_process['id']}")
        response = await admin_client.get("/api/v1/processes")
        ids = [p["id"] for p in response.json()]
        assert created_process["id"] not in ids

    async def test_date_normalization(self, admin_client: AsyncClient):
        """T2.8: Fechas se normalizan"""
        payload = {"name": "Test", "start_date": "2024-01-15", "end_date": "2024-06-30"}
        response = await admin_client.post("/api/v1/processes", json=payload)
        data = response.json()
        assert data["start_date"].endswith("T00:00:00")
        assert data["end_date"].endswith("T23:59:59")
```

---

## Sprint 3 — Proceso-Póliza y Cuestionarios

**Objetivo:** Implementar la asignación de pólizas a procesos y cuestionarios a proceso-pólizas.

### Tareas

| # | Tarea | Responsable |
|---|-------|-------------|
| 3.1 | Crear modelos: `ProcessPolicy`, `PolicyQuestionary` | Backend |
| 3.2 | Crear schemas Pydantic para ProcessPolicy y PolicyQuestionary | Backend |
| 3.3 | Implementar `ProcessPolicyService`: bulk_create, filter, update, soft-delete | Backend |
| 3.4 | Implementar `PolicyQuestionaryService`: assign (individual + masivo), filter, status update | Backend |
| 3.5 | Implementar creación asíncrona de `UserQuestion` via Celery al asignar cuestionario | Backend |
| 3.6 | Implementar prevención de asignación duplicada | Backend |
| 3.7 | Crear endpoints de process-policies (5 endpoints) | Backend |
| 3.8 | Crear endpoints de policy-questionnaires (11 endpoints) | Backend |
| 3.9 | Implementar cálculo de porcentaje: ProcessPolicy % y PolicyQuestionary % | Backend |
| 3.10 | Frontend: vista de pólizas asignadas a un proceso | Frontend |
| 3.11 | Frontend: modal de asignación masiva de pólizas | Frontend |
| 3.12 | Frontend: vista de cuestionarios por proceso-póliza con indicador de avance | Frontend |
| 3.13 | Frontend: asignación de cuestionarios a proceso-póliza | Frontend |

### Pruebas — Sprint 3

| ID | Prueba | Tipo | Criterio de Aceptación |
|----|--------|------|------------------------|
| T3.1 | Asignación masiva de pólizas a proceso | Integración | `POST /process-policies` con array → `201`, todas las asignaciones creadas |
| T3.2 | Asignación duplicada de póliza es rechazada | Integración | `POST /process-policies` con póliza ya asignada → `409 Conflict` |
| T3.3 | Soft-delete de process-policy | Integración | `DELETE /process-policies/{id}` → `200`, `is_deleted=True` |
| T3.4 | Asignar cuestionario a proceso-póliza (individual) | Integración | `POST /policy-questionnaires/assign-single` → `201` |
| T3.5 | Asignación masiva de cuestionarios (éxito parcial) | Integración | `POST /policy-questionnaires/assign` → response indica cuáles se asignaron y cuáles fallaron |
| T3.6 | Asignación de cuestionario dispara tarea Celery para crear UserQuestions | Integración | Verificar que Celery worker procesa la tarea y crea registros `UserQuestion` |
| T3.7 | Obtener cuestionarios de un proceso-póliza | Integración | `GET /policy-questionnaires/by-process-policy/{id}` retorna lista con status y porcentaje |
| T3.8 | Estadísticas de UserQuestion por cuestionario | Integración | `GET /policy-questionnaires/{id}/user-questions/stats` retorna total, respondidas, porcentaje |
| T3.9 | Porcentaje de ProcessPolicy se calcula correctamente | Unitaria | 2 de 4 cuestionarios RESOLVED → 50% |
| T3.10 | Frontend: pólizas se muestran con estado y porcentaje | E2E | Tabla muestra columnas de estado y barra de progreso |
| T3.11 | Frontend: asignar cuestionario actualiza la vista | E2E | Después de asignar, el cuestionario aparece en la lista |

```python
# tests/test_process_policies.py
class TestProcessPolicyAssignment:
    async def test_bulk_assign_policies(self, admin_client, process_id, policy_ids):
        """T3.1: Asignación masiva exitosa"""
        payload = {
            "process_id": process_id,
            "policy_ids": policy_ids  # lista de 3 pólizas
        }
        response = await admin_client.post("/api/v1/process-policies", json=payload)
        assert response.status_code == 201
        assert len(response.json()) == 3

    async def test_duplicate_assignment_rejected(self, admin_client, existing_assignment):
        """T3.2: Duplicado rechazado"""
        payload = {
            "process_id": existing_assignment["process_id"],
            "policy_ids": [existing_assignment["policy_id"]]
        }
        response = await admin_client.post("/api/v1/process-policies", json=payload)
        assert response.status_code == 409

# tests/test_policy_questionnaires.py
class TestQuestionnaireAssignment:
    async def test_celery_creates_user_questions(self, admin_client, process_policy_id, questionary_id, celery_worker):
        """T3.6: Celery crea UserQuestions tras asignar cuestionario"""
        response = await admin_client.post("/api/v1/policy-questionnaires/assign-single", json={
            "process_policy_id": process_policy_id,
            "questionary_id": questionary_id
        })
        assert response.status_code == 201
        # Esperar a que Celery procese
        celery_worker.wait_for_pending()
        # Verificar que se crearon UserQuestions
        pq_id = response.json()["id"]
        stats = await admin_client.get(f"/api/v1/policy-questionnaires/{pq_id}/user-questions/stats")
        assert stats.json()["total"] > 0
```

---

## Sprint 4 — Preguntas, Respuestas y Validación Inteligente

**Objetivo:** Implementar el flujo de registro de información por usuarios, con validación contra datos históricos.

### Tareas

| # | Tarea | Responsable |
|---|-------|-------------|
| 4.1 | Crear modelos: `UserQuestion`, `UserAnswer` | Backend |
| 4.2 | Crear schemas Pydantic para UserQuestion y UserAnswer (incluyendo batch) | Backend |
| 4.3 | Implementar `UserQuestionService`: get, filter, update assignment, update status | Backend |
| 4.4 | Implementar `UserAnswerService`: create (individual + batch), update, prevención de duplicados | Backend |
| 4.5 | Implementar validación inteligente: comparación con respuestas del período anterior | Backend |
| 4.6 | Implementar cierre automático de edición al alcanzar 100% de completitud | Backend |
| 4.7 | Crear endpoints de user-questions (6 endpoints) | Backend |
| 4.8 | Crear endpoints de user-answers (8 endpoints) | Backend |
| 4.9 | Crear endpoints de question-options: `GET /question-options/question/{id}`, `POST /questions/batch` | Backend |
| 4.10 | Frontend: formulario dinámico de cuestionario con tipos de respuesta (NORMAL, SELECTOR, TABLE, etc.) | Frontend |
| 4.11 | Frontend: resaltado visual de campos con discrepancias históricas | Frontend |
| 4.12 | Frontend: indicador de porcentaje de completitud en tiempo real | Frontend |
| 4.13 | Frontend: modo batch para envío de múltiples respuestas | Frontend |

### Pruebas — Sprint 4

| ID | Prueba | Tipo | Criterio de Aceptación |
|----|--------|------|------------------------|
| T4.1 | Enviar una respuesta individual | Integración | `POST /user-answers` → `201`, respuesta almacenada |
| T4.2 | Enviar respuestas en batch | Integración | `POST /user-answers/batch` → `201`, todas las respuestas creadas |
| T4.3 | Respuesta duplicada es rechazada | Integración | `POST /user-answers` con misma `user_question` + `user_assigned` → `409` |
| T4.4 | Validación inteligente detecta discrepancia | Integración | Respuesta con valor diferente al período anterior → response incluye `warnings[]` |
| T4.5 | Sin discrepancia no genera warnings | Integración | Respuesta con mismo valor que período anterior → `warnings` vacío |
| T4.6 | Cierre automático al 100% de completitud | Integración | Última respuesta completada → status del cuestionario cambia a RESOLVED |
| T4.7 | Usuario no puede responder cuestionario CLOSED | Integración | `POST /user-answers` en cuestionario cerrado → `403` |
| T4.8 | Actualizar asignación de usuario a pregunta | Integración | `PUT /user-questions/assignment` → `200`, usuario actualizado |
| T4.9 | Obtener opciones de pregunta | Integración | `GET /question-options/question/{id}` retorna opciones con valores |
| T4.10 | Obtener respuestas por usuario | Integración | `GET /user-answers/user/{user_id}` retorna solo respuestas del usuario |
| T4.11 | Batch de opciones para múltiples preguntas | Integración | `POST /question-options/questions/batch` retorna opciones agrupadas por pregunta |
| T4.12 | Frontend: formulario renderiza según AnswerType | E2E | Campos SELECTOR muestran dropdown, TABLE muestra grid, NORMAL muestra input |
| T4.13 | Frontend: campo con discrepancia se resalta en color | E2E | Campo con warning muestra borde rojo y tooltip con valor anterior |
| T4.14 | Frontend: barra de progreso se actualiza en tiempo real | E2E | Cada respuesta incrementa el porcentaje visible |

```python
# tests/test_user_answers.py
class TestUserAnswers:
    async def test_create_answer(self, register_user_client, user_question_id):
        """T4.1: Crear respuesta individual"""
        payload = {
            "user_question_id": user_question_id,
            "answer": "1500000",
            "answer_type": "NORMAL"
        }
        response = await register_user_client.post("/api/v1/user-answers", json=payload)
        assert response.status_code == 201

    async def test_batch_answers(self, register_user_client, user_question_ids):
        """T4.2: Respuestas en batch"""
        payload = [
            {"user_question_id": uq_id, "answer": f"value_{i}", "answer_type": "NORMAL"}
            for i, uq_id in enumerate(user_question_ids)
        ]
        response = await register_user_client.post("/api/v1/user-answers/batch", json=payload)
        assert response.status_code == 201

    async def test_intelligent_validation_warns(self, register_user_client, user_question_with_history):
        """T4.4: Validación inteligente genera warning"""
        payload = {
            "user_question_id": user_question_with_history["id"],
            "answer": "9999999",  # Valor muy diferente al histórico
            "answer_type": "NORMAL"
        }
        response = await register_user_client.post("/api/v1/user-answers", json=payload)
        assert response.status_code == 201
        assert len(response.json().get("warnings", [])) > 0

    async def test_auto_close_on_completion(self, register_user_client, questionary_almost_complete):
        """T4.6: Cuestionario se resuelve al 100%"""
        # Enviar la última respuesta pendiente
        payload = {
            "user_question_id": questionary_almost_complete["last_question_id"],
            "answer": "Completado",
            "answer_type": "NORMAL"
        }
        await register_user_client.post("/api/v1/user-answers", json=payload)
        # Verificar que el cuestionario cambió a RESOLVED
        pq = await register_user_client.get(
            f"/api/v1/policy-questionnaires/by-process-policy/{questionary_almost_complete['pp_id']}"
        )
        statuses = [q["status"] for q in pq.json()]
        assert "RESOLVED" in statuses
```

---

## Sprint 5 — Archivos Adjuntos y Generación de PDFs

**Objetivo:** Implementar subida/descarga de archivos y generación de reportes PDF.

### Tareas

| # | Tarea | Responsable |
|---|-------|-------------|
| 5.1 | Crear modelo `Attachment` (data_file VARBINARY, preview VARBINARY, asociaciones polimórficas) | Backend |
| 5.2 | Implementar `AttachmentService`: store (multipart), download, hard-delete, thumbnail generation (Pillow) | Backend |
| 5.3 | Crear endpoints de attachments (11 endpoints) | Backend |
| 5.4 | Implementar `PDFGeneratorService`: Jinja2 template rendering + WeasyPrint HTML→PDF | Backend |
| 5.5 | Crear plantillas Jinja2: BBB (4), CYBER (5), DO (2) — 11 plantillas total | Backend |
| 5.6 | Crear endpoints de reportes PDF (11 endpoints) | Backend |
| 5.7 | Frontend: componente Dropzone para subida de archivos | Frontend |
| 5.8 | Frontend: previsualización de imágenes con thumbnails | Frontend |
| 5.9 | Frontend: botón de descarga de archivos | Frontend |
| 5.10 | Frontend: visor/descarga de reportes PDF | Frontend |
| 5.11 | Implementar registro de motivo de justificación al descargar información | Backend |

### Pruebas — Sprint 5

| ID | Prueba | Tipo | Criterio de Aceptación |
|----|--------|------|------------------------|
| T5.1 | Subir archivo asociado a un proceso | Integración | `POST /attachments` (multipart) → `201`, archivo almacenado en BD |
| T5.2 | Subir imagen genera thumbnail 30x30 | Integración | Archivo PNG subido → campo `preview` no nulo, dimensiones 30x30 |
| T5.3 | Descargar archivo retorna bytes correctos | Integración | `GET /attachments/download/{id}` → `200`, Content-Type correcto, bytes idénticos |
| T5.4 | Hard-delete elimina archivo permanentemente | Integración | `DELETE /attachments/{id}` → `200`, registro no existe en BD |
| T5.5 | Obtener archivos por proceso | Integración | `GET /attachments/process/{id}` retorna solo archivos del proceso |
| T5.6 | Generar PDF tipo BBB/AppendixForm | Integración | `GET /reports/appendix/{id}` → `200`, Content-Type `application/pdf`, bytes > 0 |
| T5.7 | Generar PDF tipo CYBER/CyberRiskForm | Integración | `GET /reports/cyberrisk/{id}` → `200`, PDF válido |
| T5.8 | Generar PDF tipo DO/WillisForm | Integración | `GET /reports/willis/{id}` → `200`, PDF válido |
| T5.9 | Reporte con ID inexistente retorna 404 | Integración | `GET /reports/appendix/{id_invalido}` → `404` |
| T5.10 | Archivo no-imagen no genera thumbnail | Unitaria | Subida de `.xlsx` → campo `preview` es `null` |
| T5.11 | Descarga requiere motivo de justificación | Integración | `GET /attachments/download/{id}` sin `reason` → `400` |
| T5.12 | Frontend: drag & drop sube archivo correctamente | E2E | Arrastrar archivo → previsualización visible → archivo en servidor |
| T5.13 | Frontend: botón PDF genera y descarga documento | E2E | Click "Generar PDF" → descarga archivo .pdf |

```python
# tests/test_attachments.py
class TestAttachments:
    async def test_upload_file(self, admin_client, process_id):
        """T5.1: Subir archivo a un proceso"""
        files = {"file": ("test.xlsx", b"contenido binario", "application/vnd.ms-excel")}
        data = {"process_id": process_id}
        response = await admin_client.post("/api/v1/attachments", files=files, data=data)
        assert response.status_code == 201
        assert response.json()["name"] == "test.xlsx"

    async def test_image_generates_thumbnail(self, admin_client, process_id, sample_png_bytes):
        """T5.2: Imagen PNG genera thumbnail"""
        files = {"file": ("foto.png", sample_png_bytes, "image/png")}
        data = {"process_id": process_id}
        response = await admin_client.post("/api/v1/attachments", files=files, data=data)
        assert response.status_code == 201
        assert response.json()["preview"] is not None

    async def test_hard_delete(self, admin_client, attachment_id, db_session):
        """T5.4: Hard-delete elimina permanentemente"""
        response = await admin_client.delete(f"/api/v1/attachments/{attachment_id}")
        assert response.status_code == 200
        # Verificar que no existe en BD
        from app.models.attachment import Attachment
        result = await db_session.get(Attachment, attachment_id)
        assert result is None

# tests/test_reports.py
class TestPDFGeneration:
    async def test_generate_appendix_pdf(self, admin_client, policy_questionary_id):
        """T5.6: Generar PDF BBB/Appendix"""
        response = await admin_client.get(f"/api/v1/reports/appendix/{policy_questionary_id}")
        assert response.status_code == 200
        assert response.headers["content-type"] == "application/pdf"
        assert len(response.content) > 100  # PDF no vacío
```

---

## Sprint 6 — Módulo de Siniestros

**Objetivo:** Implementar CRUD completo de siniestros, estados, comentarios e historial.

### Tareas

| # | Tarea | Responsable |
|---|-------|-------------|
| 6.1 | Crear modelos: `Sinister`, `SinisterAttachment`, `SinisterComment`, `SinisterStatusHistory` | Backend |
| 6.2 | Crear schemas Pydantic para Sinister (Create, Update, Response, Filter) | Backend |
| 6.3 | Implementar `SinisterService`: create (genera código POXXXX/SOXXXX), update, status change, assign provider | Backend |
| 6.4 | Implementar generación automática de código secuencial (PO0001, PO0002... / SO0001, SO0002...) | Backend |
| 6.5 | Implementar máquina de estados: OPEN → PENDING → SENT → CLOSED → PAID | Backend |
| 6.6 | Implementar `SinisterStatusHistory`: registro automático de cada cambio de estado | Backend |
| 6.7 | Implementar `SinisterComment`: agregar/listar comentarios | Backend |
| 6.8 | Crear endpoints de siniestros (10 endpoints) | Backend |
| 6.9 | Implementar validación de monto umbral (configuración SuperAdmin) | Backend |
| 6.10 | Frontend: página de listado de siniestros con filtros y paginación | Frontend |
| 6.11 | Frontend: formulario de registro de siniestro (PO y SO) | Frontend |
| 6.12 | Frontend: vista de detalle del siniestro con timeline de estados | Frontend |
| 6.13 | Frontend: sección de comentarios del siniestro | Frontend |
| 6.14 | Frontend: selector de estado con restricciones por rol | Frontend |

### Pruebas — Sprint 6

| ID | Prueba | Tipo | Criterio de Aceptación |
|----|--------|------|------------------------|
| T6.1 | Crear siniestro tipo PO genera código POXXXX | Integración | `POST /sinisters` con type=PO → `201`, `sinister_code` = `PO0001` |
| T6.2 | Crear siniestro tipo SO genera código SOXXXX | Integración | `POST /sinisters` con type=SO → `201`, `sinister_code` = `SO0001` |
| T6.3 | Códigos secuenciales son consecutivos | Integración | Segundo PO → `PO0002`, tercer PO → `PO0003` |
| T6.4 | Transición OPEN → PENDING válida | Integración | `PUT /sinisters/{id}/status` body=PENDING → `200` |
| T6.5 | Transición OPEN → CLOSED inválida (salto de estado) | Integración | `PUT /sinisters/{id}/status` body=CLOSED → `400` |
| T6.6 | Transición PAID → OPEN inválida (retroceso) | Integración | `PUT /sinisters/{id}/status` body=OPEN → `400` |
| T6.7 | Cambio de estado crea registro en historial | Integración | `PUT /status` → verificar nueva entrada en `sinister_status_history` |
| T6.8 | Asignar proveedor a siniestro | Integración | `PUT /sinisters/{id}/assign` → `200`, `provider_id` actualizado |
| T6.9 | Agregar comentario al siniestro | Integración | `POST /sinisters/{id}/comments` → `201` |
| T6.10 | Obtener comentarios del siniestro | Integración | `GET /sinisters/{id}/comments` retorna lista ordenada por fecha |
| T6.11 | Solo Admin/SuperAdmin pueden cerrar siniestro | Integración | RegisterUser → `PUT /status` body=CLOSED → `403` |
| T6.12 | Monto bajo umbral no se considera siniestro | Unitaria | PO con amount < threshold → flag `below_threshold=true` |
| T6.13 | Frontend: formulario PO vs SO muestra campos adecuados | E2E | Tipo PO muestra campos de pérdida operativa, SO muestra campos de robo |
| T6.14 | Frontend: timeline muestra historial de estados | E2E | Detalle del siniestro muestra línea de tiempo con fechas y usuarios |

```python
# tests/test_sinisters.py
class TestSinisterCreation:
    async def test_create_po_generates_code(self, admin_client):
        """T6.1: Código POXXXX generado automáticamente"""
        payload = {
            "type": "PO",
            "description": "Pérdida en operación de caja",
            "amount": 5000.00,
            "subsidiary_id": "uuid-sub"
        }
        response = await admin_client.post("/api/v1/sinisters", json=payload)
        assert response.status_code == 201
        assert response.json()["sinister_code"].startswith("PO")
        assert response.json()["status"] == "OPEN"

    async def test_sequential_codes(self, admin_client):
        """T6.3: Códigos son secuenciales"""
        codes = []
        for _ in range(3):
            r = await admin_client.post("/api/v1/sinisters", json={
                "type": "PO", "description": "Test", "amount": 100
            })
            codes.append(r.json()["sinister_code"])
        assert codes == ["PO0001", "PO0002", "PO0003"]

class TestSinisterStateMachine:
    async def test_valid_transition(self, admin_client, open_sinister_id):
        """T6.4: Transición válida OPEN → PENDING"""
        response = await admin_client.put(
            f"/api/v1/sinisters/{open_sinister_id}/status",
            json={"status": "PENDING"}
        )
        assert response.status_code == 200

    async def test_invalid_skip_transition(self, admin_client, open_sinister_id):
        """T6.5: Transición inválida OPEN → CLOSED"""
        response = await admin_client.put(
            f"/api/v1/sinisters/{open_sinister_id}/status",
            json={"status": "CLOSED"}
        )
        assert response.status_code == 400

    async def test_status_change_creates_history(self, admin_client, open_sinister_id, db_session):
        """T6.7: Historial de cambio de estado"""
        await admin_client.put(
            f"/api/v1/sinisters/{open_sinister_id}/status",
            json={"status": "PENDING"}
        )
        from app.models.sinister import SinisterStatusHistory
        history = await db_session.execute(
            select(SinisterStatusHistory).where(
                SinisterStatusHistory.sinister_id == open_sinister_id
            )
        )
        records = history.scalars().all()
        assert len(records) == 1
        assert records[0].previous_status == "OPEN"
        assert records[0].new_status == "PENDING"
```

---

## Sprint 7 — Proveedores, Notificaciones y Tareas Programadas

**Objetivo:** Implementar gestión de proveedores, envío de emails y tareas Celery Beat.

### Tareas

| # | Tarea | Responsable |
|---|-------|-------------|
| 7.1 | Crear modelos: `Provider`, `ProviderContact` | Backend |
| 7.2 | Implementar `ProviderService`: CRUD, agregar contactos | Backend |
| 7.3 | Crear endpoints de proveedores (6 endpoints) | Backend |
| 7.4 | Implementar `EmailService` con fastapi-mail (aiosmtplib) | Backend |
| 7.5 | Crear endpoints de email: `GET /email/test`, `POST /email/send` | Backend |
| 7.6 | Implementar notificación automática al asignar siniestro a proveedor | Backend |
| 7.7 | Configurar Celery Beat con schedule de 4 tareas programadas | Backend |
| 7.8 | Implementar tarea: alerta de expiración de proceso (7, 3, 1 días) | Backend |
| 7.9 | Implementar tarea: notificación de proceso expirado (1-5 días) | Backend |
| 7.10 | Implementar tarea: alerta de expiración de póliza (90, 60, 30, 7, 1 días) | Backend |
| 7.11 | Implementar tarea: notificación de póliza vencida | Backend |
| 7.12 | Implementar toggles de activación/desactivación por tarea | Backend |
| 7.13 | Frontend: gestión de proveedores (CRUD + contactos) | Frontend |
| 7.14 | Frontend: vista de proveedor con sus siniestros asignados | Frontend |

### Pruebas — Sprint 7

| ID | Prueba | Tipo | Criterio de Aceptación |
|----|--------|------|------------------------|
| T7.1 | Crear proveedor con contactos | Integración | `POST /providers` → `201`, proveedor creado |
| T7.2 | Agregar contacto a proveedor | Integración | `POST /providers/{id}/contacts` → `201`, contacto agregado |
| T7.3 | Obtener proveedor con contactos | Integración | `GET /providers/{id}` retorna proveedor + array de contactos |
| T7.4 | Soft-delete de proveedor | Integración | `DELETE /providers/{id}` → `200`, `is_active=False` |
| T7.5 | Enviar email de prueba | Integración | `GET /email/test` → `200`, email enviado (verificar con MailHog en dev) |
| T7.6 | Enviar email a usuario por ID | Integración | `POST /email/send` con user_id → `200`, email recibido |
| T7.7 | Asignar siniestro a proveedor envía notificación | Integración | `PUT /sinisters/{id}/assign` → verificar email enviado al proveedor |
| T7.8 | Tarea: alerta de expiración a 7 días | Unitaria | Proceso con `end_date` en 7 días → función retorna email a enviar |
| T7.9 | Tarea: no alerta si proceso ya cerrado | Unitaria | Proceso CLOSED con `end_date` en 3 días → no genera email |
| T7.10 | Tarea: alerta de póliza vencida | Unitaria | ProcessPolicy pasada de `end_date` en estado OPEN → genera email |
| T7.11 | Toggle global desactiva todas las tareas | Unitaria | `SCHEDULER_GLOBAL_ENABLED=false` → ninguna tarea ejecuta |
| T7.12 | Toggle individual desactiva tarea específica | Unitaria | `PROCESS_EXPIRATION_ENABLED=false` → solo esa tarea no ejecuta |
| T7.13 | Celery Beat ejecuta tareas en horarios configurados | Integración | Tarea programada a las 8:00 → verificar ejecución a las 8:00 |
| T7.14 | Frontend: CRUD de proveedor funciona E2E | E2E | Crear, editar, ver detalle, eliminar proveedor |

```python
# tests/test_providers.py
class TestProviders:
    async def test_create_provider(self, admin_client):
        """T7.1: Crear proveedor"""
        payload = {
            "name": "Seguros Ecuador",
            "ruc": "1790123456001",
            "email": "contacto@segurosecuador.com",
            "phone": "+593999888777"
        }
        response = await admin_client.post("/api/v1/providers", json=payload)
        assert response.status_code == 201

    async def test_add_contact(self, admin_client, provider_id):
        """T7.2: Agregar contacto"""
        payload = {
            "name": "María López",
            "email": "maria@segurosecuador.com",
            "phone": "+593988777666",
            "role": "Gerente de Siniestros"
        }
        response = await admin_client.post(f"/api/v1/providers/{provider_id}/contacts", json=payload)
        assert response.status_code == 201

# tests/test_scheduled_tasks.py
class TestScheduledTasks:
    def test_process_expiration_warning_7_days(self, db_session):
        """T7.8: Alerta a 7 días de expiración"""
        from app.tasks.notifications import process_expiration_warning
        from datetime import datetime, timedelta
        # Crear proceso que expira en 7 días
        process = create_test_process(
            db_session, end_date=datetime.utcnow() + timedelta(days=7)
        )
        emails = process_expiration_warning()
        assert len(emails) == 1
        assert "7 días" in emails[0]["body"]

    def test_no_alert_for_closed_process(self, db_session):
        """T7.9: No alerta proceso cerrado"""
        from app.tasks.notifications import process_expiration_warning
        process = create_test_process(
            db_session, end_date=datetime.utcnow() + timedelta(days=3), status="CLOSED"
        )
        emails = process_expiration_warning()
        assert len(emails) == 0
```

---

## Sprint 8 — Reportes, Auditoría y Frontend Avanzado

**Objetivo:** Implementar reportes exportables, sistema de auditoría completo y funcionalidades avanzadas del frontend.

### Tareas

| # | Tarea | Responsable |
|---|-------|-------------|
| 8.1 | Implementar `AuditMiddleware`: intercepta POST/PUT/PATCH/DELETE y registra en `audit_history` | Backend |
| 8.2 | Crear modelo `AuditHistory` y endpoints de auditoría (6 endpoints) | Backend |
| 8.3 | Implementar reporte de siniestros con filtros (año, mes, tipo, proveedor) | Backend |
| 8.4 | Implementar exportación de reportes de siniestros (PDF, Excel, CSV) | Backend |
| 8.5 | Implementar acciones de proceso: `POST /process-actions` (REOPEN/CLOSED con cascada) | Backend |
| 8.6 | Implementar cascada de estado: Process → ProcessPolicy → PolicyQuestionary → UserQuestion → UserAnswer | Backend |
| 8.7 | Implementar procesamiento Excel multi-riesgos (upload + validación + agrupación asíncrona) | Backend |
| 8.8 | Frontend: dashboard principal con KPIs (procesos activos, siniestros abiertos, pólizas por vencer) | Frontend |
| 8.9 | Frontend: vista de log de auditoría con filtros | Frontend |
| 8.10 | Frontend: reportes de siniestros con filtros y exportación | Frontend |
| 8.11 | Frontend: reportes de pólizas con filtros (Año, Periodo, DSLA, Costos) | Frontend |
| 8.12 | Frontend: configuración del sistema (paletas de color por subsidiaria) | Frontend |
| 8.13 | Frontend: componente de upload Excel para multi-riesgos | Frontend |

### Pruebas — Sprint 8

| ID | Prueba | Tipo | Criterio de Aceptación |
|----|--------|------|------------------------|
| T8.1 | Middleware registra operación POST exitosa | Integración | `POST /processes` → audit_history contiene entrada con action=CREATE |
| T8.2 | Middleware no registra en operaciones GET | Integración | `GET /processes` → no crea entrada en audit_history |
| T8.3 | Middleware no registra en operaciones fallidas (4xx/5xx) | Integración | `POST /processes` con datos inválidos (422) → no crea entrada |
| T8.4 | Consultar auditoría por entidad | Integración | `GET /audit/entity/Process` retorna historial de procesos |
| T8.5 | Consultar auditoría por rango de fechas | Integración | `GET /audit/date-range?from=X&to=Y` retorna entradas en rango |
| T8.6 | Acción REOPEN propaga cascada completa | Integración | `POST /process-actions` REOPEN → Process, ProcessPolicies, PolicyQuestionnaires, UserQuestions, UserAnswers vuelven a ACTIVE |
| T8.7 | Acción CLOSED propaga cascada completa | Integración | `POST /process-actions` CLOSED → toda la jerarquía cambia a CLOSED |
| T8.8 | Reporte de siniestros filtrado por año y tipo | Integración | `GET /sinisters/reports/filter?year=2024&type=PO` retorna solo PO de 2024 |
| T8.9 | Exportar reporte a Excel | Integración | `GET /sinisters/reports/export?format=xlsx` → Content-Type xlsx, archivo válido |
| T8.10 | Exportar reporte a CSV | Integración | `GET /sinisters/reports/export?format=csv` → Content-Type csv, datos separados por comas |
| T8.11 | Upload Excel multi-riesgos: formato válido | Integración | `POST` con Excel válido → `202 Accepted`, tarea Celery procesando |
| T8.12 | Upload Excel multi-riesgos: formato inválido | Integración | `POST` con Excel sin estructura requerida → `400` con detalle |
| T8.13 | Frontend: dashboard muestra KPIs actualizados | E2E | Dashboard muestra conteos correctos de procesos y siniestros |
| T8.14 | Frontend: exportar reporte descarga archivo | E2E | Click "Exportar Excel" → descarga archivo .xlsx |

```python
# tests/test_audit.py
class TestAuditMiddleware:
    async def test_post_creates_audit_entry(self, admin_client, db_session):
        """T8.1: POST exitoso crea entrada de auditoría"""
        await admin_client.post("/api/v1/processes", json={
            "name": "Audit Test", "start_date": "2024-01-01", "end_date": "2024-12-31"
        })
        from app.models.audit_history import AuditHistory
        entries = await db_session.execute(
            select(AuditHistory).where(AuditHistory.entity_name == "Process")
        )
        assert len(entries.scalars().all()) >= 1

    async def test_get_no_audit(self, admin_client, db_session):
        """T8.2: GET no genera auditoría"""
        count_before = await count_audit_entries(db_session)
        await admin_client.get("/api/v1/processes")
        count_after = await count_audit_entries(db_session)
        assert count_after == count_before

# tests/test_cascade.py
class TestCascadeActions:
    async def test_reopen_cascade(self, admin_client, closed_process_hierarchy):
        """T8.6: REOPEN propaga a toda la jerarquía"""
        process_id = closed_process_hierarchy["process_id"]
        response = await admin_client.post("/api/v1/process-actions", json={
            "process_id": process_id, "action": "REOPEN"
        })
        assert response.status_code == 200
        # Verificar que todo volvió a ACTIVE
        process = await admin_client.get(f"/api/v1/processes/{process_id}")
        assert process.json()["status"] == "ACTIVE"
        # Verificar process-policies
        pps = await admin_client.get(f"/api/v1/process-policies/filter?process_id={process_id}")
        for pp in pps.json()["items"]:
            assert pp["status"] == "ACTIVE"
```

---

## Sprint 9 — Integración, QA y Despliegue

**Objetivo:** Pruebas de integración end-to-end, corrección de defectos, optimización de rendimiento y preparación para producción.

### Tareas

| # | Tarea | Responsable |
|---|-------|-------------|
| 9.1 | Pruebas E2E completas del flujo de pólizas (proceso → póliza → cuestionario → respuestas → PDF) | QA |
| 9.2 | Pruebas E2E completas del flujo de siniestros (registro → asignación → proveedor → cierre → pago) | QA |
| 9.3 | Pruebas de carga: verificar respuesta < 500ms para CRUD y < 5s para operaciones complejas | QA |
| 9.4 | Pruebas de seguridad: inyección SQL, XSS, CSRF, validación de tokens | Seguridad |
| 9.5 | Revisión de código y refactorización de deuda técnica | Equipo |
| 9.6 | Optimizar consultas N+1 y agregar índices a SQL Server | Backend |
| 9.7 | Configurar Uvicorn con workers=4 para producción | DevOps |
| 9.8 | Configurar Nginx para servir frontend con compresión gzip y cache | DevOps |
| 9.9 | Crear scripts de seeding de datos iniciales (catálogos, usuarios) | Backend |
| 9.10 | Documentar API completa en Swagger con ejemplos de request/response | Backend |
| 9.11 | Preparar runbook de despliegue y rollback | DevOps |
| 9.12 | Despliegue en ambiente de staging | DevOps |
| 9.13 | UAT (User Acceptance Testing) con usuarios finales del banco | QA / Negocio |
| 9.14 | Corrección de defectos encontrados en UAT | Equipo |
| 9.15 | Despliegue a producción | DevOps |

### Pruebas — Sprint 9

| ID | Prueba | Tipo | Criterio de Aceptación |
|----|--------|------|------------------------|
| T9.1 | **Flujo completo de pólizas E2E** | E2E | Admin crea proceso → asigna pólizas → asigna cuestionarios → usuario responde → 100% completitud → genera PDF → descarga exitosa |
| T9.2 | **Flujo completo de siniestros E2E** | E2E | Admin crea siniestro PO → asigna proveedor (email enviado) → proveedor agrega comentario → admin cierra → admin marca pagado → historial completo |
| T9.3 | **Rendimiento: CRUD < 500ms** | Carga | 100 requests concurrentes a `GET /processes` → P95 < 500ms |
| T9.4 | **Rendimiento: PDF < 5s** | Carga | Generación de PDF con cuestionario de 50 preguntas → tiempo < 5s |
| T9.5 | **Seguridad: tokens expirados** | Seguridad | Request con JWT expirado → `401`, no se filtra información |
| T9.6 | **Seguridad: inyección SQL** | Seguridad | `GET /processes/filter?name='; DROP TABLE--` → `422` o resultado vacío (sin error de BD) |
| T9.7 | **Seguridad: XSS en comentarios** | Seguridad | Comentario con `<script>alert(1)</script>` → se almacena escapado, no ejecuta en frontend |
| T9.8 | **Seguridad: RBAC completo** | Seguridad | Cada endpoint validado con todos los roles: sólo los autorizados acceden |
| T9.9 | **Concurrencia: respuestas simultáneas** | Carga | 10 usuarios respondiendo el mismo cuestionario simultáneamente → sin duplicados ni corrupción |
| T9.10 | **Notificaciones: email de expiración** | E2E | Proceso a 1 día de expirar → tarea Celery ejecuta → email recibido en MailHog |
| T9.11 | **Cascada: cierre de proceso completo** | E2E | Cerrar proceso con 3 pólizas, 5 cuestionarios, 100 preguntas → toda la jerarquía en CLOSED |
| T9.12 | **Responsive: vista en móvil** | E2E | Listado de procesos y siniestros es usable en viewport 375px |
| T9.13 | **UAT: flujo de usuario de registro** | Aceptación | Usuario del banco completa cuestionario sin asistencia técnica |
| T9.14 | **UAT: flujo de proveedor** | Aceptación | Proveedor accede, ve siniestros asignados, agrega comentarios |

```python
# tests/test_e2e_full_flow.py
class TestFullPolicyFlow:
    async def test_complete_policy_lifecycle(self, admin_client, register_user_client):
        """T9.1: Flujo completo de pólizas"""
        # 1. Crear proceso
        process = await admin_client.post("/api/v1/processes", json={
            "name": "Revisión Anual 2024", "start_date": "2024-01-01", "end_date": "2024-12-31"
        })
        process_id = process.json()["id"]

        # 2. Asignar póliza
        policies = await admin_client.get("/api/v1/policies")
        policy_id = policies.json()[0]["id"]
        pp = await admin_client.post("/api/v1/process-policies", json={
            "process_id": process_id, "policy_ids": [policy_id]
        })
        pp_id = pp.json()[0]["id"]

        # 3. Asignar cuestionario
        questionnaires = await admin_client.get(f"/api/v1/questionnaires/by-policy/{policy_id}")
        q_id = questionnaires.json()[0]["id"]
        pq = await admin_client.post("/api/v1/policy-questionnaires/assign-single", json={
            "process_policy_id": pp_id, "questionary_id": q_id
        })
        pq_id = pq.json()["id"]

        # 4. Esperar creación asíncrona de UserQuestions
        import asyncio
        await asyncio.sleep(2)

        # 5. Obtener y responder todas las preguntas
        questions = await register_user_client.get(
            f"/api/v1/user-questions/policy-questionary/{pq_id}"
        )
        answers = [
            {"user_question_id": q["id"], "answer": "Respuesta", "answer_type": "NORMAL"}
            for q in questions.json()
        ]
        await register_user_client.post("/api/v1/user-answers/batch", json=answers)

        # 6. Verificar completitud 100%
        stats = await admin_client.get(
            f"/api/v1/policy-questionnaires/{pq_id}/user-questions/stats"
        )
        assert stats.json()["percentage"] == 100

        # 7. Generar PDF
        pdf = await admin_client.get(f"/api/v1/reports/{pq_id}")
        assert pdf.status_code == 200
        assert pdf.headers["content-type"] == "application/pdf"

class TestFullSinisterFlow:
    async def test_complete_sinister_lifecycle(self, admin_client, provider_client):
        """T9.2: Flujo completo de siniestros"""
        # 1. Crear siniestro
        sinister = await admin_client.post("/api/v1/sinisters", json={
            "type": "PO", "description": "Pérdida en caja", "amount": 15000
        })
        sin_id = sinister.json()["id"]
        assert sinister.json()["status"] == "OPEN"

        # 2. Crear proveedor
        provider = await admin_client.post("/api/v1/providers", json={
            "name": "Aseguradora XYZ", "ruc": "123", "email": "xyz@test.com"
        })
        prov_id = provider.json()["id"]

        # 3. Asignar proveedor (envía email)
        await admin_client.put(f"/api/v1/sinisters/{sin_id}/assign", json={
            "provider_id": prov_id
        })

        # 4. Avanzar estados
        await admin_client.put(f"/api/v1/sinisters/{sin_id}/status", json={"status": "PENDING"})
        await admin_client.put(f"/api/v1/sinisters/{sin_id}/status", json={"status": "SENT"})

        # 5. Proveedor agrega comentario
        await provider_client.post(f"/api/v1/sinisters/{sin_id}/comments", json={
            "comment": "Inspección completada, daño confirmado"
        })

        # 6. Cerrar y pagar
        await admin_client.put(f"/api/v1/sinisters/{sin_id}/status", json={"status": "CLOSED"})
        await admin_client.put(f"/api/v1/sinisters/{sin_id}/status", json={"status": "PAID"})

        # 7. Verificar historial completo
        detail = await admin_client.get(f"/api/v1/sinisters/{sin_id}")
        assert detail.json()["status"] == "PAID"
        comments = await admin_client.get(f"/api/v1/sinisters/{sin_id}/comments")
        assert len(comments.json()) >= 1
```

---

## Resumen de Métricas de Pruebas

| Sprint | Unitarias | Integración | E2E | Total |
|--------|-----------|-------------|-----|-------|
| Sprint 0 | 1 | 3 | 2 | **7** |
| Sprint 1 | 4 | 7 | 4 | **15** |
| Sprint 2 | 2 | 7 | 2 | **12** (acumulado 34) |
| Sprint 3 | 1 | 8 | 2 | **11** (acumulado 45) |
| Sprint 4 | 0 | 9 | 3 | **14** (acumulado 59) |
| Sprint 5 | 1 | 9 | 2 | **13** (acumulado 72) |
| Sprint 6 | 1 | 10 | 2 | **14** (acumulado 86) |
| Sprint 7 | 4 | 7 | 1 | **14** (acumulado 100) |
| Sprint 8 | 0 | 10 | 2 | **14** (acumulado 114) |
| Sprint 9 | 0 | 0 | 14 | **14** (acumulado 128) |
| **Total** | **14** | **70** | **34** | **128** |

## Herramientas de Testing

| Herramienta | Uso |
|-------------|-----|
| **pytest** + **httpx** (AsyncClient) | Tests unitarios e integración del backend |
| **Vitest** + **React Testing Library** | Tests unitarios de componentes React |
| **Playwright** o **Cypress** | Tests E2E del frontend |
| **Locust** o **k6** | Tests de carga y rendimiento |
| **MailHog** | Servidor SMTP mock para verificar emails en dev |
| **pytest-celery** | Testing de tareas Celery |
| **Factory Boy** | Generación de datos de prueba (fixtures) |

## Criterios de Definition of Done (DoD)

Cada sprint se considera completado cuando:

1. Todas las tareas del sprint están implementadas
2. Todas las pruebas del sprint pasan (verde)
3. Cobertura de código backend ≥ 80%
4. Sin errores críticos o bloqueantes abiertos
5. Code review aprobado por al menos un par
6. Documentación de API actualizada en Swagger
7. Demo exitosa al Product Owner

---

## Dependencias entre Sprints

```
Sprint 0 (Infraestructura)
    │
    ├── Sprint 1 (Auth + Usuarios) ──────────────────┐
    │       │                                         │
    │       ├── Sprint 2 (Procesos)                   │
    │       │       │                                 │
    │       │       └── Sprint 3 (Proceso-Póliza)     │
    │       │               │                         │
    │       │               └── Sprint 4 (Preguntas)  │
    │       │                       │                 │
    │       │                       └── Sprint 5 (Adjuntos + PDF)
    │       │                                         │
    │       └── Sprint 6 (Siniestros) ◄───────────────┘
    │               │
    │               └── Sprint 7 (Proveedores + Notificaciones)
    │
    └── Sprint 8 (Reportes + Auditoría + UI Avanzado) ◄── depende de Sprints 1-7
            │
            └── Sprint 9 (Integración + QA + Deploy)
```

> **Nota:** Los sprints 6 y 7 (Siniestros y Proveedores) pueden ejecutarse en paralelo con los sprints 3-5 (Pólizas) si se dispone de dos equipos de desarrollo, ya que comparten únicamente la dependencia del Sprint 1 (Autenticación).
