package net.bandle.veigar.rsrv.session.config;

import net.bandle.veigar.rsrv.session.filter.LogoutFilter;
import net.bandle.veigar.rsrv.session.filter.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by watertao on 3/24/16.
 */
@Configuration
public class SecurityConfig {



  @Bean
  public SecurityFilter securityFilter() {
    return new SecurityFilter();
  }

  @Bean
  public LogoutFilter logoutFilter() {
    LogoutFilter logoutFilter = new LogoutFilter();
    return logoutFilter;
  }

}
