package net.bandle.veigar.rsrv.reqlog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.servlet.http.HttpServletRequest;

public class DefaultRequestLogger implements RequestLogger {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRequestLogger.class);

    private static final String PRELOG_PREFIX = "<--o";
    private static final String POSTLOG_PREFIX = "o-->";

    private String prelogPrefix;
    private String postlogPrefix;

    private ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();

    public DefaultRequestLogger() {
        this.prelogPrefix = PRELOG_PREFIX;
        this.postlogPrefix = POSTLOG_PREFIX;
    }

    public String getPrelogPrefix() {
        return prelogPrefix;
    }

    public void setPrelogPrefix(String prelogPrefix) {
        this.prelogPrefix = prelogPrefix;
    }

    public String getPostlogPrefix() {
        return postlogPrefix;
    }

    public void setPostlogPrefix(String postlogPrefix) {
        this.postlogPrefix = postlogPrefix;
    }

    @Override
    public void preLog(HttpServletRequest request, Object requestBody) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();


        logger.info("{} {} {}", this.prelogPrefix, method, uri + (queryString == null ? "" : "?" + queryString));
        if (requestBody != null) {
            String requestPayload = null;
            try {
                requestPayload = objectMapper.writeValueAsString(requestBody);
                logger.info("PAYLOAD: {}", requestPayload);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void postLog(Object result, Throwable exception, Long cost) {
        if (exception != null) {
            logger.error(exception.getMessage(), exception);
        }

        try {
            String responsePayload = null;
            if (result == null) {
                responsePayload = "<empty>";
            } else {
                responsePayload = objectMapper.writeValueAsString(result);
            }
            logger.info("{} COST: {}ms; PAYLOAD: {}", this.postlogPrefix, cost, responsePayload);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
