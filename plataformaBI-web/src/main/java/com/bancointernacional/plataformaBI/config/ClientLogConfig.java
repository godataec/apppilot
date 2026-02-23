/*package com.bancointernacional.plataformaBI.config;

import ec.com.bancointernacional.log.client.base.ClientLogSender;
import ec.com.bancointernacional.log.client.config.ConfigLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
*/
//@Configuration
//public class ClientLogConfig {
//
//    private static final Logger logger = LogManager.getLogger(ClientLogConfig.class);
//
//    @Value("${log.enabled:false}")
//    private boolean loggingEnabled;
//
//    @Value("${log.url.client}")
//    private String logUrl;
//
//    @Value("${log.port.client}")
//    private int port;
//
//    @Value("${log.timeout.client:3000}")
//    private int timeout;
//
//    @Bean
//    public ClientLogSender clientLogSender() {
//        if (!loggingEnabled) {
//            logger.info("Client logging is disabled, ClientLogSender will be null");
//            return null;
//        }
//
//        try {
//            // Create configuration for ConfigLog whit the BI properties
//            ConfigLog configLog = ConfigLog.defaultInstance().withHost(logUrl).withPort(port);
//            configLog.withHost(logUrl);
//
//            ClientLogSender clientLogSender = new ClientLogSender(configLog);
//            logger.info("ClientLogSender initialized successfully with URL: {}", logUrl);
//            return clientLogSender;
//
//        } catch (Exception e) {
//            logger.error("Failed to initialize ClientLogSender: {}", e.getMessage(), e);
//            return null;
//        }
//    }
//}
