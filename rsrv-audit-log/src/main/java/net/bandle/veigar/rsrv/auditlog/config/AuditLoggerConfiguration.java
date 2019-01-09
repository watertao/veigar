package net.bandle.veigar.rsrv.auditlog.config;

import net.bandle.veigar.rsrv.DefaultAuditLogger;
import net.bandle.veigar.rsrv.auditlog.spi.AuditLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by watertao on 3/24/16.
 */
@Configuration
public class AuditLoggerConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(AuditLoggerConfiguration.class);

  @Bean
  @ConditionalOnMissingBean(AuditLogger.class)
  public AuditLogger auditLogger() {

    logger.info("[ rsrv-audit-log ] No customized request logger found, use DefaultAuditLogger instead.");

    return new DefaultAuditLogger();

  }

}
