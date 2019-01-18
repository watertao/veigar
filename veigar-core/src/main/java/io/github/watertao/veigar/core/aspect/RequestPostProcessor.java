package io.github.watertao.veigar.core.aspect;

import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestPostProcessor {

  /**
   *
   *
   * @param request
   * @param response
   * @param requestBody HTTP 请求体内容，若为 GET 等无请求体的请求，则为 null
   * @param responseBody 若请求执行失败，则为空
   * @param e 若不为空，则代表请求执行失败
   * @param cost 耗时，毫秒
   */
  void process(
    HttpServletRequest request,
    HttpServletResponse response,
    @Nullable Object requestBody,
    @Nullable Object responseBody,
    @Nullable Throwable e,
    Long cost);

}
