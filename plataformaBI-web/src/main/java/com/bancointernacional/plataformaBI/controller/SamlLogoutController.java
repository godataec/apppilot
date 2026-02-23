package com.bancointernacional.plataformaBI.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * SAML Single Logout Controller
 * Implements SP-initiated and IdP-initiated logout flows according to SAML 2.0 standards
 */
@Controller
public class SamlLogoutController {

    private static final Logger logger = LogManager.getLogger(SamlLogoutController.class);

    @Value("${azure.ad.tenant.id}")
    private String tenantId;

    @Value("${spring.security.saml2.relyingparty.registration.azure.entity-id}")
    private String spEntityId;

    @Value("${saml.logout.success.url:http://localhost:4200/logged-out}")
    private String logoutSuccessUrl;

    /**
     * SP-initiated SAML logout endpoint
     * GET /saml/logout
     *
     * This endpoint initiates the SAML Single Logout flow by:
     * 1. Building a SAML LogoutRequest with NameID and SessionIndex
     * 2. Encoding it using DEFLATE + Base64 (HTTP-Redirect binding)
     * 3. Redirecting browser to Azure AD logout endpoint
     * 4. Invalidating the local session
     */
    @GetMapping("/saml/logout")
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication,
            @RequestParam(value = "returnTo", required = false) String returnTo
    ) throws IOException {

        logger.info("=== SP-initiated SAML Logout Requested ===");
        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("Request URL: {}", request.getRequestURL());
        logger.info("Query String: {}", request.getQueryString());
        logger.info("returnTo parameter: {}", returnTo);

        String idpLogoutUrl = "https://login.microsoftonline.com/" + tenantId + "/saml2";
        HttpSession session = request.getSession(false);

        // Determine final redirect URL
        String finalReturnUrl = (returnTo != null && !returnTo.isEmpty()) ? returnTo : logoutSuccessUrl;
        logger.info("Final return URL (RelayState): {}", finalReturnUrl);

        if (authentication != null) {
            logger.info("Authentication found - Type: {}", authentication.getClass().getName());
            logger.info("Authentication principal type: {}", authentication.getPrincipal().getClass().getName());
            logger.info("Is authenticated: {}", authentication.isAuthenticated());
        } else {
            logger.warn("Authentication is NULL");
        }

        if (authentication != null && authentication.getPrincipal() instanceof Saml2AuthenticatedPrincipal) {
            Saml2AuthenticatedPrincipal principal = (Saml2AuthenticatedPrincipal) authentication.getPrincipal();

            logger.info("SAML Principal found - Building SAML LogoutRequest for user: {}", principal.getName());

            String nameId = principal.getName();
            String sessionIndex = getSessionIndex(principal);
            logger.info("NameID: {}", nameId);
            logger.info("SessionIndex: {}", sessionIndex);

            try {
                // Build SAML LogoutRequest XML
                String logoutRequestXml = buildLogoutRequest(nameId, sessionIndex, idpLogoutUrl);
                logger.debug("SAML LogoutRequest XML: {}", logoutRequestXml);

                // Encode for HTTP-Redirect binding
                String encodedRequest = deflateAndBase64(logoutRequestXml);
                logger.debug("Encoded SAMLRequest (first 100 chars): {}",
                    encodedRequest.length() > 100 ? encodedRequest.substring(0, 100) + "..." : encodedRequest);

                // Invalidate local session before redirecting to IdP
                if (session != null) {
                    logger.info("Invalidating local session: {}", session.getId());
                    session.invalidate();
                } else {
                    logger.warn("No session to invalidate");
                }

                // Build redirect URL with SAMLRequest and RelayState
                String encodedRelayState = URLEncoder.encode(finalReturnUrl, "UTF-8");
                String redirectUrl = idpLogoutUrl + "?SAMLRequest=" +
                                   URLEncoder.encode(encodedRequest, "UTF-8") +
                                   "&RelayState=" + encodedRelayState;

                logger.info("=== Redirecting to Azure AD Logout ===");
                logger.info("IdP Logout URL: {}", idpLogoutUrl);
                logger.info("RelayState (encoded): {}", encodedRelayState);
                logger.info("Full redirect URL length: {} chars", redirectUrl.length());

                response.sendRedirect(redirectUrl);
                logger.info("Redirect sent successfully");
                return;

            } catch (Exception e) {
                logger.error("Error building SAML LogoutRequest", e);
                logger.error("Exception type: {}", e.getClass().getName());
                logger.error("Exception message: {}", e.getMessage());
                // Fallback: just invalidate local session
            }
        } else {
            logger.warn("No SAML authentication found, performing local logout only");
            logger.warn("This means the session may have already been invalidated or user was not logged in via SAML");
        }

        // Fallback: invalidate local session and redirect to success page
        logger.info("=== Fallback Logout (No SAML) ===");
        if (session != null) {
            logger.info("Invalidating session: {}", session.getId());
            session.invalidate();
        } else {
            logger.warn("No session to invalidate in fallback");
        }
        logger.info("Redirecting to: {}", finalReturnUrl);
        response.sendRedirect(finalReturnUrl);
    }

    /**
     * IdP-initiated logout handler
     * POST /saml/slo
     *
     * This endpoint receives LogoutRequest or LogoutResponse from the IdP
     * and completes the logout by invalidating the local session
     */
    @PostMapping("/saml/slo")
    public void handleIdpLogout(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "SAMLRequest", required = false) String samlRequest,
            @RequestParam(value = "SAMLResponse", required = false) String samlResponse,
            @RequestParam(value = "RelayState", required = false) String relayState
    ) throws IOException {

        if (samlRequest != null) {
            logger.info("Received IdP-initiated LogoutRequest");
            // IdP wants to log us out
            HttpSession session = request.getSession(false);
            if (session != null) {
                logger.info("Invalidating session in response to IdP logout");
                session.invalidate();
            }

            // In production, you should build and return a LogoutResponse
            // For now, just return success
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Logout successful");
            return;
        }

        if (samlResponse != null) {
            logger.info("Received LogoutResponse from IdP");
            // IdP is confirming our logout request
            // Session should already be invalidated, just redirect
        }

        String redirectUrl = (relayState != null) ? relayState : logoutSuccessUrl;
        logger.info("Logout complete, redirecting to: {}", redirectUrl);
        response.sendRedirect(redirectUrl);
    }

    /**
     * Builds a SAML 2.0 LogoutRequest XML message
     */
    private String buildLogoutRequest(String nameId, String sessionIndex, String destination) {
        String id = "_" + UUID.randomUUID().toString();

        // Format timestamp for Java 1.8 (ISO 8601 format)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String issueInstant = sdf.format(new Date());

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<samlp:LogoutRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"");
        sb.append(" xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"");
        sb.append(" ID=\"").append(escapeXml(id)).append("\"");
        sb.append(" Version=\"2.0\"");
        sb.append(" IssueInstant=\"").append(issueInstant).append("\"");
        sb.append(" Destination=\"").append(escapeXml(destination)).append("\">");

        // Issuer (SP entity ID)
        sb.append("<saml:Issuer>").append(escapeXml(spEntityId)).append("</saml:Issuer>");

        // NameID
        sb.append("<saml:NameID Format=\"urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress\">");
        sb.append(escapeXml(nameId)).append("</saml:NameID>");

        // SessionIndex (if available)
        if (sessionIndex != null && !sessionIndex.isEmpty()) {
            sb.append("<samlp:SessionIndex>").append(escapeXml(sessionIndex)).append("</samlp:SessionIndex>");
        }

        sb.append("</samlp:LogoutRequest>");

        return sb.toString();
    }

    /**
     * Encodes the SAML message for HTTP-Redirect binding
     * Uses DEFLATE compression followed by Base64 encoding
     */
    private String deflateAndBase64(String xml) throws IOException {
        byte[] input = xml.getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Deflater deflater = new Deflater(Deflater.DEFLATED, true); // true = no ZLIB wrapper
        DeflaterOutputStream dos = null;

        try {
            dos = new DeflaterOutputStream(baos, deflater);
            dos.write(input);
            dos.finish();
            return java.util.Base64.getEncoder().encodeToString(baos.toByteArray());
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    logger.warn("Error closing deflater stream", e);
                }
            }
            deflater.end();
        }
    }

    /**
     * Extracts SessionIndex from SAML principal attributes
     */
    private String getSessionIndex(Saml2AuthenticatedPrincipal principal) {
        // Try different possible attribute names for session index
        String sessionIndex = principal.getFirstAttribute("sessionIndex");
        if (sessionIndex == null) {
            sessionIndex = principal.getFirstAttribute("SessionIndex");
        }
        if (sessionIndex == null) {
            sessionIndex = principal.getFirstAttribute("http://schemas.microsoft.com/ws/2008/06/identity/claims/sessionindex");
        }

        logger.debug("SessionIndex extracted: {}", sessionIndex);
        return sessionIndex;
    }

    /**
     * Basic XML escaping to prevent injection
     */
    private String escapeXml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
}

