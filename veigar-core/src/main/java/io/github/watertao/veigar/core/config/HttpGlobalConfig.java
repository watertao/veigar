package io.github.watertao.veigar.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class HttpGlobalConfig implements WebMvcConfigurer {

  private static final String DEFAULT_DATE_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
  private static final String DATE_FORMATTER_KEY = "spring.jackson.date-format";

  @Autowired
  private CorsConfigBean corsConfigBean;

  @Autowired
  private Environment env;

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

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addFormatter(new DateFormatter(env.getProperty(DATE_FORMATTER_KEY, DEFAULT_DATE_FORMATTER)));
  }


}
