package io.github.watertao.veigar.session.api;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

/**
 * Created by watertao on 3/26/16.
 */
public class URIPatternMatcher {

  private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

  public static boolean matches(String pattern, String uri) {
    if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(uri)) {
      return false;
    }

    return antPathMatcher.match(pattern, uri);
  }

}
