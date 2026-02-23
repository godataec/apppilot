package com.bancointernacional.plataformaBI.aspect;

import com.bancointernacional.plataformaBI.service.audit.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Slf4j
@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditService auditService;

    @AfterReturning(value = "execution(* com.bancointernacional.plataformaBI.service.*.save*(..)) || " +
                           "execution(* com.bancointernacional.plataformaBI.service.*.create*(..))", returning = "result")
    public void auditCreateOperations(JoinPoint joinPoint, Object result) {
        try {
            String entityName = extractEntityName(joinPoint);
            String entityId = extractEntityId(result);
            Object[] args = joinPoint.getArgs();
            Object requestData = args.length > 0 ? args[0] : null;

            auditService.logCreate(entityName, entityId, requestData);
            log.debug("Audit logged for CREATE operation on entity: {} with ID: {}", entityName, entityId);
        } catch (Exception e) {
            log.error("Failed to audit CREATE operation: {}", e.getMessage(), e);
        }
    }

    @AfterReturning(value = "execution(* com.bancointernacional.plataformaBI.service.*.update*(..)) || " +
                           "execution(* com.bancointernacional.plataformaBI.service.*.modify*(..))", returning = "result")
    public void auditUpdateOperations(JoinPoint joinPoint, Object result) {
        try {
            String entityName = extractEntityName(joinPoint);
            String entityId = extractEntityId(result);
            Object[] args = joinPoint.getArgs();
            Object requestData = args.length > 0 ? args[0] : null;

            auditService.logUpdate(entityName, entityId, requestData);
            log.debug("Audit logged for UPDATE operation on entity: {} with ID: {}", entityName, entityId);
        } catch (Exception e) {
            log.error("Failed to audit UPDATE operation: {}", e.getMessage(), e);
        }
    }

    @AfterReturning(value = "execution(* com.bancointernacional.plataformaBI.service.*.delete*(..)) || " +
                           "execution(* com.bancointernacional.plataformaBI.service.*.remove*(..))")
    public void auditDeleteOperations(JoinPoint joinPoint) {
        try {
            String entityName = extractEntityName(joinPoint);
            Object[] args = joinPoint.getArgs();

            String entityId = null;
            Object requestData = null;

            if (args.length > 0) {
                Object firstArg = args[0];
                if (firstArg instanceof UUID) {
                    entityId = firstArg.toString();
                    requestData = "Entity ID: " + entityId;
                } else if (firstArg instanceof String) {
                    entityId = (String) firstArg;
                    requestData = "Entity ID: " + entityId;
                } else {
                    entityId = extractEntityIdFromArg(firstArg);
                    requestData = firstArg;
                }
            }

            if (entityId != null) {
                auditService.logDelete(entityName, entityId, requestData);
                log.debug("Audit logged for DELETE operation on entity: {} with ID: {}", entityName, entityId);
            }
        } catch (Exception e) {
            log.error("Failed to audit DELETE operation: {}", e.getMessage(), e);
        }
    }

    private String extractEntityName(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        if (className.endsWith("Service")) {
            className = className.substring(0, className.length() - 7);
        }
        return className;
    }

    private String extractEntityId(Object result) {
        if (result == null) {
            return "unknown";
        }

        try {
            if (result instanceof UUID) {
                return result.toString();
            }

            Method getIdMethod = findIdMethod(result.getClass());
            if (getIdMethod != null) {
                Object id = getIdMethod.invoke(result);
                return id != null ? id.toString() : "unknown";
            }

            if (result.toString().contains("Id")) {
                return result.toString();
            }

            return "unknown";
        } catch (Exception e) {
            log.warn("Failed to extract entity ID from result: {}", e.getMessage());
            return "unknown";
        }
    }

    private String extractEntityIdFromArg(Object arg) {
        if (arg == null) {
            return "unknown";
        }

        try {
            Method getIdMethod = findIdMethod(arg.getClass());
            if (getIdMethod != null) {
                Object id = getIdMethod.invoke(arg);
                return id != null ? id.toString() : "unknown";
            }
            return arg.toString();
        } catch (Exception e) {
            log.warn("Failed to extract entity ID from argument: {}", e.getMessage());
            return "unknown";
        }
    }

    private Method findIdMethod(Class<?> clazz) {
        try {
            for (Method method : clazz.getMethods()) {
                String methodName = method.getName();
                if ((methodName.startsWith("get") &&
                    (methodName.endsWith("Id") || methodName.equals("getId"))) ||
                    methodName.equals("id")) {
                    return method;
                }
            }
        } catch (Exception e) {
            log.debug("No ID method found for class: {}", clazz.getSimpleName());
        }
        return null;
    }
}
