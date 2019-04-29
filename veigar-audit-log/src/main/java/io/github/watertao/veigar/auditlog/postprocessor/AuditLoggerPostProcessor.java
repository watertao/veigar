package io.github.watertao.veigar.auditlog.postprocessor;

import io.github.watertao.veigar.auditlog.spi.AuditLogger;
import io.github.watertao.veigar.core.aspect.RequestPostProcessor;
import io.github.watertao.veigar.session.api.AuthObjHolder;
import io.github.watertao.veigar.session.api.ResourceHolder;
import io.github.watertao.veigar.session.spi.AuthenticationObject;
import io.github.watertao.veigar.session.spi.Resource;
import io.github.watertao.veigar.session.spi.SecurityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AuditLoggerPostProcessor implements RequestPostProcessor {

  // we just log the POST/PUT/PATCH/DELETE methods
  private static final List<String> METHODS_TO_LOG = new ArrayList<String>();

  static {
    METHODS_TO_LOG.add("POST");
    METHODS_TO_LOG.add("PUT");
    METHODS_TO_LOG.add("PATCH");
    METHODS_TO_LOG.add("DELETE");
  }

  private static final String AUDIT_LOG_IP_HEADER_KEY = "auditLog.ip.header";
  private static final String AUDIT_LOG_LOG_FAIL_KEY = "auditLog.logFail";

  @Autowired
  private Environment env;

  @Autowired
  private AuditLogger auditLogger;

  @Autowired
  private SecurityHandler securityHandler;

  @Override
  public void process(
    HttpServletRequest request,
    HttpServletResponse response,
    Object requestBody,
    Object responseBody,
    Throwable e,
    Date requestTime,
    Long cost) {

    if (METHODS_TO_LOG.contains(request.getMethod())) {

      Boolean isLogFail = env.getProperty(AUDIT_LOG_LOG_FAIL_KEY, Boolean.class, false);

      if (!isLogFail && e != null) {
        return;
      }

      AuthenticationObject authObj = AuthObjHolder.getAuthObj(request);
      if (authObj == null) {
        return;
      }

      Resource restApi = securityHandler.identifyResource(request.getMethod(), request.getRequestURI(), authObj);
      if (restApi == null) {
        return;
      }

      Date requestAcceptTimestamp = new Date();

      // retrieve remote ip
      String remoteIp = null;
      if (env.getProperty(AUDIT_LOG_IP_HEADER_KEY) != null) {
        remoteIp = request.getHeader(env.getProperty(AUDIT_LOG_IP_HEADER_KEY));
      } else {
        remoteIp = request.getRemoteAddr();
      }

      auditLogger.log(
        authObj,
        restApi.getVerb(),
        restApi.getUriPattern(),
        restApi.getName(),
        remoteIp,
        requestBody,
        responseBody,
        e,
        requestAcceptTimestamp,
        cost
      );

    }



  }

}
