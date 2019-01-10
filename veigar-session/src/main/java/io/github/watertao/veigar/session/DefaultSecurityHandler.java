package io.github.watertao.veigar.session;

import io.github.watertao.veigar.session.spi.AuthenticationObject;
import io.github.watertao.veigar.session.spi.Resource;
import io.github.watertao.veigar.session.spi.SecurityHandler;

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
