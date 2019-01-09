package net.bandle.veigar.rsrv.session.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bandle.veigar.rsrv.exception.InternalServerErrorException;
import net.bandle.veigar.rsrv.exception.UnauthenticatedException;
import net.bandle.veigar.rsrv.filter.AbstractJsonRequestAwareFilter;
import net.bandle.veigar.rsrv.filter.FilterOrders;
import net.bandle.veigar.rsrv.message.LocaleMessage;
import net.bandle.veigar.rsrv.session.api.AuthObjHolder;
import net.bandle.veigar.rsrv.session.spi.AuthenticationObject;
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
