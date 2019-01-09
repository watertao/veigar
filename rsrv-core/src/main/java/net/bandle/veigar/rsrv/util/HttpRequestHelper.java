package net.bandle.veigar.rsrv.util;

import net.bandle.veigar.rsrv.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Created by watertao on 3/27/16.
 */
public class HttpRequestHelper {

  public static String retrieveRequestBodyAsString(HttpServletRequest request) {
    if (request.getContentLength() <= 0) {
      return null;
    }

    String entity;

    int contentLength = request.getContentLength();
    byte[] contentBytes = new byte[contentLength];
    try {
      InputStream inputStream = request.getInputStream();
      inputStream.read(contentBytes);
      entity = new String(contentBytes, "UTF-8");
    } catch (Exception e) {
      throw new BadRequestException(e.getMessage(), e);
    }

    return entity;
  }

  public static String trimRequestContextPath(HttpServletRequest request) {

    String uri = resolveUri(request);

    if ("".equals(request.getContextPath())) {
      return uri;
    } else {
      return uri.substring(0, request.getContextPath().length());
    }

  }

  public static boolean matches(HttpServletRequest request, String tMethod, String tUri) {
    // check whether URI satisfied
    boolean isUriSatisfied = false;
    String uri = resolveUri(request);

    if (request.getContextPath().equals("")) {
      isUriSatisfied = uri.endsWith(tUri);
    } else {
      isUriSatisfied = uri.endsWith(request.getContextPath() + tUri);
    }

    if (!isUriSatisfied) {
      return false;
    }

    // check whether method is POST
    if (!(request.getMethod().equals(tMethod))) {
      return false;
    }

    return true;
  }

  public static HttpServletRequest getCurrentRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }

  public static HttpServletResponse getCurrentResponse() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
  }

  public static HttpStatus resolveStatus(String method) {
    switch(method) {
      case "GET":
        return HttpStatus.OK;
      case "POST":
        return HttpStatus.CREATED;
      case "DELETE":
        return HttpStatus.NO_CONTENT;
      default:
        return HttpStatus.OK;
    }
  }

  private static String resolveUri(HttpServletRequest request) {
    String uri = request.getRequestURI();

    int pathParamIndex = uri.indexOf(';');

    if (pathParamIndex > 0) {
      // strip everything after the first semi-colon
      uri = uri.substring(0, pathParamIndex);
    }
    return uri;
  }

}
