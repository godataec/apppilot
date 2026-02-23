/*package com.bancointernacional.plataformaBI.aspect;

import com.bancointernacional.plataformaBI.service.ClientLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
*/
//@Aspect
//@Component
//public class CrudOperationLoggingAspect {
//
//    private static final Logger logger = LogManager.getLogger(CrudOperationLoggingAspect.class);
//
//    @Autowired
//    private ClientLogService clientLogService;
//
//    /**
//     * Intercepts CREATE operations (methods starting with 'create', 'save', 'insert', 'add')
//     */
//    @AfterReturning(pointcut = "execution(* com.bancointernacional.plataformaBI.service.*.create*(..)) || " +
//                              "execution(* com.bancointernacional.plataformaBI.service.*.save*(..)) || " +
//                              "execution(* com.bancointernacional.plataformaBI.service.*.insert*(..)) || " +
//                              "execution(* com.bancointernacional.plataformaBI.service.*.add*(..))",
//                   returning = "result")
//    public void logCreateOperation(JoinPoint joinPoint, Object result) {
//        logCrudOperation("CREATE", joinPoint, result);
//    }
//
//    /**
//     * Intercepts UPDATE operations (methods starting with 'update', 'modify', 'edit')
//     */
//    @AfterReturning(pointcut = "execution(* com.bancointernacional.plataformaBI.service.*.update*(..)) || " +
//                              "execution(* com.bancointernacional.plataformaBI.service.*.modify*(..)) || " +
//                              "execution(* com.bancointernacional.plataformaBI.service.*.edit*(..))",
//                   returning = "result")
//    public void logUpdateOperation(JoinPoint joinPoint, Object result) {
//        logCrudOperation("UPDATE", joinPoint, result);
//    }
//
//    /**
//     * Intercepts DELETE operations (methods starting with 'delete', 'remove')
//     */
//    @AfterReturning(pointcut = "execution(* com.bancointernacional.plataformaBI.service.*.delete*(..)) || " +
//                              "execution(* com.bancointernacional.plataformaBI.service.*.remove*(..))",
//                   returning = "result")
//    public void logDeleteOperation(JoinPoint joinPoint, Object result) {
//        logCrudOperation("DELETE", joinPoint, result);
//    }
//
//    /**
//     * Common method to log CRUD operations using ClientLogService
//     */
//    private void logCrudOperation(String operation, JoinPoint joinPoint, Object result) {
//        try {
//            String currentUser = getCurrentUser();
//            String className = joinPoint.getTarget().getClass().getSimpleName();
//            String methodName = joinPoint.getSignature().getName();
//            Object[] methodArgs = joinPoint.getArgs();
//
//            String description = String.format("%s operation on %s.%s", operation, className, methodName);
//            Object requestData = sanitizeRequestData(methodArgs);
//            Object responseData = sanitizeResponseData(result);
//
//            clientLogService.sendCrudOperationLog(
//                operation,
//                className,
//                methodName,
//                currentUser,
//                description,
//                requestData,
//                responseData
//            );
//
//            logger.info("CRUD operation logged: {} on {}.{} by user {}",
//                       operation, className, methodName, currentUser);
//
//        } catch (Exception e) {
//            logger.error("Error logging CRUD operation: {}", e.getMessage(), e);
//        }
//    }
//
//    /**
//     * Get current authenticated user
//     */
//    private String getCurrentUser() {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication != null && authentication.isAuthenticated()) {
//                return authentication.getName();
//            }
//        } catch (Exception e) {
//            logger.warn("Could not get current user: {}", e.getMessage());
//        }
//        return "SYSTEM";
//    }
//
//    /**
//     * Sanitize request data for logging (remove sensitive information)
//     */
//    private Object sanitizeRequestData(Object[] args) {
//        if (args == null || args.length == 0) {
//            return null;
//        }
//
//        Map<String, Object> sanitizedArgs = new HashMap<>();
//        for (int i = 0; i < args.length; i++) {
//            Object arg = args[i];
//            if (arg != null) {
//                // Convert to string and limit size for logging
//                String argString = arg.toString();
//                if (argString.length() > 500) {
//                    argString = argString.substring(0, 500) + "... [TRUNCATED]";
//                }
//                sanitizedArgs.put("arg" + i, argString);
//                sanitizedArgs.put("arg" + i + "_type", arg.getClass().getSimpleName());
//            }
//        }
//        return sanitizedArgs;
//    }
//
//    /**
//     * Sanitize response data for logging
//     */
//    private Object sanitizeResponseData(Object result) {
//        if (result == null) {
//            return null;
//        }
//
//        Map<String, Object> sanitizedResult = new HashMap<>();
//        String resultString = result.toString();
//        if (resultString.length() > 500) {
//            resultString = resultString.substring(0, 500) + "... [TRUNCATED]";
//        }
//        sanitizedResult.put("result", resultString);
//        sanitizedResult.put("resultType", result.getClass().getSimpleName());
//
//        return sanitizedResult;
//    }
//}
