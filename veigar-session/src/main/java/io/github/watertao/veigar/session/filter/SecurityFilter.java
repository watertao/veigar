package io.github.watertao.veigar.session.filter;

import io.github.watertao.veigar.session.api.AuthObjHolder;
import io.github.watertao.veigar.session.spi.AuthenticationObject;
import io.github.watertao.veigar.session.spi.Resource;
import io.github.watertao.veigar.session.spi.SecurityHandler;
import io.github.watertao.veigar.core.util.HttpRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.filter.GenericFilterBean;
import io.github.watertao.veigar.core.exception.ForbiddenException;
import io.github.watertao.veigar.core.filter.FilterOrders;
import io.github.watertao.veigar.session.api.ResourceHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SecurityFilter extends GenericFilterBean implements Ordered {

  @Autowired
  private SecurityHandler securityHandler;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    // 判断当前 REQUEST  是否具有访问权限

    HttpServletRequest req = (HttpServletRequest) servletRequest;

    String uri = HttpRequestHelper.trimRequestContextPath(req);
    String verb = req.getMethod();

    Resource resource = securityHandler.identifyResource(verb, uri, AuthObjHolder.getAuthObj(req));

    // 若无法识别资源,则放行
    if (resource == null) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {

      ResourceHolder.setResource(req, resource);

      HttpSession session = req.getSession();
      if (session == null) {
        throw new ForbiddenException("未登陆");
      }
      AuthenticationObject authObj = AuthObjHolder.getAuthObj(req);
      if (authObj == null) {
        throw new ForbiddenException("未登录");
      }

      List<String> userAttrs = authObj.getAttributes();

      if (hasAttribute(userAttrs, resource.getAttributes())) {
        filterChain.doFilter(servletRequest, servletResponse);
      } else {
        throw new ForbiddenException("权限不足");
      }

    }

  }

  private boolean hasAttribute(List<String> userAttrs, List<String> resourceAttrs) {

    // if attributes of target resource is emtpty, then none one could access it
    if (resourceAttrs == null || resourceAttrs.size() == 0) {
      return false;
    }

    if (userAttrs == null || userAttrs.size() == 0) {
      return false;
    }

    boolean hasAttribute = false;

    for (String userAttr : userAttrs) {
      for (String resourceAttr : resourceAttrs) {
        if (userAttr != null && userAttr.equals(resourceAttr)) {
          hasAttribute = true;
          break;
        }
      }
    }

    return hasAttribute;

  }

  @Override
  public int getOrder() {
    return FilterOrders.SECURITY;
  }
}
