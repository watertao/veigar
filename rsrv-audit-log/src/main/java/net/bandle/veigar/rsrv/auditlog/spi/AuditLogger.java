package net.bandle.veigar.rsrv.auditlog.spi;

import net.bandle.veigar.rsrv.session.spi.AuthenticationObject;
import net.bandle.veigar.rsrv.session.spi.Resource;
import org.springframework.lang.Nullable;

public interface AuditLogger {

  /**
   *
   *
   * @param authObj
   * @param resource
   * @param reqVerb
   * @param requestUri
   * @param remoteIp
   * @param requestBody
   * @param responseBody
   * @param e
   * @param cost
   */
  void log(
    @Nullable AuthenticationObject authObj,
    @Nullable Resource resource,
    String reqVerb,
    String requestUri,
    String remoteIp,
    @Nullable Object requestBody,
    @Nullable Object responseBody,
    @Nullable Throwable e,
    Long cost);

}
