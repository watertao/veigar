package net.bandle.veigar.rsrv.aspect;

import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestPostProcessor {

  void process(
    HttpServletRequest request,
    HttpServletResponse response,
    @Nullable Object requestBody,
    Object responseBody,
    @Nullable Throwable e,
    Long cost);

}
