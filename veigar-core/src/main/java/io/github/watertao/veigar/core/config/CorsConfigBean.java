package io.github.watertao.veigar.core.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CorsConfigBean {

  private static final String CORS_ALLOW_ORIGINS_KEY = "cors.allowedOrigins";
  private static final String CORS_ALLOW_ORIGINS_DEFAULT_VALUE = "*";
  private static final String CORS_ALLOW_METHODS_KEY = "cors.allowedMethods";
  private static final String CORS_ALLOW_METHODS_DEFAULT_VALUE = "POST,PUT,GET,PATCH,DELETE,OPTIONS";
  private static final String CORS_ALLOW_HEADERS_KEY = "cors.allowedHeaders";
  private static final String CORS_ALLOW_HEADERS_DEFAULT_VALUE = "accept,accept-language,content-type,x-auth-token,x-devTools-emulate-network-conditions-client-id,if-modified-since";
  private static final String CORS_EXPOSE_HEADERS_KEY = "cors.exposedHeaders";
  private static final String CORS_EXPOSE_HEADERS_DEFAULT_VALUE = "x-total-count,content-type,x-auth-token";
  private static final String CORS_MAXAGE_KEY = "cors.maxAge";
  private static final Long CORS_MAXAGE_DEFAULT_VALUE = 1728000l;


  @Autowired
  private Environment env;

  private String[] allowedOrigns;
  private String[] allowedHeaders;
  private String[] exposedHeaders;
  private String[] allowedMethods;
  private Long maxAge;


  public String[] getAllowedOrigns() {
    if (allowedOrigns == null) {
      String literal = env.getProperty(CORS_ALLOW_ORIGINS_KEY, CORS_ALLOW_ORIGINS_DEFAULT_VALUE);
      String[] values = literal.split(",", -1);
      allowedOrigns = values;
    }
    return allowedOrigns;
  }

  public String[] getAllowedHeaders() {
    if (allowedHeaders == null) {
      String literal = env.getProperty(CORS_ALLOW_HEADERS_KEY, CORS_ALLOW_HEADERS_DEFAULT_VALUE);
      String[] values = literal.split(",", -1);
      allowedHeaders = values;
    }
    return allowedHeaders;
  }

  public String[] getExposedHeaders() {
    if (exposedHeaders == null) {
      String literal = env.getProperty(CORS_EXPOSE_HEADERS_KEY, CORS_EXPOSE_HEADERS_DEFAULT_VALUE);
      String[] values = literal.split(",", -1);
      exposedHeaders = values;
    }
    return exposedHeaders;
  }

  public String[] getAllowedMethods() {
    if (allowedMethods == null) {
      String literal = env.getProperty(CORS_ALLOW_METHODS_KEY, CORS_ALLOW_METHODS_DEFAULT_VALUE);
      String[] values = literal.split(",", -1);
      allowedMethods = values;
    }
    return allowedMethods;
  }

  public Long getMaxAge() {
    if (maxAge == null) {
      String literal = env.getProperty(CORS_MAXAGE_KEY);
      if (StringUtils.isEmpty(literal)) {
        maxAge = CORS_MAXAGE_DEFAULT_VALUE;
      } else {
        maxAge = Long.parseLong(literal);
      }
    }
    return maxAge;
  }


}
