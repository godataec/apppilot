# Technical Specifications — PlataformaBI (Portal Administrativo)

> Insurance policy management platform for Banco Internacional.  
> **Stack:** Java 8 · Spring Boot 2.7.18 · SQL Server · SAML 2.0 / JWT · JBoss EAP

---

## Table of Contents

1. [System Overview](#1-system-overview)
2. [Entity Hierarchy](#2-entity-hierarchy)
3. [Business Flows](#3-business-flows)
4. [Authentication & Authorization Flow](#4-authentication--authorization-flow)
5. [API Endpoint Catalog](#5-api-endpoint-catalog)
6. [Scheduled Tasks](#6-scheduled-tasks)
7. [Cross-Cutting Concerns](#7-cross-cutting-concerns)
8. [Configuration Summary](#8-configuration-summary)
9. [Enums & Status Model](#9-enums--status-model)

---

## 1. System Overview

### Architecture

```
┌────────────────┐     SAML2      ┌──────────────┐     JWT Bearer    ┌─────────────────┐
│  Azure AD /    │◄──────────────►│ plataformaBI │◄──────────────────│ Angular Frontend │
│  Entra ID (IdP)│                │   -web (WAR) │                   │ localhost:4200   │
└────────────────┘                └──────┬───────┘                   └─────────────────┘
                                         │
                                    ┌────▼────┐
                                    │ SQL     │
                                    │ Server  │
                                    │(bipolizas)│
                                    └─────────┘
```

| Module | Packaging | Role |
|---|---|---|
| `plataformaBI-web` | WAR | All business logic, controllers, services, entities, security, API |
| `plataformaBI-ear` | EAR | Enterprise archive wrapping WAR for JBoss deployment |
| `bke-jva-portalAdministrativo-plataformaBI` | WAR | Legacy standalone module — **not active, do not extend** |

### Key Frameworks

| Component | Technology |
|---|---|
| Language | Java 8 |
| Framework | Spring Boot 2.7.18 |
| ORM | Spring Data JPA + Hibernate (SQL Server dialect) |
| Auth (IdP) | SAML 2.0 via Azure AD / Entra ID |
| Auth (API) | JWT (HS256, JJWT 0.11.5) |
| Mapping | MapStruct 1.5.5 + Lombok 1.18.34 |
| PDF | Thymeleaf → openhtmltopdf / Flying Saucer |
| API Docs | springdoc-openapi 1.7.0 (Swagger UI) |
| Logging | Log4j2 |
| Mail | Spring Mail (SMTP) |
| App Server | JBoss EAP / WildFly |

---

## 2. Entity Hierarchy

All entities use **UUID primary keys** and **soft-delete** (`is_deleted` boolean). Status changes cascade downward through the full hierarchy.

```
Process (insurance review period)
│   Fields: name, subsidiaryId, startDate, endDate, status, percentage
│
└── ProcessPolicy (links Process ↔ Policy template)
    │   Fields: processId, policyId, status, percentage, startDate, endDate
    │
    └── PolicyQuestionary (assigns questionnaire to a process-policy)
        │   Fields: processPolicyId, questionaryId, status, startDate, endDate
        │
        └── UserQuestion (assigns a question to a specific user)
            │   Fields: policyQuestionaryId, questionId, userAssigned, status
            │
            └── UserAnswer (user's response to a question)
                │   Fields: userQuestionId, answer, answerType, userAssigned, createdBy
                │
                └── Attachment (file uploads — also supported at every parent level)
                    Fields: dataFile (bytes), preview (bytes), name, associations to parent entities
```

---

## 3. Business Flows

### 3.1 Process Lifecycle (Insurance Period Management)

**Purpose:** Create and manage time-bounded insurance review periods.

```
                    ┌──────────┐
       create ──────►  ACTIVE  │◄──────── reopen (ActionService)
                    └────┬─────┘
                         │ all policies resolved
                         ▼
                    ┌──────────┐
                    │ RESOLVED │
                    └────┬─────┘
                         │ manual close
                         ▼
                    ┌──────────┐
                    │  CLOSED  │
                    └──────────┘
```

1. **Create Process** → `POST /process` → `ProcessService.save()`  
   - Sets default subsidiary (1L), normalizes dates (startDate → 00:00:00, endDate → 23:59:59)
2. **Assign Policies** → `POST /process-policy` (bulk) → `ProcessPolicyService.saveProcessPolicy()`  
   - Validates Process and Policy existence before linking
3. **Assign Questionnaires** → `POST /policy-questionnaires/assign` → `PolicyQuestionaryService.assignQuestionaryToProcess()`  
   - Prevents duplicate assignment; **asynchronously** creates `UserQuestion` records via `UserQuestionAsyncService`
4. **Users Answer Questions** → `POST /user-answers` or `/user-answers/batch`  
   - Duplicate prevention (per userQuestion + userAssigned); batch mode skips validation
5. **Track Progress** → Percentages auto-calculated:  
   - Process % = (RESOLVED + CLOSED policies) / total × 100  
   - ProcessPolicy % = (RESOLVED + CLOSED questionnaires) / total × 100  
   - PolicyQuestionary % = solved questions / total questions × 100
6. **Close / Reopen** → `POST /process-actions` → `ActionService.createAction()`  
   - **Full cascade:** status change propagates Process → ProcessPolicy → PolicyQuestionary → UserQuestion → UserAnswer
7. **Soft Delete** → `DELETE /process/{id}` → cascades soft-delete to all child ProcessPolicies

### 3.2 Authentication Flow

```
┌──────────┐   1. SAML Request    ┌───────────┐   2. Authenticate    ┌──────────┐
│  Angular  │────────────────────►│  Backend   │────────────────────►│ Azure AD │
│ Frontend  │                     │ (SAML CFG) │                     │ (IdP)    │
└──────────┘                     └───────────┘                     └──────────┘
     ▲                                  │                                │
     │   5. Bearer JWT on all           │   3. SAML Response             │
     │      API requests                │      + role assertions         │
     │                                  ◄────────────────────────────────┘
     │                                  │
     │   4. Redirect to /auth/callback  │
     │      with session cookie         │
     └──────────────────────────────────┘
     │
     │   POST /api/auth/token  ────►  Generate JWT (HS256, 24h expiry)
     │                                 Claims: email, name, roles
```

**Two Security Filter Chains (ordered):**

| Chain | Order | Paths | Auth Method |
|---|---|---|---|
| SAML | 1 | `/saml2/**`, `/login/saml2/**`, `/saml/logout`, `/api/auth/token` | SAML 2.0 (Azure AD) |
| JWT | 2 | `/api/**`, `/**` | Bearer JWT (HS256) |

**Roles:** `SuperAdmin`, `Admin`, `RegisterUser` — extracted from SAML assertions, encoded in JWT.

### 3.3 SAML Logout Flow

Two paths:

- **SP-initiated** (`GET /saml/logout?returnTo=`): Builds SAML `LogoutRequest` XML, DEFLATE + Base64 encodes, redirects to Azure AD SLO endpoint
- **IdP-initiated** (`POST /saml/slo`): Receives `SAMLRequest`/`SAMLResponse` from Azure AD, invalidates HTTP session

### 3.4 Azure AD User Sync

`POST /settings/sync-azure-ad-users` → `AzureADSyncService.synchronizeUsers()`

1. Acquires OAuth2 access token via client credentials flow (Graph API scope)
2. Reads app role assignments → groups → group members
3. Maps Azure AD roles → platform roles (`SUPERADMIN` / `ADMIN` / `USER`)
4. For each user: upsert via `UserService.verifyAndSaveUser()` (create if new, update if name changed, skip if unchanged)

### 3.5 PDF Report Generation

`GET /report/{type}/{policyQuestionaryId}` → form-specific service → `PDFGeneratorService`

1. Service loads questionnaire answers from DB for the `policyQuestionaryId`
2. Populates a Thymeleaf context with the data
3. Renders HTML template from `resources/templates/{BBB|CYBER|DO}/`
4. Converts HTML → PDF via **openhtmltopdf** (with Flying Saucer fallback)
5. Returns PDF as `application/pdf` byte stream

**Available report types (11):**

| Endpoint Suffix | Template | Insurance Type |
|---|---|---|
| `/appendix/{id}` | `BBB/AppendixForm.html` | BBB |
| `/covid/{id}` | `BBB/CovidForm.html` | BBB |
| `/crime/{id}` | `BBB/CrimeForm.html` | BBB |
| `/pi/{id}` | `BBB/PIForm.html` | BBB |
| `/cyberlock/{id}` | `CYBER/CyberLocktonProposalForm.html` | Cyber |
| `/cyberrisk/{id}` | `CYBER/CyberRiskForm.html` | Cyber |
| `/cyberriskcom/{id}` | `CYBER/CyberRiskInComplementaryInfoForm.html` | Cyber |
| `/cyberriskp/{id}` | `CYBER/CyberRisksPersonalDataForm.html` | Cyber |
| `/ransomware/{id}` | `CYBER/RansomwareForm.html` | Cyber |
| `/willis/{id}` | `DO/WillisForm.html` | D&O |
| `/{id}` | `DO/WillisForm.html` | Generic (default) |

### 3.6 File Attachment Flow

`POST /attachment` (multipart) → `AttachmentService.storeService()`

- Uploads binary file and associates it with any combination of: Process, ProcessPolicy, PolicyQuestionary, UserAnswer, Action
- Images (PNG/JPEG/JPG) get a 30×30 thumbnail preview stored alongside
- Download via `GET /attachment/download/{id}` returns raw bytes
- **Note:** Attachments use **hard delete** (exception to the project's soft-delete convention)

### 3.7 Email Notification Flow

Two email mechanisms:

1. **Manual:** `POST /send` → `EmailService.sendSimpleEmail()` — sends to a specific user by ID
2. **Scheduled:** `ProcessNotificationScheduler` → `EmailNotificationService` — automated expiration/overdue alerts (see [Section 6](#6-scheduled-tasks))

### 3.8 Audit Trail Flow

**Automatic** (AOP-driven, no explicit calls needed):

1. Service method executes (any `save*`, `create*`, `update*`, `delete*`, `remove*`, `modify*`)
2. `AuditAspect` intercepts `@AfterReturning`
3. Extracts entity name (from service class name, strips "Service") and entity ID (via reflection)
4. Delegates to `AuditService` which **asynchronously** persists an `AuditHistory` record:
   - `entityName`, `entityId`, `action` (CREATE/UPDATE/DELETE), `requestData` (JSON), `timestamp`

**Manual:** `POST /audit/log/{action}` — for explicit audit entries from frontend.

---

## 4. Authentication & Authorization Flow

### URL-Based Authorization Rules

| Path Pattern | Allowed Roles |
|---|---|
| `/actuator/**`, `/swagger-ui/**`, `/v3/api-docs/**` | Public (permitAll) |
| `/api/auth/**` | Public (permitAll) |
| `/users/**` | SuperAdmin, Admin |
| `/settings/sync-azure-ad-users` | SuperAdmin, Admin |
| `/register-user/**` | RegisterUser |
| All other `/api/**` paths | SuperAdmin, Admin, RegisterUser |

### User Data Encryption

- **Fields encrypted:** `userName`, `userLastName` (AES-256 via PBKDF2WithHmacSHA256)
- **Key source:** `encryption.key` property (base64 passphrase)
- **Salt:** hardcoded `"PlataformaBI_Salt"`, 65536 iterations
- Transparent encrypt-on-write, decrypt-on-read in `UserService`

---

## 5. API Endpoint Catalog

### 5.1 Authentication & Security (14 endpoints)

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/auth/token` | Generate JWT from SAML session |
| `GET` | `/api/auth/validate` | Validate current JWT token |
| `GET` | `/api/auth/me` | Get current user info (email, name, roles) |
| `GET` | `/api/auth/status` | Get authentication status |
| `POST` | `/api/auth/logout` | Initiate logout, returns SAML logout URL |
| `GET` | `/auth/test` | Test JWT authentication (dev) |
| `GET` | `/auth/admin-test` | Test SuperAdmin access (dev) |
| `GET` | `/auth/manager-test` | Test Admin/SuperAdmin access (dev) |
| `GET` | `/auth/profile` | Full user profile from JWT claims (dev) |
| `GET` | `/auth/role-test` | Test all role-based checks (dev) |
| `GET` | `/saml/logout` | SP-initiated SAML SLO redirect |
| `POST` | `/saml/slo` | IdP-initiated logout handler |
| `GET` | `/saml/user-info` | SAML principal attributes (dev) |
| `GET` | `/saml/metadata` | SP metadata URL |

### 5.2 Settings & Users (5 endpoints)

| Method | Path | Description |
|---|---|---|
| `POST` | `/settings/synchronize-users` | Sync users from frontend list |
| `GET` | `/settings/color` | Get active color palettes (`?subsidiaryId=` optional) |
| `POST` | `/settings/sync-azure-ad-users` | Sync users from Azure AD Graph API |
| `GET` | `/users` | Get all active users (returns `{users, total}`) |
| `GET` | `/users/{openUserId}` | Get user by OpenID |

### 5.3 Process Management (6 endpoints)

| Method | Path | Description |
|---|---|---|
| `POST` | `/process` | Create a new process |
| `PUT` | `/process/{processId}` | Update a process |
| `GET` | `/process/filter` | Filter processes (`?subsidiary, character, page, size, dateBegin, dateEnd, year`) |
| `DELETE` | `/process/{processId}` | Soft-delete process (cascades to children) |
| `GET` | `/process` | Get all active processes |
| `GET` | `/process/{processId}/validate-closure` | Validate if process can be closed |

### 5.4 Process Actions (3 endpoints)

| Method | Path | Description |
|---|---|---|
| `POST` | `/process-actions` | Create action (REOPEN/CLOSED — cascades status) |
| `GET` | `/process-actions/process/{processId}` | Get all actions for a process |
| `GET` | `/process-actions/{actionId}` | Get a specific action |

### 5.5 Process-Policy Assignment (5 endpoints)

| Method | Path | Description |
|---|---|---|
| `POST` | `/process-policy` | Bulk assign policies to a process |
| `GET` | `/process-policy/filter` | Filter assignments with pagination |
| `DELETE` | `/process-policy/{id}` | Soft-delete assignment |
| `PUT` | `/process-policy` | Bulk update process-policies |
| `PUT` | `/process-policy/{id}` | Update a single process-policy |

### 5.6 Policy Questionnaire Assignment (11 endpoints)

| Method | Path | Description |
|---|---|---|
| `POST` | `/policy-questionnaires/assign-single` | Assign one questionnaire to a process-policy |
| `POST` | `/policy-questionnaires/assign` | Bulk assign questionnaires (partial success) |
| `GET` | `/policy-questionnaires/by-process-policy/{id}` | Get questionnaires for a process-policy |
| `GET` | `/policy-questionnaires/detailed/by-process-policy/{id}` | Get detailed questionnaire info |
| `DELETE` | `/policy-questionnaires/{id}` | Remove questionnaire assignment |
| `PUT` | `/policy-questionnaires/{id}/status` | Update questionnaire status |
| `PUT` | `/policy-questionnaires/update` | Full update of assignment |
| `GET` | `/policy-questionnaires/{id}/user-questions/stats` | UserQuestion count stats |
| `DELETE` | `/policy-questionnaires/soft-delete/{id}` | Alternate soft-delete |
| `GET` | `/policy-questionnaires/filter` | Filter with pagination |
| `GET` | `/policy-questionnaires/user/{userUUID}` | Get assignments for a user |

### 5.7 User Questions (6 endpoints)

| Method | Path | Description |
|---|---|---|
| `PUT` | `/user-questions/assignment` | Update user assigned to a question |
| `PUT` | `/user-questions/status` | Update question status |
| `GET` | `/user-questions/{id}` | Get by ID |
| `GET` | `/user-questions/policy-questionary/{id}` | Get all for a policy-questionary |
| `DELETE` | `/user-questions/{id}` | Soft-delete |
| `GET` | `/user-questions/filter` | Filter/paginate |

### 5.8 User Answers (8 endpoints)

| Method | Path | Description |
|---|---|---|
| `POST` | `/user-answers` | Submit a single answer |
| `POST` | `/user-answers/batch` | Submit multiple answers |
| `PUT` | `/user-answers` | Update an answer |
| `GET` | `/user-answers/{id}` | Get by ID |
| `GET` | `/user-answers/user-question/{id}` | Get answers for a user-question |
| `GET` | `/user-answers/policy-questionary/{id}` | Get answers for a policy-questionary |
| `GET` | `/user-answers/user/{userAssigned}` | Get answers by user |
| `DELETE` | `/user-answers/{id}` | Soft-delete |

### 5.9 Attachments (11 endpoints)

| Method | Path | Description |
|---|---|---|
| `POST` | `/attachment` | Upload file (multipart, with optional parent associations) |
| `GET` | `/attachment/process/{id}` | Get files by process |
| `GET` | `/attachment/process-policy/{id}` | Get files by process-policy |
| `GET` | `/attachment/policy-questionary/{id}` | Get files by policy-questionary |
| `GET` | `/attachment/questionaryAndAnswer/{pqId}/{answerId}` | Get files by questionary + answer |
| `GET` | `/attachment/user-answer/{id}` | Get files by user answer |
| `GET` | `/attachment/process-action/{id}` | Get files by action |
| `GET` | `/attachment/questionimages` | Get image previews |
| `GET` | `/attachment/download/{id}` | Download file |
| `DELETE` | `/attachment/{id}` | **Hard delete** file |

### 5.10 Template / Catalog Data (6 endpoints)

| Method | Path | Description |
|---|---|---|
| `GET` | `/policy` | Get all insurance policy types |
| `GET` | `/policy/unassigned` | Get policies not yet assigned |
| `GET` | `/questionnaires/available` | Get available questionnaires for a policy |
| `GET` | `/questionnaires/by-policy/{id}` | Get questionnaires for a policy |
| `GET` | `/questionnaires` | Get all active questionnaires |
| `GET` | `/question-options/question/{id}` | Get options for a question |
| `POST` | `/question-options/questions/batch` | Get options for multiple questions |

### 5.11 Audit (6 endpoints)

| Method | Path | Description |
|---|---|---|
| `GET` | `/audit/entity/{entityName}` | Audit history by entity type |
| `GET` | `/audit/entity-id/{entityId}` | Audit history by entity ID |
| `GET` | `/audit/action/{action}` | Audit history by action (CREATE/UPDATE/DELETE) |
| `GET` | `/audit/date-range` | Audit history within date range |
| `GET` | `/audit/entity/{name}/id/{id}` | Audit history by entity name + ID |
| `POST` | `/audit/log/{action}` | Manual audit log entry |

### 5.12 Reports — PDF Generation (11 endpoints)

| Method | Path | Template |
|---|---|---|
| `GET` | `/report/appendix/{id}` | BBB/AppendixForm |
| `GET` | `/report/covid/{id}` | BBB/CovidForm |
| `GET` | `/report/crime/{id}` | BBB/CrimeForm |
| `GET` | `/report/pi/{id}` | BBB/PIForm |
| `GET` | `/report/cyberlock/{id}` | CYBER/CyberLocktonProposal |
| `GET` | `/report/cyberrisk/{id}` | CYBER/CyberRisk |
| `GET` | `/report/cyberriskcom/{id}` | CYBER/CyberRiskComplementary |
| `GET` | `/report/cyberriskp/{id}` | CYBER/CyberRiskPersonalData |
| `GET` | `/report/ransomware/{id}` | CYBER/Ransomware |
| `GET` | `/report/willis/{id}` | DO/Willis |
| `GET` | `/report/{id}` | DO/Willis (default) |

### 5.13 Email (2 endpoints)

| Method | Path | Description |
|---|---|---|
| `GET` | `/send-email` | Send test email |
| `POST` | `/send` | Send email to user by ID |

### Summary

| Domain | Endpoints |
|---|---|
| Authentication & Security | 14 |
| Settings & Users | 5 |
| Process Management | 6 |
| Process Actions | 3 |
| Process-Policy | 5 |
| Policy Questionnaires | 11 |
| User Questions | 6 |
| User Answers | 8 |
| Attachments | 11 |
| Template / Catalog | 7 |
| Audit | 6 |
| Reports (PDF) | 11 |
| Email | 2 |
| **Total (new architecture)** | **~95** |
| Legacy endpoints (duplicated) | ~46 |

---

## 6. Scheduled Tasks

All jobs run daily, individually toggleable. Global toggle: `scheduler.global.enabled`.

| Job | Default Cron | Logic |
|---|---|---|
| Process Expiration Warning | `0 0 8 * * ?` (8 AM) | Email when active processes are **7, 3, or 1** day(s) from endDate |
| Expired Process Notification | `0 0 9 * * ?` (9 AM) | Email for processes expired **1–5 days ago** (daily reminders) |
| Policy Expiration Warning | `0 0 10 * * ?` (10 AM) | Email when ProcessPolicies are **90, 60, 30, 7, or 1** day(s) from endDate |
| Overdue Policy Notification | `0 0 11 * * ?` (11 AM) | Email for ProcessPolicies past endDate still in OPEN status |

**Dependencies:** `ProcessRepository`, `ProcessPolicyRepository`, `EmailNotificationService`  
**Recipients:** Comma-separated list in `notification.emails` property  
**Language:** Email bodies in Spanish, severity-graded by days remaining

---

## 7. Cross-Cutting Concerns

### 7.1 Audit (AOP)

- **Pointcuts:** `service.*.save*`, `create*`, `update*`, `modify*`, `delete*`, `remove*`  
- **Trigger:** `@AfterReturning` — only logs successful operations  
- **Storage:** `AuditHistory` table (entityName, entityId, action, requestData JSON, timestamp)  
- **Async:** Audit writes run asynchronously to avoid blocking main transactions

### 7.2 Global Exception Handling

`@RestControllerAdvice` — `GlobalExceptionHandler`:
- `CustomException` → returns `ErrorDetails` with the exception's HTTP status
- All other exceptions → `500 Internal Server Error` with `ErrorDetails` (status, timestamp, message, request path)

### 7.3 Async Processing

- Thread pool: `userQuestionTaskExecutor` (core=2, max=5, queue=100)
- Used by: `UserQuestionAsyncService` — creates `UserQuestion` records when a questionnaire is assigned

### 7.4 CORS

- Configured in `SpringSecurityConfig` from `cors.allowed-origins` property (comma-separated)
- Credentials enabled; methods `GET, POST, PUT, DELETE, OPTIONS`

### 7.5 API Documentation

- Swagger UI at `/swagger-ui/`
- OpenAPI 3.0 spec at `/v3/api-docs`
- JWT Bearer auth scheme configured

---

## 8. Configuration Summary

### Key Properties (`application.properties`)

| Property | Purpose |
|---|---|
| `spring.datasource.*` | SQL Server connection (database: `bipolizas`) |
| `spring.jpa.properties.hibernate.dialect` | `SQLServerDialect` |
| `jwt.secret` | HS256 signing key for JWT tokens |
| `encryption.key` | AES key for user PII encryption |
| `cors.allowed-origins` | Comma-separated CORS origins |
| `saml.success.redirect.url` | Post-SAML redirect to Angular (default: `http://localhost:4200/auth/callback`) |
| `email.sending.enabled` | Toggle email functionality |
| `notification.emails` | Recipients for scheduled notifications |
| `scheduler.global.enabled` | Master toggle for all scheduled tasks |
| `scheduler.process.expiration.warning.enabled` | Per-job toggle |
| `scheduler.process.expiration.warning.cron` | Per-job cron expression |

### Spring Config Classes

| Class | Purpose |
|---|---|
| `AopConfig` | Enables `@AspectJ` auto-proxy |
| `AsyncConfig` | Enables `@Async` + thread pool for UserQuestion creation |
| `AuditConfig` | Enables AOP + Async for audit subsystem |
| `SchedulingConfig` | Enables `@Scheduled` methods |
| `OpenApiConfig` | Swagger/OpenAPI with JWT Bearer auth |
| `JpaConfig` | JPA configuration (legacy) |
| `MailConfig` | SMTP mail configuration (legacy) |

---

## 9. Enums & Status Model

### Entity Status Lifecycle (`AppStatus`)

```
ACTIVE  ──►  RESOLVED  ──►  CLOSED
   ▲                            │
   └────────── REOPEN ──────────┘
                            REJECTED (exceptional)
```

| Enum | Values | Used By |
|---|---|---|
| `AppStatus` | ACTIVE, RESOLVED, CLOSED, REJECTED | Process, ProcessPolicy, PolicyQuestionary, UserQuestion, UserAnswer |
| `PolicyStatus` | ACTIVE, RESOLVED, CLOSED, REJECTED, EXPIRED | Extended for policy-specific workflows |
| `ActionTypes` | REOPEN, CLOSED | Process action records |
| `AnswerTypes` | NORMAL, DES, SUB, OPTIONAL, SELECTOR, OPTIONALNORMAL, MULTIPLE, SUBOPTIONAL, SELECTOREXCEL, SELECTORNORMAL, TABLE | Question answer format classification |
| `AuditAction` | CREATE, UPDATE, DELETE | Audit log action types |
| `UserRole` | SUPER_ADMIN, ADMIN, USER | Security roles (maps to Spring `ROLE_` prefix) |

---

## Legacy Endpoints (Not Maintained)

The following controllers exist in the `controllers/` flat package (legacy architecture). They duplicate functionality from the new architecture and should **not be extended**:

| Controller | Base Path | Duplicates |
|---|---|---|
| `UserController` | `/users` | `controller.settings.UserController` |
| `ProcessManagerController` | `/processmanager` | `controller.period.ProcessController` |
| `InsurancePolicyController` | `/insurancePolicy` | `controller.template.PolicyController` |
| `ProcessInsurancePolicyController` | `/processinsurancepolicy` | `controller.period.ProcessPolicyController` |
| `ProcessQuestionaryController` | `/processQuestionary` | `controller.period.PolicyQuestionaryController` |
| `QuestionController` | `/question` | `controller.period.UserQuestionController` |
| `AnswerController` | `/answer` | `controller.period.UserAnswerController` |
| `QuestionnaireController` | `/questionnaires` | `controller.template.QuestionnaireController` |
| `OptionsQuestionsController` | `/options` | `controller.template.QuestionOptionController` |
| `AttachFileController` | `/fileManager` | `controller.period.AttachmentController` |
| `ColorPaletteController` | `/config` | `controller.settings.SettingsController` |
