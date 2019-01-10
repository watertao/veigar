package io.github.watertao.veigar.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for resource not found.
 *
 * This exception always been thrown on a missing resource finding with specified identifier
 *
 * @author watertao
 */
public class NotFoundException extends HttpStatusException {

  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public NotFoundException() {
    super.setStatus(STATUS);
  }

  public NotFoundException(String message) {
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public NotFoundException(String message, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public NotFoundException(String messageTplt, Object[] args) {
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

  public NotFoundException(String messageTplt, Object[] args, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

}
