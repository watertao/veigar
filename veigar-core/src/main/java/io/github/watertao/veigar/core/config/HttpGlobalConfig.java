package io.github.watertao.veigar.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class HttpGlobalConfig implements WebMvcConfigurer {

  private static final String CORS_ALLOW_ORIGINS_KEY = "cors.allowedOrigins";

  @Autowired
  private Environment env;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    String allowedOriginsLiteral = env.getProperty(CORS_ALLOW_ORIGINS_KEY, "");
    String[] allowedOrigins = allowedOriginsLiteral.split(",", -1);
    registry.addMapping("/**")
      .allowedMethods("POST", "PUT", "GET", "PATCH", "DELETE", "OPTIONS")
      .allowedHeaders("accept", "accept-language", "content-type", "x-auth-token", "if-modified-since")
      .exposedHeaders("x-total-count", "content-type", "x-auth-token")
      .allowedOrigins(allowedOrigins)
      .allowCredentials(true)
      .maxAge(1728000l);
  }

}
