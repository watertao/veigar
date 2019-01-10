package io.github.watertao.veigar.core.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @Autowired
  private ControllerExceptionRenderer exceptionRenderer;

  /**
   * Handler for processing Exceptions other than HttpStatusException
   *
   * @param req
   * @param res
   * @param exception
   */
  @ExceptionHandler(Throwable.class)
  public ExceptionView handleException(HttpServletRequest req, HttpServletResponse res, Throwable exception) {

    logger.warn(exception.getMessage(), exception);

    return exceptionRenderer.renderException(exception, res);

  }

}
