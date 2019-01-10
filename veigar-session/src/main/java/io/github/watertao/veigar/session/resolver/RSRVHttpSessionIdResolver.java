package io.github.watertao.veigar.session.resolver;

import org.springframework.session.web.http.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

public class RSRVHttpSessionIdResolver implements HttpSessionIdResolver {

  private static final String HEADER_X_AUTH_TOKEN = "X-Auth-Token";
  private static final String COOKIE_AUTH_TOKEN = "auth_token";
  private static final String QUERY_PARAM_AUTH_TOKEN = "auth_token";

  private HttpSessionIdResolver headerIdResolver;

  private HttpSessionIdResolver cookieIdResolver;

  public RSRVHttpSessionIdResolver() {

    headerIdResolver = new HeaderHttpSessionIdResolver(HEADER_X_AUTH_TOKEN);

    CookieSerializer cookieSerializer = new DefaultCookieSerializer();
    ((DefaultCookieSerializer) cookieSerializer).setCookieName(COOKIE_AUTH_TOKEN);
    ((DefaultCookieSerializer) cookieSerializer).setUseBase64Encoding(false);
    cookieIdResolver = new CookieHttpSessionIdResolver();
    ((CookieHttpSessionIdResolver) cookieIdResolver).setCookieSerializer(cookieSerializer);

  }

  @Override
  public List<String> resolveSessionIds(HttpServletRequest httpServletRequest) {
    String sessionId = getRequestedSessionId(httpServletRequest);
    if (sessionId != null) {
      return Collections.singletonList(sessionId);
    } else {
      return Collections.EMPTY_LIST;
    }
  }

  @Override
  public void setSessionId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String s) {
    headerIdResolver.setSessionId(httpServletRequest, httpServletResponse, s);
    cookieIdResolver.setSessionId(httpServletRequest, httpServletResponse, s);
  }

  @Override
  public void expireSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    headerIdResolver.expireSession(httpServletRequest, httpServletResponse);
    cookieIdResolver.expireSession(httpServletRequest, httpServletResponse);
  }


  private String getRequestedSessionId(HttpServletRequest request) {

    String token = request.getParameter(QUERY_PARAM_AUTH_TOKEN);

    if (token == null) {
      List<String> ids = headerIdResolver.resolveSessionIds(request);
      token = ids.size() == 0 ? null : ids.get(0);
    }

    // if still not found try obtaining from cookie
    if (token == null) {
      List<String> ids = cookieIdResolver.resolveSessionIds(request);
      token = ids.size() == 0 ? null : ids.get(0);
    }

    return token;
  }

}
