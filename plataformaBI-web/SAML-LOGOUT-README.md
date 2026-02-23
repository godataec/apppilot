# SAML Logout Implementation - Quick Start Guide

## Overview

This implementation provides complete SAML Single Logout (SLO) functionality for your Spring Boot application integrated with Azure AD. The logout flow follows SAML 2.0 standards and supports both SP-initiated and IdP-initiated logout.

## Architecture

### Components Added

1. **SamlLogoutController.java** - Main logout controller
   - Location: `com.bancointernacional.plataformaBI.controller.SamlLogoutController`
   - Handles SAML logout request generation and processing

2. **AuthController.java** - Updated with logout endpoint
   - Location: `com.bancointernacional.plataformaBI.controller.AuthController`
   - Provides REST API for frontend to initiate logout

3. **SamlSecurityConfig.java** - Updated security configuration
   - Location: `com.bancointernacional.plataformaBI.auth.SamlSecurityConfig`
   - Configured logout endpoints and permissions

## How It Works

### SP-Initiated Logout Flow

```
Frontend                Backend                 Azure AD
   |                       |                       |
   |--POST /api/auth/logout--> Returns logout URL |
   |                       |                       |
   |--GET /saml/logout---->|                       |
   |                       |--Builds LogoutRequest-->
   |                       |--DEFLATE + Base64---->|
   |                       |--HTTP Redirect------->|
   |                       |                       |
   |<-----------------Redirect to Azure------------|
   |                       |                       |
   |-------Azure AD logout UI-------------------->|
   |                       |                       |
   |<--POST /saml/slo------LogoutResponse---------|
   |                       |                       |
   |--Redirect to returnTo URL------------------>|
```

### IdP-Initiated Logout Flow

```
Frontend                Backend                 Azure AD
   |                       |                       |
   |                       |<--POST /saml/slo------|
   |                       |   (LogoutRequest)     |
   |                       |--Invalidate Session-->|
   |                       |--Return Success------>|
   |                       |                       |
```

## Endpoints

### 1. `/api/auth/logout` (POST)
**Purpose**: Initiate logout from the frontend

**Parameters**:
- `returnTo` (optional): URL to redirect after logout

**Request**:
```javascript
fetch('http://localhost:8080/api/auth/logout', {
  method: 'POST',
  credentials: 'include',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    returnTo: 'http://localhost:4200/logged-out'
  })
})
```

**Response**:
```json
{
  "success": true,
  "logoutUrl": "/saml/logout?returnTo=http://localhost:4200/logged-out",
  "message": "Redirect to SAML logout"
}
```

### 2. `/saml/logout` (GET)
**Purpose**: Process SAML logout and redirect to Azure AD

**Parameters**:
- `returnTo` (optional): URL to redirect after logout completes

**Behavior**:
1. Extracts NameID and SessionIndex from SAML session
2. Builds SAML LogoutRequest XML
3. Encodes using DEFLATE + Base64 (HTTP-Redirect binding)
4. Invalidates local HTTP session
5. Redirects browser to Azure AD logout endpoint

### 3. `/saml/slo` (POST)
**Purpose**: Handle IdP-initiated logout or LogoutResponse

**Parameters**:
- `SAMLRequest`: Base64-encoded LogoutRequest from IdP
- `SAMLResponse`: Base64-encoded LogoutResponse from IdP
- `RelayState`: Return URL

**Behavior**:
- Receives logout messages from Azure AD
- Invalidates local session
- Redirects to RelayState or default success URL

## Configuration

### Application Properties

Add these properties to `application.properties`:

```properties
# Azure AD Configuration
azure.ad.tenant.id=201b2e7a-4bf7-437d-b035-7cbbe231dc50
spring.security.saml2.relyingparty.registration.azure.entity-id=http://localhost:8080/saml2/service-provider-metadata/azure

# SAML Logout Success URL
saml.logout.success.url=http://localhost:4200/logged-out
```

### Azure AD Configuration

Configure the logout URL in Azure Portal:

1. Go to **Azure Portal** → **Enterprise Applications**
2. Select your application
3. Navigate to **Single sign-on** → **Basic SAML Configuration**
4. Set **Logout URL**: `http://localhost:8080/saml/slo`
5. Save configuration

## Frontend Integration

### Option 1: Direct Redirect (Simplest)

```javascript
// Logout button handler
function logout() {
  // Clear any frontend tokens/state
  localStorage.clear();
  
  // Redirect to SAML logout
  window.location.href = 'http://localhost:8080/saml/logout?returnTo=' + 
                         encodeURIComponent('http://localhost:4200/logged-out');
}
```

### Option 2: Using API (Recommended)

```javascript
// Logout service
async function logout() {
  try {
    const response = await fetch('http://localhost:8080/api/auth/logout', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        returnTo: 'http://localhost:4200/logged-out'
      })
    });
    
    const data = await response.json();
    
    if (data.success && data.logoutUrl) {
      // Clear frontend tokens/state
      localStorage.clear();
      
      // Redirect to SAML logout
      window.location.href = 'http://localhost:8080' + data.logoutUrl;
    }
  } catch (error) {
    console.error('Logout failed:', error);
    // Fallback: clear local state and redirect to login
    localStorage.clear();
    window.location.href = '/login';
  }
}
```

### Option 3: Angular Example

```typescript
// auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  logout(returnTo?: string): void {
    const returnUrl = returnTo || `${window.location.origin}/logged-out`;
    
    this.http.post<any>(`${this.apiUrl}/logout`, 
      { returnTo: returnUrl },
      { withCredentials: true }
    ).subscribe({
      next: (response) => {
        if (response.success && response.logoutUrl) {
          // Clear local storage
          localStorage.clear();
          sessionStorage.clear();
          
          // Redirect to SAML logout
          window.location.href = `http://localhost:8080${response.logoutUrl}`;
        }
      },
      error: (error) => {
        console.error('Logout error:', error);
        // Fallback logout
        localStorage.clear();
        sessionStorage.clear();
        this.router.navigate(['/login']);
      }
    });
  }
}
```

## Session and Cookie Handling

### Important Notes

1. **JSESSIONID Cookie**: 
   - Automatically sent by browser with `credentials: 'include'`
   - Required for logout to access SAML session data
   - No need to read/store in frontend

2. **Cookie Flags**:
   - `HttpOnly`: Frontend cannot read (security feature)
   - `SameSite`: Set to `None` for cross-origin SAML flows
   - `Secure`: Required in production (HTTPS)

3. **CORS Configuration**:
   - Already configured in `SamlSecurityConfig`
   - Allows credentials from `http://localhost:4200`

## Testing

### Test SP-Initiated Logout

1. Login to the application via SAML
2. Verify you're authenticated: `GET /api/auth/status`
3. Initiate logout: `POST /api/auth/logout`
4. Browser redirects to Azure AD logout
5. After Azure logout, returns to `returnTo` URL
6. Verify session is cleared

### Test IdP-Initiated Logout

1. Login to the application via SAML
2. Go to Azure Portal and sign out
3. Azure should send LogoutRequest to `/saml/slo`
4. Application invalidates session
5. User is logged out of application

### Manual Testing Commands

```bash
# Check authentication status
curl -X GET http://localhost:8080/api/auth/status \
  -H "Cookie: JSESSIONID=your-session-id" \
  --cookie-jar cookies.txt

# Initiate logout
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{"returnTo":"http://localhost:4200/logged-out"}'

# Direct SAML logout (will return redirect)
curl -X GET "http://localhost:8080/saml/logout" \
  -b cookies.txt \
  -L  # Follow redirects
```

## Troubleshooting

### Issue: 403 Forbidden on logout

**Solution**: Check that logout endpoints are permitted in security config:
```java
.antMatchers("/saml/logout", "/saml/slo").permitAll()
```

### Issue: Session not invalidated

**Solution**: Ensure cookies are sent with request:
```javascript
fetch('/api/auth/logout', {
  credentials: 'include'  // Required!
})
```

### Issue: CORS errors

**Solution**: Verify CORS configuration allows your origin and credentials:
```java
corsConfig.setAllowedOriginPatterns(Arrays.asList("http://localhost:4200"));
corsConfig.setAllowCredentials(true);
```

### Issue: Azure AD doesn't log out

**Solution**: 
1. Verify Logout URL is configured in Azure Portal
2. Check that LogoutRequest is properly signed (required by Azure)
3. Review Azure AD logs for error messages

## Production Considerations

1. **Enable Request Signing**:
   - Current implementation does NOT sign LogoutRequest
   - Azure AD typically requires signed requests in production
   - See `SAML-implementation.md` for OpenSAML signing examples

2. **HTTPS Required**:
   - All SAML endpoints must use HTTPS in production
   - Update entity IDs and URLs in config

3. **Certificate Management**:
   - Store signing certificates securely (vault/keystore)
   - Implement certificate rotation procedures

4. **Logging**:
   - Enable DEBUG logging for troubleshooting:
   ```properties
   logging.level.com.bancointernacional.plataformaBI.controller.SamlLogoutController=DEBUG
   ```

5. **Security**:
   - Validate RelayState parameter to prevent open redirect
   - Implement rate limiting on logout endpoints
   - Monitor for logout failures

## Next Steps

1. **Test thoroughly** with your frontend application
2. **Configure Azure AD** logout URL
3. **Implement signing** for production (see `SAML-implementation.md`)
4. **Add monitoring** and alerting for logout failures
5. **Document** logout flow for your team

## Support

For detailed SAML 2.0 standards and advanced configuration, refer to:
- `SAML-implementation.md` - Complete SAML implementation guide
- OASIS SAML 2.0 Profiles specification
- Azure AD SAML documentation

## Summary

✅ **Implemented**:
- SP-initiated logout with Azure AD
- IdP-initiated logout support
- Frontend API endpoint
- Session management
- CORS configuration
- Comprehensive documentation

🔄 **Optional** (for production):
- Request signing with OpenSAML
- Certificate-based authentication
- Enhanced validation and error handling

The logout implementation is ready to use! Start testing with your frontend application.

