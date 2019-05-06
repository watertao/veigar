package io.github.watertao.veigar.auditlog.spi;

import io.github.watertao.veigar.session.spi.AuthenticationObject;
import io.github.watertao.veigar.session.spi.Resource;
import org.springframework.lang.Nullable;

import java.util.Date;

public interface AuditLogger {

  /**
   *
   *
   * @param authObj
   * @param reqVerb
   * @param requestUri
   * @param remoteIp
   * @param requestBody
   * @param responseBody
   * @param e
   * @param cost
   */
  void log(
    AuthenticationObject authObj,
    Integer operationId,
    String reqVerb,
    String requestUri,
    String operationName,
    String remoteIp,
    @Nullable Object requestBody,
    @Nullable Object responseBody,
    @Nullable Throwable e,
    Date requestTime,
    Long cost);

}
