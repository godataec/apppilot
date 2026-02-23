package com.bancointernacional.plataformaBI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;

//@Service
public class WSLogAppService {
/*
    @Value("${log.enabled:false}")
    private boolean wslogappEnabled;

    @Value("${wslogapp.url.log.portales}")
    private String wslogappUrl;

    @Value("${wslogapp.endponit.log.portales}")
    private String wslogappEndpoint;

    @Value("${wslogapp.nombre.servicios.log.portales}")
    private String serviceName;

    @Value("${wslogapp.timeout.log.portales:3000}")
    private int timeout;

    @Value("${wslogapp.timeout.log.portales.nombreservicio}")
    private String serviceNameForTimeout;

    // Individual service activation flags
    @Value("${wslogapp.log.active.ibsGetBankCertificate:false}")
    private boolean ibsGetBankCertificateActive;

    @Value("${wslogapp.log.active.ibsSignedDocument:false}")
    private boolean ibsSignedDocumentActive;

    @Value("${wslogapp.log.active.ibsTermsAndConditions:false}")
    private boolean ibsTermsAndConditionsActive;

    @Value("${wslogapp.log.active.ibsRegisterCustomerUnroled:false}")
    private boolean ibsRegisterCustomerUnroledActive;

    @Value("${wslogapp.log.active.ibsValidateBankCertificate:false}")
    private boolean ibsValidateBankCertificateActive;

    @Value("${wslogapp.log.active.ibsRegisterConsumptionCreditCard:false}")
    private boolean ibsRegisterConsumptionCreditCardActive;

    private final RestTemplate restTemplate;

    public WSLogAppService() {
        this.restTemplate = new RestTemplate();
    }*/

    /**
     * Sends a log entry to WSLogApp service
     * @param logData The log data to send
     * @param serviceType The type of service (e.g., "ibsGetBankCertificate")
     * @return Response from WSLogApp service
     */
   /* public ResponseEntity<String> sendLog(Map<String, Object> logData, String serviceType) {
        if (!wslogappEnabled) {
            return ResponseEntity.ok("WSLogApp is disabled");
        }

        if (!isServiceActive(serviceType)) {
            return ResponseEntity.ok("Service " + serviceType + " logging is disabled");
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Add service name and timeout info to the log data
            Map<String, Object> enrichedLogData = new HashMap<>(logData);
            enrichedLogData.put("serviceName", serviceName);
            enrichedLogData.put("serviceNameForTimeout", serviceNameForTimeout);
            enrichedLogData.put("timeout", timeout);
            enrichedLogData.put("serviceType", serviceType);
            enrichedLogData.put("timestamp", System.currentTimeMillis());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(enrichedLogData, headers);

            return restTemplate.postForEntity(wslogappEndpoint, request, String.class);

        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                    .body("WSLogApp service timeout: " + e.getMessage());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body("WSLogApp service error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error calling WSLogApp: " + e.getMessage());
        }
    }*/

    /**
     * Convenience method for ibsGetBankCertificate service
     */
    /*public ResponseEntity<String> logIbsGetBankCertificate(Map<String, Object> logData) {
        return sendLog(logData, "ibsGetBankCertificate");
    }*/

    /**
     * Convenience method for ibsSignedDocument service
     */
   /* public ResponseEntity<String> logIbsSignedDocument(Map<String, Object> logData) {
        return sendLog(logData, "ibsSignedDocument");
    }*/

    /**
     * Convenience method for ibsTermsAndConditions service
     */
    /*public ResponseEntity<String> logIbsTermsAndConditions(Map<String, Object> logData) {
        return sendLog(logData, "ibsTermsAndConditions");
    }*/

    /**
     * Convenience method for ibsRegisterCustomerUnroled service
     */
   /* public ResponseEntity<String> logIbsRegisterCustomerUnroled(Map<String, Object> logData) {
        return sendLog(logData, "ibsRegisterCustomerUnroled");
    }*/

    /**
     * Convenience method for ibsValidateBankCertificate service
     */
    /*public ResponseEntity<String> logIbsValidateBankCertificate(Map<String, Object> logData) {
        return sendLog(logData, "ibsValidateBankCertificate");
    }*/

    /**
     * Convenience method for ibsRegisterConsumptionCreditCard service
     */
    /*public ResponseEntity<String> logIbsRegisterConsumptionCreditCard(Map<String, Object> logData) {
        return sendLog(logData, "ibsRegisterConsumptionCreditCard");
    }*/

    /**
     * Checks if logging is active for a specific service type
     */
    /*private boolean isServiceActive(String serviceType) {
        switch (serviceType) {
            case "ibsGetBankCertificate":
                return ibsGetBankCertificateActive;
            case "ibsSignedDocument":
                return ibsSignedDocumentActive;
            case "ibsTermsAndConditions":
                return ibsTermsAndConditionsActive;
            case "ibsRegisterCustomerUnroled":
                return ibsRegisterCustomerUnroledActive;
            case "ibsValidateBankCertificate":
                return ibsValidateBankCertificateActive;
            case "ibsRegisterConsumptionCreditCard":
                return ibsRegisterConsumptionCreditCardActive;
            default:
                return false;
        }
    }*/

    /**
     * Creates a standard log entry structure
     */
    /*public Map<String, Object> createLogEntry(String operation, String user, String details, Object requestData, Object responseData) {
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("operation", operation);
        logEntry.put("user", user);
        logEntry.put("details", details);
        logEntry.put("requestData", requestData);
        logEntry.put("responseData", responseData);
        logEntry.put("timestamp", System.currentTimeMillis());
        return logEntry;
    }*/

    // Getters for configuration validation
    /*public boolean isWslogappEnabled() {
        return wslogappEnabled;
    }*/

    /*public String getWslogappUrl() {
        return wslogappUrl;
    }*/

   /* public String getWslogappEndpoint() {
        return wslogappEndpoint;
    }*/

    /*public String getServiceName() {
        return serviceName;
    }*/

    /*public int getTimeout() {
        return timeout;
    }*/
}
