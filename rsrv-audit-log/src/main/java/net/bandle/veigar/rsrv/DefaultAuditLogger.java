package net.bandle.veigar.rsrv;

import net.bandle.veigar.rsrv.session.spi.AuthenticationObject;
import net.bandle.veigar.rsrv.session.spi.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bandle.veigar.rsrv.auditlog.spi.AuditLogger;

/**
 * A default implementation of <code>net.bandle.veigar.rsrv.reqlog.AuditLogger</code>
 */
public class DefaultAuditLogger implements AuditLogger {

  private static final Logger logger = LoggerFactory.getLogger(DefaultAuditLogger.class);

  @Override
  public void log(
    AuthenticationObject authObj,
    Resource resource,
    String reqVerb,
    String requestUri,
    String remoteIp,
    Object requestBody,
    Object responseBody,
    Throwable e,
    Long cost) {

    logger.warn("Dummy AuditLogger: you should implemented a meaningful AuditLogger");

  }

}
