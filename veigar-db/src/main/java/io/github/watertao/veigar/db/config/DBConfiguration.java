package io.github.watertao.veigar.db.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by watertao on 3/25/16.
 */
@Configuration
@EnableTransactionManagement
public class DBConfiguration {

  private static String MYBATIS_CONF_LOCATION = "classpath:mybatis/mybatis-config.xml";
  private static String MYBATIS_MAPPER_PATTERN = "classpath*:mybatis/mapper/**/*.xml";
  private static String APP_BASEPACKAGE_KEY = "app.basePackage";

  @Autowired
  private Environment env;

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource druidDataSource() {
    DruidDataSource datasource = new DruidDataSource();
    // default config
    datasource.setInitialSize(1);
    datasource.setMinIdle(0);
    datasource.setMaxActive(20);
    datasource.setMaxWait(6000l);
    datasource.setValidationQuery("SELECT 1");
    datasource.setTestOnBorrow(false);
    datasource.setTestOnReturn(false);
    datasource.setTestWhileIdle(true);
    datasource.setTimeBetweenEvictionRunsMillis(60000l);
    datasource.setMinEvictableIdleTimeMillis(25200000l);
    datasource.setRemoveAbandoned(true);
    datasource.setRemoveAbandonedTimeout(1800);
    datasource.setLogAbandoned(true);
    return datasource;
  }

  @Bean("sqlSessionFactory")
  public SqlSessionFactoryBean sqlSessionFactoryBean() {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    factoryBean.setConfigLocation(resolver.getResource(MYBATIS_CONF_LOCATION));
    factoryBean.setDataSource(druidDataSource());
    try {
      factoryBean.setMapperLocations(resolver.getResources(MYBATIS_MAPPER_PATTERN));
    } catch (IOException e) {
      throw new BeanInitializationException("Error during initializing mybatis", e);
    }
    factoryBean.setTypeAliasesPackage(env.getProperty(APP_BASEPACKAGE_KEY) + ".model");

    return factoryBean;
  }

}
