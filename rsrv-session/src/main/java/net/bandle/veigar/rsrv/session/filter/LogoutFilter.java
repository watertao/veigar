package net.bandle.veigar.rsrv.session.filter;

import net.bandle.veigar.rsrv.filter.AbstractRequestAwareFilter;
import org.springframework.http.HttpStatus;
import net.bandle.veigar.rsrv.filter.FilterOrders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by watertao on 3/26/16.
 */
public class LogoutFilter extends AbstractRequestAwareFilter {

  private static final String VERB = "DELETE";
  private static final String URI = "/system/session";

  public LogoutFilter() {
    super(VERB, URI);
  }

  public LogoutFilter(String verb, String uri) {
    super(verb, uri);
  }

  @Override
  public int getOrder() {
    return FilterOrders.LOGOUT;
  }


  @Override
  protected void handle(HttpServletRequest request, HttpServletResponse response) {

    HttpSession session = request.getSession();
    if (session != null) {
      session.invalidate();
    }

    response.setStatus(HttpStatus.NO_CONTENT.value());

  }
}
