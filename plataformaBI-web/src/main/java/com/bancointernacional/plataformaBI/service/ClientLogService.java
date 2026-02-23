/*package com.bancointernacional.plataformaBI.service;

import ec.com.bancointernacional.log.client.base.ClientLogSender;
import ec.com.bancointernacional.log.model.ProcessLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
*/
//@Service
//public class ClientLogService {
//
//    private static final Logger logger = LogManager.getLogger(ClientLogService.class);
//
//    @Autowired(required = false)
//    private ClientLogSender clientLogSender;
//
//    @Value("${log.enabled:false}")
//    private boolean loggingEnabled;
//
//    @Value("${log.name.service:BIPolizas}")
//    private String applicationName;
//
//    @Value("${log.address.service:0.0.0.0}")
//    private String ipAddress;
//
//    @Value("${log.msgcode.service:CODE}")
//    private String msgCode;
//
//    @Value("${log.channel.service:CHANNEL}")
//    private String channel;
//
//    @Value("${log.productid.service:prodId}")
//    private String productId;
//
//    @Value("${log.clientid.service:cliId}")
//    private String clientId;
//
//
//    /**
//     * Sends a CRUD operation log using the client-logs library
//     * @param operation The CRUD operation (CREATE, UPDATE, DELETE)
//     * @param className The service class name
//     * @param methodName The method name
//     * @param currentUser The current user
//     * @param description The operation description
//     * @param requestData The request data
//     * @param responseData The response data
//     */
//    public void sendCrudOperationLog(String operation, String className, String methodName,
//                                    String currentUser, String description,
//                                    Object requestData, Object responseData) {
//        if (!loggingEnabled) {
//            logger.debug("Logging is disabled, skipping log entry");
//            return;
//        }
//
//        if (clientLogSender == null) {
//            logger.warn("ClientLogSender is not available, skipping log entry");
//            return;
//        }
//
//        try {
//            ProcessLog processLog = createProcessLog(operation, className, methodName,
//                                                   currentUser, description, requestData, responseData);
//
//            clientLogSender.sendLog(processLog);
//
//            logger.info("CRUD operation logged via ClientLogSender: {} on {}.{} by user {}",
//                       operation, className, methodName, currentUser);
//
//        } catch (Exception e) {
//            logger.error("Error sending log via ClientLogSender: {}", e.getMessage(), e);
//        }
//    }
//
//    /**
//     * Creates a ProcessLog object for CRUD operations
//     */
//    private ProcessLog createProcessLog(String operation, String className, String methodName,
//                                       String currentUser, String description,
//                                       Object requestData, Object responseData) {
//        ProcessLog processLog = new ProcessLog();
//
//        // Get operation-specific message code and description
//        String operationMsgCode = getOperationMsgCode(operation);
//        String operationDescription = getOperationDescription(operation, description);
//
//        // Set basic fields
//        processLog.setApplicationCode(applicationName);
//        processLog.setServiceCode(applicationName);
//        processLog.setTrxDate(new Date());
//        processLog.setTrxDateIn(new Date());
//        processLog.setTrxDateOu(new Date());
//        processLog.setSequentialId(generateSequentialId());
//        processLog.setClientId(clientId);
//        processLog.setProductId(productId);
//        processLog.setChannel(channel);
//        processLog.setClassName(className);
//        processLog.setMethodName(methodName);
//        processLog.setActionName(operation);
//        processLog.setMsgCode(operationMsgCode);
//        processLog.setMsgDescription(operationDescription);
//        processLog.setIpAddress(ipAddress);
//        processLog.setUserName(currentUser);
//        processLog.setDataIn(objectToByteArray(requestData));
//
//        //OPTIONALS
//        processLog.setDataOut(objectToByteArray(responseData));
//        processLog.setAditionalData(null);
//        return processLog;
//    }
//
//    /**
//     * Get operation-specific message code based on CRUD operation
//     */
//    private String getOperationMsgCode(String operation) {
//        if (operation == null) {
//            return msgCode;
//        }
//
//        switch (operation.toUpperCase()) {
//            case "CREATE":
//                return "201";
//            case "UPDATE":
//                return "200";
//            case "DELETE":
//                return "204";
//            default:
//                return msgCode;
//        }
//    }
//
//    /**
//     * Get operation-specific description based on CRUD operation
//     */
//    private String getOperationDescription(String operation, String customDescription) {
//        if (operation == null) {
//            return customDescription != null ? customDescription : "Operación realizada";
//        }
//
//        if (customDescription != null && !customDescription.trim().isEmpty() &&
//            !customDescription.equals("CRUD operation") && !customDescription.equals(operation)) {
//            return customDescription;
//        }
//
//        switch (operation.toUpperCase()) {
//            case "CREATE":
//                return "Entidad creada con exito";
//            case "UPDATE":
//                return "Entidad actualizada exitosamente";
//            case "DELETE":
//                return "Entidad eliminada exitosamente";
//            default:
//                return customDescription != null ? customDescription : "Operación realizada";
//        }
//    }
//
//    /**
//     * Sanitizes data for logging by limiting size and removing sensitive information
//     */
//    private String sanitizeDataForLogging(Object data) {
//        if (data == null) {
//            return null;
//        }
//
//        String dataString = data.toString();
//
//        if (dataString.length() > 1000) {
//            dataString = dataString.substring(0, 1000) + "... [TRUNCATED]";
//        }
//        dataString = dataString.replaceAll("(?i)(password|token|secret|key)\\s*[:=]\\s*[^\\s,}]+", "$1=***");
//
//        return dataString;
//    }
//
//    private byte[] objectToByteArray(Object obj) {
//        if (obj == null) {
//            return new byte[0];
//        }
//
//        try {
//            String jsonString = obj.toString();
//            return jsonString.getBytes("UTF-8");
//        } catch (Exception e) {
//            logger.warn("Error converting object to byte array: {}", e.getMessage());
//            return new byte[0];
//        }
//    }
//
//    /**
//     * Generates a sequential ID for the log
//     */
//    private String generateSequentialId() {
//        return "SEQ_" + System.currentTimeMillis();
//    }
//}
