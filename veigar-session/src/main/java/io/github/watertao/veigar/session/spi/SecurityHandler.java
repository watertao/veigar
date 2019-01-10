package io.github.watertao.veigar.session.spi;

import io.github.watertao.veigar.session.api.URIPatternMatcher;

/**
 * Created by watertao on 3/26/16.
 */
public interface SecurityHandler {

  /**
   * 根据 verb 和 uri 判断 该请求需要具备哪些 attributes 才可被访问
   * 这里 attribute 可以视作是角色或用户组等
   *
   * @see URIPatternMatcher
   *
   * @param verb
   * @param uri
   * @param authObj this could be null if user do not loged in
   * @return 返回资源,若为空,则说明此 verb uri 并不受保护,倘若返回的资源其属性列表为空则不予任何访问
     */
  Resource identifyResource(String verb, String uri, AuthenticationObject authObj);

}
