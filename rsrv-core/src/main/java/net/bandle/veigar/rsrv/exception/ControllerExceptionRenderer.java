package net.bandle.veigar.rsrv.exception;

import net.bandle.veigar.rsrv.message.LocaleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

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


  private ExceptionView handleMethodArgumentNotValidException(HttpServletResponse res, MethodArgumentNotValidException exception) {
    List<ObjectError> errors = exception.getBindingResult().getAllErrors();

    HttpStatus status = HttpStatus.BAD_REQUEST;
    res.setStatus(status.value());

    StringBuilder msgSb = new StringBuilder();
    msgSb.append("{").append(((DefaultMessageSourceResolvable) errors.get(0).getArguments()[0]).getDefaultMessage())
      .append("} ").append(errors.get(0).getDefaultMessage());


    return new ExceptionView(status, msgSb.toString());
  }


  private ExceptionView handleException(HttpServletResponse res, Throwable exception) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    res.setStatus(status.value());
    return new ExceptionView(status, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
  }

}
