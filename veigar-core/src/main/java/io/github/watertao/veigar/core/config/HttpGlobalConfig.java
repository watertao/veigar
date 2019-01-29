package io.github.watertao.veigar.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class HttpGlobalConfig implements WebMvcConfigurer {

  @Autowired
  private CorsConfigBean corsConfigBean;

  @Override
  public void addCorsMappings(CorsRegistry registry) {

    registry.addMapping("/**")
      .allowedMethods(corsConfigBean.getAllowedMethods())
      .allowedHeaders(corsConfigBean.getAllowedHeaders())
      .exposedHeaders(corsConfigBean.getExposedHeaders())
      .allowedOrigins(corsConfigBean.getAllowedOrigns())
      .allowCredentials(true)
      .maxAge(corsConfigBean.getMaxAge());

  }

}
