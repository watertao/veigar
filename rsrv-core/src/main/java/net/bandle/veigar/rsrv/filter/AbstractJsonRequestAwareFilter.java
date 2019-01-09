package net.bandle.veigar.rsrv.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bandle.veigar.rsrv.exception.BadRequestException;
import net.bandle.veigar.rsrv.exception.InternalServerErrorException;
import net.bandle.veigar.rsrv.filter.AbstractRequestAwareFilter;
import net.bandle.veigar.rsrv.util.HttpRequestHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by watertao on 3/26/16.
 */
public abstract class AbstractJsonRequestAwareFilter extends AbstractRequestAwareFilter {

  private ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build();

  protected AbstractJsonRequestAwareFilter(String verb, String uri) {
    super(verb, uri);
  }

  @Override
  protected void handle(HttpServletRequest request, HttpServletResponse response) {

    String content = HttpRequestHelper.retrieveRequestBodyAsString(request);
    Object requestBody = null;
    if (!StringUtils.isEmpty(content)) {
      try {
        requestBody = mapper.readValue(content, getReqBindingClass());
      } catch (Exception e) {
        throw new BadRequestException(e.getMessage(), e);
      }
    }

    Object result = handleJson(request, response, requestBody);

    response.setContentType("application/json;charset=UTF-8");
    try {
      response.getWriter().write(mapper.writeValueAsString(result));
    } catch (IOException e) {
      throw new InternalServerErrorException(e.getMessage(), e);
    }

  }

  protected abstract Object handleJson(HttpServletRequest request, HttpServletResponse response, @Nullable Object requestBody);

  protected Class getReqBindingClass() {
    return HashMap.class;
  }

}
