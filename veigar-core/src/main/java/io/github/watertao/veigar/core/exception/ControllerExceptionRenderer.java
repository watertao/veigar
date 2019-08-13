package io.github.watertao.veigar.core.exception;

import io.github.watertao.veigar.core.message.LocaleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class ControllerExceptionRenderer {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionRenderer.class);

    @Autowired
    private LocaleMessage localeMessage;

    public ExceptionView renderException(Throwable e, HttpServletResponse response) {
        if (e instanceof HttpStatusException) {
          return handleHttpStatusException(response, (HttpStatusException) e);
        } else if (e instanceof HttpMessageNotReadableException) {
          return handleHttpMessageNotReadableException(response, (HttpMessageNotReadableException) e);
        } else if (e instanceof MethodArgumentNotValidException) {
          return handleMethodArgumentNotValidException(response, (MethodArgumentNotValidException) e);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
          return handleMethodArgumentTypeMismatchException(response, (MethodArgumentTypeMismatchException) e);
        } else if (e instanceof MissingServletRequestParameterException) {
          return handleMissingServletRequestParameterException(response, (MissingServletRequestParameterException) e);
        } else {
          return handleException(response, e);
        }
    }

    private ExceptionView handleHttpStatusException(HttpServletResponse res, HttpStatusException exception) {
        res.setStatus(exception.getStatus().value());

        if (exception.getMsgTplt() != null) {
            try {
                String message = localeMessage.m(exception.getMsgTplt(), exception.getArgs());
                exception.setMessage(message);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                exception.setMessage(exception.getMsgTplt());
            }

        }

        return exception.toView();
    }


  private ExceptionView handleHttpMessageNotReadableException(HttpServletResponse res, HttpMessageNotReadableException exception) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    res.setStatus(status.value());

    return new ExceptionView(status, "message not readable");
  }

  private ExceptionView handleMissingServletRequestParameterException(HttpServletResponse res, MissingServletRequestParameterException exception) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    res.setStatus(status.value());

    return new ExceptionView(status, "missing query parameter [ " + exception.getParameterName() + " ]");
  }

  private ExceptionView handleMethodArgumentNotValidException(HttpServletResponse res, MethodArgumentNotValidException exception) {
    List<ObjectError> errors = exception.getBindingResult().getAllErrors();

    HttpStatus status = HttpStatus.BAD_REQUEST;
    res.setStatus(status.value());

    StringBuilder msgSb = new StringBuilder();
    msgSb.append("{").append(((DefaultMessageSourceResolvable) errors.get(0).getArguments()[0]).getDefaultMessage())
      .append("} ").append(errors.get(0).getDefaultMessage());


    return new ExceptionView(status, msgSb.toString());
  }

  private ExceptionView handleMethodArgumentTypeMismatchException(HttpServletResponse res, MethodArgumentTypeMismatchException exception) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    res.setStatus(status.value());
    return new ExceptionView(HttpStatus.BAD_REQUEST, "invalid parameter: " + exception.getName());
  }


  private ExceptionView handleException(HttpServletResponse res, Throwable exception) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    res.setStatus(status.value());
    return new ExceptionView(status, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
  }

}
