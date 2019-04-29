package io.github.watertao.veigar.auth.filter;

import io.github.watertao.veigar.core.aspect.RequestPostProcessor;
import io.github.watertao.veigar.core.exception.UnauthenticatedException;
import io.github.watertao.veigar.core.filter.AbstractJsonRequestAwareFilter;
import io.github.watertao.veigar.core.message.LocaleMessage;
import io.github.watertao.veigar.core.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import io.github.watertao.veigar.core.filter.FilterOrders;
import io.github.watertao.veigar.session.api.AuthObjHolder;
import io.github.watertao.veigar.session.spi.AuthenticationObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by watertao on 3/26/16.
 */
public abstract class CredentialUpdateFilter extends AbstractJsonRequestAwareFilter {

  private static final Logger logger = LoggerFactory.getLogger(CredentialUpdateFilter.class);

  private static final String VERB = "PUT";
  private static final String URI = "/system/session/credential";

  @Autowired(required = false)
  private List<RequestPostProcessor> requestPostProcessors;

  @Autowired
  private LocaleMessage localeMessage;

  public CredentialUpdateFilter() {
    super(VERB, URI);
  }

  public CredentialUpdateFilter(String verb, String uri) {
    super(verb, uri);
  }

  @Override
  protected Object handleJson(HttpServletRequest request, HttpServletResponse response, Object json) {

    Long startTime = System.currentTimeMillis();

    AuthenticationObject authObj = AuthObjHolder.getAuthObj(request);

    if (authObj == null) {
      throw new UnauthenticatedException(localeMessage.bm("message.session", "session.notExist"));
    }

    updateCredential(authObj, json);

    if (requestPostProcessors != null && requestPostProcessors.size() > 0) {
      for (RequestPostProcessor processor : requestPostProcessors) {
        try {
          logger.info("[ post process ] {}", processor.getClass().getSimpleName());
          processor.process(
            HttpRequestHelper.getCurrentRequest(),
            HttpRequestHelper.getCurrentResponse(),
            json,
            authObj,
            null,
            System.currentTimeMillis() - startTime
          );
        } catch (Throwable e) {
          logger.error("Error on post process", e);
        }
      }
    }

    response.setStatus(HttpStatus.OK.value());

    return null;

  }

  @Override
  public int getOrder() {
    return FilterOrders.CREDENTIAL_UPDATE;
  }

  protected abstract void updateCredential(AuthenticationObject authObj, Object requestBody);

}
