package io.github.watertao.veigar.core.filter;

import io.github.watertao.veigar.core.config.CorsConfigBean;
import io.github.watertao.veigar.core.util.HttpRequestHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class AbstractRequestAwareFilter extends GenericFilterBean implements Ordered {

  protected String verb;
  protected String uri;

  @Autowired
  private CorsConfigBean corsConfigBean;

  protected AbstractRequestAwareFilter(String verb, String uri) {
    this.verb = verb;
    this.uri = uri;
  }

  @Override
  public final void doFilter(
    ServletRequest servletRequest,
    ServletResponse servletResponse,
    FilterChain filterChain) throws IOException, ServletException {
    if (matches((HttpServletRequest) servletRequest)) {
      setCorrespondingStatusCode((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
      handle((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
      setCorsResponseHeader((HttpServletResponse) servletResponse);
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  protected abstract void handle(HttpServletRequest request, HttpServletResponse response);

  protected boolean matches(HttpServletRequest request) {
    return HttpRequestHelper.matches(request, this.verb, this.uri);
  }

  public void setVerb(String verb) {
    this.verb = verb;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  private void setCorrespondingStatusCode(HttpServletRequest request, HttpServletResponse response) {
    response.setStatus(HttpRequestHelper.resolveStatus(request.getMethod()).value());
  }

  private void setCorsResponseHeader(HttpServletResponse response) {
    response.setHeader("Access-Control-Allow-Origin", StringUtils.join(corsConfigBean.getAllowedOrigns(), ","));
    response.setHeader("Access-Control-Allow-Methods", StringUtils.join(corsConfigBean.getAllowedMethods(), ","));
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Max-Age", String.valueOf(corsConfigBean.getMaxAge()));
    response.setHeader("Access-Control-Allow-Headers", StringUtils.join(corsConfigBean.getAllowedHeaders(), ","));
    response.setHeader("Access-Control-Expose-Headers", StringUtils.join(corsConfigBean.getExposedHeaders(), ","));
  }

}
