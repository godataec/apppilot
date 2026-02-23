package com.bancointernacional.plataformaBI.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class EmailNotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${notification.emails}")
    private String notificationEmails;

    @Value("${notification.from.email}")
    private String fromEmail;

    @Value("${notification.enabled:true}")
    private boolean notificationEnabled;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter DATE_ONLY_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void sendProcessExpirationWarning(UUID processId, String processDescription,
                                           LocalDateTime endDate, int daysUntilExpiration) {
        if (!notificationEnabled) {
            log.info("Email notifications are disabled");
            return;
        }

        List<String> recipients = Arrays.asList(notificationEmails.split(","));

        String subject = String.format("Advertencia: Proceso próximo a vencer - %d días restantes", daysUntilExpiration);
        String body = buildExpirationWarningBody(processId, processDescription, endDate, daysUntilExpiration);

        sendEmail(recipients, subject, body);
        log.info("Process expiration warning sent for process {} - {} days until expiration",
                processId, daysUntilExpiration);
    }

    public void sendProcessExpiredNotification(UUID processId, String processDescription,
                                             LocalDateTime endDate, int daysAfterExpiration) {
        if (!notificationEnabled) {
            log.info("Email notifications are disabled");
            return;
        }

        List<String> recipients = Arrays.asList(notificationEmails.split(","));

        String subject = String.format("Proceso Vencido: %s - %d días después del vencimiento",
                                      processDescription, daysAfterExpiration);
        String body = buildExpiredNotificationBody(processId, processDescription, endDate, daysAfterExpiration);

        sendEmail(recipients, subject, body);
        log.info("Process expired notification sent for process {} - {} days after expiration",
                processId, daysAfterExpiration);
    }

    public void sendProcessPolicyExpirationWarning(UUID processPolicyId, String processDescription,
                                                  LocalDate endDate, int daysUntilExpiration) {
        if (!notificationEnabled) {
            log.info("Email notifications are disabled");
            return;
        }

        List<String> recipients = Arrays.asList(notificationEmails.split(","));

        String subject = String.format("Recordatorio: Póliza próxima a vencer - %d días restantes", daysUntilExpiration);
        String body = buildPolicyExpirationWarningBody(processPolicyId, processDescription, endDate, daysUntilExpiration);

        sendEmail(recipients, subject, body);
        log.info("ProcessPolicy expiration warning sent for policy {} - {} days until expiration",
                processPolicyId, daysUntilExpiration);
    }

    public void sendProcessPolicyOverdueNotification(UUID processPolicyId, String processDescription,
                                                   LocalDate endDate, int daysAfterExpiration) {
        if (!notificationEnabled) {
            log.info("Email notifications are disabled");
            return;
        }

        List<String> recipients = Arrays.asList(notificationEmails.split(","));

        String subject = String.format("Póliza Vencida Activa: %s - %d días después del vencimiento",
                                      processDescription, daysAfterExpiration);
        String body = buildPolicyOverdueNotificationBody(processPolicyId, processDescription, endDate, daysAfterExpiration);

        sendEmail(recipients, subject, body);
        log.info("ProcessPolicy overdue notification sent for policy {} - {} days after expiration",
                processPolicyId, daysAfterExpiration);
    }

    private void sendEmail(List<String> recipients, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(recipients.toArray(new String[0]));
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            log.info("Email sent successfully to {} recipients", recipients.size());
        } catch (Exception e) {
            log.error("Failed to send email notification", e);
        }
    }

    private String buildExpirationWarningBody(UUID processId, String processDescription,
                                            LocalDateTime endDate, int daysUntilExpiration) {
        StringBuilder body = new StringBuilder();
        body.append("Estimado equipo,\n\n");
        body.append("Este es un recordatorio automático sobre un proceso que está próximo a vencer.\n\n");
        body.append("Detalles del proceso:\n");
        body.append("- ID del Proceso: ").append(processId).append("\n");
        body.append("- Descripción: ").append(processDescription).append("\n");
        body.append("- Fecha de vencimiento: ").append(endDate.format(DATE_FORMATTER)).append("\n");
        body.append("- Días restantes: ").append(daysUntilExpiration).append("\n\n");

        if (daysUntilExpiration == 1) {
            body.append(" ATENCION: Este proceso vence MANANA. Se requiere accion inmediata.\n\n");
        } else if (daysUntilExpiration == 3) {
            body.append(" IMPORTANTE: Este proceso vence en 3 dias. Planifique las acciones necesarias.\n\n");
        } else if (daysUntilExpiration == 7) {
            body.append(" RECORDATORIO: Este proceso vence en una semana. Comience a preparar las actividades finales.\n\n");
        }

        body.append("Por favor, tome las medidas necesarias para completar este proceso antes de su vencimiento.\n\n");
        body.append("Saludos cordiales,\n");
        body.append("Sistema de Notificaciones Automáticas\n");
        body.append("Plataforma BI - Banco Internacional");

        return body.toString();
    }

    private String buildExpiredNotificationBody(UUID processId, String processDescription,
                                              LocalDateTime endDate, int daysAfterExpiration) {
        StringBuilder body = new StringBuilder();
        body.append("Estimado equipo,\n\n");
        body.append("Este es un recordatorio sobre un proceso que ha vencido y requiere atención inmediata.\n\n");
        body.append("Detalles del proceso vencido:\n");
        body.append("- ID del Proceso: ").append(processId).append("\n");
        body.append("- Descripción: ").append(processDescription).append("\n");
        body.append("- Fecha de vencimiento: ").append(endDate.format(DATE_FORMATTER)).append("\n");
        body.append("- Días desde el vencimiento: ").append(daysAfterExpiration).append("\n\n");
        body.append(" URGENTE: Este proceso ha vencido y requiere resolución inmediata.\n\n");
        body.append("Por favor, tome las medidas correctivas necesarias lo antes posible.\n\n");
        body.append("Saludos cordiales,\n");
        body.append("Sistema de Notificaciones Automáticas\n");
        body.append("Plataforma BI - Banco Internacional");

        return body.toString();
    }

    private String buildPolicyExpirationWarningBody(UUID processPolicyId, String processDescription,
                                                  LocalDate endDate, int daysUntilExpiration) {
        StringBuilder body = new StringBuilder();
        body.append("Estimado equipo,\n\n");
        body.append("Este es un recordatorio automático sobre una póliza que está próxima a vencer.\n\n");
        body.append("Detalles de la póliza:\n");
        body.append("- ID de la Póliza: ").append(processPolicyId).append("\n");
        body.append("- Descripción: ").append(processDescription).append("\n");
        body.append("- Fecha de vencimiento: ").append(endDate.format(DATE_ONLY_FORMATTER)).append("\n");
        body.append("- Días restantes: ").append(daysUntilExpiration).append("\n\n");

        if (daysUntilExpiration == 1) {
            body.append(" ATENCIÓN: Esta póliza vence MAÑANA. Se requiere acción inmediata.\n\n");
        } else if (daysUntilExpiration == 7) {
            body.append(" RECORDATORIO: Esta póliza vence en una semana. Comience a preparar las actividades finales.\n\n");
        } else if (daysUntilExpiration == 30) {
            body.append(" PLANIFICACIÓN: Esta póliza vence en 30 días. Es momento de iniciar el proceso de renovación.\n\n");
        } else if (daysUntilExpiration == 60) {
            body.append(" AVISO TEMPRANO: Esta póliza vence en 60 días. Considere iniciar las gestiones de renovación.\n\n");
        } else if (daysUntilExpiration == 90) {
            body.append(" NOTIFICACIÓN: Esta póliza vence en 90 días. Manténgase al tanto para futuras acciones.\n\n");
        }

        body.append("Por favor, tome las medidas necesarias para completar o renovar esta póliza antes de su vencimiento.\n\n");
        body.append("Saludos cordiales,\n");
        body.append("Sistema de Notificaciones Automáticas\n");
        body.append("Plataforma BI - Banco Internacional");

        return body.toString();
    }

    private String buildPolicyOverdueNotificationBody(UUID processPolicyId, String processDescription,
                                                   LocalDate endDate, int daysAfterExpiration) {
        StringBuilder body = new StringBuilder();
        body.append("Estimado equipo,\n\n");
        body.append("Este es un recordatorio sobre una póliza que ha vencido y aún permanece activa (OPEN).\n\n");
        body.append("Detalles de la póliza vencida:\n");
        body.append("- ID de la Póliza: ").append(processPolicyId).append("\n");
        body.append("- Descripción: ").append(processDescription).append("\n");
        body.append("- Fecha de vencimiento: ").append(endDate.format(DATE_ONLY_FORMATTER)).append("\n");
        body.append("- Días desde el vencimiento: ").append(daysAfterExpiration).append("\n");
        body.append("- Estado actual: OPEN (Activa)\n\n");
        body.append(" URGENTE: Esta póliza ha vencido pero aún está activa y requiere atención inmediata.\n\n");
        body.append("Por favor, tome las medidas correctivas necesarias para cerrar o renovar esta póliza lo antes posible.\n\n");
        body.append("Saludos cordiales,\n");
        body.append("Sistema de Notificaciones Automáticas\n");
        body.append("Plataforma BI - Banco Internacional");

        return body.toString();
    }
}
