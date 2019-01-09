package net.bandle.veigar.rsrv.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for bad request.
 * This exception will be throwed by RSRV framework on:
 *  - json parsing error
 *  - bean validation error
 *
 * It can also be thrown by service implementation when you think there is a bad request
 *
 * @author watertao
 */
public class BadRequestException extends HttpStatusException {

  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public BadRequestException() {
    super.setStatus(STATUS);
  }

  public BadRequestException(String message) {
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public BadRequestException(String message, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public BadRequestException(String messageTplt, Object[] args) {
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

  public BadRequestException(String messageTplt, Object[] args, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

}
