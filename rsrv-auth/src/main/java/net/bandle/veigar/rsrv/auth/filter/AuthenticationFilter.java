package net.bandle.veigar.rsrv.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bandle.veigar.rsrv.exception.UnauthenticatedException;
import net.bandle.veigar.rsrv.filter.AbstractJsonRequestAwareFilter;
import net.bandle.veigar.rsrv.message.LocaleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import net.bandle.veigar.rsrv.filter.FilterOrders;
import net.bandle.veigar.rsrv.session.api.AuthObjHolder;
import net.bandle.veigar.rsrv.session.spi.AuthenticationObject;

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
