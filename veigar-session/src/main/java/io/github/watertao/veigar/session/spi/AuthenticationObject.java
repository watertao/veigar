package io.github.watertao.veigar.session.spi;

import java.util.List;

/**
 * Created by watertao on 3/26/16.
 */
public abstract class AuthenticationObject {

  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public abstract List<String> getAttributes();

}
