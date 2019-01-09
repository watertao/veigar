package net.bandle.veigar.rsrv.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ExceptionView {

  private Integer status;

  private String error;

  private String message;

  private Map<String, Object> verbose;

  public ExceptionView() {}

  public ExceptionView(HttpStatus status, String message) {
    this.status = status.value();
    this.error = status.getReasonPhrase();
    this.message = message;
  }

  public ExceptionView(Integer status, String error, String message) {
    this.status = status;
    this.error = error;
    this.message = message;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Map<String, Object> getVerbose() {
    return verbose;
  }

  public void setVerbose(Map<String, Object> verbose) {
    this.verbose = verbose;
  }

}
