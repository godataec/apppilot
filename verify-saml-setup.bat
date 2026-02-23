@echo off
echo ================================================
echo    SAML Configuration Verification Script
echo ================================================
echo.

echo [1/5] Checking if SAML dependency is in pom.xml...
findstr /C:"spring-security-saml2-service-provider" plataformaBI-web\pom.xml > nul
if %errorlevel%==0 (
    echo [OK] SAML dependency found in pom.xml
) else (
    echo [ERROR] SAML dependency NOT found in pom.xml
)
echo.

echo [2/5] Checking if SAML configuration exists in application.properties...
findstr /C:"spring.security.saml2" plataformaBI-web\src\main\resources\application.properties > nul
if %errorlevel%==0 (
    echo [OK] SAML configuration found in application.properties
) else (
    echo [ERROR] SAML configuration NOT found in application.properties
)
echo.

echo [3/5] Checking if SamlSecurityConfig.java exists...
if exist plataformaBI-web\src\main\java\com\bancointernacional\plataformaBI\auth\SamlSecurityConfig.java (
    echo [OK] SamlSecurityConfig.java exists
) else (
    echo [ERROR] SamlSecurityConfig.java NOT found
)
echo.

echo [4/5] Checking if SamlTestController.java exists...
if exist plataformaBI-web\src\main\java\com\bancointernacional\plataformaBI\controller\SamlTestController.java (
    echo [OK] SamlTestController.java exists
) else (
    echo [ERROR] SamlTestController.java NOT found
)
echo.

echo [5/5] Checking if documentation exists...
if exist SAML_CONFIGURATION.md (
    echo [OK] SAML_CONFIGURATION.md exists
) else (
    echo [ERROR] SAML_CONFIGURATION.md NOT found
)

if exist SAML_IMPLEMENTATION_SUMMARY.md (
    echo [OK] SAML_IMPLEMENTATION_SUMMARY.md exists
) else (
    echo [ERROR] SAML_IMPLEMENTATION_SUMMARY.md NOT found
)
echo.

echo ================================================
echo    Verification Complete
echo ================================================
echo.
echo Next Steps:
echo 1. Configure Azure AD Portal with the URLs from SAML_CONFIGURATION.md
echo 2. Run: mvn clean install
echo 3. Start application: mvn spring-boot:run
echo 4. Test: http://localhost:8080/saml/user-info
echo.
echo For detailed instructions, see: SAML_CONFIGURATION.md
echo For quick summary, see: SAML_IMPLEMENTATION_SUMMARY.md
echo.
pause

