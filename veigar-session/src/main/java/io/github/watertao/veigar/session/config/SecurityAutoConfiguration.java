package io.github.watertao.veigar.session.config;

import io.github.watertao.veigar.session.spi.SecurityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.watertao.veigar.session.DefaultSecurityHandler;


/**
 * Created by watertao on 3/24/16.
 */
@Configuration
public class SecurityAutoConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(SecurityAutoConfiguration.class);

  @Bean
  @ConditionalOnMissingBean(SecurityHandler.class)
  public SecurityHandler securityDataHandler() {

    logger.info("[rsrv-security] No customized security data handler found, use DefaultSecurityHandler instead.");

    return new DefaultSecurityHandler();
  }

}
