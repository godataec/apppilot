package com.bancointernacional.plataformaBI.scheduler;

import com.bancointernacional.plataformaBI.domain.model.period.Process;
import com.bancointernacional.plataformaBI.domain.model.period.ProcessPolicy;
import com.bancointernacional.plataformaBI.repository.period.ProcessRepository;
import com.bancointernacional.plataformaBI.repository.period.ProcessPolicyRepository;
import com.bancointernacional.plataformaBI.service.EmailNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
public class ProcessNotificationScheduler {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ProcessPolicyRepository processPolicyRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Value("${scheduler.process.expiration.warning.enabled:true}")
    private boolean processExpirationWarningEnabled;

    @Value("${scheduler.process.expired.notification.enabled:true}")
    private boolean processExpiredNotificationEnabled;

    @Value("${scheduler.process.policy.expiration.warning.enabled:true}")
    private boolean processPolicyExpirationWarningEnabled;

    @Value("${scheduler.process.policy.overdue.notification.enabled:true}")
    private boolean processPolicyOverdueNotificationEnabled;

    @Value("${scheduler.global.enabled:true}")
    private boolean globalSchedulerEnabled;

    /**
     * Scheduled task to check for processes approaching their expiration date.
     * Sends email notifications at specific intervals before expiration.
     *
     * The cron expression is loaded from application.properties:
     * scheduler.process.expiration.warning.cron
     *
     * Default value: "0 0 8 * * ?" (daily at 8:00 AM)
     *
     * Cron Format: seconds minutes hours day-of-month month day-of-week
     * - 0: seconds (0 = at 0 seconds)
     * - 0: minutes (0 = at 0 minutes)
     * - 8: hours (8 = at 8 AM)
     * - *: day of month (every day)
     * - *: month (every month)
     * - ?: day of week (ignored, using day of month instead)
     *
     * This job sends notifications when processes are exactly 7, 3, or 1 day(s) away from their end date.
     *
     * To change the schedule, modify the property in application.properties:
     * Examples:
     * - Every 30 minutes: 0 30 * * * ?
     * - Monday to Friday at 8 AM: 0 0 8 * * MON-FRI
     * - Every 6 hours: 0 0 6 * * ?
     */
    @Scheduled(cron = "${scheduler.process.expiration.warning.cron:0 0 8 * * ?}")
    public void checkProcessExpirationWarnings() {
        if (!globalSchedulerEnabled || !processExpirationWarningEnabled) {
            log.debug("Process expiration warning scheduler is disabled - Global: {}, Specific: {}",
                     globalSchedulerEnabled, processExpirationWarningEnabled);
            return;
        }

        log.info("Starting process expiration check at {}", LocalDateTime.now());

        try {
            List<Process> activeProcesses = processRepository.findActiveProcesses();
            LocalDateTime now = LocalDateTime.now();

            for (Process process : activeProcesses) {
                if (process.getEndDate() != null) {
                    long daysUntilExpiration = ChronoUnit.DAYS.between(now.toLocalDate(), process.getEndDate().toLocalDate());

                    // Send notifications exactly 7, 3, and 1 day(s) before expiration
                    if (daysUntilExpiration == 7 || daysUntilExpiration == 3 || daysUntilExpiration == 1) {
                        log.info("Sending expiration warning for process {} - {} days until expiration",
                                process.getProcessId(), daysUntilExpiration);

                        emailNotificationService.sendProcessExpirationWarning(
                            process.getProcessId(),
                            process.getProcessDescription(),
                            process.getEndDate(),
                            (int) daysUntilExpiration
                        );
                    }
                }
            }

            log.info("Process expiration check completed successfully");

        } catch (Exception e) {
            log.error("Error during process expiration check", e);
        }
    }

    /**
     * Scheduled task to check for processes that have already expired.
     * Sends daily email notifications for processes that expired within the last 5 days.
     *
     * The cron expression is loaded from application.properties:
     * scheduler.process.expired.notification.cron
     *
     * Default value: "0 0 9 * * ?" (daily at 9:00 AM)
     *
     * Cron Format: seconds minutes hours day-of-month month day-of-week
     * - 0: seconds (0 = at 0 seconds)
     * - 0: minutes (0 = at 0 minutes)
     * - 9: hours (9 = at 9 AM)
     * - *: day of month (every day)
     * - *: month (every month)
     * - ?: day of week (ignored, using day of month instead)
     *
     * This job sends notifications for processes that expired 1-5 days ago.
     * After 5 days, no more notifications are sent for that expired process.
     *
     * To change the schedule, modify the property in application.properties:
     * Examples:
     * - Every hour: 0 0 * * * ?
     * - Twice daily (9 AM and 2 PM): 0 0 9,14 * * ?
     * - Business days only at 9 AM: 0 0 9 * * MON-FRI
     */
    @Scheduled(cron = "${scheduler.process.expired.notification.cron:0 0 9 * * ?}")
    public void checkExpiredProcesses() {
        if (!globalSchedulerEnabled || !processExpiredNotificationEnabled) {
            log.debug("Expired process notification scheduler is disabled - Global: {}, Specific: {}",
                     globalSchedulerEnabled, processExpiredNotificationEnabled);
            return;
        }

        log.info("Starting expired process check at {}", LocalDateTime.now());

        try {
            List<Process> activeProcesses = processRepository.findActiveProcesses();
            LocalDateTime now = LocalDateTime.now();

            for (Process process : activeProcesses) {
                if (process.getEndDate() != null) {
                    long daysAfterExpiration = ChronoUnit.DAYS.between(process.getEndDate().toLocalDate(), now.toLocalDate());

                    // Send daily notifications for processes expired between 1-5 days ago
                    // daysAfterExpiration > 0: process has expired
                    // daysAfterExpiration <= 5: only send notifications for up to 5 days after expiration
                    if (daysAfterExpiration > 0 && daysAfterExpiration <= 5) {
                        log.info("Sending expired notification for process {} - {} days after expiration",
                                process.getProcessId(), daysAfterExpiration);

                        emailNotificationService.sendProcessExpiredNotification(
                            process.getProcessId(),
                            process.getProcessDescription(),
                            process.getEndDate(),
                            (int) daysAfterExpiration
                        );
                    }
                }
            }

            log.info("Expired process check completed successfully");

        } catch (Exception e) {
            log.error("Error during expired process check", e);
        }
    }

    /**
     * Scheduled task to check for ProcessPolicies approaching their expiration date.
     * Sends email notifications at specific intervals before expiration.
     *
     * The cron expression is loaded from application.properties:
     * scheduler.process.policy.expiration.warning.cron
     *
     * Default value: "0 0 10 * * ?" (daily at 10:00 AM)
     *
     * Cron Format: seconds minutes hours day-of-month month day-of-week
     * - 0: seconds (0 = at 0 seconds)
     * - 0: minutes (0 = at 0 minutes)
     * - 10: hours (10 = at 10 AM)
     * - *: day of month (every day)
     * - *: month (every month)
     * - ?: day of week (ignored, using day of month instead)
     *
     * This job sends notifications when ProcessPolicies are exactly 90, 60, 30, 7, or 1 day(s) away from their end date.
     *
     * To change the schedule, modify the property in application.properties:
     * Examples:
     * - Every 2 hours: 0 0 2 * * ?
     * - Monday to Friday at 10 AM: 0 0 10 * * MON-FRI
     * - Twice daily: 0 0 10,15 * * ?
     */
    @Scheduled(cron = "${scheduler.process.policy.expiration.warning.cron:0 0 10 * * ?}")
    public void checkProcessPolicyExpirationWarnings() {
        if (!globalSchedulerEnabled || !processPolicyExpirationWarningEnabled) {
            log.debug("ProcessPolicy expiration warning scheduler is disabled - Global: {}, Specific: {}",
                     globalSchedulerEnabled, processPolicyExpirationWarningEnabled);
            return;
        }

        log.info("Starting ProcessPolicy expiration check at {}", LocalDateTime.now());

        try {
            List<ProcessPolicy> activePolicies = processPolicyRepository.findActiveProcessPolicies();
            LocalDate now = LocalDate.now();

            for (ProcessPolicy policy : activePolicies) {
                if (policy.getDateEnd() != null) {
                    long daysUntilExpiration = ChronoUnit.DAYS.between(now, policy.getDateEnd());

                    // Send notifications exactly 90, 60, 30, 7, and 1 day(s) before expiration
                    if (daysUntilExpiration == 90 || daysUntilExpiration == 60 || daysUntilExpiration == 30 ||
                        daysUntilExpiration == 7 || daysUntilExpiration == 1) {

                        log.info("Sending ProcessPolicy expiration warning for policy {} - {} days until expiration",
                                policy.getProcessPolicyId(), daysUntilExpiration);

                        String processDescription = policy.getProcess() != null ?
                            policy.getProcess().getProcessDescription() : "Proceso ID: " + policy.getProcessPolicyId();

                        emailNotificationService.sendProcessPolicyExpirationWarning(
                            policy.getProcessPolicyId(),
                            processDescription,
                            policy.getDateEnd(),
                            (int) daysUntilExpiration
                        );
                    }
                }
            }

            log.info("ProcessPolicy expiration check completed successfully");

        } catch (Exception e) {
            log.error("Error during ProcessPolicy expiration check", e);
        }
    }

    /**
     * Scheduled task to check for ProcessPolicies that have expired but are still in OPEN status.
     * Sends daily email notifications for overdue policies.
     *
     * The cron expression is loaded from application.properties:
     * scheduler.process.policy.overdue.notification.cron
     *
     * Default value: "0 0 11 * * ?" (daily at 11:00 AM)
     *
     * Cron Format: seconds minutes hours day-of-month month day-of-week
     * - 0: seconds (0 = at 0 seconds)
     * - 0: minutes (0 = at 0 minutes)
     * - 11: hours (11 = at 11 AM)
     * - *: day of month (every day)
     * - *: month (every month)
     * - ?: day of week (ignored, using day of month instead)
     *
     * This job sends notifications for ProcessPolicies that have passed their end date
     * but still have an OPEN status, indicating they need immediate attention.
     *
     * To change the schedule, modify the property in application.properties:
     * Examples:
     * - Every hour during business hours: 0 0 8-17 * * MON-FRI
     * - Three times daily: 0 0 9,13,17 * * ?
     * - Every 4 hours: 0 0 4 * * ?
     */
    @Scheduled(cron = "${scheduler.process.policy.overdue.notification.cron:0 0 11 * * ?}")
    public void checkOverdueProcessPolicies() {
        if (!globalSchedulerEnabled || !processPolicyOverdueNotificationEnabled) {
            log.debug("ProcessPolicy overdue notification scheduler is disabled - Global: {}, Specific: {}",
                     globalSchedulerEnabled, processPolicyOverdueNotificationEnabled);
            return;
        }

        log.info("Starting overdue ProcessPolicy check at {}", LocalDateTime.now());

        try {
            List<ProcessPolicy> activePolicies = processPolicyRepository.findActiveProcessPolicies();
            LocalDate now = LocalDate.now();

            for (ProcessPolicy policy : activePolicies) {
                if (policy.getDateEnd() != null && policy.getProcessPolicyStatus() != null) {
                    long daysAfterExpiration = ChronoUnit.DAYS.between(policy.getDateEnd(), now);

                    // Send notifications for policies that have expired and are still in OPEN status
                    // Check if policy is overdue (past end date) and status is still OPEN
                    if (daysAfterExpiration > 0 && "OPEN".equalsIgnoreCase(policy.getProcessPolicyStatus())) {

                        log.info("Sending overdue notification for ProcessPolicy {} - {} days after expiration, status: {}",
                                policy.getProcessPolicyId(), daysAfterExpiration, policy.getProcessPolicyStatus());

                        String processDescription = policy.getProcess() != null ?
                            policy.getProcess().getProcessDescription() : "Proceso ID: " + policy.getProcessPolicyId();

                        emailNotificationService.sendProcessPolicyOverdueNotification(
                            policy.getProcessPolicyId(),
                            processDescription,
                            policy.getDateEnd(),
                            (int) daysAfterExpiration
                        );
                    }
                }
            }

            log.info("Overdue ProcessPolicy check completed successfully");

        } catch (Exception e) {
            log.error("Error during overdue ProcessPolicy check", e);
        }
    }
}
