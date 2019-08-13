package io.github.watertao.veigar.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.watertao.veigar.core.exception.BadRequestException;
import io.github.watertao.veigar.core.exception.InternalServerErrorException;
import io.github.watertao.veigar.core.util.HttpRequestHelper;
import org.hibernate.validator.HibernateValidator;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by watertao on 3/26/16.
 */
public abstract class AbstractJsonRequestAwareFilter extends AbstractRequestAwareFilter {

  private ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build();

  private static final Validator validator = Validation
    .byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();


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

      Set<ConstraintViolation<Object>> violations = validator.validate(requestBody);
      if (violations.size() > 0) {
        ConstraintViolation<Object> violation = violations.iterator().next();
        throw new BadRequestException("{" + violation.getPropertyPath() + "} " + violation.getMessage());
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
