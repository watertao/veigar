package io.github.watertao.veigar.core.config;

import io.github.watertao.veigar.core.reqlog.DefaultRequestLogger;
import io.github.watertao.veigar.core.reqlog.RequestLogger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestAspectConfig {

    @Bean
    @ConditionalOnMissingBean(RequestLogger.class)
    public RequestLogger requestLogger() {
        return new DefaultRequestLogger();
    }

}
