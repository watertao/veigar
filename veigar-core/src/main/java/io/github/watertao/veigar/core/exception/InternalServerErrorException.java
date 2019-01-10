package io.github.watertao.veigar.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for internal server error (e.g. i/o exception)
 *
 * @author watertao
 */
public class InternalServerErrorException extends HttpStatusException {

  private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

  public InternalServerErrorException() {
    super.setStatus(STATUS);
  }

  public InternalServerErrorException(String message) {
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public InternalServerErrorException(String message, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public InternalServerErrorException(String messageTplt, Object[] args) {
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

  public InternalServerErrorException(String messageTplt, Object[] args, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

}
