package io.github.watertao.veigar.core.filter;

import io.github.watertao.veigar.core.util.HttpRequestHelper;
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

}
