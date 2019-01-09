package net.bandle.veigar.rsrv.session.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.util.StringUtils;

/**
 * Created by watertao on 3/26/16.
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
public class RedisHttpSessionConfig {

  private static final Logger logger = LoggerFactory.getLogger(RedisHttpSessionConfig.class);

  @Autowired
  private Environment env;

  @Autowired
  private RedisConnectionFactory connectionFactory;

  @Bean
  public RedisTemplate sessionRedisTemplate(RedisConnectionFactory connectionFactory) {

    RedisTemplate template = new RedisTemplate();
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());

    template.setConnectionFactory(connectionFactory);

    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    ObjectMapper om = new Jackson2ObjectMapperBuilder().build();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);

    template.setValueSerializer(jackson2JsonRedisSerializer);
    template.setHashValueSerializer(jackson2JsonRedisSerializer);

    return template;
  }


//  @Bean
//  public LettuceConnectionFactory redisConnectionFactory() {
//    String host = env.getProperty("spring.redis.host");
//    Integer port = env.getProperty("spring.redis.port", Integer.class);
//    if (StringUtils.isEmpty(host) || port == null) {
//      logger.warn("[rsrv-session] parameter 'session.redis.host' 'session.redis.port' could not be empty");
//      throw new BeanInitializationException("RedisConnectionFactory initialization failed");
//    }
//    return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
//  }

}
