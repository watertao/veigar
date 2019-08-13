package io.github.watertao.veigar.core.aspect;

import io.github.watertao.veigar.core.exception.ControllerExceptionRenderer;
import io.github.watertao.veigar.core.reqlog.RequestLogger;
import io.github.watertao.veigar.core.util.HttpRequestHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;


@Aspect
@Component
@Order(PostProcessorOrders.AUDIT_LOG)
public class RequestAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestAspect.class);

    @Autowired(required = false)
    private List<RequestPostProcessor> requestPostProcessors;

    @Autowired
    private RequestLogger requestLogger;

    @Autowired
    private ControllerExceptionRenderer exceptionRenderer;

    @Around("controller() && allPubOp() && (reqAnno() || cReqAnno())")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
      Date requestTime = new Date();

      // log request
      try {
          HttpServletRequest req = HttpRequestHelper.getCurrentRequest();
          Object requestBody = retrieveRequestBody(joinPoint);
          requestLogger.preLog(req, requestBody);
      } catch (Throwable e) {
          logger.error("Error on logging request", e);
      }

      // execute
      Object result = null;
      Throwable exception = null;
      try {
          result = joinPoint.proceed();
      } catch (Throwable e) {
          HttpServletResponse res = HttpRequestHelper.getCurrentResponse();
          // result = exceptionRenderer.renderException(e, res);
          exception = e;
      }

      Long cost = System.currentTimeMillis() - requestTime.getTime();

      // log response
      try {
        requestLogger.postLog(result, exception, cost);
      } catch (Throwable e) {
        logger.error("Error on logging response", e);
      }

      // post processor
      if (requestPostProcessors != null && requestPostProcessors.size() > 0) {
        for (RequestPostProcessor processor : requestPostProcessors) {
          try {
            long postProcessStart = System.currentTimeMillis();
            processor.process(
              HttpRequestHelper.getCurrentRequest(),
              HttpRequestHelper.getCurrentResponse(),
              retrieveRequestBody(joinPoint),
              result,
              exception,
              requestTime,
              cost
            );
            logger.info("[ req post process ] {} ({})", processor.getClass().getSimpleName(), System.currentTimeMillis() - postProcessStart);
          } catch (Throwable e) {
            logger.error("Error on post process", e);
          }
        }
      }

      if (exception != null) {
        throw exception;
      }

      return result;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
    }

    @Pointcut("execution(public * *(..))")
    public void allPubOp() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void reqAnno() {
    }

    @Pointcut("getAnno() || postAnno() || putAnno() || deleteAnno() || patchAnno()")
    public void cReqAnno() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getAnno() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postAnno() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putAnno() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deleteAnno() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PatchMapping)")
    public void patchAnno() {
    }

    private Object retrieveRequestBody(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Integer idx = 0;
        Boolean isRequestBodyExist = false;
        GLOBAL:
        for (Annotation[] mAnnotations : parameterAnnotations) {
            for (Annotation annotation : mAnnotations) {
                if (annotation instanceof RequestBody) {
                    isRequestBodyExist = true;
                    break GLOBAL;
                }
            }
            idx++;
        }
        Object requestBody = null;
        if (isRequestBodyExist) {
            requestBody = joinPoint.getArgs()[idx];
        }
        return requestBody;
    }

}
