package io.github.watertao.veigar.session.api;

import io.github.watertao.veigar.session.spi.AuthenticationObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by watertao on 3/26/16.
 */
public class AuthObjHolder {

  private static final String AUTH_OBJ_SESSION_KEY = "net.bandle.veigar.rsrv.session.AuthenticationObject";

  /**
   *
   *
   * @param request
   * @return
     */
  public static AuthenticationObject getAuthObj(HttpServletRequest request) {

    HttpSession session = request.getSession();
    if (session == null) {
      return null;
    }
    AuthenticationObject authObj = (AuthenticationObject) session.getAttribute(AUTH_OBJ_SESSION_KEY);

    return authObj;

  }

  public static void createSession(HttpServletRequest request, AuthenticationObject authObj) {
    HttpSession session = request.getSession();
    if (session != null) {
      session.invalidate();
    }
    session = request.getSession(true);
    authObj.setToken(session.getId());
    session.setAttribute(AUTH_OBJ_SESSION_KEY, authObj);
  }


}
