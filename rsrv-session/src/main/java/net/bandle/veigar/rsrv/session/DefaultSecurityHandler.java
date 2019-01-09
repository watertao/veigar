package net.bandle.veigar.rsrv.session;

import net.bandle.veigar.rsrv.session.spi.AuthenticationObject;
import net.bandle.veigar.rsrv.session.spi.Resource;
import net.bandle.veigar.rsrv.session.spi.SecurityHandler;

/**
 * Created by watertao on 3/27/16.
 */
public class DefaultSecurityHandler implements SecurityHandler {

  @Override
  public Resource identifyResource(String verb, String uri, AuthenticationObject authObj) {
    // all request will be marked as un-security
    return null;
  }

}
