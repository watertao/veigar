package io.github.watertao.veigar.auth.filter;

import io.github.watertao.veigar.core.aspect.RequestPostProcessor;
import io.github.watertao.veigar.core.exception.UnauthenticatedException;
import io.github.watertao.veigar.core.filter.AbstractJsonRequestAwareFilter;
import io.github.watertao.veigar.core.message.LocaleMessage;
import io.github.watertao.veigar.core.util.HttpRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.watertao.veigar.core.filter.FilterOrders;
import io.github.watertao.veigar.session.api.AuthObjHolder;
import io.github.watertao.veigar.session.spi.AuthenticationObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public abstract class AuthenticationFilter extends AbstractJsonRequestAwareFilter {

  private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

  private static final String VERB = "POST";
  private static final String URI = "/system/session";

  @Autowired
  private LocaleMessage localeMessage;

  @Autowired(required = false)
  private List<RequestPostProcessor> requestPostProcessors;

  public AuthenticationFilter() {
    super(VERB, URI);
  }

  @Override
  protected Object handleJson(HttpServletRequest request, HttpServletResponse response, Object content) {

    Long startTime = System.currentTimeMillis();

    AuthenticationObject authObj = authenticate(request, response, content);

    if (authObj == null) {
      throw new UnauthenticatedException(localeMessage.bm("message.auth", "authentication.error"));
    }

    if (requestPostProcessors != null && requestPostProcessors.size() > 0) {
      for (RequestPostProcessor processor : requestPostProcessors) {
        try {
          logger.info("[ post process ] {}", processor.getClass().getSimpleName());
          processor.process(
            HttpRequestHelper.getCurrentRequest(),
            HttpRequestHelper.getCurrentResponse(),
            content,
            authObj,
            null,
            System.currentTimeMillis() - startTime
          );
        } catch (Throwable e) {
          logger.error("Error on post process", e);
        }
      }
    }

    AuthObjHolder.createSession(request, authObj);

    return authObj;

  }

  protected abstract AuthenticationObject authenticate(
    HttpServletRequest request,
    HttpServletResponse response,
    Object requestBody
  );

  @Override
  public int getOrder() {
    return FilterOrders.AUTHENTICATE;
  }

}
