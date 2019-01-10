package net.bandle.veigar.rsrv.auth.filter;

import io.github.watertao.veigar.core.exception.UnauthenticatedException;
import io.github.watertao.veigar.core.filter.AbstractJsonRequestAwareFilter;
import io.github.watertao.veigar.core.message.LocaleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.watertao.veigar.core.filter.FilterOrders;
import io.github.watertao.veigar.session.api.AuthObjHolder;
import io.github.watertao.veigar.session.spi.AuthenticationObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class AuthenticationFilter extends AbstractJsonRequestAwareFilter {

  private static final String VERB = "POST";
  private static final String URI = "/system/session";

  @Autowired
  private LocaleMessage localeMessage;

  public AuthenticationFilter() {
    super(VERB, URI);
  }

  @Override
  protected Object handleJson(HttpServletRequest request, HttpServletResponse response, Object content) {

    AuthenticationObject authObj = authenticate(content);

    if (authObj == null) {
      throw new UnauthenticatedException(localeMessage.bm("message.auth", "authentication.error"));
    }

    AuthObjHolder.createSession(request, authObj);

    return authObj;

  }

  protected abstract AuthenticationObject authenticate(Object requestBody);

  @Override
  public int getOrder() {
    return FilterOrders.AUTHENTICATE;
  }

}
