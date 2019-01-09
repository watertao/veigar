package net.bandle.veigar.rsrv.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * The order used on servlet filter in  RSRV framework
 * A filter with smaller order will be executed earlier
 *
 *
 * @author watertao
 */
public interface FilterOrders {

  int EXCEPTION_CONVERSION = Ordered.LOWEST_PRECEDENCE + 1;

  int AUTH_OBJ_RETRIEVE = 1010;

  int LOGOUT = 1020;
  int AUTHENTICATE = 1021;
  int CREDENTIAL_UPDATE = 1022;
  int SECURITY = 1030;

}
