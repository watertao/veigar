package io.github.watertao.veigar.session;

import io.github.watertao.veigar.session.spi.AuthenticationObject;

import java.util.List;

/**
 * Created by watertao on 3/26/16.
 */
public class DefaultAuthenticationObject extends AuthenticationObject {

  private Integer userId;

  private String username;

  private List<String> attributes;

  public void setAttributes(List<String> attributes) {
    this.attributes = attributes;
  }

  @Override
  public List<String> getAttributes() {
    return this.attributes;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
