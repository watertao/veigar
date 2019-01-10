package io.github.watertao.veigar.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for modifying resource on an unexpected status
 *
 * @author watertao
 */
public class ConflictException extends HttpStatusException {

  private static final HttpStatus STATUS = HttpStatus.CONFLICT;

  public ConflictException() {
    super.setStatus(STATUS);
  }

  public ConflictException(String message) {
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public ConflictException(String message, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public ConflictException(String messageTplt, Object[] args) {
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

  public ConflictException(String messageTplt, Object[] args, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

}
