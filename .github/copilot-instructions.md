# Project Guidelines — PlataformaBI (Portal Administrativo)

## Architecture

Multi-module Maven project for an insurance policy management platform (Banco Internacional). Deployed as **WAR inside EAR** on JBoss EAP/WildFly.

| Module | Packaging | Role |
|---|---|---|
| `plataformaBI-web` | WAR | Main app — all controllers, services, entities, security, API |
| `plataformaBI-ear` | EAR | Enterprise archive wrapping the WAR for JBoss deployment |
| `bke-jva-portalAdministrativo-plataformaBI` | WAR | **Legacy standalone module** (not in multi-module build). Do not add features here. |

Base package: `com.bancointernacional.plataformaBI`

## Code Style

- **Java 8** — do not use Java 9+ features (streams, `var`, records, etc.)
- **Lombok** everywhere: `@Data`, `@Builder`, `@Slf4j`, `@AllArgsConstructor`, `@NoArgsConstructor`
- **MapStruct** for DTO↔Entity mapping: `@Mapper(componentModel = "spring")` interfaces in `mapper/`
- Comments and log messages are often in **Spanish** — maintain this convention
- Two coexisting package structures exist in `plataformaBI-web`:
  - **Newer (preferred)**: domain-driven sub-packages — `controller.period/`, `service.usecase/`, `domain.model/`, `domain.dto/`, `repository.period/`
  - **Legacy**: flat packages — `controllers/`, `services/serviceImpl/`, `models/entities/`, `repositories/`
  - **New code must follow the newer pattern.** Do not add to legacy flat packages.

## Build and Test

```bash
# Full build (from root)
mvnw.cmd clean install

# Build web module only
cd plataformaBI-web && ..\mvnw.cmd clean package

# Run in dev mode
cd plataformaBI-web && ..\mvnw.cmd spring-boot:run

# Run tests
mvnw.cmd test
```

- Maven Wrapper (`mvnw.cmd`) is present at root — always use it over system Maven
- `spring-boot-maven-plugin` is skipped in web module (WAR deployed inside EAR)
- MapStruct + Lombok annotation processors configured in `maven-compiler-plugin`

## Project Conventions

### Controllers
- `@RestController` + `@RequestMapping("/api/...")` returning `ResponseEntity<?>`
- Validation: `@Valid @RequestBody` + `BindingResult`, manual error extraction
- Wrap list responses in `Map<String, Object>` with total count: `{ "data": [...], "total": N }`
- Reference: [ProcessController](plataformaBI-web/src/main/java/com/bancointernacional/plataformaBI/controller/period/ProcessController.java)

### Services & Use Cases
- Interfaces in `service/usecase/` (e.g., `ProcessUseCase`), implementations in `service/`
- `@Service`, `@Transactional` on write methods, `@Slf4j` for logging
- Reference: [ProcessService](plataformaBI-web/src/main/java/com/bancointernacional/plataformaBI/service/ProcessService.java)

### Entities
- `@Entity @Table(name="...")` with UUID primary keys (`@GeneratedValue(strategy = AUTO)`, `@Type(type = "uuid-char")`)
- Soft-delete via `is_deleted` boolean column — never hard-delete
- Bean validation: `@NotBlank`, `@NotNull`, `@Size`
- Reference: [domain/model/period/](plataformaBI-web/src/main/java/com/bancointernacional/plataformaBI/domain/model/period/)

### Repositories
- Extend `JpaRepository<Entity, UUID>`
- **Native SQL queries** (`@Query(nativeQuery = true)`) with SQL Server syntax (`OFFSET...FETCH NEXT`, `GETDATE()`)
- Manual pagination via `OFFSET/FETCH` — not Spring Data `Pageable`
- Always filter by `is_deleted = 0` in queries

### DTOs
- Separate request/response DTOs: `CreateXxxDTO`, `UpdateXxxDTO`, `XxxDTO`
- Place in `domain/dto/` sub-packaged by feature

### Audit
- AOP-based via `AuditAspect` — CRUD operations are logged automatically via pointcuts
- Enum `AuditAction` defines tracked operations

## Integration Points

- **Database**: Microsoft SQL Server — schema in [liquibase/sql/](plataformaBI-web/src/main/resources/liquibase/sql/) (run manually, no Liquibase runtime)
- **Azure AD / Entra ID**: SAML 2.0 authentication (IdP) → internal JWT for API access
- **SMTP**: Internal mail server `172.16.67.172:25` — toggled via `email.sending.enabled`
- **Angular frontend**: Runs on `localhost:4200`, route prefix `/PolizasSegurosBI/`
- **PDF generation**: Thymeleaf templates → openhtmltopdf/Flying Saucer; templates in [resources/templates/](plataformaBI-web/src/main/resources/templates/) organized by insurance type (`BBB/`, `CYBER/`, `DO/`)
- **External logging**: `WSLogAppService` for centralized log forwarding
- **OpenAPI docs**: Swagger UI at `/swagger-ui/` with JWT bearer auth

## Security

- **Auth flow**: SAML2 login (Azure AD) → backend extracts roles → issues internal JWT (HS256) → frontend sends `Bearer` token
- **Two Spring Security filter chains** (ordered): SAML chain (order 1) for `/saml2/**`, `/login/saml2/**`; JWT chain (order 2) for `/api/**`
- **Roles**: `SuperAdmin`, `Admin`, `RegisterUser` — defined in `UserRole` enum, sourced from SAML assertions
- **User PII encrypted at rest** via `EncryptionService` (AES key from `encryption.key` property)
- **CORS**: Configured in `SpringSecurityConfig` from `cors.allowed-origins` property
- Config references: [SamlSecurityConfig](plataformaBI-web/src/main/java/com/bancointernacional/plataformaBI/auth/SamlSecurityConfig.java), [SpringSecurityConfig](plataformaBI-web/src/main/java/com/bancointernacional/plataformaBI/auth/SpringSecurityConfig.java)
