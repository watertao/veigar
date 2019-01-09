package net.bandle.veigar.rsrv.config;

import net.bandle.veigar.rsrv.reqlog.DefaultRequestLogger;
import net.bandle.veigar.rsrv.reqlog.RequestLogger;
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
