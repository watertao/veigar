package io.github.watertao.veigar.session.api;



import io.github.watertao.veigar.session.spi.Resource;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by watertao on 3/27/16.
 */
public class ResourceHolder {

  private static final String RESOURCE_REQUEST_KEY = "net.bandle.veigar.rsrv.security.spi.Resource";

  public static Resource getResource(HttpServletRequest request) {
    return (Resource) request.getAttribute(RESOURCE_REQUEST_KEY);
  }

  public static void setResource(HttpServletRequest request, Resource resource) {
    request.setAttribute(RESOURCE_REQUEST_KEY, resource);
  }

}
