package net.bandle.veigar.rsrv.session.config;

import net.bandle.veigar.rsrv.session.resolver.RSRVHttpSessionIdResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class HttpSessionResolverConfig {

  @Bean
  public HttpSessionIdResolver httpSessionStrategy() {
    return new RSRVHttpSessionIdResolver();
  }

}
