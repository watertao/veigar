package net.bandle.veigar.rsrv.exception;

import net.bandle.veigar.rsrv.exception.HttpStatusException;
import org.springframework.http.HttpStatus;

/**
 * Exception for authenticate failed.
 *
 * In HTTP Specification, 401 Unauthorized is used for authenticate failed.
 *
 * @author watertao
 */
public class UnauthenticatedException extends HttpStatusException {

  private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

  public UnauthenticatedException() {
    super.setStatus(STATUS);
  }

  public UnauthenticatedException(String message) {
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public UnauthenticatedException(String message, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMessage(message);
  }

  public UnauthenticatedException(String messageTplt, Object[] args) {
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

  public UnauthenticatedException(String messageTplt, Object[] args, Exception cause) {
    super(cause);
    super.setStatus(STATUS);
    super.setMsgTplt(messageTplt);
    super.setArgs(args);
  }

}
