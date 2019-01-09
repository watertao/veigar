package net.bandle.veigar.rsrv.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for accessing unauthenticated resources
 *
 * @author watertao
 */
public class ForbiddenException extends HttpStatusException {

  private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;

  public ForbiddenException() {
    super.setStatus(STATUS);
  }

  public ForbiddenException(String message) {
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public ForbiddenException(String message, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public ForbiddenException(String messageTplt, Object[] args) {
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

  public ForbiddenException(String messageTplt, Object[] args, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

}
