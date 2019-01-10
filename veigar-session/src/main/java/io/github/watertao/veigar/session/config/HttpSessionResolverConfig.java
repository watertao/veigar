package io.github.watertao.veigar.session.config;

import io.github.watertao.veigar.session.resolver.RSRVHttpSessionIdResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class HttpSessionResolverConfig {

  @Bean
  public HttpSessionIdResolver httpSessionStrategy() {
    return new RSRVHttpSessionIdResolver();
  }

}
