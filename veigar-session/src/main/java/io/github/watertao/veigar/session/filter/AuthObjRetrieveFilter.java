package io.github.watertao.veigar.session.filter;

import io.github.watertao.veigar.core.exception.UnauthenticatedException;
import io.github.watertao.veigar.core.filter.AbstractJsonRequestAwareFilter;
import io.github.watertao.veigar.core.filter.FilterOrders;
import io.github.watertao.veigar.core.message.LocaleMessage;
import io.github.watertao.veigar.session.api.AuthObjHolder;
import io.github.watertao.veigar.session.spi.AuthenticationObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthObjRetrieveFilter extends AbstractJsonRequestAwareFilter {

  private static final String VERB = "GET";
  private static final String URI = "/system/session";

  @Autowired
  private LocaleMessage localeMessage;

  public AuthObjRetrieveFilter() {
    super(VERB, URI);
  }

  @Override
  protected Object handleJson(HttpServletRequest request, HttpServletResponse response, Object requestBody) {
    AuthenticationObject authObj = AuthObjHolder.getAuthObj(request);

    if (authObj == null) {
      throw new UnauthenticatedException(localeMessage.bm("message.session", "session.notExist"));
    }

    return authObj;
  }

  @Override
  public int getOrder() {
    return FilterOrders.AUTH_OBJ_RETRIEVE;
  }

}
