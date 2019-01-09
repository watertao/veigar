package net.bandle.veigar.rsrv.auth.filter;

import net.bandle.veigar.rsrv.exception.UnauthenticatedException;
import net.bandle.veigar.rsrv.filter.AbstractJsonRequestAwareFilter;
import net.bandle.veigar.rsrv.message.LocaleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import net.bandle.veigar.rsrv.filter.FilterOrders;
import net.bandle.veigar.rsrv.session.api.AuthObjHolder;
import net.bandle.veigar.rsrv.session.spi.AuthenticationObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by watertao on 3/26/16.
 */
public abstract class CredentialUpdateFilter extends AbstractJsonRequestAwareFilter {

  private static final String VERB = "PUT";
  private static final String URI = "/system/session/credential";

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

    AuthenticationObject authObj = AuthObjHolder.getAuthObj(request);

    if (authObj == null) {
      throw new UnauthenticatedException(localeMessage.bm("message.session", "session.notExist"));
    }

    updateCredential(authObj, json);

    response.setStatus(HttpStatus.OK.value());

    return null;

  }

  @Override
  public int getOrder() {
    return FilterOrders.CREDENTIAL_UPDATE;
  }

  protected abstract void updateCredential(AuthenticationObject authObj, Object requestBody);

}
