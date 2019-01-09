package net.bandle.veigar.rsrv.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * The base exception wrapper class.
 * structure:
 * <code>
 * {
 *    status: 400,
 *    error: 'Bad Request',
 *    message: 'user.name should not empty',
 *    verbose: {
 *      p1: 'xxx',
 *      p2: 'xxx'
 *    }
 * }
 * </code>
 *
 * @author watertao
 */
public class HttpStatusException extends RuntimeException {

  private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

  private String message;

  private Map verbose;

  /**
   * locale message key
   */
  private String msgTplt;

  /**
   * locale message placeholder value
   */
  private Object[] args;

  public HttpStatusException() {}

  public HttpStatusException(Exception cause) {
    super(cause);
  }

  public ExceptionView toView() {
    ExceptionView view = new ExceptionView();
    view.setStatus(this.status.value());
    view.setError(this.status.getReasonPhrase());
    view.setMessage(this.message);

    if (this.verbose != null) {
      view.setVerbose(this.verbose);
    }

    return view;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public Map getVerbose() {
    return verbose;
  }

  public void setVerbose(Map verbose) {
    this.verbose = verbose;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMsgTplt() {
    return msgTplt;
  }

  public void setMsgTplt(String msgTplt) {
    this.msgTplt = msgTplt;
  }

  public Object[] getArgs() {
    return args;
  }

  public void setArgs(Object[] args) {
    this.args = args;
  }
}
