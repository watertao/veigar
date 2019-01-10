package io.github.watertao.veigar.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.watertao.veigar.core.exception.ControllerExceptionRenderer;
import io.github.watertao.veigar.core.exception.ExceptionView;
import io.github.watertao.veigar.core.message.LocaleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class ExceptionConversionFilter extends GenericFilterBean implements Ordered {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionConversionFilter.class);

  private static final String RESPONSE_CONTENT_TYPE = "application/json;charset=UTF-8";

  private final ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build();

  @Autowired
  private LocaleMessage localeMessage;

  @Autowired
  private ControllerExceptionRenderer exceptionRenderer;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    try {
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (Exception e) {
      logger.warn(e.getMessage(), e);
      try {
        ExceptionView view = exceptionRenderer.renderException(e, (HttpServletResponse) servletResponse);
        String json = mapper.writeValueAsString(view);
        servletResponse.setContentType(RESPONSE_CONTENT_TYPE);
        servletResponse.getWriter().write(json);
      } catch (Exception ex) {
        throw new RuntimeException(ex.getMessage());
      }

    }

  }


  @Override
  public int getOrder() {
    return FilterOrders.EXCEPTION_CONVERSION;
  }
}
